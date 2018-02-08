package classes;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

public interface AppConstants {
		public final String USERS = "users";
		public final String USERS_FILE = USERS + ".json";
		public final String USERNAME = "username";
		public final Type USER_COLLECTION = new TypeToken<Collection<User>>() {}.getType();
		//derby constants
		public final String DB_NAME = "DB_NAME";
		public final String DB_DATASOURCE = "DB_DATASOURCE";
		public final String PROTOCOL = "jdbc:derby:"; 
		public final String OPEN = "Open";
		public final String SHUTDOWN = "Shutdown";
		
		//sql statements
		public final String CREATE_USERS_TABLE = "CREATE TABLE USERS(Username varchar(100),"
				+ "Password varchar(100)" + "Type varchar(100)";
		public final String INSERT_USER_STMT = "INSERT INTO USERS VALUES(?,?,?)";
		public final String SELECT_ALL_USERS_STMT = "SELECT * FROM USERS";
		public final String SELECT_USER_BY_USERNAME_STMT = "SELECT * FROM USERS "
				+ "WHERE Username=?";
}
