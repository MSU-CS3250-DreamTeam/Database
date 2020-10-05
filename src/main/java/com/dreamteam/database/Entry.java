package com.dreamteam.database;

public class Entry {
	public Entry(String productID, int quantity, double wholesalePrice, double salePrice,
				 String sellerID) {
		this.productID = productID;
		this.quantity = quantity;
		this.wholesalePrice = wholesalePrice;
		this.salePrice = salePrice;
		this.sellerID = sellerID;
	}
	
	private String productID;
	private int quantity;
	private double salePrice;
	private String sellerID;
	double wholesalePrice;

	static Entry getEntry(String[] temp, String productID) {
		int quantity = Integer.parseInt(temp[1]);
		double wholesalePrice = Double.parseDouble(temp[2]);
		double salePrice = Double.parseDouble(temp[3]);
		String supplierID = temp[4];
		return new Entry(productID, quantity, wholesalePrice, salePrice, supplierID);
	}

	public String getProductID() {
		return productID;
	}
	
	public int getQuantity() { return quantity; }
	
	public double getSalePrice() {
		return salePrice;
	}
	
	public String getSellerID() {
		return sellerID;
	}
	
	public double getWholesalePrice() {
		return wholesalePrice;
	}
	
	public static Entry parse(String[] temp) {
		String productID = temp[0];
		return getEntry(temp, productID);
	}
	
	/** Create a new Entry from a String array. */
	public static Entry parseEntry(String[] temp) {
		String productID = temp[0] + "";
		return getEntry(temp, productID);
	}
	
	public void setProductID(String productID) {
		this.productID = productID;
	}
	
	public boolean addQuantity(int increment) {
		return setQuantity(getQuantity() + increment);
	}
	
	public boolean subtractQuantity(int increment) {
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
		String s =  "Entry {" +
			   "  \n\tproductID = '" + productID + '\'' +
			   ", \n\tquantity = " + quantity +
			   ", \n\twholesalePrice = " + wholesalePrice +
			   ", \n\tsalePrice = " + salePrice +
			   ", \n\tsellerID = '" + sellerID + '\'' +
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
