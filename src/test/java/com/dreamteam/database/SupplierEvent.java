package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SupplierEvent {
	//Empty Constructor
	public SupplierEvent() {
		
	}
	
	public SupplierEvent(String date, String email, String shippingAddress,
						 String product_id, int quantity) {
		this.date = date;
		this.email = email;
		this.shippingAddress = shippingAddress;
		this.product_id = product_id;
		this.quantity = quantity;
	}
	
	//Class Variables
	private String date;
	private String email;
	private String product_id;
	private int quantity;
	private String shippingAddress;
	private String supplier_id;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//CSV file that holds supplier event parameters
		File file = new File("src/test/supplier_event.csv");
		Scanner scanner = new Scanner(file);
		
		System.out.println("Supplier Event Simulation Initiated...\n");
		
		//Each supplier event (line of data in csv file) is stored as an object
		SupplierEvent event = new SupplierEvent();
		
		//Reads corresponding input fields from csv and assigns them to object
		while(scanner.hasNextLine()) {
			String[] data_row = scanner.nextLine().split(",");
			event.date = data_row[0];
			event.supplier_id = data_row[1];
			event.product_id = data_row[2];
			event.quantity = Integer.parseInt(data_row[3]);
			
			//updateQuantity();
			
			//As of now, the program only reads from the csv and prints it.
			System.out.println(event.toString());
		}
		
		System.out.println("Supplier Event Simulation Complete");
	}
	
	public String toString() {
		String supplierEvent = "";
		supplierEvent += ("Date: " + date + "\n");
		supplierEvent += ("Supplier ID: " + supplier_id + "\n");
		supplierEvent += ("Product ID: " + product_id + "\n");
		supplierEvent += ("Quantity: " + quantity + "\n");
		
		return supplierEvent;
	}
	
	private static void updateQuantity() {
		//
	}
}
