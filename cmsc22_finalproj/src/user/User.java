/*
 * This package is the superclass of Buyer and Seller objects
 * This shows the screen after logging in.
 * The methods here are overridden by its subclass depending on the type of user they are
 * For example, in the viewDashboard, a buyer will have a view cart option
 * while a seller will have a view products option
 */

package user;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String displayName;
    private String username;
    private String password;
    private String location;
    private float balance; 

    public User(String displayName, String username, String password, float balance, String location) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.location = location;
    }

    // Abstract method for dashboards
    public abstract void displayDashboard();

    // Mutators
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setLocation(String location) { this.location = location; }
    public void setBalance(float balance) { this.balance = balance; }

    // Accessors
    public String getDisplayName() { return displayName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getLocation() { return location; }
    public float getBalance() { return balance; }
}
