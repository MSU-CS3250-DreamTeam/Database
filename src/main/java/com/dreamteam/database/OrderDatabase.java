package com.dreamteam.database;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;

public class OrderDatabase implements Database<Order> {
	/**
	 * Member Variables
	 */
	private static String[] data_head; // The column labels of the data structure.
	private static HashMap<String, HashSet<Order>> data_table;
	/**
	 * Construction
	 */
	private static final OrderDatabase ORDERS = new OrderDatabase();
	
	private OrderDatabase()
	{
		
		final String file_path = "files/customer_history.csv";
		File order_history = new File(file_path);
		
		try(Scanner dbScanner = new Scanner(order_history))
		{
			
			data_table = new HashMap<>();
			
			if(dbScanner.hasNextLine())
			{
				OrderDatabase.data_head = dbScanner.nextLine().split(",");
			}
			
			String dbRow;
			
			while(dbScanner.hasNextLine())
			{
				dbRow = dbScanner.nextLine();
				create(dbRow);
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Is the data file " + file_path + " in the wrong directory?");
		}
	}
	
	/**
	 * Getters
	 */
	public static OrderDatabase getOrders()
	{
		return ORDERS;
	}
	
	@Override
	public String[] get_data_head()
	{
		return OrderDatabase.data_head;
	}
	
	/**
	 * Setters
	 */
	@Override
	public void set_data_head(String[] labels)
	{
		OrderDatabase.data_head = labels;
	}
	
	/* Class Methods (Alphabetical Order) */
	// TODO javadoc for class methods without @override.
	
	/**
	 * @param order
	 * @throws IOException
	 */
	private static void appendCustomerHistory(Order order) throws IOException
	{
		String location = "files/customer_history.csv";
		
		FileWriter writer = new FileWriter(location, true);
		writer.append(order.toString()).append("\n");
		
		writer.flush();
		writer.close();
	}
	
	/**
	 * @param date
	 * @return
	 */
	public boolean contains(String date)
	{
		boolean hasDate = data_table.containsKey(date);
		if(!hasDate)
		{
			System.out.println("The order database has no orders for the date: " + date);
		}
		return hasDate;
	}
	
	/**
	 * @param new_order
	 * @return
	 */
	public boolean contains(Order new_order)
	{
		final boolean COMPARE_TIMES = false;
		String order_date = new_order.getDate();
		String order_details = new_order.toString(COMPARE_TIMES);
		HashSet<Order> orders = data_table.get(order_date);
		if(orders != null)
		{
			for(Order o: orders)
			{
				if(o.toString(COMPARE_TIMES).equals(order_details))
				{
					System.out.println("An identical order already exists: " + o.toString());
					return true;
				}
			}
		}
		return false;
	}
	
	public int countDailyOrders(String date)
	{
		return data_table.get(date).size();
	}
	
	public double countSales(String date) {

		ProductDatabase product_database = ProductDatabase.getProducts();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		Iterator<Order> it = data_table.get(date).iterator();
		
		double totalSales = 0.00;
		Order current;
		double price;
		int quantity;
		String productID;
		
		while(it.hasNext())
		{
			current = it.next();
			productID = current.getProductID();
			price = product_database.read(productID).getSalePrice();
			quantity = current.getQuantity();
			totalSales += price * quantity;
		}
		
		System.out
		 .println("The company's total sale for " + date + " are: " + formatter.format(totalSales));
		return totalSales;
	}
	
	@Override
	public void create(Order new_order)
	{
		String order_date = new_order.getDate();
		HashSet<Order> orders = data_table.get(order_date);
		if(!contains(new_order))
		{
			if(orders == null)
			{
				orders = new HashSet<>();
				data_table.put(new_order.getDate(), orders);
			}
			orders.add(new_order);
		}
	}
	
	@Override
	public void create(String[] entry_string)
	{
		Order new_order = new Order(entry_string);
		create(new_order);
	}
	
	@Override
	public boolean delete(String id)
	{
		boolean isRemoved = false;
		if(OrderDatabase.data_table.containsKey(id))
		{
			isRemoved = (OrderDatabase.data_table.remove(id) != null);
		}
		
		return isRemoved;
	}
	
	@Override
	public void display()
	{
		int number_of_orders = 0;
		Set<String> dates = data_table.keySet();
		for(String date: dates)
		{
			number_of_orders += data_table.get(date).size();
		}
		System.out.println("The orders database has " + number_of_orders + " orders.");
	}
	
	// TODO Find the top products (by spending) and return
	
	/**
	 * @param date
	 * @return a string array of top product's ids to use in reports/etc.
	 */

	public LinkedHashMap<String, Double> findTopProducts(String date) {
		LinkedHashMap<String, Double> products = new LinkedHashMap<>();
		HashSet<Order> date_orders = data_table.get(date);
		TreeMap<Double, Order> mapper = new TreeMap<>();
		ProductDatabase z = ProductDatabase.getProducts();
		double price = 0;

		for (Order next_order : date_orders) {
			price = z.read(next_order.getProductID()).getSalePrice() * next_order.getQuantity();
			mapper.put(price, next_order);
		}

		ArrayList<Double> order_keys = new ArrayList<>(mapper.keySet());
		Collections.sort(order_keys);
		Collections.reverse(order_keys);
		for (double x: order_keys) {
			System.out.println("Key = " + mapper.get(x).getProductID() +
					", Value = " + x);
			products.put(mapper.get(x).getProductID(), x);
		}
		return products;
	}
	
	// TODO Find the top customers (by spending) and return
	
	/**
	 * @param date
	 * @return a string array of top customer's ids to use in reports/etc.
	 */

	public LinkedHashMap<String, Double> findTopCustomers(String date) {
		LinkedHashMap<String, Double> customers = new LinkedHashMap<>();
		HashSet<Order> date_orders = data_table.get(date);
		TreeMap<String, Double> mapper = new TreeMap<>();
		TreeMap<Double, String> inverse_mapper = new TreeMap<>();
		HashSet<String> email_mapper = new HashSet<>();
		ProductDatabase z = ProductDatabase.getProducts();
		double price = 0;

		for (Order next_order : date_orders) {
			price = z.read(next_order.getProductID()).getSalePrice() * next_order.getQuantity();
			if (email_mapper.contains(next_order.getEmail())) {
				mapper.put(next_order.getEmail(), mapper.get(next_order.getEmail()) + price);
			}
			else {
				mapper.put(next_order.getEmail(), price);
				email_mapper.add(next_order.getEmail());
			}

		}
		for (String email : mapper.keySet()) {
			inverse_mapper.put(mapper.get(email), email);
		}

		ArrayList<Double> order_keys = new ArrayList<>(mapper.values());
		Collections.sort(order_keys);
		Collections.reverse(order_keys);
		for (double x: order_keys) {
			System.out.println("Key = " + inverse_mapper.get(x) +
					", Value = " + x);
			customers.put(inverse_mapper.get(x), x);
		}

		return customers;
	}
	
	/**
	 *
	 */
	public void processOrders()
	{
		
		// Cleans out annual-plot-data csv for a fresh simulation.
		try(FileWriter eraser = new FileWriter(new File("files/annual-plot-data.csv"), false))
		{
			eraser.write("");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		String order_log_path = "files/customer_orders_A_team1.csv";
		Order processed_order;
		String next_order;
		Product existing_product;
		String date = "2020-01-01";
		ProductDatabase products = ProductDatabase.getProducts();
		File order_log = new File(order_log_path);
		
		try(Scanner order_scanner = new Scanner(order_log))
		{
			
			order_scanner.nextLine();
			
			while(order_scanner.hasNextLine())
			{
				next_order = order_scanner.nextLine();
				create(next_order);
				
				if(!next_order.contains(date) && date != null)
				{
					main.dailyAssetsReport(date);
					date = next_order.substring(0, date.length());
					System.out.println(date);
				}
				
				processed_order = read(date, next_order);
				existing_product = products.read(processed_order.getProductID());
				
				if(existing_product.buyQuantity(processed_order.getQuantity()))
				{
					try
					{
						appendCustomerHistory(processed_order);
					}
					catch(IOException e)
					{
						e.printStackTrace();
						System.out
						 .println(processed_order.getEmail() + " could not be written to file.");
					}
				}
				else
				{
					System.out.println(processed_order.getEmail() + " could not be processed.");
				}
			}
			if (date != null) {
				main.dailyAssetsReport(date);
			}
			
			// Wipes the file of customer orders, so disabled for convenience.
//			try (FileWriter fWriter = new FileWriter(order_log_path, false)) {
//
//				StringBuilder string_head = new StringBuilder();
//
//				for (String value : OrderDatabase.data_head) {
//					if (!value.equals("time"))
//						string_head.append(value).append(",");
//				}
//
//				fWriter.write(string_head.toString() + '\n');
//
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			
			System.out.println("The orders are processed and appended to history.");
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("Is the data file " + order_log_path + " in the wrong directory?");
		}
	}
	
	@Override
	public Order read(String date)
	{
		if(OrderDatabase.data_table.containsKey(date))
		{
			HashSet<Order> orders = data_table.get(date);
			Scanner retrieve_scanner = new Scanner(System.in);
			
			System.out.println("Select the order you wish to retrieve: ");
			
			for(Order order: orders)
			{
				System.out.println("Is this your order y/n? \n" + order.toString());
				if("y".equals(retrieve_scanner.nextLine().toLowerCase()))
				{
					return order;
				}
			}
		}
		
		System.out.println("The order was not found.");
		return new Order("000,000,000,000,000".split(","));
	}
	
	public Order read(String date, String order)
	{
		HashSet<Order> orders = data_table.get(date);
		for(Order o: orders)
		{
			if(o.toString(false).equals(order))
			{
				return o;
			}
		}
		
		System.out.println("The order was not found.");
		return new Order("000,000,000,000,000".split(","));
	}
	
	// TODO control flow like ProductDatabase.update() method for field setters. Try c&v, then 
	//  adapt for orders.
	@Override
	public boolean update(Order existing_order, Scanner order_scanner)
	{
		HashSet<Order> matching_orders = data_table.get(existing_order.getDate());
		return matching_orders.contains(existing_order);
	}
}
