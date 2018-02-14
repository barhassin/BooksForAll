package classes;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

public interface AppConstants {
		public final String USERS = "users";
		public final String USERINFO = "users";
		public final String BOOKS = "books";
		public final String USERS_FILE = USERS + ".json";
		public final String USERINFO_FILE = USERINFO + ".json";
		public final String BOOKS_FILE = BOOKS + ".json";
		public final String USERNAME = "username";
		public final Type BOOK_COLLECTION = new TypeToken<Collection<Book>>() {}.getType();
		//derby constants
		public final String DB_NAME = "DB_NAME";
		public final String DB_DATASOURCE = "DB_DATASOURCE";
		public final String PROTOCOL = "jdbc:derby:"; 
		public final String OPEN = "Open";
		public final String SHUTDOWN = "Shutdown";
		
		//sql statements
		public final String CREATE_USERS_TABLE = "CREATE TABLE USERS(username varchar(100),"
				+ "password varchar(100)," + "type varchar(100))";
		public final String CREATE_USERINFO_TABLE = "CREATE TABLE USERINFO(username varchar(100),"
				+ "email varchar(100)," + "street varchar(100),"+ "streetnumber varchar(100)," + "city varchar(100),"+ "zipcode varchar(100)," + "telephone varchar(100),"+ "nickname varchar(100)," + "description varchar(100)," + "photo varchar(100))";
		public final String INSERT_USER_STMT = "INSERT INTO USERS VALUES(?,?,?)";
		public final String INSERT_USERINFO_STMT = "INSERT INTO USERINFO VALUES(?,?,?,?,?,?,?,?,?,?)";
		public final String SELECT_ALL_USERS_STMT = "SELECT * FROM USERS";
		public final String SELECT_USER_BY_USERNAME_STMT = "SELECT * FROM USERS "
				+ "WHERE username=?";
		public final String CREATE_BOOKS_TABLE = "CREATE TABLE BOOKS(name varchar(100),"
				+ "image varchar(100), description varchar(100), price varchar(100))";
		public final String SELECT_ALL_BOOKS_STMT = "SELECT * FROM BOOKS";
		public final String INSERT_BOOK_STMT = "INSERT INTO BOOKS VALUES(?,?,?,?)";
		public final String SELECT_BOOKS_BY_USERNAME_STMT = "SELECT * FROM USERS "
				+ "WHERE username=?";
}
