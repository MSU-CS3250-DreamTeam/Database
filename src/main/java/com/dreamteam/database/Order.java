package com.dreamteam.database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order extends DatabaseEntry {
    // class variables
    private String date;
	private String process_time;
    private String customer_email;
    private String customer_location;
    private String product_id;
    private String order_id;
    private int quantity;
    
    private DateTimeFormatter time_formatter = DateTimeFormatter.ofPattern("kk:mm:ss");

    public Order(String[] order) {
        int index = 0;

        this.date = order[index++];

        if (order.length == 5) {
            this.process_time = LocalDateTime.now().format(time_formatter);
        } else {
            this.process_time = order[index++];
        }
        
        this.customer_email = order[index++];;
        this.customer_location = order[index++];;
		this.product_id = order[index++];;
        this.quantity = Integer.parseInt(order[index++]);
        
        this.order_id = this.getCustomerLocation() + "-" + this.getProductID() + "-" + this.getDate();
        
    }

    /** Getters */

    public String getDate() { return this.date; }

	public String getTime() { return this.process_time; }
	
	public String getEmail() { return this.customer_email; }
	
	public String getProductID() { return this.product_id; }
	
	public int getQuantity() { return this.quantity; }
	
    public String getCustomerLocation() { return this.customer_location; }
    
    public String getOrderID() { return this.order_id; }

    // *** Class Methods (Alphabetical Order) */

    public String prettyPrint() {
		String s =  "Order {" +
			   ", \n\tdate: " + this.date +
			   ", \n\tcustomer email: " + this.customer_email +
               ", \n\tcustomer location: '" + this.customer_location + '\'' +
               ", \n\tproduct id: '" + this.product_id + '\'' +
			   ", \n\tquantity: " + this.quantity +
			   "\n}";
		System.out.println(s);
		return s;
	}
	
	@Override public String toString() {
        return 
            this.date + "," +
            this.process_time + "," +
            this.customer_email + "," +
            this.customer_location + "," +
            this.product_id + "," +
            this.quantity;
	}

}
