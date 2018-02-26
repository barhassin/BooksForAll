package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.crypto.provider.RSACipher;

import classes.AppConstants;
import classes.Book;
import classes.Like;
import classes.User;
import classes.UserInfo;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class browseBooksServlet
 */
@WebServlet("/browseBooksLikesServlet")
public class browseBooksLikesServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public browseBooksLikesServlet() {
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
			String params = sb.toString(); // Book object in json form
			PreparedStatement stmt;
			PreparedStatement ptmt;
			ResultSet rs = null;
			Gson gson2 = new Gson();
			Book book = gson2.fromJson(params, Book.class);
			Collection<User> userList = new ArrayList<User>();
			Collection<UserInfo> userInfoList = new ArrayList<UserInfo>();
			try {
				stmt = conn.prepareStatement(AppConstants.SELECT_LIKES_BY_BOOKNAME_STMT);
				stmt.setString(1, book.getName());
				rs = stmt.executeQuery();
				while (rs.next()) {
					userList.add(new User(rs.getString(2), "", ""));// user name
				}
				stmt.close();
			} catch (SQLException e) {
				getServletContext().log("Error while querying for books", e);
				response.sendError(500);// internal server error
			}
			try {
				ptmt = conn.prepareStatement(AppConstants.SELECT_USERINFO_BY_USERNAME_STMT);
				for (User user : userList) {
					ptmt.setString(1, user.getUsername());
					rs = ptmt.executeQuery();
					if (rs.next()) {
						userInfoList.add(
								new UserInfo(rs.getString(1), "", "", "", "", "", "", "", "", rs.getString(8), "", "")); // username and nickname																													
					}
				}
				rs.close();
				ptmt.close();
			} catch (SQLException e) {
				getServletContext().log("Error while querying for books", e);
				response.sendError(500);// internal server error
			}
			conn.close();
			Gson gson = new Gson();
			String LikesJsonResult = gson.toJson(userInfoList, new TypeToken<Collection<UserInfo>>() {
			}.getType());
			response.addHeader("Content-Type", "application/json");
			PrintWriter writer = response.getWriter();
			writer.println(LikesJsonResult);
			writer.close();
		} catch (SQLException | NamingException e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);// internal server error
		}
	}
}
