package com.dreamteam.database;

import java.util.Scanner;

public class DatabaseSimulator {


    private final String buyFile = "buyEvent.csv";
    private final String supplyFile = "supplyEvent.csv";
    private String FILE_NAME;

    //BUYER EVENT
    public static void buyerEvent(){
        //This method reads from the respective buyer event file
        //Update quantity, remove products/data rows if necessary
        //Store customer email and date of purchase
    }

    //SUPPLIER EVENT
//    public static void supplierEvent() {
//        File new_file = new File("");
//        if (new_file.exists()) {
//            Scanner data_input = new Scanner(new_file);
//            while (data_input.hasNextLine()) {
//                String[] data_row = data_input.nextLine().split(",");
//                String[] entry_row = new String[]{
//                        data_row[3],data_row[4]
//                };
//              if (new_database == null) {
//                  new_database = new Database(data_row.length);
//                  new_database.set_data_head(data_row);
//
//              }
//              new_database.create(data_row);
//                new_database.read(entry_row[0]);
//                new_database.update(entry_row,entry_row);
//
//
//            }
//        }
//        data_input.close();
//    }

    public static void main(String[] args) {

        System.out.println("---------------------------------------------");
        System.out.println("   Welcome To DreamTeam Database Simulator   ");
        System.out.println("---------------------------------------------");
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        int input;
        do {
            System.out.println("What simulation are you running?");
            System.out.println("1. BUYER EVENT    2. SUPPLIER EVENT    3. EXIT");
            System.out.print("? ");

            input = scanner.nextInt();
            if(input == 1){
                System.out.println("Buyer event method is a work in progress.");
                System.out.println("");
                //This input will call the buyer event method
                //buyerEvent();
            }
            else if(input == 2){
                System.out.println("Supplier event method still a work in progress.");
                System.out.println("");
                //This input will call the supplier event method
                //supplierEvent();

            }
            else if(input == 3){
                System.out.println("Exiting simulation...");
                scanner.close();
                System.out.println("");
            }
            else{
                System.out.println("Invalid input.");
                System.out.println("");
            }
        } while (input != 3);

        System.out.println("Exited simulation.");

    }

}
