/*
 * This package is the superclass of Buyer and Seller objects
 * This shows the screen after logging in.
 * The methods here are overridden by its subclass depending on the type of user they are
 * For example, in the viewDashboard, a buyer will have a view cart option
 * while a seller will have a view products option
 */

package user;

import java.util.Scanner;
import java.util.ArrayList;
import user.Buyer;
import user.Seller;

public abstract class User {
	
	private static Scanner sc = new Scanner(System.in);
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private float balance;
	private String location;
	
	public User(String firstName, String lastName, String username, String password, float balance, String location) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.location = location;
	}
	
	public abstract void displayDashboard();
	
	public void viewProfile() {
		System.out.println("Name: " + firstName + " " + lastName);
		System.out.println("Balance: " + balance);
		System.out.println("Location: " + location);
		
		// options
		System.out.println("[1] Change display name");
		System.out.println("[2] Change location ");
		System.out.println("[3] Back");
		
		int profileChoice = sc.nextInt();
		sc.nextLine();
		
		if(profileChoice == 1) {
			changeDisplayName();
		}
		else if(profileChoice == 2) {
			changeLocation();
		}
		else if(profileChoice == 3) {
			displayDashboard();
		}
	}
	
	public void changeDisplayName() {
		System.out.println("Enter your new display name: ");
		String newUsername = sc.nextLine();
		
		System.out.println("Enter your password to confirm");
		String validateUname = sc.nextLine();
		
		if(this.getPassword().equals(validateUname)) {
			this.username = newUsername;
			System.out.println("Username successfully updated");
			viewProfile();
		}
		
		System.out.println("Incorrect password. Username failed to change");
		viewProfile();
	}
	
	public void changeLocation() {
		System.out.println("Enter your new location: ");
		String newLocation = sc.nextLine();
		
		System.out.println("Enter your password to confirm");
		String validateUname = sc.nextLine();
		
		if(this.getPassword().equals(validateUname)) {
			this.location = newLocation;
			System.out.println("Location successfully updated");
			viewProfile();
		}
		
		System.out.println("Incorrect password. Location failed to change");
		viewProfile();
	}
	
	
	public String getName() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public float getBalance() {
	    return this.balance;
	}

	public void setBalance(float balance) {
	    this.balance = balance;
	}




}
