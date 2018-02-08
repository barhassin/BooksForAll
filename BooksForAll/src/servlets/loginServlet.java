package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

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

import classes.loginValidator;
import classes.AppConstants;
import classes.User;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet(urlPatterns = { "/login"})
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
try {
        	//obtain CustomerDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(
    				getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
    		Connection conn = ds.getConnection();

    		Collection<User> usersResult = new ArrayList<User>(); 
    		String uri = request.getRequestURI();
    		if (uri.indexOf(AppConstants.USERNAME) != -1){//filter customer by specific name
    			String name = uri.substring(uri.indexOf(AppConstants.USERNAME) + AppConstants.USERNAME.length() + 1);
    			PreparedStatement stmt;
    			try {
    				stmt = conn.prepareStatement(AppConstants.SELECT_USER_BY_USERNAME_STMT);
    				name = name.replaceAll("\\%20", " ");
    				stmt.setString(1, name);
    				ResultSet rs = stmt.executeQuery();
    				while (rs.next()){
    					usersResult.add(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
    				}
    				rs.close();
    				stmt.close();
    			} catch (SQLException e) {
    				getServletContext().log("Error while querying for customers", e);
    	    		response.sendError(500);//internal server error
    			}
    		}else{
    			Statement stmt;
    			try {
    				stmt = conn.createStatement();
    				ResultSet rs = stmt.executeQuery(AppConstants.SELECT_ALL_USERS_STMT);
    				while (rs.next()){
    					usersResult.add(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
    				}
    				rs.close();
    				stmt.close();
    			} catch (SQLException e) {
    				getServletContext().log("Error while querying for customers", e);
    	    		response.sendError(500);//internal server error
    			}

    		}

    		conn.close();
    		
    		Gson gson = new Gson();
        	//convert from customers collection to json
        	String userJsonResult = gson.toJson(usersResult, AppConstants.USER_COLLECTION);
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(userJsonResult);
        	writer.close();
    	} catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		response.sendError(500);//internal server error
    	}

    	
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
    		Gson gson = new Gson();
    		User user = gson.fromJson(reader, User.class);
    		String name = user.getUsername();
    		PreparedStatement stmt;
    			try {
    				stmt = conn.prepareStatement(AppConstants.SELECT_USER_BY_USERNAME_STMT);
    				name = name.replaceAll("\\%20", " ");
    				stmt.setString(1, name);
    				ResultSet rs = stmt.executeQuery();
    				String dbPassword="";
    				while (rs.next()){
    					dbPassword=rs.getString(2);
    				}
    				if(user.getPassword()!=dbPassword)
    					response.sendError(500);
    				rs.close();
    				stmt.close();
    			} catch (SQLException e) {
    				getServletContext().log("Error while querying for customers", e);
    	    		response.sendError(500);//internal server error
    			}
    		conn.close();
    		
    		/*Gson gson2 = new Gson();
        	//convert from customers collection to json
        	String userJsonResult = gson.toJson(usersResult, AppConstants.USER_COLLECTION);
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(userJsonResult);
        	writer.close();*/
		} catch (SQLException | NamingException e) {
    		getServletContext().log("Error while closing connection", e);
    		response.sendError(500);//internal server error
    	}
	}

}
