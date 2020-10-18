package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {
  private static final String SPREAD_SHEET = "files/inventory_team1.csv";
  private static Database new_database;
  private static Product product_entry;

  @BeforeAll static void setup() throws FileNotFoundException {

    new_database = new Database(SPREAD_SHEET);

  }

  @Test void buyTest() throws FileNotFoundException {
    File new_file = new File("files/buyer_event.csv");
    int quantity = 0;

    if (new_file.exists()) {

      Scanner data_input = new Scanner(new_file);
      data_input.nextLine(); // Return and throw away column headers of file.

      while (data_input.hasNextLine()) {

        String[] data_row = data_input.nextLine().split(",");
        String[] entry_row = new String[]{
                data_row[3],data_row[4]
        };

        try {
          product_entry = new_database.read(entry_row[0]);
          quantity = Integer.parseInt(entry_row[1]) + product_entry.getQuantity();
          new_database.read(entry_row[0]).buyQuantity(Integer.parseInt(entry_row[1]));
          System.out.println("Updated Database Successfully"); // Executes if purchase is successful!
        } catch(NumberFormatException ex) {
          System.err.println(ex.getMessage());
          new_database.create(entry_row);
          quantity = new_database.read(data_row[0]).getQuantity();
        }

        assertEquals(Integer.toString(quantity), new_database.read(entry_row[0]).getQuantity());

      }

      data_input.close();

    } else {
        throw new FileNotFoundException("Is the data file in the wrong directory?");
    }
  }

  @Test void supplyTest() throws FileNotFoundException {
    File new_file = new File("files/supplier_event.csv");
    int quantity = 0;

    if (new_file.exists()) {

      Scanner data_input = new Scanner(new_file);
      data_input.nextLine(); // Return and throw away column headers of file.

      while (data_input.hasNextLine()) {

        String[] data_row = data_input.nextLine().split(",");
        String[] entry_row = new String[]{
                data_row[2],data_row[3]
        };

        try {
          product_entry = new_database.read(entry_row[0]);
          quantity = Integer.parseInt(entry_row[1]) + product_entry.getQuantity();
          new_database.read(entry_row[0]).supplyQuantity(Integer.parseInt(entry_row[1]));
          System.out.println("Updated Database Successfully"); // Executes if purchase is successful!
        } catch(NumberFormatException ex) {
          System.err.println(ex.getMessage());
          new_database.create(entry_row);
          quantity = new_database.read(data_row[0]).getQuantity();
        }
        

        assertEquals(Integer.toString(quantity), new_database.read(entry_row[0]).getQuantity());

      }

      data_input.close();

    } else {
        throw new FileNotFoundException("Is the data file in the wrong directory?");
    }
      
  }
  
}
