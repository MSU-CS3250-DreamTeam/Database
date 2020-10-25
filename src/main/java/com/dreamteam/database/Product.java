package com.dreamteam.database;

public class Product extends DatabaseEntry {

	/** Member Variables */

	private String product_id;
	private int quantity;
	private int capacity;
	private double wholesale_cost;
	private double sale_price;
	private String supplier_id;

	/** Construction */

	public Product(String id, int quantity, double cost, double price,
				 String seller_id) {
		this.product_id = id;
		this.quantity = quantity;
		this.capacity = quantity;
		this.wholesale_cost = cost;
		this.sale_price = price;
		this.supplier_id = seller_id;
	}

	public Product(String[] product) {
		this(product[0], Integer.parseInt(product[1]), 
				Double.parseDouble(product[2]), Double.parseDouble(product[3]), product[4]);
	}

	 /** Getters */

	public String getProductID() { return this.product_id; }
	
	public int getQuantity() { return this.quantity; }
	
	public int getCapacity() { return this.capacity; }
		
	public double getWholesaleCost() { return this.wholesale_cost; }
	
	public double getSalePrice() { return this.sale_price; }
	
	public String getSupplierID() { return this.supplier_id; }

	/** Setters */
	
	public void setProductID(String productID) { this.product_id = productID; }
		
	private boolean setQuantity(int quantity) {

		if(quantity >= 0) {
			this.quantity = quantity;
			System.out.println("Product " + getProductID() + " quantity after transaction: " + quantity + ".");
		
		} else {
			int attempted_quantity = getQuantity() - quantity;

			System.out.print("\n\t\tYou attempted to purchase " + attempted_quantity + ". " +
								"\n\t\tWe need " + (-quantity) + " more of product " + product_id +
								" to make the sale, so we are restocking the product now.");
			
			if (restock()) {
				System.out.print("\t\tSuccess. ");
				buyQuantity(attempted_quantity);
			}
		}

		return getQuantity() == quantity;
	}

	public void setCapacity(int quantity_limit) { this.capacity = quantity_limit; }
		
	public void setSalePrice(double salePrice) { this.sale_price = salePrice; }
	
	public void setWholesaleCost(double wholesalePrice) { this.wholesale_cost = wholesalePrice; }

	public void setSupplierID(String sellerID) { this.supplier_id = sellerID; }

	/** Class Methods (Alphabetical Order) */
	// TODO javadoc of Class Methods.
	
	/**
	 * 
	 * @param increment
	 * @return
	 */
	public boolean buyQuantity(int increment) {
		System.out.print("Buy Transaction of " + increment + " " + getProductID() + ". ");
		return setQuantity(getQuantity() - increment);
	}

	/**
	 * 
	 * @return
	 */
	public String prettyPrint() {
		String regex = ", \n\t\t";
		String s =  "\nProduct:\t" +
			   "{ product id:\t\t\"" + product_id + '\'' +
			   regex + "  quantity:\t\t" + quantity +
			   regex + "  wholesale cost:\t$" + wholesale_cost +
			   regex + "  sale price:\t\t$" + sale_price +
			   regex + "  supplier id:\t\t\"" + supplier_id + '\"' + "\t}\n";

		System.out.println(s);
		return s;
	}

	/**
	 * 
	 * @return
	 */
	private boolean restock() {
		System.out.print("\n\t\tRestock ");
		return supplyQuantity(getCapacity());
	}
	
	/**
	 * 
	 * @param increment
	 * @return
	 */
	public boolean supplyQuantity(int increment) {
		System.out.print("Supply Transaction of " + increment + ". ");
		ProductDatabase.getProducts().appendSupplierHistory(this, increment);
		return setQuantity(getQuantity() + increment);
	}
	
	/**
	 * 
	 * @return
	 */
	@Override public String toString() {
		return 
		 product_id + "," +
		 quantity + "," +
		 wholesale_cost + "," +
		 sale_price + "," +
		 supplier_id + ",";
	}

}
