package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class ProductDatabase implements Database<Product> {
	
	private String[] data_head; // The column labels of the data structure.
	// Position of final entry in data structure minus the header.
	private HashMap<String, Product> data_table;

	private static final ProductDatabase PRODUCTS = new ProductDatabase();

    private ProductDatabase() {

		final String file_path = "files/inventory_team1.csv";

		try {

			File inventory = new File(file_path);
			this.data_table = new HashMap<>();
			Scanner dbScanner = new Scanner(inventory);
			this.data_head = dbScanner.nextLine().split(",");

			while(dbScanner.hasNextLine()) {
				String[] dbRow = dbScanner.nextLine().split(",");
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

    	/**
	 * Check to see if we can make a sale
	 *
	 * @param attemptedQuantity
	 *  which we need to have enough of
	 */
	@Override
	public boolean canProcessOrder(String id, int attemptedQuantity) {
		Product product = data_table.get(id);
		if(product != null) {
			return (attemptedQuantity <= product.getQuantity());
		}
		return false;
	}

	private boolean contains(String product_id) {
		try {
			return this.data_table.containsKey(product_id);
		} finally {
			throw new NullPointerException("The data structure data_table is not initialized.");
		}
		
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
		data_table.put(new_product.getProductID(), new_product);
	}

	@Override
    public void create(String entry_string) {
		Product new_product = new Product((entry_string.split(",")));
		data_table.put(new_product.getProductID(), new_product);
	}
	
	@Override
	public void create(String[] entry_string) {
		Product new_product = new Product(entry_string);
		data_table.put(new_product.getProductID(), new_product);
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
			isRemoved = (this.data_table.remove(id) != null);
		
		return isRemoved;
    }

    /**
	 * Prints the entire database to console. May want to disable in finished project.
	 * Also prints the number of current entries in database.
	 */
	@Override
	public void display() {
		System.out.println(this.data_table.size());
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
			return  this.data_table.get(id);
		} else {
			System.out.println("The product was not found.");
			return new Product("000,000,000,000,000".split(","));
		}
		
	}
	
	@Override
	public void set_data_head(String[] labels) {
		this.data_head = labels;
	}

	@Override
    public boolean update(Product existing_product) {
		boolean isUpdated = false;
		if (contains(existing_product.getProductID()))
			isUpdated = (data_table.put(existing_product.getProductID(), existing_product) != null);
		
		return isUpdated;
	}


	@Override
	public int get_column_size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] get_data_head() {
		// TODO Auto-generated method stub
		return null;
	}


}

