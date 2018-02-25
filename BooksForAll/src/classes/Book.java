package classes;

public class Book {
	private String name, image, description, price;
	
	public Book(String _name, String _image, String _description, String _price) {
		name = _name;
		image = _image;
		description = _description; 
		price = _price;
	}
	
	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getPrice() {
		return price;
	}
}
