package user;

public abstract class User {
	String username;
	String password;
	String displayName;
	Double balance;
	String location;
	
	public User(String username, String password, String displayName, double balance, String location) {
		this.username = username;
		this.password = password;
		this.displayName = displayName;
		this.balance = balance;
		this.location = location;
	}
	
	public abstract void login();
	public abstract void viewDetails();
	public abstract void changeDisplayName();
	public abstract void changeLocation();
}
