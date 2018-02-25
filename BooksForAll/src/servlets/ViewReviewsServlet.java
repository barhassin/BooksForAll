package servlets;

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
import classes.Purchase;
import classes.Review;

/**
 * Servlet implementation class ViewReviewsServlet
 */
@WebServlet("/ViewReviewsServlet")
public class ViewReviewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewReviewsServlet() {
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
			PreparedStatement stmt;
			Collection<Review> reviews = new ArrayList<Review>();
			try {
				stmt = conn.prepareStatement(AppConstants.SELECT_REVIEWS_BY_APPROVE_STMT);
				stmt.setString(1, "no");
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					reviews.add(new Review(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				getServletContext().log("Error while querying for customers", e);
				response.sendError(500);// internal server error
			}
			conn.close();
			Gson gson = new Gson();
			String booksJsonResult = gson.toJson(reviews, AppConstants.REVIEW_COLLECTION);
			response.addHeader("Content-Type", "application/json");
			PrintWriter writer = response.getWriter();
			writer.println(booksJsonResult);
			writer.close();
		} catch (SQLException | NamingException e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);// internal server error
		}
	}

}
