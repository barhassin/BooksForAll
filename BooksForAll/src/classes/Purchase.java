package classes;

public class Purchase {
	private String username, bookname, price;
	
	public Purchase(String _username, String _bookname, String _price) {
		username = _username;
		bookname = _bookname;
		price = _price;
	}
	public String getUsername() {
		return username;
	}

	public String getBookname() {
		return bookname;
	}
	
	public String getPrice() {
		return price;
	}
}
