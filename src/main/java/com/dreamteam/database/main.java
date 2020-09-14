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
  // !!! Use these examples to use CRUD methods in the menu!

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

          System.out.println(new_database); // Prints the object address in memory.
          // Adds a new product.
          break;

        case READ:

          System.out.println(new_database); // Prints the object address in memory.
          // Retrieves a product and displays it.
          break;

        case UPDATE:

          System.out.println(new_database); // Prints the object address in memory.
          // Updates a product
          break;

        case DELETE:

          System.out.println(new_database); // Prints the object address in memory.
          // Deletes a product
          break;

        case AUTOMATE:

          System.out.println(new_database); // Prints the object address in memory.
          // Deletes a product
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
    int index;
    

    while (true) {

      // Reset Options counter.
      index = 1;

      // Prompt user for a choice from Options.
      System.out.println("\nOptions:");
      for (Options choice : Options.values()) {
        System.out.println("  " + index++ + ": " + choice);
      }
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