package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Iterator;

public class ProductDatabase implements Database<Product> {

	/** Member Variables */

	private static String[] data_head; // The column labels of the data structure.
	// Position of final entry in data structure minus the header.
	private static HashMap<String, Product> data_table;

	/** Construction */

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
	 * @return a reference to the ProductDatabase instance.
	 */
	public static ProductDatabase getProducts() {
		return PRODUCTS;
	}

	@Override
	public String[] get_data_head() {
		return data_head;
	}

	/** Setters */

	@Override
	public void set_data_head(String[] labels) {
		ProductDatabase.data_head = labels;
	}

	/** Class Methods (Alphabetical Order) */
	// TODO javadoc for class methods without @override.

	/**
	 * Check to see if we can make a sale
	 *
	 * @param attemptedQuantity which we need to have enough of
	 */
	public boolean canProcessOrder(String id, int attemptedQuantity) {
		Product product = data_table.get(id);
		if (product != null) {
			return (attemptedQuantity <= product.getQuantity());
		}
		return false;
	}

	/**
	 * 
	 * @param product_id
	 * @return
	 */
	private boolean contains(String product_id) {
		return ProductDatabase.data_table.containsKey(product_id);
	}

	/**
	 * 
	 * @return
	 */
	public double countAssets() {
		Iterator<HashMap.Entry<String, Product>> it = data_table.entrySet().iterator();

		double totalAssets = 0.00;
		Product current;
		double price;
		int quantity;

		while (it.hasNext()) {
			HashMap.Entry<String, Product> pair = (HashMap.Entry<String, Product>) it.next();
			current = (Product) pair.getValue();
			price = current.getWholesaleCost();
			quantity = current.getQuantity();
			totalAssets += price * quantity;
			it.remove();
		}

		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		System.out.println("The company's total assets are: " + formatter.format(totalAssets));
		return totalAssets;
	}

	@Override
	public void create(Product new_product) {
		if (!contains(new_product.getProductID())) {
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

	@Override
	public boolean delete(String id) {
		boolean isRemoved = false;
		if (contains(id))
			isRemoved = (data_table.remove(id) != null);

		return isRemoved;
	}

	@Override
	public void display() {
		System.out.println("The products database has " + ProductDatabase.data_table.size() + " products.");
	}

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

	/**
	 * 
	 * @param product
	 * @param quantity
	 */
	public void appendSupplierHistory(Product product, int quantity) {

		String file_destination = "files/supplier_order_history.csv";
		FileWriter writer = null;

		try {
			writer = new FileWriter(file_destination, true);
			
			String supplier_order = "";
			String regex = ",";
			supplier_order += LocalDate.now() + regex;
			supplier_order += product.getSupplierID() + regex;
			supplier_order += product.getProductID() + regex;
			supplier_order += quantity + "\n";

			writer.append(supplier_order);

			writer.close();

		} catch (IOException e) {
	
			e.printStackTrace();
			System.out.println("Failed document the supply order.");

		}

	}

}