package com.dreamteam.database;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order extends DatabaseEntry {

	/** Member Variables */
    private String date;
	private String process_time;
    private String customer_email;
    private String customer_location;
    private String product_id;
    private int quantity;
    
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("kk:mm:ss");

    /** Construction */

    public Order(String[] order) {
        int index = 0;

        this.date = order[index++];

        if (order.length == 5) {
            this.process_time = LocalDateTime.now().format(TIME_FORMAT);
        } else {
            this.process_time = order[index++];
        }
        
        this.customer_email = order[index++];;
        this.customer_location = order[index++];;
		this.product_id = order[index++];;
        this.quantity = Integer.parseInt(order[index++]);
        
    }

    /** Getters */

    public String getDate() { return this.date; }

	public String getTime() { return this.process_time; }
	
	public String getEmail() { return this.customer_email; }
	
	public String getProductID() { return this.product_id; }
	
	public int getQuantity() { return this.quantity; }
	
    public String getCustomerLocation() { return this.customer_location; }

    /** Class Methods (Alphabetical Order) */

    public String prettyPrint() {
        String regex = ", \n\t";
		String s =  "Order:\t" +
			   "{ date:\t\t" + this.date +
			   regex + "  customer email:\t\t" + this.customer_email +
               regex + "  customer location:\t\"" + this.customer_location + '\"' +
               regex + "  product id:\t\t\"" + this.product_id + '\"' +
               regex + "  quantity:\t\t" + this.quantity + "\t}\n";
               
		System.out.println(s);
		return s;
	}
	
	@Override public String toString() {
        return 
            this.date + "," +
            this.customer_email + "," +
            this.customer_location + "," +
            this.product_id + "," +
            this.quantity;
	}

}
