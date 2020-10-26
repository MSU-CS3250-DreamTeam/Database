package com.dreamteam.database;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BuyerEvent {

    //Class variables
    public String date;
    public String email;
    public String shipping_address;
    public String product_id;
    public int quantity;
    public String time;
    private static Database<Product> my_database;

    //Empty constructor
    public BuyerEvent() {

    }

    public String getDate() {
		return date;
	}

	public CharSequence getTime() {
		return time;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getProduct_id() {
		return product_id;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String getShipping_address() {
		return shipping_address;
	}

    public String toString(){
        String buyerEvent = "";
        buyerEvent += ("Date: " + date + "\n");
        buyerEvent += ("Email: " + email + "\n");
        buyerEvent += ("Shipping Address: " + shipping_address + "\n");
        buyerEvent += ("Product ID: " + product_id + "\n");
        buyerEvent += ("Quantity Purchased: " + quantity);

        return buyerEvent;
    }

    /**
     * Updates quantity of product inside of the Database
     * @param event
     */
    private static void updateQuantity(BuyerEvent event){
        if (((Product) my_database.read(event.product_id)).buyQuantity(event.quantity))
            System.out.println("Updated Database Successfully"); // Executes if purchase is successful!
    }

    /**
     * Appends Buyer Event information into CSV file using OrderHistory.java class
     * @param event
     */
    private static void createOrder(BuyerEvent event) {
        try {
            OrderHistory.generateOrderHistory(event);
            System.out.println("Customer Order Logged Successfully \n");
        } catch (Exception e) {
            System.out.println("Customer Order Log for this event FAILED \n");
        }

    }

    public static void main(String[] args) throws IOException {

        //Initialize database with original inventory
        my_database = null;//new Database("inventory_team1.csv");

        //CSV file that holds buyer event parameters
        File file = new File("files/customer_orders_A_team1.csv");
        Scanner scanner = new Scanner(file);

        //This creates the csv file that the following buyerEvents will be stored into
        OrderHistory.generateCsvFile();

        System.out.println("\nBuyer Event Simulation Initiated\n");

        BuyerEvent event = new BuyerEvent(); //Each buyer event (line of data in csv file) is stored as an object

        scanner.nextLine(); //This skips the header row in the csv file

        //Reads corresponding input fields from buyerEvent csv and assigns them to object
        //Object then updates database, and then stores order information in a new orderHistory csv file
        String[] data_row;
        int count = 0;
        while(scanner.hasNextLine()){
            data_row = scanner.nextLine().split(",");
            event.date = data_row[0];
            event.email = data_row[1];
            event.shipping_address = data_row[2];
            event.product_id = data_row[3];
            event.quantity = Integer.parseInt(data_row[4]);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date time = new Date();
            event.time = format.format(time);

            System.out.println("Processing Customer Order #" + count + "... ");
            updateQuantity(event);
            createOrder(event);
            count++;
        }

        //Prints out the total number of Buyer Events and closes program
        scanner.close();
        System.out.println("Total number of Buyer Events: " + count + "\n");
        System.out.println("Buyer Event Simulation Complete");
        System.exit(1);
    }
}
