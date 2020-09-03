

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

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
    
    File new_file = new File(SPREAD_SHEET);
    if (!new_file.exists()) {
      throw new FileNotFoundException("Is the data file " + SPREAD_SHEET + " in the wrong directory?"); // For debugging.
    }

    Scanner data_input = new Scanner(new_file);

    while (data_input.hasNextLine()) {
      
      String[] data_row = data_input.nextLine().split(",");

      if (new_database == null) {

        new_database = new Database(data_row.length);

        // For debugging.
        //System.out.println("You should see the column labels if program read the populated spreadsheet: ");
        //System.out.println(Arrays.toString(data_row));
      }

      new_database.create(data_row);
    }

    data_input.close();

    // For debugging. There are ~22k entries to display.
    //new_database.display();

  }
}
