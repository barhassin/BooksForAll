package classes;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * The Interface AppConstants.
 */
public interface AppConstants {
		
		/** The users. */
		public final String USERS = "users";
		
		/** The userinfo. */
		public final String USERINFO = "userinfo";
		
		/** The books. */
		public final String BOOKS = "books";
		
		/** The likes. */
		public final String LIKES = "likes";
		
		/** The reviews. */
		public final String REVIEWS = "reviews";
		
		/** The purchases. */
		public final String PURCHASES = "purchases";
		
		/** The locations. */
		public final String LOCATIONS = "locations";
		
		/** The users file. */
		public final String USERS_FILE = "json/" + USERS + ".json";
		
		/** The userinfo file. */
		public final String USERINFO_FILE = "json/" + USERINFO + ".json";
		
		/** The books file. */
		public final String BOOKS_FILE = "json/" + BOOKS + ".json";
		
		/** The likes file. */
		public final String LIKES_FILE = "json/" + LIKES + ".json";
		
		/** The reviews file. */
		public final String REVIEWS_FILE = "json/" + REVIEWS + ".json";
		
		/** The purchases file. */
		public final String PURCHASES_FILE = "json/" + PURCHASES + ".json";
		
		/** The locations file. */
		public final String LOCATIONS_FILE = "json/" + LOCATIONS + ".json";
		
		/** The username. */
		public final String USERNAME = "username";
		
		/** The book collection. */
		public final Type BOOK_COLLECTION = new TypeToken<Collection<Book>>() {}.getType();
		
		/** The purchase collection. */
		public final Type PURCHASE_COLLECTION = new TypeToken<Collection<Purchase>>() {}.getType();
		
		/** The userinfo collection. */
		public final Type USERINFO_COLLECTION = new TypeToken<Collection<UserInfo>>() {}.getType();
		
		/** The review collection. */
		public final Type REVIEW_COLLECTION = new TypeToken<Collection<Review>>() {}.getType();
		
		/** The db name. */
		//derby constants
		public final String DB_NAME = "DB_NAME";
		
		/** The db datasource. */
		public final String DB_DATASOURCE = "DB_DATASOURCE";
		
		/** The protocol. */
		public final String PROTOCOL = "jdbc:derby:"; 
		
		/** The open. */
		public final String OPEN = "Open";
		
		/** The shutdown. */
		public final String SHUTDOWN = "Shutdown";
		
		/** The create users table. */
		//sql statements
		public final String CREATE_USERS_TABLE = "CREATE TABLE USERS(username varchar(100),"
				+ "password varchar(100)," + "type varchar(100))";
		
		/** The insert user stmt. */
		public final String INSERT_USER_STMT = "INSERT INTO USERS VALUES(?,?,?)";
		
		/** The select all users stmt. */
		public final String SELECT_ALL_USERS_STMT = "SELECT * FROM USERS";
		
		/** The select user by username stmt. */
		public final String SELECT_USER_BY_USERNAME_STMT = "SELECT * FROM USERS "
				+ "WHERE username=?";
		
		/** The delete user by username stmt. */
		public final String DELETE_USER_BY_USERNAME_STMT = "DELETE FROM USERS WHERE username=?";
		
		
		/** The create userinfo table. */
		public final String CREATE_USERINFO_TABLE = "CREATE TABLE USERINFO(username varchar(100),"
				+ "email varchar(100)," + "street varchar(100),"+ "streetnumber varchar(100)," + "city varchar(100),"+ "zipcode varchar(100)," + "telephone varchar(100),"+ "nickname varchar(100)," + "description varchar(100)," + "photo varchar(100))";
		
		/** The insert userinfo stmt. */
		public final String INSERT_USERINFO_STMT = "INSERT INTO USERINFO VALUES(?,?,?,?,?,?,?,?,?,?)";
		
		/** The select userinfo by username stmt. */
		public final String SELECT_USERINFO_BY_USERNAME_STMT = "SELECT * FROM USERINFO "
				+ "WHERE username=?";
		
		/** The select userinfo by nickname stmt. */
		public final String SELECT_USERINFO_BY_NICKNAME_STMT = "SELECT * FROM USERINFO "
				+ "WHERE nickname=?";
		
		/** The select all userinfo stmt. */
		public final String SELECT_ALL_USERINFO_STMT = "SELECT * FROM USERINFO";
		
		/** The delete userinfo by username stmt. */
		public final String DELETE_USERINFO_BY_USERNAME_STMT = "DELETE FROM USERINFO WHERE username=?";
		
		
		/** The create books table. */
		public final String CREATE_BOOKS_TABLE = "CREATE TABLE BOOKS(name varchar(100),"
				+ "image varchar(100), description varchar(1000), price varchar(100))";
		
