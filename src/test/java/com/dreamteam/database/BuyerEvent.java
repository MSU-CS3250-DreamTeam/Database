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

    //Empty constructor
    public BuyerEvent(){

    }

    public String toString(){
        String buyerEvent = "";
        buyerEvent += ("Date: " + date + "\n");
        buyerEvent += ("Email" + email + "\n");
        buyerEvent += ("Shipping Address: " + shipping_address + "\n");
        buyerEvent += ("Product ID: " + product_id + "\n");
        buyerEvent += ("Quantity: " + quantity + "\n");

        return buyerEvent;
    }

    /**
     * Updates quantity of product inside of the Database
     * @param product_id
     * @param quantity
     */
    private static void updateQuantity(String product_id, int quantity){
        //search for product in database with product_id
        //update quantity of product by subtracting quantity variable of BuyerEvent
        System.out.println("UPDATE METHOD STILL IN PROGRESS...");
    }

    public static void main(String[] args) throws FileNotFoundException {

        //CSV file that holds buyer event parameters
        File file = new File("buyer_event.csv");
        Scanner scanner = new Scanner(file);

        //Each buyer event (line of data in csv file) is stored as an object
        BuyerEvent event = new BuyerEvent();

        //Reads corresponding input fields from csv and assigns them to object
        while(scanner.hasNextLine()){
            String[] data_row = scanner.nextLine().split(",");
            event.date = data_row[0];
            event.email = data_row[1];
            event.shipping_address = data_row[2];
            event.product_id = data_row[3];
            event.quantity = Integer.parseInt(data_row[4]);


            //updateQuantity(event.product_id, event.quantity);

            //As of now, the program only reads from the csv and prints it.
            System.out.println(event.toString());
        }
    }
}
