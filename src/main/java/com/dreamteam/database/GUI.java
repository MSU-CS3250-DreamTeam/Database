package com.dreamteam.database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EnumSet;

public class GUI implements ActionListener {

    //Class member variables
    private JFrame frame;
    private JFrame frame2;
    private JPanel panel;
    private JPanel panel2;
    private ProductDatabase database = ProductDatabase.getProducts();

    //Necessary labels for most/all methods
    JLabel productLabel = new JLabel("Product ID:");
    JLabel quantityLabel = new JLabel("Quantity:");
    JLabel wholesaleLabel = new JLabel("Wholesale Cost:");
    JLabel salesLabel = new JLabel("Sales Price:");
    JLabel supplierLabel = new JLabel("Supplier ID:");

    //Globalized text area components to store user input
    JTextArea product;
    JTextArea quantity;
    JTextArea wholesale;
    JTextArea sales;
    JTextArea supplier;
    JTextArea date;
    JButton submit;


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
                EnumSet<Options> OPTIONS = EnumSet.of(Options.QUANTITY, Options.CAPACITY, Options.WHOLESALE_COST, Options.SALE_PRICE, Options.SUPPLIER, Options.DONE);
                ArrayList<JButton> buttonList = new ArrayList<JButton>();
                for(Options option : OPTIONS){
                    buttonList.add(new JButton(String.valueOf(option)));
                }
                for(int i = 0; i < buttonList.size(); i++){
                    panel2.add(buttonList.get(i));
                }
                newGUI("UPDATE");

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
                JLabel processLabel = new JLabel("Order Processing NOT Complete!");
                panel2.add(processLabel);
                newGUI("PROCESS ORDERS");

                break;

            case "REPORTS":
                JLabel reportsLabel = new JLabel("For which date would you like reports for?");
                date = new JTextArea();
                panel2.add(reportsLabel);
                panel2.add(date);
                newGUI("REPORTS");

                break;

            case "QUIT":
                System.exit(0);

                break;
        }

    }

    public void createSubmit(ActionEvent e) {
        String product_id = product.getText();
        int product_quantity = Integer.parseInt(quantity.getText());
        double wholesale_cost = Double.parseDouble(wholesale.getText());
        double sales_price = Double.parseDouble(sales.getText());
        String supplier_id = supplier.getText();

        Product newProduct = new Product(product_id, product_quantity, wholesale_cost,sales_price,supplier_id);
        database.create(newProduct);
        System.out.println("Product created!");
    }

    public void readSubmit(ActionEvent e) {
        String product_id = product.getText();
        System.out.println(database.read(product_id));
        frame2.dispose();
    }

    public void deleteSubmit(ActionEvent e) {
        String product_id = product.getText();
        database.delete(product_id);
        System.out.println("Product deleted successfully!");
    }

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
