package user;

import product.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Buyer extends User {

    private static final long serialVersionUID = 1L; // FIX 1

    private ArrayList<Product> wishlist = new ArrayList<>();
    private ArrayList<Vouchers> voucherList = new ArrayList<>();
    private HashMap<Product, Integer> cart = new HashMap<>();
    private ArrayList<TransactionHistory> transactions = new ArrayList<>();

    private float balance;

    public Buyer(String displayName, String username, String password, float balance, String location) {
        super(displayName, username, password, balance, location);
        this.balance = balance;
    }

    // REQUIRED IMPLEMENTATION (FIX 2)
    @Override
    public void displayDashboard() {
        System.out.println("Buyer Dashboard");
    }

    public void setBalance(float balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    // Wishlist
    public void addToWishlist(Product product) {
        if (!wishlist.contains(product)) {
            wishlist.add(product);
        } else {
            System.out.println("Product already in wishlist.");
        }
    }

    public void removeWishlist(Product product) {
        wishlist.remove(product);
    }

    // Cart
    public void addToCart(Product product) {
        cart.put(product, cart.getOrDefault(product, 0) + 1);
    }

    public void addQuantity(Product product) {
        if (cart.containsKey(product)) {
            cart.put(product, cart.get(product) + 1);
        }
    }

    public void reduceQuantity(Product product) {
        if (cart.containsKey(product)) {
            int qty = cart.get(product);
            if (qty <= 1) {
                cart.remove(product);
            } else {
                cart.put(product, qty - 1);
            }
        }
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        float overallTotal = 0f;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            float total = p.getPrice() * qty;

            System.out.println("ID: " + p.getId()
                    + " | Name: " + p.getName()
                    + " | Qty: " + qty
                    + " | Price each: ₱" + p.getPrice()
                    + " | Total: ₱" + total);

            overallTotal += total;
        }

        System.out.println("-----------------------------------");
        System.out.println("Overall total: ₱" + overallTotal);
    }

    // Purchase
    public void checkStock(ArrayList<Product> toBuy) {
        ArrayList<Product> outOfStock = new ArrayList<>();

        for (Product product : toBuy) {
            if (product.getStock() <= 0) {
                outOfStock.add(product);
            }
        }

        if (!outOfStock.isEmpty()) {
            System.out.println("Items out of stock:");
            for (Product product : outOfStock) {
                System.out.println(product.getName());
            }
        }
    }

    public void buy(ArrayList<Product> toBuy) {
        checkStock(toBuy);

        ArrayList<Seller> sellersBoughtFrom = new ArrayList<>();

        for (Product p : toBuy) {
            if (!sellersBoughtFrom.contains(p.getSeller())) {
                sellersBoughtFrom.add(p.getSeller());
            }
        }

        float overallCost = 0f;

        for (Seller seller : sellersBoughtFrom) {
            float sellerTotal = 0f;

            for (Product p : toBuy) {
                if (p.getSeller() == seller) {
                    sellerTotal += p.getPrice();
                }
            }

            for (Vouchers voucher : voucherList) {
                if (voucher.getSeller() == seller) {
                    sellerTotal *= (1 - voucher.getDiscount());
                }
            }

            overallCost += sellerTotal;
        }

        if (this.getBalance() < overallCost) {
            System.out.println("Insufficient balance.");
            return;
        }

        ArrayList<Vouchers> usedVouchers = new ArrayList<>();

        for (Seller seller : sellersBoughtFrom) {

            float sellerTotal = 0f;
            ArrayList<Product> purchasedFromSeller = new ArrayList<>();

            for (Product p : toBuy) {
                if (p.getSeller() == seller) {
                    sellerTotal += p.getPrice();
                    p.setStock(p.getStock() - 1);
                    purchasedFromSeller.add(p);

                    TransactionHistory th =
                            new TransactionHistory(this, seller, p, 1, p.getPrice());
                    transactions.add(th);
                }
            }

            for (Vouchers voucher : voucherList) {
                if (voucher.getSeller() == seller) {
                    sellerTotal *= (1 - voucher.getDiscount());
                    usedVouchers.add(voucher);
                    break;
                }
            }

            seller.setBalance(seller.getBalance() + sellerTotal);
            this.setBalance(this.getBalance() - sellerTotal);

            toBuy.removeAll(purchasedFromSeller);
        }

        voucherList.removeAll(usedVouchers);
        System.out.println("Purchase successful!");
    }

    // Transaction History
    public void viewTransactionHistory() {
        System.out.println(getUsername() + "'s Transaction History:");

        if (transactions.isEmpty()) {
            System.out.println("No recorded transactions yet.");
            return;
        }

        int totalItems = 0;
        float totalSpent = 0f;

        for (TransactionHistory th : transactions) {

            System.out.println("ID: " + th.getProduct().getId()
                    + " | Name: " + th.getProduct().getName()
                    + " | Price: ₱" + th.getProduct().getPrice());

            totalItems += th.getQuantity();
            totalSpent += th.getTotalCost();
        }

        System.out.println("-----------------------------------");
        System.out.println("Total items bought: " + totalItems);
        System.out.println("Total amount spent: ₱" + totalSpent);
    }
}
