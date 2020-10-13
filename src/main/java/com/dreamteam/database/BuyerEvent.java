package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class BuyerEvent {
	//Empty constructor
	public BuyerEvent(String buyerArgs) {
		String[] array = buyerArgs.split(",");
		this.date = array[0];
		this.email = array[1];
		this.shipping_address = array[2];
		this.product_id = array[3];
		this.quantity = Integer.parseInt(array[4]);
	}
	
	private static Database my_database;
	//Class variables
	private String date;
	private String time;
	private String email;
	private String product_id;
	private int quantity;
	private String shipping_address;
	
	public String getDate() {
		return date;
	}

	public CharSequence getTime() {
		return time;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getProduct_id() {
		return product_id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String getShipping_address() {
		return shipping_address;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// Initialize database inventory from csv
		my_database = new Database("inventory_team1.csv");
		
		File file = new File("buyer_event.csv");
		Scanner scanner = new Scanner(file);
		
		// Reads corresponding input fields from csv and assigns them to object
		Queue<BuyerEvent> events = new ArrayDeque<>();
		
		while(scanner.hasNextLine()) {
			// Each buyer event (line of data in csv file) is stored as an 
			// object
			events.add(new BuyerEvent(scanner.nextLine()));
		}
		scanner.close();
		System.out.println("Buyer Event Simulation Initiated...\n");
	
		while(!events.isEmpty()) {
			BuyerEvent event = events.remove();
			if(my_database.contains(event.getProduct_id())) {
			Entry entry = my_database.getEntry(event.getProduct_id());
			entry.buyQuantity(event.getQuantity());
			} else {
				System.out.println("that product Id does not exist...");
			}
		}
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setShipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	
	public String toString() {
		String buyerEvent = "";
		buyerEvent += ("Date: " + date + "\n");
		buyerEvent += ("Email: " + email + "\n");
		buyerEvent += ("Shipping Address: " + shipping_address + "\n");
		buyerEvent += ("Product ID: " + product_id + "\n");
		buyerEvent += ("Quantity Purchased: " + quantity);
		
		return buyerEvent;
	}
	
	/**
	 * Updates quantity of product inside of the Database
	 *
	 * @param event
	 */
	static void updateQuantity(BuyerEvent event) {
		Entry record = my_database.read(event.product_id);
		if(record != null) {
			System.out.println(
			 "Product quantity before purchase: " + record.getQuantity());
			record.buyQuantity(event.quantity);
		}
	}

}
