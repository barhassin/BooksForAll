package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
	
	/** The type. */
	private String username, password, type;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param _username the username
	 * @param _password the password
	 * @param _type the type
	 */
	public User(String _username, String _password, String _type) {
		username = _username;
		password = _password;
		type = _type; 
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
}