		/** The insert book stmt. */
		public final String INSERT_BOOK_STMT = "INSERT INTO BOOKS VALUES(?,?,?,?)";
		
		/** The select all books stmt. */
		public final String SELECT_ALL_BOOKS_STMT = "SELECT * FROM BOOKS";
		
		/** The select books by name stmt. */
		public final String SELECT_BOOKS_BY_NAME_STMT = "SELECT * FROM BOOKS "
				+ "WHERE name=?";
		
		
		/** The create likes table. */
		public final String CREATE_LIKES_TABLE = "CREATE TABLE LIKES(bookname varchar(100),"
				+ "username varchar(100))";
		
		/** The insert likes stmt. */
		public final String INSERT_LIKES_STMT = "INSERT INTO LIKES VALUES(?,?)";
		
		/** The select all likes stmt. */
		public final String SELECT_ALL_LIKES_STMT = "SELECT * FROM LIKES";
		
		/** The select likes by bookname and username stmt. */
		public final String SELECT_LIKES_BY_BOOKNAME_AND_USERNAME_STMT = "SELECT * FROM LIKES WHERE bookname=? AND username=?";
		
		/** The select likes by bookname stmt. */
		public final String SELECT_LIKES_BY_BOOKNAME_STMT = "SELECT * FROM LIKES WHERE bookname=?";
		
		/** The delete likes by bookname and username stmt. */
		public final String DELETE_LIKES_BY_BOOKNAME_AND_USERNAME_STMT = "DELETE FROM LIKES "
				+ "WHERE bookname=? AND username=?";
		
		/** The delete likes by username stmt. */
		public final String DELETE_LIKES_BY_USERNAME_STMT = "DELETE FROM LIKES WHERE username=?";
		
		
		/** The create reviews table. */
		public final String CREATE_REVIEWS_TABLE = "CREATE TABLE REVIEWS(bookname varchar(100),"
				+ "nickname varchar(100), review varchar(5000), approved varchar(100))";
		
		/** The insert reviews stmt. */
		public final String INSERT_REVIEWS_STMT = "INSERT INTO REVIEWS VALUES(?,?,?,?)";
		
		/** The select reviews by bookname stmt. */
		public final String SELECT_REVIEWS_BY_BOOKNAME_STMT = "SELECT * FROM REVIEWS WHERE bookname=? AND approved=?";
		
		/** The select reviews by approve stmt. */
		public final String SELECT_REVIEWS_BY_APPROVE_STMT = "SELECT * FROM REVIEWS WHERE approved=?";
		
		/** The update reviews stmt. */
		public final String UPDATE_REVIEWS_STMT = "UPDATE REVIEWS SET approved=? WHERE bookname=? AND nickname=? AND review=?";
		
		/** The delete reviews by nickname stmt. */
		public final String DELETE_REVIEWS_BY_NICKNAME_STMT = "DELETE FROM REVIEWS WHERE nickname=?";
		
		
		/** The create purchases table. */
		public final String CREATE_PURCHASES_TABLE = "CREATE TABLE PURCHASES(username varchar(100),"
				+ "bookname varchar(100), price varchar(100))";
		
		/** The insert purchases stmt. */
		public final String INSERT_PURCHASES_STMT = "INSERT INTO PURCHASES VALUES(?,?,?)";
		
		/** The select purchases by username stmt. */
		public final String SELECT_PURCHASES_BY_USERNAME_STMT = "SELECT * FROM PURCHASES WHERE username=?";
		
		/** The select purchases by username and book stmt. */
		public final String SELECT_PURCHASES_BY_USERNAME_AND_BOOK_STMT = "SELECT * FROM PURCHASES WHERE username=? AND bookname=? ";
		
		/** The select all purchases stmt. */
		public final String SELECT_ALL_PURCHASES_STMT = "SELECT * FROM PURCHASES";
		

		/** The create locations table. */
		public final String CREATE_LOCATIONS_TABLE = "CREATE TABLE LOCATIONS(username varchar(100),"
				+ "bookname varchar(100)," + "location varchar(200))";
		
		/** The insert locations stmt. */
		public final String INSERT_LOCATIONS_STMT = "INSERT INTO LOCATIONS VALUES(?,?,?)";
		
		/** The select locations by username and bookname stmt. */
		public final String SELECT_LOCATIONS_BY_USERNAME_AND_BOOKNAME_STMT = "SELECT * FROM LOCATIONS WHERE username=? AND bookname=?";
		
		/** The update locations stmt. */
		public final String UPDATE_LOCATIONS_STMT = "UPDATE LOCATIONS SET location=? WHERE username=? AND bookname=?";
		
}
