package com.dreamteam.database;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BuyerEvent {

    //Class variables
    private String date;
    private String email;
    private String shipping_address;
    private String product_id;
    private int quantity;
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
        System.out.println("Product quantity before purchase: " + quantity);
        quantity -= event.quantity;
        System.out.println("Product quantity after purchase: " + quantity + "\n");
        newData[1] = String.valueOf(quantity);
        DATABASE.update(newData, newData);
    }

    public static void main(String[] args) throws FileNotFoundException {

        //Initialize database with original inventory
        File inventory = new File("inventory_team1.csv");
        Scanner dbScanner = new Scanner(inventory);
        while (dbScanner.hasNextLine()){
            String[] dbRow = dbScanner.nextLine().split(",");
            DATABASE.create(dbRow);
        }
        dbScanner.close();


        //CSV file that holds buyer event parameters
        File file = new File("buyer_event.csv");
        Scanner scanner = new Scanner(file);

        System.out.println("");
        System.out.println("Buyer Event Simulation Initiated...\n");

        //Each buyer event (line of data in csv file) is stored as an object
        BuyerEvent event = new BuyerEvent();
        System.out.println(scanner.nextLine());
        //Reads corresponding input fields from csv and assigns them to object
        String[] data_row;
        int count = 1;
        while(scanner.hasNextLine()){
            data_row = scanner.nextLine().split(",");
            event.date = data_row[0];
            event.email = data_row[1];
            event.shipping_address = data_row[2];
            event.product_id = data_row[3];
            event.quantity = Integer.parseInt(data_row[4]);

            count++;
            System.out.println(event.toString());
            updateQuantity(event);
        }

        //Close the program and print out the total number of Buyer Events
        scanner.close();
        System.out.println("Total number of Buyer Events: " + count + "\n");
        System.out.println("Buyer Event Simulation Complete");
        System.exit(1);
    }
}
