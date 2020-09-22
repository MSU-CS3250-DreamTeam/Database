package com.dreamteam.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseSimulator {

    @BeforeAll
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

    //BUYER EVENT
//     public static void buyerEvent(){
//         This method reads from the respective buyer event file
//         Update quantity, remove products/data rows if necessary
//     }

   @Test
   public static void supplierEvent() throws FileNotFoundException {
       File new_file = new File("");
       int quanity = 0;
       String[] database_entry = new String[5];
       if (new_file.exists()) {
           Scanner data_input = new Scanner(new_file);

           while (data_input.hasNextLine()) {
               String[] data_row = data_input.nextLine().split(",");
               String[] entry_row = new String[]{
                       data_row[3],data_row[4]
            };
               new_database.create(data_row);
               database_entry = new_database.read(entry_row[0]);
               quanity = Integer.parseInt(database_entry[1]) + Integer.parseInt(entry_row[1];
               database_entry[1] = Integer.toString(quanity);
               new_database.update(entry_row,database_entry);

               assertEquals(quanity,new_database.read(entry_row[0])[1]);

           }
           data_input.close();

    
       } else {
            throw new FileNotFoundException("Is the data file in the wrong directory?");
       }
       
    }
}
