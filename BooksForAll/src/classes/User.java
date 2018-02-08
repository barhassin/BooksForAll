package classes;

public class User {
	private String Username, Password, Type;
	
	public User(String username, String password, String type) {
		Username = username;
		Password = password;
		Type = type; 
	}

	public String getUsername() {
		return Username;
	}

	public String getPassword() {
		return Password;
	}
	
	public String getType() {
		return Type;
	}
}
