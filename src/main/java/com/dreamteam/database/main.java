package com.dreamteam.database;


import java.io.*;
import java.time.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 */
public class main {
	// Variable Declarations
	static private final ProductDatabase product_database = ProductDatabase.getProducts();
	static private final OrderDatabase order_database = OrderDatabase.getOrders();
	public static Scanner sc = new Scanner(System.in);
	
	// Menu Option Structure: order of options is the order displayed in the menu.
	enum Options {
		CREATE(1),
		READ(2),
		UPDATE(3),
		DELETE(4),
		PROCESS_ORDERS(5),
		REPORTS(6),
		QUIT(7);
		
		private Options(int v) {
			this.value = v;
		}
		
		private int value;
		
		public int getValue() { return value; }
	}
	//	***************************************************************************
	
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

	} // End main method.
		
	//	***************************************************************************

	/*A demonstration of how to use the CRUD methods on an active, visible database object.*/
	private static final void demo_database() {
		
		System.out.println("\nTesting the product database.");
		System.out.println("-----------------------------");

		String existing_product_id = "8XXKZRELM2JJ";
		String new_product = "AGEXCVFG3344,3260,370.51,623.94,SASERNVV";
		Product existing_product;
		Product created_product;
		// Product updated_product = new Product("AGEXCVFG3344,25,370.51,623.94,SASERNVV".split(","));
		
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

	//	***************************************************************************
	
	/**
	 * Prompt the user for a correct option of the existing menu.
	 *
	 * @return the selected option to menu.
	 */
	public static Options getOption() {
		
		// Local Variable Declarations
		int user_input;
		
		while(true) {
			
			// Prompt user for a choice from Options.
			System.out.println("\nOptions:");
			for(Options choice: Options.values()) {
				System.out.println("  " + choice.getValue() + ": " + choice);
			}
			System.out.print("? ");
			
			try {
				user_input = Integer.parseInt(sc.nextLine());
				
				for(Options user_choice: Options.values()) {
					if(user_input == user_choice.getValue()) {
						return user_choice;
					}
				}
			}
			catch(NumberFormatException ex) {
				
				System.err.println(ex.getMessage());
				System.out.println("Error! Incorrect format.");
			}
			
			System.out.println("That option does not exist.");
		}
	}
	
	//	***************************************************************************
	
	/**
	 * Loops through the options of a menu for the user to access and modify the database.
	 */
	public static void runMenu() {
		
		// Local Variable Declarations
		Options user_choice;
		// String[] database_header = new String[product_database.get_column_size()];
		String[] new_entry = new String[product_database.get_column_size()];
		Product existing_entry;
		
		String[] database_header = product_database.get_data_head();

		System.out.println("\n-----------------------------");
		System.out.println("       Launching Menu        ");
		System.out.println("-----------------------------\n");
		
		do {
			
			product_database.display();
			order_database.display();
			user_choice = getOption();
			
			switch(user_choice) {
				
				case CREATE:
					
					for(int i = 0; i < database_header.length; i++) {
						System.out.print("Enter " + database_header[i] + ": ");
						new_entry[i] = sc.nextLine();
					}
					product_database.create(new_entry);
					
					Product new_product = product_database.read(new_entry[0]); // Prints the object address in memory.
					new_product.prettyPrint();

					break;
				
				case READ:
					
					System.out.print("Enter " + database_header[0] + ": ");
					existing_entry = product_database.read(sc.nextLine());
					
					if(existing_entry != null) {
						existing_entry.prettyPrint();
					}
					
					break;
				
				case UPDATE:
					
					System.out.print("Enter existing entry's " + database_header[0] + ": ");
					existing_entry = product_database.read(sc.nextLine());
					
					boolean is_simulation = true;
					if(is_simulation) {
						
						System.out.println("Are you buying? y/n");
						Boolean isBuyer = (("y".equals(sc.nextLine())) ? true : false);
						
						System.out.print("Enter the desired quantity: ");
						int requestQuantity = Integer.parseInt(sc.nextLine());
						
						if(isBuyer) {
							existing_entry.buyQuantity(requestQuantity);
						} else {
							existing_entry.supplyQuantity(requestQuantity);
						}
						
						System.out.print("Enter customer Id: ");
						String customer = sc.nextLine();
						String date = LocalDate.now().toString();
						String time = LocalDateTime.now().toString();
						
						try {
							updateCustomerHistory(customer, date, time);
						}
						catch(IOException e) {
							System.err.println(e);
						}
					} else {
						String[] fields = new String[database_header.length];
						for(int i = 1; i < database_header.length; i++) {
							System.out.print("Enter new entry's " + database_header[i] + ": ");
							fields[i]  = sc.nextLine();
						}
						existing_entry = new Product(fields);
					}
					
					existing_entry.prettyPrint();
					break;
				
				case DELETE:
					
					System.out.print("Enter " + database_header[0] + ": ");
					product_database.delete(sc.nextLine());

					break;
				
				case PROCESS_ORDERS:
					order_database.processOrders();

					dailyAssetsReport("2020-01-23");
					dailyTopTenReport("2020-01-23");

					System.out.println("Simulation processed.");
					
					break;

				case REPORTS:

					System.out.println("For which date would you like reports?");
					String date = sc.nextLine();

					System.out.println("Reports generating...");

					dailyAssetsReport(date);
					dailyTopTenReport(date);

					break;
				
				case QUIT:
					
					System.out.println("\nSaving Database changes...");
					System.out.println("Done!");
					System.out.println("Bye!");
					break;
				
				default:
					
					System.out.println("The selected option exists, but is not implemented yet.");
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
		try {
			File myObj = new File("files\\dailyreport_" + date + ".txt");
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
			FileWriter myWriter = new FileWriter("files\\dailyreport_" + date + ".txt");
			myWriter.write("The company's total value in assets for "  + date + " is " + formatter.format(product_database.countAssets()) + "\n");
			myWriter.write("The total number of customer orders for "  + date + " is " + OrderDatabase.countDailyOrders(order_database.findDailyOrders(date)) + "\n");
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
	private static void updateCustomerHistory(String customer, String date, String time)
	throws IOException {
		String location = "files/supplier_history.csv";
		
		try {
			FileWriter writer = new FileWriter(location, true);
			writer.append(customer); // we would append the order from the events into here
			writer.append(", ");
			writer.append(date);
			writer.append(", ");
			writer.append(time);
			writer.append('\n');
			
			System.out.println("CSV file is created...");
			writer.flush();
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
} // End main class. EOF