package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class Purchase.
 */
public class Purchase {
	
	/** The username. */
	private String username;
	
	/** The bookname. */
	private String bookname;
	
	/** The price. */
	private String price;
	
	/**
	 * Instantiates a new purchase.
	 *
	 * @param _username the username
	 * @param _bookname the bookname
	 * @param _price the price
	 */
	public Purchase(String _username, String _bookname, String _price) {
		username = _username;
		bookname = _bookname;
		price = _price;
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
	 * Gets the price.
	 *
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
}
