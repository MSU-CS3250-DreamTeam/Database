import java.util.Arrays;

/**
 * 
 */
public class Database {
  // TODO class variables, class methods

  // A list of class private variables.
  private static int rows = 100000;
  private static int row_index = 0;
  private String[][] data_table; // Tentative data structure. We can change this.

  // Constructor
  public Database(int columns) {
    this.data_table = new String[rows][columns];
  }

  // TODO in case we need it, finds a set of data within database to return to read and update methods.
  /**
   * 
   * @param entry the entry used to find the sought row's position.
   * @return the position of an entry in the data structure; a row.
   */
  private static int search(String entry) {
    int position = 0;
    
    return position; // currently returns 0.
  }

  /**
   * Prints the entire database to console. May want to disable in finished project.
   */
  public void display() {

    for (int i = 0; i < row_index; i++) {
      System.out.println(Arrays.toString(data_table[i]));
    }

    System.out.println("\nThere are " + row_index + " entries recorded in the database.\n");

  }

  /**
   * Add new entries to database. Return true if successfully added to database.
   * @param new_data
   */
  public void create(String[] new_data) {
    
    for (int i = 0; i < new_data.length; i++) {
      data_table[row_index][i] = new_data[i];
    }
    
    row_index++;
  }

  // TODO retrieve some data from database, perhaps by a search or coordinates ie two_dimensional_array[1], parameter may be incorrect.
  /**
   * Read existing entry from database.
   * @param data_set
   */
  public void read(String[] data_set) {

  }

  // TODO modifies a row of database, replacing old data with new data. Parameters are tentative. Change if needed.
  /**
   * Find existing entry in database and update with new entry.
   * @param new_data
   * @param old_data
   */
  public void update(String[] new_data, String[] old_data) {
    
  }

  // TODO removes a row from database
  /**
   * Delete existing entry from database. Return true if successfully removed from database.
   * @param old_data
   */
  public boolean delete(String[] old_data) {
    boolean is_del = false;

    return is_del;
  }

}
