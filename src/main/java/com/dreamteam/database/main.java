package com.dreamteam.database;


import java.io.*;
import java.util.Scanner;

/**
 *
 */
public class main {

  // Variable Declarations
  static private final String SPREAD_SHEET = "inventory_team1.csv";
  static private final String Delete = "output.csv";

  static private Database new_database;
  private int key;

  public static Scanner sc = new Scanner(System.in);

  // Menu Option Structure
  enum Options {
    CREATE(1),
    READ(2),
    UPDATE(3),
    DELETE(4),
    AUTOMATE(5),
    QUIT(6);

    private int value;

    private Options(int v) {
      this.value = v;
    }

  }

  /**
   * @param args
   * @throws FileNotFoundException
   */
  static public void main(String[] args) throws IOException {

    // System.out.println("Welcome to DreamTeam DataBase");
    System.out.println("-------------------------------------------------------------------");
    System.out.println("               Welcome to DreamTeam DataBase                       ");
    System.out.println("-------------------------------------------------------------------");

    File new_file = new File(SPREAD_SHEET);

    if (!new_file.exists()) {
      throw new FileNotFoundException("Is the data file " + SPREAD_SHEET + " in the wrong directory?");
    }

    Scanner data_input = new Scanner(new_file);

    while (data_input.hasNextLine()) {

      String[] data_row = data_input.nextLine().split(",");

      // Initializes the database to the spreadsheet's columns.
      if (new_database == null) {

        new_database = new Database(data_row.length);

        // For debugging.
        // System.out.println("You should see the column labels if program read the
        // populated spreadsheet: ");
        // System.out.println(Arrays.toString(data_row));
      }

      new_database.create(data_row);

    }

    data_input.close();

    // For debugging. There are ~22k entries to display when method is entirely
    // uncommented.
    new_database.display();

    // For debugging. Disable in final project.
    demo_database();

    // Call the menu for user to access and modify the database.
    runMenu();

  } // End main method.
  
  //	***************************************************************************
  // TODO Finish menu options. Each option should call the corresponding Database.java method.

  /**
   *  Loops through the options of a menu for the user to access and modify the database.
   */
  public static void runMenu() {

    // Local Variable Declarations
    Options user_choice;

    do {

      user_choice = getOption();

      switch (user_choice) {

        case CREATE:

          DataList dataList = getData();
          System.out.println(new_database); // Prints the object address in memory.
          break;

        case READ:

          // DataList readData = loadData();
          System.out.println(new_database); // Prints the object address in memory.
          // DataList dataList = getData();

          // Add product id
          break;

        case UPDATE:

          DataList update = updateData();
          System.out.println(new_database); // Prints the object address in memory.
          // Update a product
          break;

        case DELETE:

          // DataList delete = deleteData();
          System.out.println(new_database); // Prints the object address in memory.
          // Delete a product
          break;

        case AUTOMATE:

          // DataList automate = getAutomate();
          System.out.println(new_database); // Prints the object address in memory.
          // Delete a product
          break;

        default:

          System.out.println("\nSaving Database changes...");
          System.out.println("Done!");
          System.out.println("Bye!");

      }

    } while (user_choice != Options.QUIT);

  }

  //	***************************************************************************

  /**
   * Prompt the user for a correct option of the existing menu.
   */
  public static Options getOption() {

    // Local Variable Declarations
    int user_input;

    while (true) {
      
      // Prompt user for a choice.
      System.out.println("\nOptions: 1:Create 2:Read 3:Update 4:Delete 5:Automate 6:Quit");
      System.out.print("? ");
      
      user_input = Integer.parseInt(sc.nextLine());

      try {

        for (Options user_choice : Options.values()) {
          if (user_input == user_choice.value) {
            return user_choice;
          }
        }

      } catch (NumberFormatException ex) {
      }

      System.out.println("Error!");
    }
  }


  //	***************************************************************************

  /**
   * This is to add the data into the csv but does not save it. The update method does that.
   *
   * @param dataList
   */
  public void addData(DataList dataList) {
    dataList.setKey(key);

    // Recursive. Without a condition to return to caller, will cause stack overflow if called, so it's commented.
    // addData(dataList); 

  }

  //	***************************************************************************

  // TODO Should iterate through the csv file and find empty row to add the user data.
  // Note First assignment doesn't say we need to export to file. Instead, send to the active database object.

  /**
   * Updates and saves the data that user inputs.
   *
   * @throws IOException
   */


  //	***************************************************************************
  // TODO Called as though to return a populated object, so it should probably do that.

