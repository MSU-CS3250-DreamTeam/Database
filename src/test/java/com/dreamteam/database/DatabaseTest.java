package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {
  
  private static ProductDatabase product_database;

  @BeforeAll public static void setup() {
    product_database = ProductDatabase.getProducts();
  }

  
  @AfterAll public static void end(TestInfo testInfo) {
    // System.out.print(testInfo);
  }

  @Test void buyTest(TestReporter reporter) throws FileNotFoundException {
    try {
      executeTransactions("files/buyer_event.csv", -1, reporter);
    } catch (FileNotFoundException e) {
      System.err.println(e);
    }
  }

  @Test void supplyTest(TestReporter reporter) {
    try {
      executeTransactions("files/supplier_event.csv", 1, reporter);
    } catch (FileNotFoundException e) {
      System.err.println(e);
    }
  }

  private void executeTransactions(String file_path, int math_sign, TestReporter reporter) throws FileNotFoundException {
    
    File existing_file = new File(file_path);
    int quantity = 0;
    Product product;
    Product product_entry;

    if (existing_file.exists()) {

      Scanner data_input = new Scanner(existing_file);
      data_input.nextLine(); // Return and throw away column headers of file.

      while (data_input.hasNextLine()) {

        String[] data_row = data_input.nextLine().split(",");
        String[] entry_row = new String[]{
                data_row[3],data_row[4]
        };

        try {
          product_entry = product_database.read(entry_row[0]);
          quantity = product_entry.getQuantity() + math_sign * Integer.parseInt(entry_row[1]);
          product = product_database.read(entry_row[0]);

          if (math_sign > 0) {
            product.supplyQuantity(Integer.parseInt(entry_row[1]));
          } else {
            product.buyQuantity(Integer.parseInt(entry_row[1]));
          }

          System.out.println("Updated Database Successfully"); // Executes if purchase is successful!
        } catch(NumberFormatException ex) {
          System.err.println(ex.getMessage());
          product_database.create(entry_row);
          product = product_database.read(data_row[0]);
          quantity = product.getQuantity();
        }

        assertEquals(quantity, product_database.read(entry_row[0]).getQuantity());
        reporter.publishEntry(product.getProductID() + "'s transaction passed:" 
                    + quantity + " = " + product.getQuantity());

      }

      data_input.close();

    } else {
        throw new FileNotFoundException("Is the data file in the wrong directory:" + file_path);
    }
  }
  
}
