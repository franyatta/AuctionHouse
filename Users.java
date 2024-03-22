package users;

public class Users {

	// FOLLOW INSTRUCTIONS, CREATE VARIABLES, SETTERS AND GETTERS, AND CONSTRUCTOR
	// NEED TO DO

	String username;
	String password;

	public Users() {
		username = "";
		password = "";
	}

	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
