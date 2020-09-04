package com.dreamteam.database;

public class DataList {

    //Class variables match order and type presented in excel file
    private String product_id;
    private int quantity;
    private double wholesale_cost;
    private double sale_price;
    private String supplier_id;

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
    private void setSale_price(double sale_price){
        this.sale_price = Math.round(sale_price * 100.0) / 100.0;  //Ensures two decimal places for price
    }

    //Methods to get/set supplier_id
    public String getSupplier_id(){
        return supplier_id;
    }

    private void setSupplier_id(String supplier_id){
        this.supplier_id = supplier_id;
    }
    
    //Still need to add checks to other methods for valid inputs
    //Probably link it to the excel file to get the inputs?
    //Feel free to make changes or add suggestions :)
}
