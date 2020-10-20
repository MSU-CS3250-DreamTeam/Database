package com.dreamteam.database;

public class Product extends DatabaseEntry {

	private String productID;
	private int quantity;
	private double salePrice;
	private String sellerID;
	private double wholesalePrice;

	public Product(String productID, int quantity, double wholesalePrice, double salePrice,
				 String sellerID) {
		this.productID = productID;
		this.quantity = quantity;
		this.wholesalePrice = wholesalePrice;
		this.salePrice = salePrice;
		this.sellerID = sellerID;
	}

	public Product(String[] product) {
		this.productID = product[0];
		this.quantity = Integer.parseInt(product[1]);
		this.wholesalePrice = Double.parseDouble(product[2]);
		this.salePrice = Double.parseDouble(product[3]);
		this.sellerID = product[4];
	}



	// TODO What is this and why have it?
	// static Product getProduct(String[] temp, String productID) {
	// 	int quantity = Integer.parseInt(temp[1]);
	// 	double wholesalePrice = Double.parseDouble(temp[2]);
	// 	double salePrice = Double.parseDouble(temp[3]);
	// 	String supplierID = temp[4];
	// 	return new Product(productID, quantity, wholesalePrice, salePrice, supplierID);
	// }

	public String getProductID() { return this.productID; }
	
	public int getQuantity() { return this.quantity; }
	
	public double getSalePrice() { return this.salePrice; }
	
	public String getSellerID() { return this.sellerID; }
	
	public double getWholesalePrice() { return this.wholesalePrice; }
	
	// TODO What is this and why have it?
	// public static Product parse(String[] temp) {
	// 	String productID = temp[0];
	// 	return getProduct(temp, productID);
	// }
	
	// TODO Does not the constructor already do this?
	/** Create a new Entry from a String array. */
	// public static Product parseProduct(String[] temp) {
	// 	String productID = temp[0] + "";
	// 	return getProduct(temp, productID);
	// }
	
	public void setProductID(String productID) {
		this.productID = productID;
	}

	public boolean supplyQuantity(int increment) {
		return setQuantity(getQuantity() + increment);
	}
	
	public boolean buyQuantity(int increment) {
		return setQuantity(getQuantity() - increment);
	}
	
	private boolean setQuantity(int quantity) {
		if(quantity >= 0) {
			this.quantity = quantity;
			System.out
				 .println("Product quantity after purchase: " + quantity + "\n");
			return true;
		} else {
			System.out.println(
				 "We need " + (-quantity) + " more of product " + productID +
				 " to make the sale...");
			
			return false;
		}
	}
	
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	
	public void setSellerID(String sellerID) {
		this.sellerID = sellerID;
	}
	
	public void setWholesalePrice(double wholesalePrice) {
		this.wholesalePrice = wholesalePrice;
	}
	
	public String prettyPrint() {
		String s =  "Product {" +
			   "  \n\tproduct id: '" + productID + '\'' +
			   ", \n\tquantity: " + quantity +
			   ", \n\twholesale price: " + wholesalePrice +
			   ", \n\tsale price: " + salePrice +
			   ", \n\tseller id: '" + sellerID + '\'' +
			   "\n}";
		System.out.println(s);
		return s;
	}
	
	@Override public String toString() {
		return 
		 productID + "," +
		 quantity + "," +
		 wholesalePrice + "," +
		 salePrice + "," +
		 sellerID + ",";
	}
}
