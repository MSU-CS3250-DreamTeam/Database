package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 *
 */
public class main {

  static private final String SPREAD_SHEET = "inventory_team1.csv";
  static private Database new_database;
  public static final int Option_Create = 1;
  public static final int Option_READ = 2;
  public static final int Option_Update = 3;
  public static final int Option_Delete = 4;
  public static final int Option_Quit = 5;
  public static Scanner sc = new Scanner(System.in);
  private int key;


  public static int getOption() {
    while (true) {
      System.out.println("Options: 1:Create 2:Read 3:Update 4:Delete 5:Quit");
      System.out.print("? ");
      String line = sc.nextLine();
      try {
        int option = Integer.parseInt(line);
        if (option == Option_Create || option == Option_READ || option == Option_Update || option == Option_Delete || option == Option_Quit)
          return option;
      } catch (NumberFormatException ex) {
      }
      System.out.println("Error!");
    }
  }


  public main() throws FileNotFoundException {
    loadData();
  }


  // read from the file and give each of the following options to choose from
  public static DataList loadData() throws FileNotFoundException {
    Scanner sc = new Scanner(new File(SPREAD_SHEET));
    sc.useDelimiter(",");   //sets the delimiter pattern
    while (sc.hasNext())  //returns a boolean value
    {
      System.out.print(sc.next());  //find and returns the next complete token from this scanner
    }
    sc.close();  //closes the scanner
    return null;
  }


  //Updates and saves the data that user inputs.
  //Still needs some work done, need it to iterate through the csv file and find empty column to add the user data.
  public static DataList updateData() throws IOException {
    File f = new File(SPREAD_SHEET);
    PrintStream p = new PrintStream(f);
    StringBuffer buffer = new StringBuffer();
    String line[];
    while ((line = f.list()) != null) {
      for (int i = 0; i < line.length; i++) {
        System.out.print(line[i] + " ");
      }
      System.out.println(" ");
    }
    return null;
  }
  //This was for testing dont know if we should do a iterator that iterates through each column by their category or do an array
       /* String Product = line.next().getProduct();
        p.print(Product + ", ");
        int Quantity = line.next().getProduct();
        p.print(Quantity + ", ");
        double Wholesale = line.next().getProduct();
        p.print(Wholesale + ", ");
        double SalesPrice = line.next().getProduct();
        p.print(SalesPrice + ", ");
        String Supplier = line.next().getProduct();
        p.print(Supplier + ", ");
      }
    }
  }*/



  //This is to add the data into the csv but does not save it
  // the update method does that.
  public void addData(DataList dataList){
    dataList.setKey(key);
    addData(dataList);
  }



  public static DataList getData() {
    System.out.println("Product");
    String Product = sc.nextLine();
    System.out.println("Quanity");
    int Quanitity = Integer.parseInt(sc.nextLine());
    System.out.println("Wholesale");
    double Wholesale = Double.parseDouble(sc.nextLine());
    System.out.println("SalesPrice");
    double SalesPrice = Double.parseDouble(sc.nextLine());
    System.out.println("Supplier");
    String Supplier = sc.nextLine();
    return new DataList(Product,  Quanitity, Wholesale, SalesPrice,Supplier);
  }





  static public void main(String[] args) throws FileNotFoundException {
    System.out.println("Welcome to DreamTeam DataBase");
    Database database = new Database();
    boolean quit = false;
    while (!quit) {
      int option = getOption();
      switch (option) {
        case Option_Create:
          DataList dataList = getData();
          System.out.println(database);
          break;
        case Option_READ:
          DataList readData = loadData();
          System.out.println(database);
          //DataList dataList = getData();

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
    System.out.println("Bye!");

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

  private static DataList demo_database() {

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
return null;
  }

}