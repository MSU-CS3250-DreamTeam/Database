package com.dreamteam.database;

public class DataList {

    //Class variables match order and type presented in excel file
    private String product_id;
    private int quantity;
    private double wholesale_cost;
    private double sale_price;
    private String supplier_id;
    private int key;

    public static final int PRODUCT = 1;
    public static final int QUANTITY = 2;
    public static final int WHOLESALE_COST = 3;
    public static final int SALE_PRICE = 4;
    public static final int SUPPLIER_ID = 5;



    public DataList(String product_id, int quantity, double wholesale_cost, double sale_price, String supplier_id){
        this.product_id = product_id;
        this.quantity = quantity;
        this.wholesale_cost = wholesale_cost;
        this.sale_price = sale_price;
        this.supplier_id = supplier_id;
    }

    //Methods to set/get product_id
    public String getProduct_id(){
        return product_id;
    }
    private void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    //Methods to get/set quantity
    public int getQuantity(){
        return quantity;
    }
    private void setQuantity(int quantity) {
        if(quantity >= 0){
            this.quantity = quantity;
        }
        else {
            System.out.println("Invalid input. Quantity not set.");
        }
    }

    //Methods to get/set wholesale_cost
    public double getWholesale_cost(){
        return wholesale_cost;
    }
    private void setWholesale_cost(double wholesale_cost){
        this.wholesale_cost = Math.round(wholesale_cost * 100.0) / 100.0;  //Ensures two decimal places for cost
    }

    //Methods to get/set sale_price
    public double getSale_price(){
        return sale_price;
    }
    private void  setSale_price(double sale_price){
        this.sale_price = Math.round(sale_price * 100.0) / 100.0;  //Ensures two decimal places for price
    }

    //Methods to get/set supplier_id
    public String getSupplier_id(){
        return supplier_id;
    }

    private void setSupplier_id(String supplier_id){
        this.supplier_id = supplier_id;
    }

    public void setKey(int key){
        if(key==PRODUCT || key==QUANTITY || key==WHOLESALE_COST || key==SALE_PRICE || key==SUPPLIER_ID){
            this.key=key;
        }
    }

    @Override
    public String toString(){
        return "Product" + PRODUCT + "Quantity" + QUANTITY + "Wholesale" + WHOLESALE_COST + "SalePrice" + SALE_PRICE +
                "Supplier" + SUPPLIER_ID;
    }


    //Still need to add checks to other methods for valid inputs
    //Probably link it to the excel file to get the inputs?
    //Feel free to make changes or add suggestions :)

    
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
}
