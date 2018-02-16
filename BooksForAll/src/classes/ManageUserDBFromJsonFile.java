package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ManageUserDBFromJsonFile implements ServletContextListener {
	public ManageUserDBFromJsonFile() {
        // TODO Auto-generated constructor stub
    }
	
	//utility that checks whether the customer tables already exists
    private boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }

    public void contextInitialized(ServletContextEvent event)  { 
    	ServletContext cntx = event.getServletContext();
    	
    	try{
    		
    		//obtain CustomerDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(
    				cntx.getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
    		Connection conn = ds.getConnection();
    		
    		boolean created = false;
    		try{
    			//create Users table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_USERS_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created = tableAlreadyExists(e);
    			if (!created){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		
    		//if no database exist in the past - further populate its records in the table
    		if (!created){
    			//populate customers table with customer data from json file
    			Collection<User> users = loadUser(cntx.getResourceAsStream(File.separator +
    														   AppConstants.USERS_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_USER_STMT);
    			for (User user : users){
    				pstmt.setString(1,user.getUsername());
    				pstmt.setString(2,user.getPassword());
    				pstmt.setString(3,user.getType());
    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}
    		
    		
    		boolean created2 = false;
    		try{
    			//create Customers table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_USERINFO_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created2 = tableAlreadyExists(e);
    			if (!created2){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		
    		//if no database exist in the past - further populate its records in the table
    		if (!created2){
    			//populate customers table with customer data from json file
    			Collection<UserInfo> users = loadUserInfo(cntx.getResourceAsStream(File.separator +
    														   AppConstants.USERINFO_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_USERINFO_STMT);
    			for (UserInfo user : users){
	    				pstmt.setString(1,user.getUsername());
	    				pstmt.setString(2,user.getEmail());
	    				pstmt.setString(3,user.getStreet());
	    				pstmt.setString(4,user.getStreetNumber());
	    				pstmt.setString(5,user.getCity());
	    				pstmt.setString(6,user.getZipcode());
	    				pstmt.setString(7,user.getTelephone());
	    				pstmt.setString(8,user.getNickname());
	    				pstmt.setString(9,user.getDescription());
	    				pstmt.setString(10, user.getPhoto());
	    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}
    		
    		boolean created3 = false;
    		try{
    			//create Users table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_BOOKS_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created3 = tableAlreadyExists(e);
    			if (!created3){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		//if no database exist in the past - further populate its records in the table
    		if (!created3){
    			//populate customers table with customer data from json file
    			Collection<Book> books = loadBook(cntx.getResourceAsStream(File.separator +
    														   AppConstants.BOOKS_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_BOOK_STMT);
    			for (Book book : books){
    				pstmt.setString(1,book.getName());
    				pstmt.setString(2,book.getImage());
    				pstmt.setString(3,book.getDescription());
    				pstmt.setString(4,book.getPrice());
    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}
    		
    		boolean created4 = false;
    		try{
    			//create Users table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_LIKES_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created4 = tableAlreadyExists(e);
    			if (!created4){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		//if no database exist in the past - further populate its records in the table
    		if (!created4){
    			//populate customers table with customer data from json file
    			Collection<Like> likes = loadLike(cntx.getResourceAsStream(File.separator +
    														   AppConstants.LIKES_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_LIKES_STMT);
    			for (Like like : likes){
    				pstmt.setString(1, like.getBookname());
    				pstmt.setString(2,like.getUsername());
    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}
    		boolean created5 = false;
    		try{
    			//create Users table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_REVIEWS_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created5 = tableAlreadyExists(e);
    			if (!created5){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		//if no database exist in the past - further populate its records in the table
    		if (!created5){
    			//populate customers table with customer data from json file
    			Collection<Review> reviews = loadreview(cntx.getResourceAsStream(File.separator +
    														   AppConstants.REVIEWS_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_REVIEWS_STMT);
    			for (Review review : reviews){
    				pstmt.setString(1,review.getBookname());
    				pstmt.setString(2,review.getNickname());
    				pstmt.setString(3,review.getReview());
    				pstmt.setString(4,review.getApproved());
    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}
    		
    		boolean created6 = false;
    		try{
    			//create Users table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_PURCHASES_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created6 = tableAlreadyExists(e);
    			if (!created6){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		//if no database exist in the past - further populate its records in the table
    		if (!created6){
    			//populate customers table with customer data from json file
    			Collection<Purchase> purchases = loadpurchase(cntx.getResourceAsStream(File.separator +
    														   AppConstants.PURCHASES_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_PURCHASES_STMT);
    			for (Purchase purchase : purchases){
    				pstmt.setString(1,purchase.getUsername());
    				pstmt.setString(2,purchase.getBookname());
    				pstmt.setString(3,purchase.getPrice());
    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}

    		//close connection
    		conn.close();

    	} catch (IOException | SQLException | NamingException e) {
    		//log error 
    		cntx.log("Error during database initialization",e);
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    	 ServletContext cntx = event.getServletContext();
    	 
         //shut down database
    	 try {
     		//obtain CustomerDB data source from Tomcat's context and shutdown
     		Context context = new InitialContext();
     		BasicDataSource ds = (BasicDataSource)context.lookup(
     				cntx.getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.SHUTDOWN);
     		ds.getConnection();
     		ds = null;
		} catch (SQLException | NamingException e) {
			cntx.log("Error shutting down database",e);
		}

    }
    
    
    /**
	 * Loads customers data from json file that is read from the input stream into 
	 * a collection of Customer objects
	 * @param is input stream to json file
	 * @return collection of customers
	 * @throws IOException
	 */
	private Collection<User> loadUser(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}


		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<User>>(){}.getType();
		Collection<User> users = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return users;

	}
	private Collection<UserInfo> loadUserInfo(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<UserInfo>>(){}.getType();
		Collection<UserInfo> users = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return users;
	
	}
	private Collection<Book> loadBook(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}


		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Book>>(){}.getType();
		Collection<Book> books = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return books;

	}
	private Collection<Like> loadLike(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}


		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Like>>(){}.getType();
		Collection<Like> likes = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return likes;

	}
	private Collection<Review> loadreview(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}


		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Review>>(){}.getType();
		Collection<Review> reviews = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return reviews;

	}
private Collection<Purchase> loadpurchase(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}


		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Purchase>>(){}.getType();
		Collection<Purchase> purchases = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return purchases;

	}
}
