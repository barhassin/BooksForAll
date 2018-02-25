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

import classes.AppConstants;
import classes.Book;
import classes.Purchase;
import classes.User;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class myBooksServlet
 */
@WebServlet("/myBooksServlet")
public class myBooksServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public myBooksServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
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
			String params = sb.toString();
			Gson gson = new Gson();
			User user = gson.fromJson(params, User.class);
			Collection<Purchase> purchasesList = new ArrayList<Purchase>();
			Collection<Book> bookList = new ArrayList<Book>();
			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(AppConstants.SELECT_PURCHASES_BY_USERNAME_STMT);
				stmt.setString(1, user.getUsername());
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					purchasesList.add(new Purchase(rs.getString(1), rs.getString(2), rs.getString(3)));
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				getServletContext().log("Error while querying for customers", e);
				response.sendError(500);// internal server error
			}
			for (Purchase purchase : purchasesList) {
				try {
					stmt = conn.prepareStatement(AppConstants.SELECT_BOOKS_BY_NAME_STMT);
					stmt.setString(1, purchase.getBookname());
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
					}
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					getServletContext().log("Error while querying for customers", e);
					response.sendError(500);// internal server error
				}
			}
			conn.close();
			Gson gson2 = new Gson();
			String userJsonResult = gson2.toJson(bookList, AppConstants.BOOK_COLLECTION);
			response.addHeader("Content-Type", "application/json");
			PrintWriter writer = response.getWriter();
			writer.println(userJsonResult);
			writer.close();
		} catch (SQLException | NamingException e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);// internal server error
		}
	}

}
