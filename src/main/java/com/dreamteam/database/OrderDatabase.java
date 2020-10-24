package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class OrderDatabase implements Database<Order> {
	
	/** Member Variables */

	private static String[] data_head; // The column labels of the data structure.
	private static HashMap<String, Order> data_table;

	/** Construction */

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

	/** Getters */

	/**
	 * 
	 * @return a reference to the OrderDatabase instance.
	 */
	public static OrderDatabase getOrders() {
		return ORDERS;
	}

	@Override
	public String[] get_data_head() { return OrderDatabase.data_head; }

	/** Setters */

	@Override
	public void set_data_head(String[] labels) { OrderDatabase.data_head = labels; }

	/** Class Methods (Alphabetical Order) */
	// TODO javadoc for class methods without @override.

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

	public static int countDailyOrders(String date) {
		return findDailyOrders(date).size();
	}

	public static double countSales(String date) {
		ProductDatabase product_database = ProductDatabase.getProducts();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		Iterator<HashMap.Entry<String,Order>> it = findDailyOrders(date).entrySet().iterator();

		double totalSales = 0.00;
		Order current;
		double price;
		int quantity;
		String productID;

		while (it.hasNext()) {
			HashMap.Entry<String,Order> pair = (HashMap.Entry<String,Order>) it.next();
			current = (Order) pair.getValue();
			productID = current.getProductID();
			price = product_database.read(productID).getSalePrice();
			quantity = current.getQuantity();
			totalSales += price * quantity;
		}

		System.out.println("The company's total sale for " + date + " are: " + formatter.format(totalSales));
		return totalSales;
	}

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

	@Override
	public boolean delete(String id) {
		boolean isRemoved = false;
		if (OrderDatabase.data_table.containsKey(id))
			isRemoved = (OrderDatabase.data_table.remove(id) != null);
		
		return isRemoved;
    }

	@Override
	public void display() {
		System.out.println("The orders database has " + OrderDatabase.data_table.size() + " orders.");
	}

	// TODO Find matching orders by date and put in the returning hashmap.
	/**
	 * 
	 * @param date
	 * @return
	 */

	public static HashMap<String,Order> findDailyOrders(String date) {

		HashMap<String,Order> orders = new HashMap<>(); // orders<order_id, order>
		for (Order order:data_table.values()) {
			if (order.getDate().equals(date))
				orders.put(order.getOrderID(), order);
		} 
		return orders;
	} 

	//TODO Find the top ten products and customers (by spending) and return
	/**
	 * 
	 * @param date
	 * @return a string array of top product's ids to use in reports/etc.
	 */
	public Order[] findTopProducts(String date) {
		Order[] products = new Order[10];
		HashMap<String,Order> date_orders = findDailyOrders(date);
		
		return products;
	}

	//TODO Find the top ten customers (by spending) and return
	/**
	 * 
	 * @param date
	 * @return @return a string array of top customer's ids to use in reports/etc.
	 */
	public Order[] findTopCustomers(String date) {
		Order[] customers = new Order[10];
		HashMap<String,Order> date_orders = findDailyOrders(date);

		return customers;
	}

	/**
	 * 
	 */
	public void processOrders() {

		String source_file_path = "files/customer_orders_A_team1.csv";
		Order processed_order = null;
		String unique_key;
		String dbRow;
		String[] rowArray;
		Product existing_product;
		String date = "2020-01-01";
		ProductDatabase products = ProductDatabase.getProducts();

		try {

			File order_log = new File(source_file_path);
			Scanner dbScanner = new Scanner(order_log);
			dbScanner.nextLine();

			while (dbScanner.hasNextLine()) {
				dbRow = dbScanner.nextLine();
				create(dbRow);
				rowArray = dbRow.split(",");
				unique_key = rowArray[2] + "-" + rowArray[3] + "-" + rowArray[0];
				processed_order = read(unique_key);

				if (!date.equals(processed_order.getDate())){
					main.dailyAssetsReport(date);
					date = processed_order.getDate();
				}

				existing_product = products.read(processed_order.getProductID());

				if (existing_product.buyQuantity(processed_order.getQuantity())) {
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
			main.dailyAssetsReport(date);

			dbScanner.close();

			try {
				FileWriter fWriter = new FileWriter(source_file_path, false);
				
				String string_head = "";

				for (String value : OrderDatabase.data_head){
					if (!value.equals("time"))
						string_head += value + ",";
				}

				fWriter.write(string_head + '\n');
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

	@Override
	public Order read(String id) {
		if (OrderDatabase.data_table.containsKey(id)) {
			return OrderDatabase.data_table.get(id);
		} else {
			System.out.println("The order was not found.");
			return new Order("000,000,000,000,000".split(","));
		}
		
	}

	@Override
    public boolean update(Order existing_order) {
		return (OrderDatabase.data_table.put(existing_order.getProductID(), existing_order) != null);
	}

}