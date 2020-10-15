package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 */
public class main {
	// Variable Declarations
	static private final String SPREAD_SHEET = "files/inventory_team1.csv";
	static private Database new_database;
	public static Scanner sc = new Scanner(System.in);
	
	// Menu Option Structure: order of options is the order displayed in the menu.
	enum Options {
		CREATE(1),
		READ(2),
		UPDATE(3),
		DELETE(4),
		AUTOMATE(5),
		QUIT(6);
		
		private Options(int v) {
			this.value = v;
		}
		
		private int value;
		
		public int getValue() { return value; }
	}
	/*A demonstration of how to use the CRUD methods on an active, visible database object.*/
	
	private static final void demo_database() {
		
		String existing_product_id = "8XXKZRELM2JJ";
		String fake_product = "AGEXCVFG3344,3260,370.51,623.94,SASERNVV";
		String updated_product = "8XXKZRELM2JJ,25,370.51,623.94,SASERNVV";
		
		System.out.print("Retrieving a product. ");
		new_database.read(existing_product_id);
		
		System.out.print("\nRemoving a product. ");
		new_database.delete(existing_product_id);
		new_database.display();
		
		System.out.print("Existing product should not be found: ");
		new_database.read(existing_product_id);
		
		System.out.print("\nNew product should be found.");
		new_database.create(fake_product);
		new_database.display();
		
		System.out.print("Retrieving a product. ");
		new_database.read(fake_product);
		
		System.out.print("\nUpdating a product. ");
		new_database.update(updated_product);
		
		System.out.print("\nRetrieving a product. ");
		new_database.read(fake_product);
		
		System.out.print("\nRemoving fake product. ");
		new_database.delete(fake_product);
	}
	
	/**
	 * Prompt the user for a correct option of the existing menu.
	 *
	 * @return the selected option to menu.
	 */
	public static Options getOption() {
		
		// Local Variable Declarations
		int user_input;
		int index;
		
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
	// TODO Finish menu options. Each option should call the corresponding Database.java method.
	// !!! Use these examples to use CRUD methods in the menu!
	
	/**
	 * @param args
	 *
	 * @throws FileNotFoundException
	 */
	static public void main(String[] args) throws FileNotFoundException {
		
		// Welcome to DreamTeam DataBase
		System.out.println("-------------------------------------------------------------------");
		System.out.println("               Welcome to DreamTeam DataBase                       ");
		System.out.println("-------------------------------------------------------------------");

		// Initializes the database to the spreadsheet's columns.
		new_database = new Database(SPREAD_SHEET);
		
		// For debugging. Disable in final project.
		demo_database();
		
		// Call the menu for user to access and modify the database.
		runMenu();

	} // End main method.
	
	//	***************************************************************************
	
	/**
	 * Loops through the options of a menu for the user to access and modify the database.
	 */
	public static void runMenu() {
		
		// Local Variable Declarations
		Options user_choice;
		String[] database_header = new String[new_database.get_column_size()];
		String[] new_entry = new String[new_database.get_column_size()];
		Entry existing_entry;
		
		database_header = new_database.get_data_head();
		
		do {
			
			user_choice = getOption();
			
			switch(user_choice) {
				
				case CREATE:
					
					for(int i = 0; i < database_header.length; i++) {
						System.out.print("Enter " + database_header[i] + ": ");
						new_entry[i] = sc.nextLine();
					}
					Entry record = new_database.create(new_entry);
					
					record.prettyPrint(); // Prints the object address in memory.
					// Adds a new product.
					break;
				
				case READ:
					
					System.out.print("Enter " + database_header[0] + ": ");
					existing_entry = new_database.read(sc.nextLine());
					
					if(existing_entry != null) {
						System.out.println(existing_entry);
						existing_entry.prettyPrint();
					}
					
					//System.out.println(new_database); // Prints the object address in memory.
					// Retrieves a product and displays it.
					break;
				
				case UPDATE:
					
					System.out.print("Enter existing entry's " + database_header[0] + ": ");
					existing_entry = new_database.read(sc.nextLine());
					
					boolean is_simulation = true;
					if(is_simulation) {
						
						System.out.println("Are you buying? y/n");
						Boolean isBuyer = (("y".equals(sc.nextLine())) ? true : false);
						
						// new_entry = existing_entry;
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
						Entry newEntry = Entry.parse(fields);
					}
					
					new_database.update(existing_entry);
					existing_entry.prettyPrint(); // Prints the object address in memory.
					// Updates a product
					break;
				
				case DELETE:
					
					System.out.print("Enter " + database_header[0] + ": ");
					new_database.delete(sc.nextLine());
					new_database.display();
					
					System.out.println(new_database); // Prints the object address in memory.
					// Deletes a product
					break;
				
				case AUTOMATE:
					
					System.out.println(new_database); // Prints the object address in memory.
					System.out.println("The selected option exists, but is not implemented yet.");
					// What does this do?
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
	
	private static void updateCustomerHistory(String customer, String date, String time)
	throws IOException {
		String location = "customer_history.csv";
		
		// if (customer != null) {}
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