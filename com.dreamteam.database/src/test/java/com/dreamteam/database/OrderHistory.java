package com.dreamteam.database;

import java.io.FileWriter;
import java.io.IOException;
// Alejandro and John
// Still writing code and this will be used as our foundation for the finance customer order history


public class OrderHistory {

    private static void generateCsvFile(String fileName) {


        FileWriter writer = null;

        try {

            writer = new FileWriter(fileName);
            writer.append("Customer order"); // we would append the order from the events into here
            writer.append(',');
            writer.append("Date");
            writer.append(',');
            writer.append("Time");
            writer.append('\n');

            writer.append("00Smith");
            writer.append(',');
            writer.append("9-12-2020");
            writer.append(',');
            writer.append("1:00pm");
            writer.append('\n');

            writer.append("00Jones");
            writer.append(',');
            writer.append("9-13-2020");
            writer.append(',');
            writer.append("1:00pm");
            writer.append('\n');

            writer.append("00Alex");
            writer.append(',');
            writer.append("9-14-2020");
            writer.append(',');
            writer.append("1:00pm");
            writer.append('\n');

            writer.append("00Brian");
            writer.append(',');
            writer.append("9-15-2020");
            writer.append(',');
            writer.append("11:00am");
            writer.append('\n');

            writer.append("00Jacob");
            writer.append(',');
            writer.append("9-16-2020");
            writer.append(',');
            writer.append("5:00pm");
            writer.append('\n');

            writer.append("00John");
            writer.append(',');
            writer.append("9-17-2020");
            writer.append(',');
            writer.append("3:00pm");
            writer.append('\n');

            System.out.println("CSV file is created...");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // writes into a file
        // this is for testing will fi
        String location = "customerHistory.csv";
        generateCsvFile(location);

    }

}






