package com.dreamteam.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;

public class GUI implements ActionListener {

    //Class member variables
    private JFrame frame;
    private JFrame frame2;
    private JPanel panel;
    private JPanel panel2;
    private static ProductDatabase productDatabase = ProductDatabase.getProducts();
    private static final OrderDatabase orderDatabase = OrderDatabase.getOrders();

    //Necessary labels for most/all methods
    JLabel productLabel = new JLabel("Product ID:");
    JLabel quantityLabel = new JLabel("Quantity:");
    JLabel wholesaleLabel = new JLabel("Wholesale Cost:");
    JLabel salesLabel = new JLabel("Sales Price:");
    JLabel supplierLabel = new JLabel("Supplier ID:");
    JLabel label = new JLabel("");

    //Globalized text area + button components to process user input
    JTextArea product;
    JTextArea quantity;
    JTextArea wholesale;
    JTextArea sales;
    JTextArea supplier;
    JTextArea date;
    JButton submit;
    JButton view;


    //Constructor
    public GUI() {

        //Create window frame
        frame = new JFrame();
        JLabel label = new JLabel("What operation would you like to perform?");

        //Create button components
        final EnumSet<Options> OPTIONS = EnumSet.of(Options.CREATE, Options.READ, Options.UPDATE, Options.DELETE, Options.PROCESS_ORDERS, Options.REPORTS, Options.QUIT);
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        for(Options option : OPTIONS){
            buttonList.add(new JButton(String.valueOf(option)));
        }

        //Create panel and configure size
        panel = new JPanel();
        panel.add(label);
        panel.setBorder(BorderFactory.createEmptyBorder(125,300,100,300));
        panel.setLayout(new GridLayout(0,1));

        //Insert buttons onto GUI panel
        for(int i = 0; i < buttonList.size(); i++){
            buttonList.get(i).addActionListener(this::actionPerformed);
            panel.add(buttonList.get(i));
        }

        //Attach panel to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("DREAMTEAM DATABASES");
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * This method creates operation buttons and also
     * allows for new windows to be created for each option
     *
     * @param e Operation button clicked on main menu
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Button events open new window
        frame2 = new JFrame();
        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(125,300,100,300));
        panel2.setLayout(new GridLayout(0,1));

