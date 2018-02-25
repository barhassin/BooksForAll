package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class Like.
 */
public class Like {
	
	/** The bookname. */
	private String username, bookname;
	
	/**
	 * Instantiates a new like.
	 *
	 * @param _username the username
	 * @param _bookname the bookname
	 */
	public Like(String _username, String _bookname) {
		username = _username;
		bookname = _bookname;
		 
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
}



