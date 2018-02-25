package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class Book.
 */
public class Book {
	
	/** The price. */
	private String name, image, description, price;
	
	/**
	 * Instantiates a new book.
	 *
	 * @param _name the name
	 * @param _image the image
	 * @param _description the description
	 * @param _price the price
	 */
	public Book(String _name, String _image, String _description, String _price) {
		name = _name;
		image = _image;
		description = _description; 
		price = _price;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