  /**
   * Read from the file and give each of the following options to choose from.
   * <p>
   * ###############################################!!!
   * Currently reads each row of file, and prints the values,
   * until the final row.
   * ###############################################!!!
   *
   * @return
   * @throws FileNotFoundException
   */
  public static DataList loadData() throws FileNotFoundException {
    Scanner sc = new Scanner(new File(Delete));
    sc.useDelimiter(","); //sets the delimiter pattern
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Product I.D    Quantity   WholesaleCost   SalePrice   Supplier I.D");
    System.out.println("-------------------------------------------------------------------");

    while (sc.hasNext())  //returns a boolean value
      System.out.print(sc.next() + ("\t\t"));  //find and returns the next complete token from this scanner
    sc.close();  //closes the scanner
    System.out.println("");
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Product I.D      Quantity   WholesaleCost   SalePrice   Supplier I.D");
    System.out.println("-------------------------------------------------------------------");
    return null;
  }

  public static DataList getAutomate() throws FileNotFoundException {
    Scanner sc = new Scanner(new File(SPREAD_SHEET));
    sc.useDelimiter(","); //sets the delimiter pattern
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Product I.D    Quantity   WholesaleCost   SalePrice   Supplier I.D");
    System.out.println("-------------------------------------------------------------------");

    while (sc.hasNext())  //returns a boolean value
      System.out.print(sc.next() + ("\t\t"));  //find and returns the next complete token from this scanner
    sc.close();  //closes the scanner
    System.out.println("");
    System.out.println("-------------------------------------------------------------------");
    System.out.println("Product I.D      Quantity   WholesaleCost   SalePrice   Supplier I.D");
    System.out.println("-------------------------------------------------------------------");
    return null;
  }

  //	***************************************************************************

  /**
   * Retrieves data entered by user and returns as an object.
   *
   * @return The data of an entry aka row.
   */
  public static DataList getData() {
    try (FileWriter fw = new FileWriter((Delete), true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter out = new PrintWriter(bw)) {
      System.out.println("Product");
      String Product = sc.nextLine();
      System.out.println("Quantity");
      int Quantity = Integer.parseInt(sc.nextLine());

      System.out.println("Wholesale");
      double Wholesale = Double.parseDouble(sc.nextLine());

      System.out.println("SalesPrice");
      double SalesPrice = Double.parseDouble(sc.nextLine());

      System.out.println("Supplier");
      String Supplier = sc.nextLine();
      out.println(Product + "\t\t" + Quantity + "\t\t" + Wholesale + "\t\t" + SalesPrice + "\t\t" + Supplier + "\t\t");
    } catch (IOException e) {
      //exception handling left as an exercise for the reader
    }
    return null;
  }












// Had to include the save option with the Create option
  //so getData saves it to file.
  public static DataList updateData() {
    System.out.println("Updating Database");
    return null;
  }

  // Was just trying some things could not get delete to work
  public static DataList deleteData() throws IOException {
    return null;
  }

  //	***************************************************************************

  /**
   * A demonstration of how to use the CRUD methods on an active, visible database object.
   */
  private static final void demo_database() {

    String existing_product_id = "8XXKZRELM2JJ";
    String fake_product_id = "AGEXCVFG3344";
    String[] new_product = new String[] {fake_product_id, "3260", "370.51", "623.94", "SASERNVV"};
    String[] updated_product = new String[] {fake_product_id, "25", "370.51", "623.94", "SASERNVV"};

    System.out.print("Retrieving a product. ");
    new_database.read(existing_product_id);

    System.out.print("\nRemoving a product. ");
    new_database.delete(existing_product_id);
    new_database.display();

    System.out.print("Existing product should not be found: ");
    new_database.read(existing_product_id);

    System.out.print("\nNew product should be found.");
    new_database.create(new_product);
    new_database.display();

    System.out.print("Retrieving a product. ");
    new_database.read(fake_product_id);

    System.out.print("\nUpdating a product. ");
    new_database.update(new_product, updated_product);

    System.out.print("\nRetrieving a product. ");
    new_database.read(fake_product_id);

  }

} // End main class.

//	***************************************************************************

  // This was for testing dont know if we should do a iterator that iterates through 
  // each column by their category or do an array

  // String Product = line.next().getProduct();
  // p.print(Product + ", ");
  // int Quantity = line.next().getProduct();
  // p.print(Quantity + ", ");
  // double Wholesale = line.next().getProduct();
  // p.print(Wholesale + ", ");
  // double SalesPrice = line.next().getProduct();
  // p.print(SalesPrice + ", ");
  // String Supplier = line.next().getProduct();
  // p.print(Supplier + ", ");