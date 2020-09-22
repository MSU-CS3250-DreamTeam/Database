package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
public class main {

  // Variable Declarations
  static private final String SPREAD_SHEET = "inventory_team1.csv";
  static private Database new_database;
  public static Scanner sc = new Scanner(System.in);

  // Menu Option Structure: order of options is the order displayed in the menu.
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
  static public void main(String[] args) throws FileNotFoundException {

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
        new_database.set_data_head(data_row);

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
    String[] database_header = new String[new_database.get_column_size()];
    String[] new_entry = new String[new_database.get_column_size()];
    String[] existing_entry = new String[new_database.get_column_size()];

    database_header = new_database.get_data_head();

    do {

      user_choice = getOption();

      switch (user_choice) {

        case CREATE:

          for (int i = 0; i < database_header.length; i++ ) {
            System.out.print("Enter " + database_header[i] + ": ");
            new_entry[i] = sc.nextLine();
          }

          new_database.create(new_entry);

          System.out.println(new_database); // Prints the object address in memory.
          // Adds a new product.
          break;

        case READ:
          
          System.out.print("Enter " + database_header[0] + ": ");
          existing_entry = new_database.read(sc.nextLine());

          if (existing_entry != null) {
            System.out.println(Arrays.toString(existing_entry));
          }

          System.out.println(new_database); // Prints the object address in memory.
          // Retrieves a product and displays it.
          break;

        case UPDATE:

          System.out.print("Enter existing entry's " + database_header[0] + ": ");
          existing_entry = new_database.read(sc.nextLine());
          
          boolean is_simulation = true;
          if (is_simulation) {

            new_entry = existing_entry;
            System.out.print("Enter the new quantity: ");
            new_entry[1] = sc.nextLine();
            System.out.println(Arrays.toString(new_entry));

          } else {

            for (int i = 1; i < database_header.length; i++ ) {
            
              System.out.print("Enter new entry's " + database_header[i] + ": ");
              new_entry[i] = sc.nextLine();
  
            }

          }

          new_database.update(existing_entry, new_entry);

          System.out.println(new_database); // Prints the object address in memory.
          // Updates a product
          break;

        case DELETE:

          System.out.print("Enter " + database_header[0] + ": ");
          new_database.delete(sc.nextLine());
          new_database.display();

          System.out.println(new_database); // Prints the object address in memory.
          // Deletes a product
          break;

        case AUTOMATE:

          System.out.println(new_database); // Prints the object address in memory.
          System.out.println("The selected option exists, but is not implemented yet.");
          // What does this do?
          break;

        case QUIT:

          System.out.println("\nSaving Database changes...");
          System.out.println("Done!");
          System.out.println("Bye!");
          break;

        default:

        System.out.println("The selected option exists, but is not implemented yet.");

      }

    } while (user_choice != Options.QUIT);

  }

  //	***************************************************************************

  /**
   * Prompt the user for a correct option of the existing menu.
   * 
   * @return the selected option to menu.
   */
  public static Options getOption() {

    // Local Variable Declarations
    int user_input;
    int index;

    while (true) {

      // Prompt user for a choice from Options.
      System.out.println("\nOptions:");
      for (Options choice : Options.values()) {
        System.out.println("  " + choice.value + ": " + choice);
      }
      System.out.print("? ");

      try {
        user_input = Integer.parseInt(sc.nextLine());

        for (Options user_choice : Options.values()) {
          if (user_input == user_choice.value) {
            return user_choice;
          }
        }

      } catch (NumberFormatException ex) {

        System.err.println(ex.getMessage());
        System.out.println("Error! Incorrect format.");

      }

      System.out.println("That option does not exist.");
      
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

    System.out.print("\nRemoving fake product. ");
    new_database.delete(fake_product_id);

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