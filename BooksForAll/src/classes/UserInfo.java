package classes;

public class UserInfo {

	private String username, password, type, email, street, streetNumber,  city, zipcode, telephone, nickname, description, photo;
	public UserInfo(String _username, String _password, String _type, String _email, String _street, String _streetNumber,String _city,String _zipcode,String _telephone, String _nickname, String _description,String _photo){
	username = _username;
	password = _password;
	type = _type; 
	email = _email;
	street=_street;
	streetNumber=_streetNumber;
	city=_city;
	zipcode=_zipcode;
	telephone=_telephone;
	nickname=_nickname;
	description=_description;
	photo=_photo;
	}
	public String getUsername() {
		return username;
	}
	public String getType() {
		return type;
	}
	public String getEmail() {
		return email;
	}
	public String getStreet() {
		return street;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public String getCity() {
		return city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getNickname() {
		return nickname;
	}
	public String getDescription() {
		return description;
	}
	public String getPassword() {
		return password;
	}
	public String getPhoto() {
		return photo;
	}
}
