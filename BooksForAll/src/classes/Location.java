package classes;

public class Location {
private String username, bookname, location;
	
	public Location(String _username, String _bookname, String _location) {
		username = _username;
		bookname = _bookname;
		location = _location;
	}
	public String getUsername() {
		return username;
	}

	public String getBookname() {
		return bookname;
	}
	
	public String getLocation() {
		return location;
	}
}
