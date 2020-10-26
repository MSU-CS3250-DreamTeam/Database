package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class main {
	// Variable Declarations
	static private final ProductDatabase product_database = ProductDatabase.getProducts();
	static private final OrderDatabase order_database = OrderDatabase.getOrders();
	public static Scanner main_scanner = new Scanner(System.in);

	// ***************************************************************************

	/**
	 * @param args
	 *
	 * @throws FileNotFoundException
	 */
	static public void main(String[] args) {

		// Welcome to DreamTeam DataBase
		System.out.println("-------------------------------------------------------------------");
		System.out.println("               Welcome to DreamTeam DataBase                       ");
		System.out.println("-------------------------------------------------------------------");

		// For debugging. Disable in final project.
		demo_database();

		// Call the menu for user to access and modify the database.
		runMenu();
		main_scanner.close();
	} // End main method.

	// ***************************************************************************

	/*
	 * A demonstration of how to use the CRUD methods on an active, visible database
	 * object.
	 */
	private static final void demo_database() {

		System.out.println("\nTesting the product database.");
		System.out.println("-----------------------------");

		String existing_product_id = "8XXKZRELM2JJ";
		String new_product = "AGEXCVFG3344,3260,370.51,623.94,SASERNVV";
		Product existing_product;
		Product created_product;

		System.out.print("\nRetrieving a product: ");
		existing_product = product_database.read(existing_product_id);
		existing_product.prettyPrint();

		System.out.print("\nRemoving a product: ");
		if (product_database.delete(existing_product_id))
			System.out.println("product removed.");
		product_database.display();

		System.out.print("\nExisting product should not be found: ");
		product_database.read(existing_product_id);

		System.out.print("\nNew product should be found: ");
		product_database.create(new_product);
		product_database.display();
		created_product = product_database.read(new_product.split(",")[0]);

		System.out.print("\nRetrieving a product. ");
		product_database.read(created_product.getProductID()).prettyPrint();

		System.out.print("\nBuy quantity of 4000: ");
		created_product.buyQuantity(4000);

		System.out.print("\nRetrieving updated product in the product database: ");
		product_database.read(created_product.getProductID()).prettyPrint();

		System.out.print("\n\nRemoving dummy product: ");
		if (product_database.delete(created_product.getProductID()))
			System.out.println("product removed.");
		product_database.display();

		product_database.create(existing_product);

		System.out.println("\n\n-----------------------------");
		System.out.println("      Testing complete.      ");
		System.out.println("-----------------------------\n");

	}

	// ***************************************************************************

	/**
	 * Loops through the options of a menu for the user to access and modify the
	 * database.
	 */
	public static void runMenu() {

		// Local Variable Declarations
		Options user_choice = null;
		final List<Options> MAIN_MENU = List.of(Options.CREATE, Options.READ, Options.UPDATE,
								Options.DELETE, Options.PROCESS_ORDERS, Options.REPORTS, Options.QUIT);
		Menu menu = new Menu(MAIN_MENU);
		String[] database_header = product_database.get_data_head();
		String[] new_entry = new String[product_database.get_column_size()];
		Product existing_entry = null;

		System.out.println("\n-----------------------------");
		System.out.println("       Launching Menu        ");
		System.out.println("-----------------------------\n");

		do {

			product_database.display();
			order_database.display();
			System.out.println();
			user_choice = menu.getOption();

			switch (user_choice) {

				case CREATE:

					for (int i = 0; i < database_header.length; i++) {
						System.out.print("Enter " + database_header[i] + ": ");
						new_entry[i] = main_scanner.nextLine();
					}
					product_database.create(new_entry);
					
					Product new_product = product_database.read(new_entry[0]); // Prints the object address in memory.
					new_product.prettyPrint();

					break;
				
				case READ:
					
					System.out.print("Enter " + database_header[0] + ": ");
					existing_entry = product_database.read(main_scanner.nextLine());
					
					if(existing_entry != null) {
						existing_entry.prettyPrint();
					}
					
					break;
				
				case UPDATE:
					
					System.out.print("Enter existing entry's " + database_header[0] + ": ");
					existing_entry = product_database.read(main_scanner.nextLine());
					
					if ((existing_entry != null) && (existing_entry.getProductID() != "000")) {
						product_database.update(existing_entry);
						existing_entry.prettyPrint();
					}

					break;
				
				case DELETE:
					
					System.out.print("Enter " + database_header[0] + ": ");
					product_database.delete(main_scanner.nextLine());

					break;
				
				case PROCESS_ORDERS:
					order_database.processOrders();

					System.out.println("Simulation processed.");
					
					break;

				case REPORTS:

					System.out.println("For which date would you like reports?");
					String date = main_scanner.nextLine();
					if (order_database.contains(date)) {
						System.out.println("Reports generating...");

						dailyAssetsReport(date);
						dailyTopTenReport(date);
					}

					break;
				
				case QUIT:
					
					System.out.println("\nSaving Database changes...");
					System.out.println("Done!");
					System.out.println("Bye!");
					break;
				
				default:
					System.out.println("The " + user_choice + " option is not in this menu.");
			}
		} while(user_choice != Options.QUIT);
	}

	//	***************************************************************************
	// TODO create a file with a date stamp like daily-report-10-18.txt under 'files/reports'

	/** TODO Write the networth of all assets to a daily report file.
	 * 
	 */

	public static void dailyAssetsReport(String date) {  //prints out report to console and txt file with assets in product_database and customer orders and sales in order_database
		NumberFormat formatter = NumberFormat.getCurrencyInstance(); //to format the print statements in dollar form
		String report_path = "files/reports/dailyreport_" + date + ".txt";
		
		try {
			File myObj = new File(report_path);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			FileWriter myWriter = new FileWriter(report_path);
			myWriter.write("The company's total value in assets for "  + date + " is " + formatter.format(product_database.countAssets()) + "\n");
			myWriter.write("The total number of customer orders for "  + date + " is " + OrderDatabase.countDailyOrders(date) + "\n");
			myWriter.write("The total dollar amount of all orders for "  + date + " is " + formatter.format(OrderDatabase.countSales(date)) + "\n");
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	
	//	***************************************************************************	

	/**	TODO Write the top ten products and customers (by spending) to a daily report file
	 * 
	 */
	private static void dailyTopTenReport(String date) {
		order_database.findTopCustomers(date);
		order_database.findTopProducts(date);
	}

	//	***************************************************************************
	
	/**
	 * 
	 * @param customer
	 * @param date
	 * @param time
	 * @throws IOException
	 */
	protected static void updateCustomerHistory(String customer, String date, String time)
	throws IOException {
		String location = "files/buyer_order_history.csv";
		
		try {
			FileWriter writer = new FileWriter(location, true);
			writer.append(customer); // we would append the order from the events into here
			writer.append(", ");
			writer.append(date);
			writer.append(", ");
			writer.append(time);
			writer.append('\n');
			
			System.out.println("Realtime order appended to file in relative path: " + location);
			writer.flush();
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
} // End main class. EOF
