package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import classes.Like;
import classes.Review;

// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class browseBooksServlet
 */
@WebServlet("/AddReviewsServlet")
public class AddReviewsServlet extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddReviewsServlet() {
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
			String params = sb.toString(); // Review object in json form
			PreparedStatement stmt;
			Gson gson = new Gson();
			Review review = gson.fromJson(params, Review.class);
			try {
				stmt = conn.prepareStatement(AppConstants.INSERT_REVIEWS_STMT);
				stmt.setString(1, review.getBookname());
				stmt.setString(2, review.getNickname());
				stmt.setString(3, review.getReview());
				stmt.setString(4, review.getApproved());
				stmt.executeUpdate();
				stmt.close();
			} catch (SQLException e) {
				getServletContext().log("Error while querying for insert review", e);
				response.sendError(500);// internal server error
			}
			conn.close();
		} catch (SQLException | NamingException e) {
			getServletContext().log("Error while closing connection", e);
			response.sendError(500);// internal server error
		}
	}

}