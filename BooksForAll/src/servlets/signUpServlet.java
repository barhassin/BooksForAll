package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import classes.AppConstants;
import classes.User;
import classes.UserInfo;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class signUpServlet
 */
@WebServlet(urlPatterns = { "/signUp" })
public class signUpServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public signUpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException Signals that a servlet exception has occurred.
     * @throws IOException Signals that an I/O exception has occurred.
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException Signals that a servlet exception has occurred.
     * @throws IOException Signals that an I/O exception has occurred.
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			Connection conn = ds.getConnection();
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line + "\n");
				line = reader.readLine();
			}
			reader.close();
			String params = sb.toString(); // UserInfo object in json form
			Gson gson = new Gson();
			UserInfo user_info = gson.fromJson(params, UserInfo.class);
			
			if (!ValidateUser(user_info)) 
			{
			    response.sendError(433);
			    return;
			}
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(AppConstants.SELECT_USERINFO_BY_USERNAME_STMT);
				stmt.setString(1, user_info.getUsername());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					if (rs.getString(1).equals(user_info.getUsername())) {
						response.sendError(410);
					}
				} else {
					PreparedStatement xstmt = conn.prepareStatement(AppConstants.SELECT_USERINFO_BY_NICKNAME_STMT);
					xstmt.setString(1, user_info.getNickname());
					ResultSet xrs = xstmt.executeQuery();
					if (xrs.next()) {
						if (xrs.getString(8).equals(user_info.getNickname())) {
							response.sendError(411);
						}
					} else {
						PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_USERINFO_STMT);
						pstmt.setString(1, user_info.getUsername());
						pstmt.setString(2, user_info.getEmail());
						pstmt.setString(3, user_info.getStreet());
						pstmt.setString(4, user_info.getStreetNumber());
						pstmt.setString(5, user_info.getCity());
						pstmt.setString(6, user_info.getZipcode());
						pstmt.setString(7, user_info.getTelephone());
						pstmt.setString(8, user_info.getNickname());
						pstmt.setString(9, user_info.getDescription());
						pstmt.setString(10, user_info.getPhoto());
						pstmt.executeUpdate();
						PreparedStatement ustmt = conn.prepareStatement(AppConstants.INSERT_USER_STMT);
						ustmt.setString(1, user_info.getUsername());
						ustmt.setString(2, user_info.getPassword());
						ustmt.setString(3, "user");
						ustmt.executeUpdate();
						// commit update
						conn.commit();
						// close statements
						pstmt.close();
						ustmt.close();
					}

				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				getServletContext().log("Error while querying for customers", e);
				response.sendError(500);// internal server error
			}
			conn.close();

			Gson gson2 = new Gson();
			User user = new User(user_info.getUsername(), "", "user");
			// convert from customers collection to json
			String userJsonResult = gson2.toJson(user);
			response.addHeader("Content-Type", "application/json");
			PrintWriter writer = response.getWriter();
			writer.println(userJsonResult);
			writer.close();
		} catch (SQLException | NamingException e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);// internal server error
		}
	}
	
	 /**
 	 * Validates the input given by the user during signup.
 	 *
 	 * @param user the user
 	 * @return true, if successful
 	 */
 	private boolean ValidateUser(UserInfo user)
	    {
		if (user == null || user.getUsername() == null || user.getEmail() == null
			|| user.getStreet() == null || user.getStreetNumber() == null || user.getCity() == null
			|| user.getZipcode() == null || user.getNickname() == null || user.getTelephone() == null
			|| user.getPassword() == null || !Pattern.matches("^[a-zA-Z1-9]{1,10}$", user.getUsername())
			|| !Pattern.matches("^[a-zA-Z_ ]{3,100}$", user.getStreet())
			|| !Pattern.matches("^[1-9][0-9]*$", user.getStreetNumber())
			|| !Pattern.matches("^[a-zA-Z_ ]{3,100}$", user.getCity())
			|| !Pattern.matches("^[0-9]{7}$", user.getZipcode())
			|| !Pattern.matches("^([0][5]\\d{1}[- ]?\\d{3}[- ]?\\d{4}|[0][2,3,4,8,9][- ]?\\d{7})$",
				user.getTelephone())
			|| user.getPassword().length() > 8 || user.getNickname().length() == 0)
		{  
		    return false;
		}
			return true;
	    }
}
