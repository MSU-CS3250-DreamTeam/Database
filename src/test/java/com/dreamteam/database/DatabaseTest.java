package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
	private static final String SPREAD_SHEET = "inventory_team1.csv";
	private static Database new_database;
	
	@Test void buyTest() throws FileNotFoundException {
		Database db = new Database(SPREAD_SHEET);
		File new_file = new File("buyer_event.csv");
		if(new_file.exists()) {
			Scanner data_input = new Scanner(new_file);
			data_input
			 .nextLine(); // Return and throw away column headers of file.
			
			Queue<BuyerEvent> buyerEvents = new ArrayDeque<>();
			while(data_input.hasNextLine()) {
				BuyerEvent event = new BuyerEvent(data_input.nextLine());
				buyerEvents.add(event);
				System.out.println(event);
				BuyerEvent.updateQuantity(event);
				
				//Close the program and print out the total number of Buyer 
				// Events
				System.out.println(
				 "Total number of Buyer Events: " + buyerEvents.size() + "\n");
				System.out.println("Buyer Event Simulation Complete");
				
				String[] data_row = data_input.nextLine().split(",");
				String[] entry_row = new String[] {data_row[3], data_row[4]};
			}
			data_input.close();
			for(BuyerEvent buyerEvent: buyerEvents) {
				String id = buyerEvent.getProduct_id();
				int buyer_quantity = buyerEvent.getQuantity();
				System.out
				 .println(
				  "Can we process the order for " + buyerEvent.getProduct_id() +
				  "?\n"
				  + db.canProcessOrder(id, buyer_quantity));
			}
		}
	}
	
	@Test void supplyTest() throws FileNotFoundException {
		File new_file = new File("src/test/supplier_event.csv");
		int quantity = 0;
		String[] database_entry = new String[5];
		
		if(new_file.exists()) {
			
			Scanner data_input = new Scanner(new_file);
			data_input
			 .nextLine(); // Return and throw away column headers of file.
			
			while(data_input.hasNextLine()) {
				Entry entry = new Entry("ABC123", 200, 4.99, 199.99, "XYZ890");
				String[] array = data_input.nextLine().split(",");
				SupplierEvent supplierEvent = new SupplierEvent(
				 array[0],// 9/16/2020
				 array[1],// buyingallyourthings@gmail.com
				 array[2],// 123 Coriander Way
				 array[3],// 2CE1RNHR6OV1
				 Integer.parseInt(array[4])// 10
				);
				assertTrue(entry.addQuantity(300));
				assertEquals(500, entry.getQuantity());
				assertFalse(entry.addQuantity(-99999));
				
				// take is some seller events
				// assertTrue(quantity is positive) if it's a positive number 
				// for quantity, then incerement it to the specified product_id
				// assertTrue(quantity is negative) because otherwise it does 
				// not make sense
				
/*				String[] data_row = data_input.nextLine().split(",");
				String[] entry_row = new String[] {
				 data_row[2], data_row[3]
				};
				
				new_database.createEntry(data_row);
				database_entry = new_database.read();
				quantity = Integer.parseInt(database_entry[1]) + Integer
				.parseInt(entry_row[1]);
				database_entry[1] = Integer.toString(quantity);
				new_database.update();
				
				assertEquals(Integer.toString(quantity), new_database.read
				(entry_row[0])[1]);
				// assertEquals("6", new_database.read(entry_row[0])[1]);*/
				
			}
			
			data_input.close();
		} else {
			throw new FileNotFoundException("Is the data file in the wrong " +
											"directory?");
		}
	}
	
	//{
	//	throw new FileNotFoundException("Is the data file in the wrong 
	//	directory?");
	//}
}
