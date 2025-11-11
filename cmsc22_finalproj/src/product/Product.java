package product;

import user.*;

public class Product {
	private int ID;
	private String name;
	private String category;
	private double price;
	private int stock;
	private Seller seller;
	
	public Product(String name, String category, double price, int stock, Seller seller) {
		this.name = name;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.seller = seller;
	}
}
