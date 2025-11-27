package product;

import java.util.Scanner;
import user.Seller;

public class Product {
    
    private static int idCounter = 0; 
    private int id;
    private String name;
    private String category;
    private float price;
    private int stock;
    private Seller seller;

    private Scanner sc = new Scanner(System.in);

    // Constructor
    public Product(String name, String category, float price, int stock, Seller seller) {
        this.id = ++idCounter;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.seller = seller;
    }

    public static Product fillInformation(Seller seller) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Product Name: ");
        String name = sc.nextLine();

        String category = chooseCategory(sc);
        
        System.out.println("Enter price");
        float price = sc.nextFloat();
        
        System.out.println("Enter stock: ");
        int stock = sc.nextInt();

        Product product = new Product(name, category, price, stock, seller);
        System.out.println("Product created: " + product);
        return product;
    }

    private static String chooseCategory(Scanner sc) {
        System.out.println("Category options: ");
        System.out.println("[1] fruits");
        System.out.println("[2] canned goods");
        System.out.println("[3] meat");
        System.out.println("[4] vegetables");
        System.out.println("[5] seafood");
        System.out.println("[6] condiments");
        System.out.println("[7] snacks");
        System.out.println("[8] bread");
        System.out.println("[9] beverages");
        System.out.println("[10] rice");
        System.out.println("[11] frozen goods");
        System.out.println("[12] personal care");
        System.out.println("[13] health care");
        System.out.println("[14] cleaning");
        System.out.println("[15] baby items");
        System.out.println("[16] pet care");
        System.out.print("Choose the category of the product: ");

        int categoryChoice = sc.nextInt();
        sc.nextLine();

        switch (categoryChoice) {
            case 1:  return "fruits";
            case 2:  return "canned goods";
            case 3:  return "meat";
            case 4:  return "vegetables";
            case 5:  return "seafood";
            case 6:  return "condiments";
            case 7:  return "snacks";
            case 8:  return "bread";
            case 9:  return "beverages";
            case 10: return "rice";
            case 11: return "frozen goods";
            case 12: return "personal care";
            case 13: return "health care";
            case 14: return "cleaning";
            case 15: return "baby items";
            case 16: return "pet care";
            default: return "unknown";
        }
    }
    
    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public float getPrice() { return this.price; }
    public int getStock() { return this.stock; }
    
    public void setStock(int stock) { this.stock = stock; }



}
