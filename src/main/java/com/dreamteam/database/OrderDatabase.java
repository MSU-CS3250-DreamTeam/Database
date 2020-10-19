package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class OrderDatabase implements Database<Order> {
    // Tentative data structure. We can change this.
	private String[] data_head; // The column labels of the data structure.
	// Position of final entry in data structure minus the header.
	private HashMap<String, Order> data_table;

	private static final OrderDatabase ORDERS = new OrderDatabase();
	
	private OrderDatabase() {
		final String file_path = "files/customer_orders_A_team1.csv";
		try {
			new OrderDatabase(file_path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

    private OrderDatabase(String file_path) throws FileNotFoundException {
		data_table = new HashMap<>();
		if (file_path != null) {
			File inventory = new File(file_path);
			Scanner dbScanner = new Scanner(inventory);
			this.data_head = dbScanner.nextLine().split(",");
			while(dbScanner.hasNextLine()) {
				String[] dbRow = dbScanner.nextLine().split(",");
				create(dbRow);
			}
			dbScanner.close();
		} else {
			throw new FileNotFoundException(
			 "Is the data file " + file_path + " in the wrong directory?");
		}
	}

	public static OrderDatabase getOrders() {
        return ORDERS;
    }


	@Override
    public boolean update(Order existing_order) {
		return (data_table.put(existing_order.getProductID(), existing_order) != null);
	}

	@Override
	public boolean canProcessOrder(String id, int attemptedQuantity) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean contains(String product_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void create(Order new_entry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(String entry_string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create(String[] entry_string) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public Order read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set_data_head(String[] labels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display() {
		System.out.println(this.data_table.size());

	}
}