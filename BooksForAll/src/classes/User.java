package classes;

public class User {
	private String username, password, type;
	
	public User(String _username, String _password, String _type) {
		username = _username;
		password = _password;
		type = _type; 
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getType() {
		return type;
	}
}
