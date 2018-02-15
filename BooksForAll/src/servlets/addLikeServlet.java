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

/**
 * Servlet implementation class browseBooksServlet
 */
@WebServlet("/addLikeServlet")
public class addLikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addLikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(
    			getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
    		Connection conn = ds.getConnection();
    		BufferedReader reader = request.getReader();
    	    StringBuilder sb = new StringBuilder();
    	    String line = reader.readLine();
    	    while (line != null) {
    	      sb.append(line + "\n");
    	      line = reader.readLine();
    	    }
    	    reader.close();
    	    String params = sb.toString();//book name + user name
    		PreparedStatement stmt;
    		Gson gson = new Gson();
    		Like like = gson.fromJson(params, Like.class);
    			try {
    				stmt = conn.prepareStatement(AppConstants.INSERT_LIKES_STMT);
    				stmt.setString(1,like.getBookname());
    				stmt.setString(2, like.getUsername());
    				stmt.executeUpdate();
    				stmt.close();
    				
    			} catch (SQLException e) {
    				getServletContext().log("Error while querying for insert like", e);
    	    		response.sendError(500);//internal server error
    			}

    			
    		conn.close();
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(like.getUsername()+"do like to"+like.getBookname());
        	writer.close();
		} catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		response.sendError(500);//internal server error
    	}
	}
	

}