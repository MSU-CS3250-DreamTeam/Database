package com.dreamteam.database;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class BuyerEvent {

    //Class variables
    protected String date;
    protected String email;
    protected String shipping_address;
    protected String product_id;
    protected int quantity;
    protected String time;
    private static final Database DATABASE = new Database(5);

    //Empty constructor
    public BuyerEvent(){

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
        String[] newData = DATABASE.read(event.product_id);
        int quantity = Integer.parseInt(newData[1]);
        quantity -= event.quantity;
        newData[1] = String.valueOf(quantity);
        DATABASE.update(newData, newData);
        System.out.println("Updated Database Successfully");
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
        File inventory = new File("inventory_team1.csv");
        Scanner dbScanner = new Scanner(inventory);
        while (dbScanner.hasNextLine()){
            String[] dbRow = dbScanner.nextLine().split(",");
            DATABASE.create(dbRow);
        }
        dbScanner.close();


        //CSV file that holds buyer event parameters
        File file = new File("customer_orders_A_team1.csv");
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
