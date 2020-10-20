package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ProductDatabase implements Database<Product> {
	
	// *** Class Variables */

	private static String[] data_head; // The column labels of the data structure.
	// Position of final entry in data structure minus the header.
	private static HashMap<String, Product> data_table;

	// *** Construction */

	private static final ProductDatabase PRODUCTS = new ProductDatabase();

    private ProductDatabase() {

		final String file_path = "files/inventory_team1.csv";

		try {

			File inventory = new File(file_path);
			ProductDatabase.data_table = new HashMap<>();
			Scanner dbScanner = new Scanner(inventory);
			
			if (dbScanner.hasNextLine())
				ProductDatabase.data_head = dbScanner.nextLine().split(",");
			
			String dbRow;

			while(dbScanner.hasNextLine()) {
				dbRow = dbScanner.nextLine();
				create(dbRow);
			}

			dbScanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(
			 "Is the data file " + file_path + " in the wrong directory?");
		}

	}

	public static ProductDatabase getProducts() {
        return PRODUCTS;
	}

	// *** Getters */

	@Override
	public int get_column_size() {
		return data_head.length;
	}
	
	public String[] get_data_head() {
		return data_head;
	}

	// *** Setters */
		
	@Override
	public void set_data_head(String[] labels) {
		data_head = labels;
	}

	// *** Class Methods (Alphabetical Order) */

    /**
	 * Check to see if we can make a sale
	 *
	 * @param attemptedQuantity
	 *  which we need to have enough of
	 */
	public boolean canProcessOrder(String id, int attemptedQuantity) {
		Product product = data_table.get(id);
		if(product != null) {
			return (attemptedQuantity <= product.getQuantity());
		}
		return false;
	}

	private boolean contains(String product_id) {
		return ProductDatabase.data_table.containsKey(product_id);
	}
	
    // TODO Tally the sum of product quantities x their wholesale prices and return in countAssets().
	/**
	 * 
	 * @return
	 */
	public double countAssets() {
		return 0.0;
	}

	@Override
	public void create(Product new_product) {
		if (!contains(new_product.getProductID())){
			data_table.put(new_product.getProductID(), new_product);
		} else {
			System.out.println("Product already exists");
		}
	}

	@Override
	public void create(String[] entry_string) {
		Product new_product = new Product(entry_string);
		create(new_product);
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
		if (contains(id))
			isRemoved = (data_table.remove(id) != null);
		
		return isRemoved;
    }

    /**
	 * Prints the entire database to console. May want to disable in finished project.
	 * Also prints the number of current entries in database.
	 */
	@Override
	public void display() {
		System.out.println("The products database has " + ProductDatabase.data_table.size() + " products.");
	}

    /**
	 * Read existing entry from database.
	 *
	 * @param id
	 *
	 * @return the entry of the database if found.
	 */
	@Override
	public Product read(String id) {
		if (contains(id)) {
			return data_table.get(id);
		} else {
			System.out.println("The product was not found.");
			return new Product("000,000,000,000,000".split(","));
		}
		
	}

	@Override
    public boolean update(Product existing_product) {
		boolean isUpdated = false;
		if (contains(existing_product.getProductID()))
			isUpdated = (data_table.put(existing_product.getProductID(), existing_product) != null);
		
		return isUpdated;
	}

} // EOF


	// public static void printDatabase() throws FileNotFoundException {
	// 	Database db = new Database("inventory_team1.csv");
	// 	Entry entry = db.get("ULSGKCQO385Y");
	// 	System.out.println(entry);
	// 	entry.prettyPrint();
	// }

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

	// public boolean update(String string) {
	// 	DatabaseEntry new_entry = read(string);
	// 	return (data_table.put(new_entry.getProductID(), new_entry) != null);
	// }
	

