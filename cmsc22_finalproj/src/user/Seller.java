package user;

import java.util.ArrayList;
import java.util.Scanner;
import product.*;

public class Seller extends User {
	
	Scanner sc = new Scanner(System.in);
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private float balance;
	private String location;
	
	private static ArrayList<Seller> sellerList = new ArrayList<>();
	private static ArrayList<Product> productList = new ArrayList<>();
	
	public Seller(String firstName, String lastName, String username, String password, float balance, String location){
		super(firstName, lastName, username, password, balance, location);
	}
	
	public static void add(Seller seller) {
		sellerList.add(seller);
	}
	
	/*
	 * Sellers are users who can sell products in their own storefront. 
	 * Sellers also have a product list, a voucher list and a transaction log. 
	 * Sellers can add, sell, arrange and hide products, add or remove vouchers, 
	 * and log transactions. 
	 */
	
	public void add(){
		
	}

	@Override
	public void displayDashboard() {
		// TODO Auto-generated method stub
		
	}
	
	

}
