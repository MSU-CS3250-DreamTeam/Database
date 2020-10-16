package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Database {
	// Tentative data structure. We can change this.
	private String[] data_head; // The column labels of the data structure.
	// Position of final entry in data structure minus the header.
	private static HashMap<String, Entry> data_table;

	public Database(String file_path) throws FileNotFoundException {
		data_table = new HashMap<>();
		if (file_path != null) {
			File inventory = new File(file_path);
			Scanner dbScanner = new Scanner(inventory);
			this.data_head = dbScanner.nextLine().split(",");
			while(dbScanner.hasNextLine()) {
				String[] dbRow = dbScanner.nextLine().split(",");
				Entry entry = new Entry(dbRow);
				data_table.put(entry.getProductID(), entry);
			}
			dbScanner.close();
		} else {
			throw new FileNotFoundException(
			 "Is the data file " + file_path + " in the wrong directory?");
		}
	}
	

	// TODOd polish, test, refactor, redesign,etc.
	// A list of class private variables.
	
	/* We don't need to keep track of the number of rows anymore. HashMap will resize dynamically 
	on its own.
	private static int rows = 3000; // Entry capacity of data structure. */
	
	/**
	 * Check to see if we can make a sale
	 *
	 * @param attemptedQuantity
	 *  which we need to have enough of
	 */
	public Boolean canProcessOrder(String id, int attemptedQuantity) {
		Entry entry = data_table.get(id);
		if(entry != null) {
			
			return attemptedQuantity <= entry.getQuantity();
		}
		return false;
	}
	
	public boolean contains(String product_id) {
		return data_table.containsKey(product_id);
	}
	
	public Entry create(String entryString) {
		return parseEntry(entryString.split(","));
	}
	
	public Entry create(String[] entryString) {
		return parseEntry(entryString);
	}
	
	/**
	 * Delete existing entry from database.
	 *
	 * @param id
	 *
	 * @return the old Entry if there was one.
	 *  otherwise, null
	 */
	public Entry delete(String id) {
		return data_table.remove(id);
	}
	
	/**
	 * Prints the entire database to console. May want to disable in finished project.
	 * Also prints the number of current entries in database.
	 */
	public void display() {
		// StringBuilder builder = new StringBuilder();
		// for(int i = 0; i < data_head.length; i++) {
		// 	builder.append(data_head[i])
		// 		   .append(i < data_head.length - 1 ? "," : "");
		// }
		// for(Entry value: data_table.values()) {
		// 	builder.append(value.toString());
		// }
		// System.out.println(builder.toString());
		// System.out.println("\nThere are " + data_table.size() +
		// 				   " entries recorded in the database.\n");
	}
	
	public int get_column_size() {
		return this.data_head.length;
	}
	
	public String[] get_data_head() {
		return this.data_head;
	}
	
	/** Create a new Entry from a String array. */
	private static Entry parseEntry(String[] temp) {
		String productID = temp[0] + "";
		return Entry.getEntry(temp, productID);
	}
	
	/**
	 * Read existing entry from database.
	 *
	 * @param id
	 *
	 * @return the entry of the database if found.
	 */
	public Entry read(String id) throws NullPointerException {
		return data_table.get(id);
	}
	
	public void set_data_head(String[] labels) {
		this.data_head = labels;
	}
	
	/**
	 * Find existing entry in database and update with new entry.
	 *
	 * @param newEntry
	 *  the new entry to overwrite the old one
	 *
	 * @return the old entry, in case we want to do something with it
	 */
	public Entry update(Entry newEntry) {
		return data_table.put(newEntry.getProductID(), newEntry);
	}
	
	public Entry update(String string) {
		Entry newEntry = create(string);
		return data_table.put(newEntry.getProductID(), newEntry);
	}
	
	// @Override public String toString() {
	// 	StringBuilder sb = new StringBuilder();
	// 	String headers = Arrays.toString(data_head);
	// 	headers = headers.substring(1, headers.length() -1);
	// 	sb.append(headers).append("\n");
	// 	Iterator<Entry> itr = data_table.values().iterator();
	// 	while(itr.hasNext()) {
	// 		Entry next = itr.next();
	// 		if(next == null) {
	// 			System.out.println("but why???");
	// 		}
	// 		sb.append(next).append("\n");
	// 	}
	// 	return sb.toString();
	// }
	
	// public static void printDatabase() throws FileNotFoundException {
	// 	Database db = new Database("inventory_team1.csv");
	// 	Entry entry = db.get("ULSGKCQO385Y");
	// 	System.out.println(entry);
	// 	entry.prettyPrint();
	// }
	
	public Entry getEntry(String productId) {
		return data_table.get(productId);
	}
}
