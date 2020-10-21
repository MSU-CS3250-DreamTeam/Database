package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SupplierEvent {

    //Class Variables
    private String date;
    private String supplier_id;
    private String product_id;
    private int quantity;
    // private String time;
    private static Database<Product> my_database;
    

    //Empty Constructor
    public SupplierEvent() {

    }

    private static void updateQuantity(SupplierEvent event) {
        if (((Product) my_database.read(event.product_id)).supplyQuantity(event.quantity))
            System.out.println("Updated Database Successfully"); // Executes if purchase is successful!
    }

    public String toString() {
        String supplierEvent = "";
        supplierEvent += ("Date: " + date + "\n");
        supplierEvent += ("Supplier ID: " + supplier_id + "\n");
        supplierEvent += ("Product ID: " + product_id + "\n");
        supplierEvent += ("Quantity: " + quantity + "\n");

        return supplierEvent;
    }

    public static void main(String[] args) throws FileNotFoundException {

        //CSV file that holds supplier event parameters
        File file = new File("files/supplier_event.csv");
        Scanner scanner = new Scanner(file);

        System.out.println("Supplier Event Simulation Initiated...\n");

        //Each supplier event (line of data in csv file) is stored as an object
        SupplierEvent event = new SupplierEvent();

        //Reads corresponding input fields from csv and assigns them to object
        while (scanner.hasNextLine()) {
            String[] data_row = scanner.nextLine().split(",");
            event.date = data_row[0];
            event.supplier_id = data_row[1];
            event.product_id = data_row[2];
            event.quantity =  Integer.parseInt(data_row[3]);

            // updateQuantity();

            //As of now, the program only reads from the csv and prints it.
            System.out.println(event.toString());
        }
        scanner.close();
        System.out.println("Supplier Event Simulation Complete");
    }

}
