package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class UserInfo.
 */
public class UserInfo {
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The type. */
	private String type;
	
	/** The email. */
	private String email;
	
	/** The street. */
	private String street;
	
	/** The street number. */
	private String streetnumber;
	
	/** The city. */
	private String city;
	
	/** The zipcode. */
	private String zipcode;
	
	/** The telephone. */
	private String telephone;
	
	/** The nickname. */
	private String nickname;
	
	/** The description. */
	private String description;
	
	/** The photo. */
	private String photo;
	
	/**
	 * Instantiates a new user info.
	 *
	 * @param _username the username
	 * @param _password the password
	 * @param _type the type
	 * @param _email the email
	 * @param _street the street
	 * @param _streetNumber the street number
	 * @param _city the city
	 * @param _zipcode the zipcode
	 * @param _telephone the telephone
	 * @param _nickname the nickname
	 * @param _description the description
	 * @param _photo the photo
	 */
	public UserInfo(String _username, String _password, String _type, String _email, String _street, String _streetNumber,String _city,String _zipcode,String _telephone, String _nickname, String _description,String _photo){
	username = _username;
	password = _password;
	type = _type; 
	email = _email;
	street=_street;
	streetnumber=_streetNumber;
	city=_city;
	zipcode=_zipcode;
	telephone=_telephone;
	nickname=_nickname;
	description=_description;
	photo=_photo;
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	
	/**
	 * Gets the street number.
	 *
	 * @return the street number
	 */
	public String getStreetNumber() {
		return streetnumber;
	}
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Gets the zipcode.
	 *
	 * @return the zipcode
	 */
	public String getZipcode() {
		return zipcode;
	}
	
	/**
	 * Gets the telephone.
	 *
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}
	
	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
}
