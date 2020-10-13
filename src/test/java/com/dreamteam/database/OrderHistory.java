package com.dreamteam.database;

import java.io.FileWriter;
import java.io.IOException;
// Alejandro and John and Kevin
// Still writing code and this will be used as our foundation for the finance customer order history


public class OrderHistory {
    private static final String FILE = "orderHistory.csv";
    private static FileWriter writer;


    protected static void generateCsvFile() throws IOException {

        writer = new FileWriter(FILE, true);

        writer.append("Date");
        writer.append(',');
        writer.append("Time");
        writer.append(',');
        writer.append("Email");
        writer.append(',');
        writer.append("Address");
        writer.append(',');
        writer.append("Product");
        writer.append(',');
        writer.append("Quantity");
        writer.append('\n');

        writer.flush();
        writer.close();

        System.out.println("CSV file generated.");
    }

    protected static void generateOrderHistory(BuyerEvent event) throws IOException {

        writer = new FileWriter(FILE, true);
        writer.append(event.getDate());
        writer.append(',');
        writer.append(event.getTime());
        writer.append(',');
        writer.append(event.getEmail());
        writer.append(',');
        writer.append(event.getShipping_address());
        writer.append(',');
        writer.append(event.getProduct_id());
        writer.append(',');
        writer.append(String.valueOf(event.getQuantity()));
        writer.append('\n');

        writer.flush();
        writer.close();
    }
}
