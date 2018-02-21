package classes;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

public interface AppConstants {
		public final String USERS = "users";
		public final String USERINFO = "userinfo";
		public final String BOOKS = "books";
		public final String LIKES = "likes";
		public final String REVIEWS = "reviews";
		public final String PURCHASES = "purchases";
		public final String LOCATIONS = "locations";
		
		public final String USERS_FILE = "json/" + USERS + ".json";
		public final String USERINFO_FILE = "json/" + USERINFO + ".json";
		public final String BOOKS_FILE = "json/" + BOOKS + ".json";
		public final String LIKES_FILE = "json/" + LIKES + ".json";
		public final String REVIEWS_FILE = "json/" + REVIEWS + ".json";
		public final String PURCHASES_FILE = "json/" + PURCHASES + ".json";
		public final String LOCATIONS_FILE = "json/" + LOCATIONS + ".json";
		
		public final String USERNAME = "username";
		public final Type BOOK_COLLECTION = new TypeToken<Collection<Book>>() {}.getType();
		public final Type PURCHASE_COLLECTION = new TypeToken<Collection<Purchase>>() {}.getType();
		public final Type USERINFO_COLLECTION = new TypeToken<Collection<UserInfo>>() {}.getType();
		public final Type REVIEW_COLLECTION = new TypeToken<Collection<Review>>() {}.getType();
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
		public final String SELECT_USERINFO_BY_USERNAME_STMT = "SELECT * FROM USERINFO "
				+ "WHERE username=?";
		public final String CREATE_BOOKS_TABLE = "CREATE TABLE BOOKS(name varchar(100),"
				+ "image varchar(100), description varchar(100), price varchar(100))";
		public final String SELECT_ALL_BOOKS_STMT = "SELECT * FROM BOOKS";
		public final String INSERT_BOOK_STMT = "INSERT INTO BOOKS VALUES(?,?,?,?)";
		public final String SELECT_BOOKS_BY_NAME_STMT = "SELECT * FROM BOOKS "
				+ "WHERE name=?";
		public final String CREATE_LIKES_TABLE = "CREATE TABLE LIKES(bookname varchar(100),"
				+ "username varchar(100))";
		public final String SELECT_ALL_LIKES_STMT = "SELECT * FROM LIKES";
		public final String INSERT_LIKES_STMT = "INSERT INTO LIKES VALUES(?,?)";
		public final String SELECT_LIKES_BY_BOOKNAME_STMT = "SELECT * FROM LIKES WHERE bookname=?";
		public final String DELETE_LIKES_BY_BOOKNAME_AND_USERNAME_STMT = "DELETE FROM LIKES "
				+ "WHERE bookname=? AND username=?";
		public final String CREATE_REVIEWS_TABLE = "CREATE TABLE REVIEWS(bookname varchar(100),"
				+ "nickname varchar(100), review varchar(5000), approved varchar(100))";
		public final String SELECT_REVIEWS_BY_BOOKNAME_STMT = "SELECT * FROM REVIEWS WHERE bookname=? AND approved=?";
		public final String INSERT_REVIEWS_STMT = "INSERT INTO REVIEWS VALUES(?,?,?,?)";
		public final String CREATE_PURCHASES_TABLE = "CREATE TABLE PURCHASES(username varchar(100),"
				+ "bookname varchar(100), price varchar(100))";
		public final String INSERT_PURCHASES_STMT = "INSERT INTO PURCHASES VALUES(?,?,?)";
		public final String SELECT_PURCHASES_BY_USERNAME_STMT = "SELECT * FROM PURCHASES WHERE username=?";
		public final String SELECT_PURCHASES_BY_USERNAME_AND_BOOK_STMT = "SELECT * FROM PURCHASES WHERE username=? AND bookname=? ";
		public final String SELECT_ALL_USERINFO_STMT = "SELECT * FROM USERINFO";
		public final String SELECT_ALL_PURCHASES_STMT = "SELECT * FROM PURCHASES";
		public final String DELETE_USER_BY_USERNAME_STMT = "DELETE FROM USERS WHERE username=?";
		public final String DELETE_USERINFO_BY_USERNAME_STMT = "DELETE FROM USERINFO WHERE username=?";
		public final String DELETE_LIKES_BY_USERNAME_STMT = "DELETE FROM LIKES WHERE username=?";
		public final String DELETE_REVIEWS_BY_NICKNAME_STMT = "DELETE FROM REVIEWS WHERE nickname=?";
		public final String SELECT_REVIEWS_BY_APPROVE_STMT = "SELECT * FROM REVIEWS WHERE approved=?";
		public final String UPDATE_REVIEWS_STMT = "UPDATE REVIEWS SET approved=? WHERE bookname=? AND nickname=? AND review=?";
		public final String SELECT_USERINFO_BY_NICKNAME_STMT = "SELECT * FROM USERINFO "
				+ "WHERE nickname=?";
		public final String SELECT_LIKES_BY_BOOKNAME_AND_USERNAME_STMT = "SELECT * FROM LIKES WHERE bookname=? AND username=?";
		public final String CREATE_LOCATIONS_TABLE = "CREATE TABLE LOCATIONS(username varchar(100),"
				+ "bookname varchar(100)," + "location varchar(200))";
		public final String INSERT_LOCATIONS_STMT = "INSERT INTO LOCATIONS VALUES(?,?,?)";
		public final String UPDATE_LOCATIONS_STMT = "UPDATE LOCATIONS SET location=? WHERE username=? AND bookname=?";
		public final String SELECT_LOCATIONS_BY_USERNAME_AND_BOOKNAME_STMT = "SELECT * FROM LOCATIONS WHERE username=? AND bookname=?";
}
