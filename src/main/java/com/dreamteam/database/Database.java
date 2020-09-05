package com.dreamteam.database;

 import java.util.Arrays;

/**
 * 
 */
public class Database {
  // TODO polish, test, refactor, redesign,etc.

  // A list of class private variables.
  private static int rows = 30000; // Entry capacity of data structure.
  private static int entry_count = 0; // Position of final entry in data structure.
  private static String[][] data_table; // Tentative data structure. We can change this.

  // Constructor
  public Database(int columns) {
    this.data_table = new String[rows][columns];
  }

  /**
   * 
   * @param id the entry used to find the sought row's position in data structure.
   * @return the position of an entry in the data structure; a row. 0 if not found.
   */
  private static int search(String id) {

    for (int i = 0; i < entry_count; i++) {
      if (data_table[i][0].equals(id)) {
        System.out.println("Found at: " + (i + 1));
        return i;
      }
    }

    System.out.println("Product not found.");

    return entry_count;
  }

  /**
   * Prints the entire database to console. May want to disable in finished project.
   * Also prints the number of current entries in database.
   */
  public void display() {

    // for (int i = 0; i < entry_count; i++) {
    //   System.out.println(Arrays.toString(data_table[i]));
    //   // System.out.println(data_table[i][0]);
    // }

    System.out.println("\nThere are " + entry_count + " entries recorded in the database.\n");

  }

  /**
   * Add new entries to database. Return true if successfully added to database.
   * @param new_data
   */
  public void create(String[] new_data) {
    
    //if (entry_count )

    data_table[entry_count] = new_data;
    
    entry_count++;

  }

  /**
   * Read existing entry from database.
   * @param id
   */
  public void read(String id) {
    int entry_position = search(id);
    
    if (entry_position < entry_count) {
      System.out.println(Arrays.toString(data_table[entry_position]));
    }
    
  }

  /**
   * Find existing entry in database and update with new entry.
   * @param new_data
   * @param old_data
   */
  public void update(String[] old_data, String[] new_data) {
    int entry_position = search(old_data[0]);

    if (entry_position < entry_count) {
      data_table[entry_position] = new_data;
    }
    
  }

  /**
   * Delete existing entry from database. Return true if successfully removed from database.
   * @param id
   */
  public void delete(String id) {
    
    int entry_position = search(id);

    if (entry_position < entry_count) {

      for (int i = entry_position; i < entry_count; i++) {
        data_table[i] = data_table[i + 1];
      }
      System.out.println("Product removed.");
      entry_count--;

    }

  }

}
