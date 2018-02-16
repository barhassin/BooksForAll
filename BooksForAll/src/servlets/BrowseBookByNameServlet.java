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

import classes.AppConstants;
import classes.Book;

/**
 * Servlet implementation class browseBooksServlet
 */
@WebServlet("/BrowseBookByNameServlet")
public class BrowseBookByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseBookByNameServlet() {
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
    	    String params = sb.toString();//book obj
    	    Gson gson2 = new Gson();
    		Book book = gson2.fromJson(params,Book.class);
    		PreparedStatement stmt;
    		
    			try {
    				stmt = conn.prepareStatement(AppConstants.SELECT_BOOKS_BY_NAME_STMT);
    				stmt.setString(1, book.getName());
    				ResultSet rs = stmt.executeQuery();
    				if(rs.next()) {
    					 book= new Book(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
    					
    				}	
    				rs.close();
    				stmt.close();
    			} catch (SQLException e) {
    				getServletContext().log("Error while querying for book", e);
    	    		response.sendError(500);//internal server error
    			}
    		conn.close();
    		Gson gson = new Gson();
    		String bookJsonResult = gson.toJson(book,new TypeToken<Book>() {}.getType());
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(bookJsonResult);
        	writer.close();
		} catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		response.sendError(500);//internal server error
    	}
	}
	

}
