package Database;

/**
 * 
 */
public class Database {
  // TODO class variables, class methods

  // A list of class private variables.
  private static int rows = 100;
  private static String[][] data_table;  // Tentative data structure. We can change this.
  
  // Constructor
  public Database(int columns) {
    this.data_table = new String[columns][rows];
  }
  
  // TODO in case we need it, finds a set of data within database to return to read and update methods.
  /**
   * 
   * @return the position of an entry in the data structure; a row.
   */
  private static int search() {
    int position = 0;
    
    return position; // currently returns 0.
  }

  // TODO create to add rows of data to database.
  /**
   * Add new entries to database. Return true if successfully added to database.
   * @param new_data
   */
  public static boolean create(String[] new_data) {
    
    boolean is_added = false;

    return is_added;
  }

  // TODO retrieve some data from database, perhaps by a search or coordinates ie two_dimensional_array[1], parameter may be incorrect.
  /**
   * Read existing entry from database.
   * @param data_set
   */
  public static void read(String[] data_set) {

  }

  // TODO modifies a row of database, replacing old data with new data. Parameters are tentative. Change if needed.
  /**
   * Find existing entry in database and update with new entry.
   * @param new_data
   * @param old_data
   */
  public static void update(String[] new_data, String[] old_data) {
    
  }

  // TODO removes a row from database
  /**
   * Delete existing entry from database. Return true if successfully removed from database.
   * @param old_data
   */
  public static boolean delete(String[] old_data) {
    boolean is_del = false;
    for (int i = 0; i < data_table.length; i++) {
      if (data_table[i] == old_data) {
        data_table[i] = null;
        is_del = true;
      }
    }


    return is_del;
  }

}
