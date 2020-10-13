package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {
  private static final String SPREAD_SHEET = "inventory_team1.csv";
  private static Database new_database;

  @BeforeAll static void setup() throws FileNotFoundException {

    new_database = new Database(SPREAD_SHEET);

  }

  @Test void buyTest() throws FileNotFoundException {
    File new_file = new File("buyer_event.csv");
    int quantity = 0;
    String[] database_entry = new String[5];

    if (new_file.exists()) {

      Scanner data_input = new Scanner(new_file);
      data_input.nextLine(); // Return and throw away column headers of file.

      while (data_input.hasNextLine()) {

          String[] data_row = data_input.nextLine().split(",");
          String[] entry_row = new String[]{
                  data_row[3],data_row[4]
          };

          new_database.create(data_row);
          database_entry = new_database.read(entry_row[0]);
          quantity = Integer.parseInt(database_entry[1]) - Integer.parseInt(entry_row[1]);
          database_entry[1] = Integer.toString(quantity);
          new_database.update(entry_row, database_entry);

          assertEquals(Integer.toString(quantity), new_database.read(entry_row[0])[1], "Test failed.");
          // assertEquals("6", new_database.read(entry_row[0])[1], "Test failed.");

      }

      data_input.close();

    } else {
        throw new FileNotFoundException("Is the data file in the wrong directory?");
    }
  }

  @Test void supplyTest() throws FileNotFoundException {
    File new_file = new File("supplier_event.csv");
    int quantity = 0;
    String[] database_entry = new String[5];

    if (new_file.exists()) {

      Scanner data_input = new Scanner(new_file);
      data_input.nextLine(); // Return and throw away column headers of file.

      while (data_input.hasNextLine()) {

          String[] data_row = data_input.nextLine().split(",");
          String[] entry_row = new String[]{
                  data_row[2],data_row[3]
          };

          new_database.create(data_row);
          database_entry = new_database.read(entry_row[0]);
          quantity = Integer.parseInt(database_entry[1]) + Integer.parseInt(entry_row[1]);
          database_entry[1] = Integer.toString(quantity);
          new_database.update(entry_row, database_entry);

          assertEquals(Integer.toString(quantity), new_database.read(entry_row[0])[1]);
          // assertEquals("6", new_database.read(entry_row[0])[1]);

      }

      data_input.close();

    } else {
        throw new FileNotFoundException("Is the data file in the wrong directory?");
    }
      
  }
  
}