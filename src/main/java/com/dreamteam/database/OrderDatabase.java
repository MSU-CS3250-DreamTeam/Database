package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class OrderDatabase implements Database<Order> {
	
	// *** Class Variables */

	private static String[] data_head; // The column labels of the data structure.
	// Position of final entry in data structure minus the header.
	private static HashMap<String, Order> data_table;

	// *** Construction */

	private static final OrderDatabase ORDERS = new OrderDatabase();

	private OrderDatabase() {

		final String file_path = "files/customer_history.csv";

		try {

			File inventory = new File(file_path);
			data_table = new HashMap<>();
			Scanner dbScanner = new Scanner(inventory);

			if (dbScanner.hasNextLine())
				OrderDatabase.data_head = dbScanner.nextLine().split(",");
			
			String dbRow;

			while (dbScanner.hasNextLine()) {
				dbRow = dbScanner.nextLine();
				create(dbRow);
			}

			dbScanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Is the data file " + file_path + " in the wrong directory?");
		}
	}

	public static OrderDatabase getOrders() {
		return ORDERS;
	}

	// *** Getters */

	@Override
	public int get_column_size() { return OrderDatabase.data_head.length; }

	@Override
	public String[] get_data_head() { return OrderDatabase.data_head; }

	// *** Setters */

	@Override
	public void set_data_head(String[] labels) { OrderDatabase.data_head = labels; }

	// *** Class Methods (Alphabetical Order) */

	@Override
	public void create(Order new_order) {
		String unique_key = new_order.getOrderID();
		OrderDatabase.data_table.put(unique_key, new_order);
	}

	@Override
	public void create(String[] entry_string) {
		Order new_order = new Order(entry_string);
		create(new_order);
	}

	/**
	 * Delete existing entry from database.
	 *
	 * @param id
	 *
	 * @return the old Entry if there was one. otherwise, null
	 */
	@Override
	public boolean delete(String id) {
		boolean isRemoved = false;
		if (OrderDatabase.data_table.containsKey(id))
			isRemoved = (OrderDatabase.data_table.remove(id) != null);
		
		return isRemoved;
    }

    /**
	 * Prints the entire database to console. May want to disable in finished project.
	 * Also prints the number of current entries in database.
	 */
	@Override
	public void display() {
		System.out.println("The orders database has " + OrderDatabase.data_table.size() + " orders.");
	}

		
	//	***************************************************************************	

	/**	TODO Find the top ten products and customers (by spending) and return
	 * 
	 */
	public String[] findTopProducts(String date) {
		String[] products = new String[10];

		return products;
	}

	/**	TODO Find the top ten customers (by spending) and return
	 * 
	 */
	public String[] findTopCustomers(String date) {
		String[] customers = new String[10];

		return customers;
	}

	public void processOrders() {

		String source_file_path = "files/customer_orders_A_team1.csv";
		Order processed_order = null;
		String unique_key;
		String dbRow;
		String[] rowArray;
		Product existing_product;
		ProductDatabase products = ProductDatabase.getProducts();


		try {

			File inventory = new File(source_file_path);
			Scanner dbScanner = new Scanner(inventory);
			dbScanner.nextLine();

			while (dbScanner.hasNextLine()) {
				dbRow = dbScanner.nextLine();
				create(dbRow);
				rowArray = dbRow.split(",");
				unique_key = rowArray[2] + "-" + rowArray[3] + "-" + rowArray[0];
				processed_order = read(unique_key);

				existing_product = products.read(processed_order.getProductID());

				if (existing_product.buyQuantity(processed_order.getQuantity())) {
					if (products.update(existing_product)) {
						
						try {
							appendCustomerHistory(processed_order);
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println(processed_order.getOrderID() + " could not be written to file.");
						}

					} else {
						System.out.println(processed_order.getOrderID() + " could not be processed.");
					}
				}
			}

			dbScanner.close();

			try {
				FileWriter fWriter = new FileWriter(source_file_path, false);
				
				String string_head = "";

				for (String value : OrderDatabase.data_head){
					if (!value.equals("time"))
						string_head += value + ",";
				}

				fWriter.write(string_head);
				fWriter.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}

			System.out.println("The orders are processed and appended to history.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Is the data file " + source_file_path + " in the wrong directory?");
		}
	}

    /**
	 * Read existing entry from database.
	 *
	 * @param id
	 *
	 * @return the entry of the database if found.
	 */
	@Override
	public Order read(String id) {
		if (OrderDatabase.data_table.containsKey(id)) {
			return OrderDatabase.data_table.get(id);
		} else {
			System.out.println("The order was not found.");
			return new Order("000,000,000,000,000".split(","));
		}
		
	}

	//	***************************************************************************
	
	/**
	 * 
	 * @param order
	 * @throws IOException
	 */
	private static void appendCustomerHistory(Order order)
	throws IOException {
		String location = "files/customer_history.csv";

		FileWriter writer = new FileWriter(location, true);
		writer.append(order.toString() + "\n");
		
		writer.flush();
		writer.close();

	}

	@Override
    public boolean update(Order existing_order) {
		return (OrderDatabase.data_table.put(existing_order.getProductID(), existing_order) != null);
	}

}