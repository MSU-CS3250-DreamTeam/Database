package com.dreamteam.database;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.EnumSet;
import java.util.Scanner;

/**
 *
 */
public class main {
	// Variable Declarations
	static private final ProductDatabase PRODUCT_DATABASE = ProductDatabase.getProducts();
	static private final OrderDatabase ORDER_DATABASE = OrderDatabase.getOrders();
	static private final Scanner MAIN_SCANNER = new Scanner(System.in);

	// ***************************************************************************

	/**
	 * @param args
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
		MAIN_SCANNER.close();
	} // End main method.

	// ***************************************************************************

	/*
	 * A demonstration of how to use the CRUD methods on an active, visible database
	 * object.
	 */
	private static void demo_database() {

		System.out.println("\nTesting the product database.");
		System.out.println("-----------------------------");

		String existing_product_id = "8XXKZRELM2JJ";
		String new_product = "AGEXCVFG3344,3260,370.51,623.94,SASERNVV";
		Product existing_product;
		Product created_product;

		System.out.print("\nRetrieving a product: ");
		existing_product = PRODUCT_DATABASE.read(existing_product_id);
		existing_product.prettyPrint();

		System.out.print("\nRemoving a product: ");
		if (PRODUCT_DATABASE.delete(existing_product_id))
			System.out.println("product removed.");
		PRODUCT_DATABASE.display();

		System.out.print("\nExisting product should not be found: ");
		PRODUCT_DATABASE.read(existing_product_id);

		System.out.print("\nNew product should be found: ");
		PRODUCT_DATABASE.create(new_product);
		PRODUCT_DATABASE.display();
		created_product = PRODUCT_DATABASE.read(new_product.split(",")[0]);

		System.out.print("\nRetrieving a product. ");
		PRODUCT_DATABASE.read(created_product.getProductID()).prettyPrint();

		System.out.print("\nBuy quantity of 4000: ");
		created_product.buyQuantity(4000);

		System.out.print("\nRetrieving updated product in the product database: ");
		PRODUCT_DATABASE.read(created_product.getProductID()).prettyPrint();

		System.out.print("\n\nRemoving dummy product: ");
		if (PRODUCT_DATABASE.delete(created_product.getProductID()))
			System.out.println("product removed.");
		PRODUCT_DATABASE.display();

		PRODUCT_DATABASE.create(existing_product);

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
		Options user_choice;
		final EnumSet<Options> MAIN_MENU = EnumSet.of(Options.CREATE, Options.READ, Options.UPDATE,
								Options.DELETE, Options.PROCESS_ORDERS, Options.REPORTS, Options.QUIT);
		Menu menu = new Menu(MAIN_MENU);
		String[] database_header = PRODUCT_DATABASE.get_data_head();
		String[] new_entry = new String[PRODUCT_DATABASE.get_column_size()];
		Product existing_entry;

		System.out.println("\n-----------------------------");
		System.out.println("       Launching Menu        ");
		System.out.println("-----------------------------\n");

		do {

			PRODUCT_DATABASE.display();
			ORDER_DATABASE.display();
			System.out.println();
			user_choice = menu.getOption(MAIN_SCANNER);

			switch (user_choice) {

				case CREATE:

					for (int i = 0; i < database_header.length; i++) {
						System.out.print("Enter " + database_header[i] + ": ");
						new_entry[i] = MAIN_SCANNER.nextLine();
					}
					PRODUCT_DATABASE.create(new_entry);
					
					Product new_product = PRODUCT_DATABASE.read(new_entry[0]); // Prints the object address in memory.
					new_product.prettyPrint();

					break;
				
				case READ:
					
					System.out.print("Enter " + database_header[0] + ": ");
					existing_entry = PRODUCT_DATABASE.read(MAIN_SCANNER.nextLine());
					
					if(existing_entry != null) {
						existing_entry.prettyPrint();
					}
					
					break;
				
				case UPDATE:
					
					System.out.print("Enter existing entry's " + database_header[0] + ": ");
					existing_entry = PRODUCT_DATABASE.read(MAIN_SCANNER.nextLine());
					
					if ((existing_entry != null) && !(existing_entry.getProductID().equals("000"))) {
						PRODUCT_DATABASE.update(existing_entry, MAIN_SCANNER);
						existing_entry.prettyPrint();
					}

					break;
				
				case DELETE:
					
					System.out.print("Enter " + database_header[0] + ": ");
					PRODUCT_DATABASE.delete(MAIN_SCANNER.nextLine());

					break;
				
				case PROCESS_ORDERS:
					ORDER_DATABASE.processOrders();

					System.out.println("Simulation processed.");
					
					break;

				case REPORTS:

					System.out.println("For which date would you like reports?");
					String date = MAIN_SCANNER.nextLine();
					if (ORDER_DATABASE.contains(date)) {
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

	/**
	 *
	 * @param date
	 */
	public static void dailyAssetsReport(String date) {  //prints out report to console and txt file with assets in product_database and customer orders and sales in order_database
		NumberFormat formatter = NumberFormat.getCurrencyInstance(); //to format the print statements in dollar form
		String report_path = "files/reports/daily-report_" + date + ".txt";

		try (FileWriter report_writer = new FileWriter(report_path, false)) {
			report_writer.write("The company's total value in assets for "  + date + " is " + formatter.format(PRODUCT_DATABASE.countAssets()) + "\n");
			report_writer.write("The total number of customer orders for "  + date + " is " + OrderDatabase.countDailyOrders(date) + "\n");
			report_writer.write("The total dollar amount of all orders for "  + date + " is " + formatter.format(OrderDatabase.countSales(date)) + "\n");

			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("Failed to create or write to file.");
			e.printStackTrace();
		}
	}

	
	//	***************************************************************************	

	//	TODO Write the top products and customers (by spending) to a daily report file

	/**
	 *
	 * @param date
	 */
	private static void dailyTopTenReport(String date) {
		ORDER_DATABASE.findTopCustomers(date);
		ORDER_DATABASE.findTopProducts(date);
	}

	//	***************************************************************************
	
	/**
	 * 
	 * @param customer
	 * @param date
	 * @param time
	 */
	protected static void updateCustomerHistory(String customer, String date, String time) {
		String location = "files/buyer_order_history.csv";
		String new_order = customer + ", " + date + ", " + time + '\n';
		
		try (FileWriter writer = new FileWriter(location, true)) {
			writer.append(new_order);
			
			System.out.println("Realtime order appended to file in relative path: " + location);
			writer.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
} // End main class. EOF
