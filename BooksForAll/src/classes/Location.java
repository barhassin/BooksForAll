package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 */
public class Location {

	/** The location. */
	private String location;

	/** The bookname. */
	private String bookname;

	/** The username. */
	private String username;
	
	/**
	 * Instantiates a new location.
	 *
	 * @param _username the username
	 * @param _bookname the bookname
	 * @param _location the location
	 */
	public Location(String _username, String _bookname, String _location) {
		username = _username;
		bookname = _bookname;
		location = _location;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the bookname.
	 *
	 * @return the bookname
	 */
	public String getBookname() {
		return bookname;
	}
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
}
