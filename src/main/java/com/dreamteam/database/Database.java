package com.dreamteam.database;

 import java.util.Arrays;

/**
 * 
 */
public class Database {
  // TODO polish, test, refactor, redesign,etc.

  // A list of class private variables.
  private static int rows = 3000; // Entry capacity of data structure.
  private static int entry_count = 0; // Position of final entry in data structure minus the header.
  private static String[][] data_table; // Tentative data structure. We can change this.
  private String[] data_head; // The column labels of the data structure.

  // Constructor
  public Database(int columns) {
    this.data_table = new String[rows][columns];
  }

  public void set_data_head(String[] labels) {
    this.data_head = labels;
  }

  public String[] get_data_head() {
    return this.data_head;
  }

  public int get_column_size() {
    return this.data_head.length;
  }

  /**
   * 
   * @param id the entry used to find the sought row's position in data structure.
   * @return the position of an entry in the data structure; a row. Entry count if not found.
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

    System.out.println("\nThere are " + (entry_count - 1) + " entries recorded in the database.\n");

  }

  /**
   * Add new entries to database.
   * @param new_data
   */
  public void create(String[] new_data) {
    
    if (entry_count + 1 > rows ) {
      System.out.println("Resizing the database from " + rows + " rows to " + (rows * 2) + " rows.");
      rows *= 2;
      data_table = Arrays.copyOf(data_table, rows);
    }

    data_table[entry_count] = new_data;
    
    entry_count++;

  }

  /**
   * Read existing entry from database.
   * @param id
   * @return the entry of the database if found.
   */
  public String[] read(String id) {
    int entry_position = search(id);
    
    if (entry_position < entry_count) {
      return data_table[entry_position];
    } else {
      return null;
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
   * Delete existing entry from database.
   * @param id
   */
  public void delete(String id) {
    
    int entry_position = search(id);

    if (entry_position < entry_count) {
      
      data_table[entry_position] = null;

      for (int i = entry_position; i <= entry_count; i++) {
        data_table[i] = data_table[i + 1];
      }
      System.out.println("Product removed.");
      entry_count--;

    }

  }
  /*public void buyerEvent(String[] new_data, String id) {
  	System.out.println("We have a new buyer event!");
  	Boolean entryExists = false;
  	if (read(id) == null) {
  		create(new_data);
	}
  	
  	
  	
  }*/
}
