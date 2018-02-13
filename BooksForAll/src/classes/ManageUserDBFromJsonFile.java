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
    			//create Customers table
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
    			created = tableAlreadyExists(e);
    			if (!created){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		
    		//if no database exist in the past - further populate its records in the table
    		if (!created){
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
}