        switch(e.getActionCommand()){

            case "CREATE":
                product = new JTextArea();
                quantity = new JTextArea();
                wholesale = new JTextArea();
                sales = new JTextArea();
                supplier = new JTextArea();

                submit = new JButton("SUBMIT");
                submit.addActionListener(this::createSubmit);

                panel2.add(productLabel);
                panel2.add(product);
                panel2.add(quantityLabel);
                panel2.add(quantity);
                panel2.add(wholesaleLabel);
                panel2.add(wholesale);
                panel2.add(salesLabel);
                panel2.add(sales);
                panel2.add(supplierLabel);
                panel2.add(supplier);
                panel2.add(submit);

                newGUI("CREATE");

                break;

            case "READ":
                product = new JTextArea();
                panel2.add(productLabel);
                panel2.add(product);
                submit = new JButton("SUBMIT");
                submit.addActionListener(this::readSubmit);
                panel2.add(submit);
                newGUI("READ");

                break;

            case "UPDATE":

                product = new JTextArea();
                quantity = new JTextArea(); //misnomer; will actually change capacity
                wholesale = new JTextArea();
                sales = new JTextArea();
                supplier = new JTextArea();

                submit = new JButton("SUBMIT");
                submit.addActionListener(this::updateSubmit);

                panel2.add(productLabel);
                panel2.add(product);

                //variables named after quantity but refer to capacity
                quantityLabel.setText("Capacity:");
                panel2.add(quantityLabel);
                panel2.add(quantity);

                panel2.add(wholesaleLabel);
                panel2.add(wholesale);
                panel2.add(salesLabel);
                panel2.add(sales);
                panel2.add(supplierLabel);
                panel2.add(supplier);
                panel2.add(submit);

                newGUI("CREATE");
                break;

            case "DELETE":
                product = new JTextArea();
                panel2.add(productLabel);
                panel2.add(product);
                submit = new JButton("SUBMIT");
                submit.addActionListener(this::deleteSubmit);
                panel2.add(submit);
                newGUI("DELETE");

                break;

            case "PROCESS_ORDERS":
                label.setText("Press button to process orders.");
                submit = new JButton("PROCESS");
                submit.addActionListener(this::processSubmit);
                panel2.add(label);
                panel2.add(submit);
                newGUI("PROCESS ORDERS");

                break;

            case "REPORTS":
                JLabel reportsLabel = new JLabel("For which date would you like reports for?");
                date = new JTextArea();
                submit = new JButton("GENERATE ORDER REPORT");
                submit.addActionListener(this::reportSubmit);
                panel2.add(reportsLabel);
                panel2.add(date);
                panel2.add(submit);
                newGUI("REPORTS");

                break;

            case "QUIT":
                frame.dispose();
                frame2.dispose();
                System.exit(0);

                break;
        }
    }

    /**
     * This method creates a Product object with user input and
     * calls on the ProductDatabase.create() method to add Product
     *
     * @param e Submit button clicked in CREATE mode
     */
    public void createSubmit(ActionEvent e) {
        String product_id = product.getText();
        int product_quantity = Integer.parseInt(quantity.getText());
        double wholesale_cost = Double.parseDouble(wholesale.getText());
        double sales_price = Double.parseDouble(sales.getText());
        String supplier_id = supplier.getText();

        Product newProduct = new Product(product_id, product_quantity, wholesale_cost,sales_price,supplier_id);
        productDatabase.create(newProduct);
        if (productDatabase.contains(newProduct.getProductID())){
            label.setText("New product created successfully!");
        } else {
            label.setText("Product not created. Unexpected issue... :(");
        }
        panel2.removeAll();
        panel2.add(label);
        newGUI("CREATE");
    }

    /**
     * This method calls on the ProductDatabase.read()
     * method to read Product given a valid product ID
     *
     * @param e Submit button clicked in READ mode
     */
    public void readSubmit(ActionEvent e) {
        String product_id = product.getText();
        if (productDatabase.contains(product_id)) {
            Product productData = productDatabase.read(product_id);
            label.setText(productData.prettyPrint());
        } else {
            label.setText("No products in database match with ID of [ " + product_id + " ]");
        }

        panel2.removeAll();
        panel2.add(label);
        newGUI("READ");
    }

    /**
     * This method allows for update of all Product variables
     * except for quantity. Must use back-end for that
     *
     * @param e Submit button clicked in UPDATE mode
     */
    public void updateSubmit(ActionEvent e) {

        String product_id = product.getText();
        int capacity = Integer.parseInt(quantity.getText()); //see comment on line 144
        double wholesale_cost = Double.parseDouble(wholesale.getText());
        double sales_price = Double.parseDouble(sales.getText());
        String supplier_id = supplier.getText();

        ProductDatabase.getProducts().read(product_id).setCapacity(capacity);
        ProductDatabase.getProducts().read(product_id).setWholesaleCost(wholesale_cost);
        ProductDatabase.getProducts().read(product_id).setSalePrice(sales_price);
        ProductDatabase.getProducts().read(product_id).setSupplierID(supplier_id);

        label.setText("Product [ " + ProductDatabase.getProducts().read(product_id).getProductID() + " ] has been successfully updated!");

        panel2.removeAll();
        panel2.add(label);
        newGUI("UPDATE");

    }

    /**
     * This method calls on the ProductDatabase.delete()
     * method to delete Product given a valid product ID
     *
     * @param e Submit button clicked in DELETE mode
     */
    public void deleteSubmit(ActionEvent e) {
        String product_id = product.getText();
        if (productDatabase.contains(product_id)) {
            productDatabase.delete(product_id);
            label.setText("Product deleted successfully!");
        } else {
            label.setText("Product doesn't exist / already deleted.");
        }
        panel2.removeAll();
        panel2.add(label);
        newGUI("DELETE");
    }

    /**
     * This method calls on the OrderDatabase.processOrders()
     * method to process any available orders
     *
     * @param e Submit button clicked in PROCESS_ORDERS mode
     */
    public void processSubmit(ActionEvent e) {
        orderDatabase.processOrders();
        label = new JLabel("Orders Processed Successfully!");
        panel2.removeAll();
        panel2.add(label);
        newGUI("PROCESS ORDERS");
    }

    /**
     * Method pulls PDF reports of orders given
     * a valid date, and displays on user desktop
     *
     * @param e Submit button clicked in REPORT mode
     */
    public void reportSubmit(ActionEvent e)  {
        try {

            File pdfFile = new File("files/reports/daily-report_" + date.getText() + ".pdf");
            if (pdfFile.exists()) {

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    label.setText("Awt Desktop is Not Supported.");
                    panel2.add(label);
                    panel2.remove(view);
                    newGUI("REPORTS");
                }

            } else {
                label.setText("Graph Report File Does Not Exist.");
                panel2.add(label);
                newGUI("REPORTS");
                System.out.println("File does not exist!");
            }

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method reduces code to create new GUI windows
     * for all operations being done
     *
     * @param title Title to know what operation user is on
     */
    public void newGUI(String title) {
        frame2.setTitle("DATABASE OPERATION: " + title);
        frame2.add(panel2, BorderLayout.CENTER);
        frame2.pack();
        frame2.setVisible(true);
    }

    public static void main(String[] args) {

        new GUI();

    }

}
