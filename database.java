class Database {
  // TODO class variables, class methods
    
  // A list of class private variables.
  // TODO data structure like 2 dimension array and other necessary variables.
  int columns = 0;
  
  // Constructor
  public Database(int col, String[] labels) {
    // TODO constructor parameters like number of columns and column labels ie cost, wholesale cost, vendor id, etc.
    // TODO instancing two dimensional array or other table data structure with appropriate dimensions and column labels.

  }
  
  // TODO in case we need it, finds a set of data within database to return to read and update methods.
  private static int[] search() {
    int arr_width = 1;
    int[] database_coords = new int[arr_width];
    
    return database_coords; // currently null value
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

    return is_del;
  }

}
