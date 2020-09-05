package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */
public class main {

  static private final String SPREAD_SHEET = "inventory_team1.csv";
  static private Database new_database;
  public static final int Option_Create = 1;
  public static final int Option_ADD = 2;
  public static final int Option_Update = 3;
  public static final int Option_Delete = 4;
  public static final int Option_Quit = 5;
  public static Scanner sc = new Scanner(System.in);


  public static int getOption() {
    while (true) {
      System.out.println("Options: 1:Create 2:Add 3:Update 4:Delete 5:Quit");
      System.out.print("? ");
      String line = sc.nextLine();
      try {
        int option = Integer.parseInt(line);
        if (option == Option_Create || option == Option_ADD || option == Option_Update || option == Option_Delete || option == Option_Quit)
          return option;
      } catch (NumberFormatException ex) {
      }
      System.out.println("Error!");
    }
  }


  public main() throws FileNotFoundException{
    loadData();
  }

 /* private String product_id;
  private int quantity;
  private double wholesale_cost;
  private double sale_price;
  private String supplier_id;*/
// read from the file and give each of the following options to choose from
  public void loadData() throws FileNotFoundException {
    File f = new File(SPREAD_SHEET);
    Scanner sc = new Scanner(f);
    String[]row;
    while (sc.hasNextLine()){
      String input = sc.nextLine();
      row = input.split(";");
      String quanity = row[0].trim();
      String wholesale_cost = row[1].trim();
      String sale_prive = row[2].trim();
      String supplier_id = row[3].trim();
    }
  }


  static public void main(String[] args) throws FileNotFoundException {
    System.out.println("Welcome to DreamTeam DataBase");
    Database database = new Database();
    boolean quit = false;
    while (!quit) {
      int option = getOption();
      switch (option) {
        case Option_Create:
          System.out.println(database);
          break;
        case Option_ADD:
          // Add product id
          break;
        case Option_Update:
          // Update Product Id w
          break;
        case Option_Delete:
          //Deletes Product Id
          break;
        case Option_Quit:
          System.out.println("Saving Database changes...");
          System.out.println("Done!");
          quit = true;
          System.out.println("Bye!");
      }
    }
    //System.out.println("Bye!");








  /**
   *
   * @param args
   * @throws FileNotFoundException
   */

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

    // For debugging. There are ~22k entries to display when method is entirely uncommented.
    new_database.display();

    // For debugging. Disable in final project.
    demo_database();

  }

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

}
