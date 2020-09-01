package Database; // Parent directory should be named "Database"

//import org.apache.poi
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Database.*;

import java.util.Arrays; // For debugging purposes. Removing will break column labels output test.

/**
 * 
 */
public class main {

  static private final String SPREAD_SHEET = "inventory_team1.csv";
  static private Database new_database;

  /**
   * 
   * @param args
   * @throws FileNotFoundException
   */
  static public void main(String[] args) throws FileNotFoundException {

    System.out.println("Hello World");  // Testing output for debugging purposes. Should remove on final project.
    
    //TODO export/write data to excel file, if we need to for update().
    
    File new_file = new File(SPREAD_SHEET);
    if (!new_file.exists()) {
      throw new FileNotFoundException("Is the data file " + SPREAD_SHEET + " in the wrong directory?"); // For debugging.
    }

    Scanner data_input = new Scanner(new_file);
    if (data_input.hasNextLine()) {
      String[] data_row = data_input.nextLine().split(",");
      new_database = new Database(data_row.length);
      new_database.create(data_row);

      // For debugging purposes.
      System.out.println("You should see the column labels if program read the populated spreadsheet: ");
      System.out.println(Arrays.toString(data_row));

    }
    
    

    while (data_input.hasNextLine()) {

      String[] data_row = data_input.nextLine().split(",");
      new_database.create(data_row);

    }

    data_input.close();

  }
}
