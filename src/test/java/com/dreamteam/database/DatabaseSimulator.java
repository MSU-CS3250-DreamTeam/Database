package com.dreamteam.database;

import java.util.Scanner;

public class DatabaseSimulator {

    private final String buyFile = "buyEvent.csv";
    private final String supplyFile = "supplyEvent.csv";
    private String FILE_NAME;

    public static void BuyerEvent(){ //Not sure what parameters are needed quite yet
        //This method reads from the respective buyer event file
        //Update quantity, remove products/data rows if necessary
        //Store customer email and date of purchase
    }

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
                //BuyerEvent();
            }
            else if(input == 2){
                System.out.println("Supplier event method is a work in progress.");
                System.out.println("");
                //This input will call the supplier event method
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
