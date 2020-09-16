package com.dreamteam.database;

import org.junit.*;
import java.io.File;
import java.io.FileNotFoundException;

public class DatabaseSimulator {
  @Test
    private void supplier() {
      File new_file = new File("supplier_event.csv");
      if (new_file.exists()) {
          Scanner data_input = new Scanner(new_file);
          while (data_input.hasNextLine()) {
              String[] data_row = data_input.nextLine().split(",");
              String[] entry_row = new String[]{
                      data_row[3],data_row[4]
              };
//              if (new_database == null) {
//                  new_database = new Database(data_row.length);
//                  new_database.set_data_head(data_row);
//
//              }
//              new_database.create(data_row);
              new_database.read(entry_row[0]);
              new_database.update(entry_row,entry_row);


          }
      }
      data_input.close();
  }
}
