package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class ProductDatabase implements Database<Product> {

	/** Member Variables */

	private static String[] data_head; // The column labels of the data structure.
	private static HashMap<String, Product> data_table;

	/** Construction */

	private static final ProductDatabase PRODUCTS = new ProductDatabase();

	private ProductDatabase() {

		final String file_path = "files/inventory_team1.csv";
		File product_inventory = new File(file_path);

		try (Scanner product_scanner = new Scanner(product_inventory)) {

			ProductDatabase.data_table = new HashMap<>();

			if (product_scanner.hasNextLine())
				ProductDatabase.data_head = product_scanner.nextLine().split(",");

			String dbRow;

			while (product_scanner.hasNextLine()) {
				dbRow = product_scanner.nextLine();
				create(dbRow);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Is the data file " + file_path + " in the wrong directory?");
		}

	}

	/** Getters */

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

	/* Class Methods (Alphabetical Order) */
	// TODO javadoc for class methods without @override.

	/**
	 * 
	 * @param product_id
	 * @return
	 */

	protected boolean contains(String product_id) {
		return data_table.containsKey(product_id);
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
			HashMap.Entry<String, Product> pair = it.next();
			current = pair.getValue();
			price = current.getWholesaleCost();
			quantity = current.getQuantity();
			totalAssets += price * quantity;
		}

		return totalAssets;
	}

	@Override
	public void create(Product new_product) {
		if (!contains(new_product.getProductID())) {
			data_table.put(new_product.getProductID(), new_product);
		} else {
			System.out.println("Product already exists.");
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
	public String display() { return "The products database has " + data_table.size() + " products."; }

	@Override
	public Product read(String id) {
		if (contains(id)) {
			return data_table.get(id);
		} else {
			return new Product("000,000,000,000,000".split(","));
		}

	}

	@Override
	public boolean update(Product existing_product, Scanner product_scanner) {
		boolean isUpdated = true;
		Options user_choice;
		final EnumSet<Options> UPDATE_MENU = EnumSet.of(Options.QUANTITY,Options.CAPACITY,Options.WHOLESALE_COST,
										Options.SALE_PRICE,Options.SUPPLIER,Options.DONE);
		Menu menu = new Menu(UPDATE_MENU);

		if (contains(existing_product.getProductID())){
			do {
				ArrayList<String> option_fields = new ArrayList<>();
				menu.printMessage(existing_product.prettyPrint());
				user_choice = menu.getOption();

				switch(user_choice) {
					case QUANTITY:
						option_fields.add("Are you buying? y/n");
						option_fields.add("Enter quantity: ");
						option_fields.add("Enter customer Id: ");
						option_fields = menu.runTextReader(Options.QUANTITY, option_fields);
						boolean isBuyer = "y".equals(option_fields.get(0));

						int requestQuantity = Integer.parseInt(option_fields.get(1));
						
						if(isBuyer) {
							existing_product.buyQuantity(requestQuantity);
						} else {
							existing_product.supplyQuantity(requestQuantity);
						}

						String customer = option_fields.get(2);
						String date = LocalDate.now().toString();
						String time = LocalDateTime.now().toString();

						main.updateCustomerHistory(customer, date, time);
						break;

					case CAPACITY:
						option_fields.add("Enter the new stock limit: ");
						option_fields = menu.runTextReader(Options.CAPACITY, option_fields);
						int limit = Integer.parseInt(option_fields.get(0));
						
						existing_product.setCapacity(limit);
						break;

					case WHOLESALE_COST:
						option_fields.add("Enter the new wholesale cost: ");
						option_fields = menu.runTextReader(Options.WHOLESALE_COST, option_fields);
						double cost = Double.parseDouble(option_fields.get(0));
						
						existing_product.setWholesaleCost(cost);
						break;

					case SALE_PRICE:
						option_fields.add("Enter the new sale price: ");
						option_fields = menu.runTextReader(Options.SALE_PRICE, option_fields);
						double price = Double.parseDouble(option_fields.get(0));
						
						existing_product.setSalePrice(price);
						break;

					case SUPPLIER:
						option_fields.add("Enter the new supplier's id: ");
						option_fields = menu.runTextReader(Options.SUPPLIER, option_fields);
						String id = option_fields.get(0);
						
						existing_product.setSupplierID(id);
						break;

					case DONE:
						menu.closeMenu();
						break;
					default:
						menu.printMessage("The " + user_choice + " option is not in this menu.");
				}

				menu.clearDialog();

			} while(user_choice != Options.DONE);

		} else {
			isUpdated = false;
			menu.closeMenu();
		}
		return isUpdated;
	}

	/**
	 * 
	 * @param product
	 * @param quantity
	 */
	public void appendSupplierHistory(Product product, int quantity) {

		String file_destination = "files/supplier_order_history.csv";

		try (FileWriter writer = new FileWriter(file_destination, true))
		{

			String supplier_order = "";
			String regex = ",";
			supplier_order += LocalDate.now() + regex;
			supplier_order += product.getSupplierID() + regex;
			supplier_order += product.getProductID() + regex;
			supplier_order += quantity + "\n";

			writer.append(supplier_order);

		} catch (IOException e) {
	
			e.printStackTrace();
			System.out.println("Failed to write the supply order to: " + file_destination);

		}

	}

}