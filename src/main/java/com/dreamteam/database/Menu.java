package com.dreamteam.database;

import java.util.Scanner;

public class Menu {

    int length;
    String[] options;

    public Menu(String[] options) {
        this.options = options;
        this.length = options.length;
    }

    //	***************************************************************************

	/**
	 * Prompt the user for a correct option of the existing menu.
	 *
	 * @return the selected option to menu.
	 */
	public int getOption() {
        
        // Local Variable Declarations
        Scanner option_sc = main.main_scanner;
		int user_input;
		
		while(true) {
			
			// Prompt user for a choice from Options.
			System.out.println("Options:");
			for(int i = 0; i < options.length; i++) {
				System.out.println("  " + (i + 1) + ": " + options[i]);
			}
			System.out.print("? ");
			
			try {

				user_input = Integer.parseInt(option_sc.nextLine()) - 1;
				for(String choice: options) {
					if(options[user_input].equals(choice)) {
						return user_input;
					}
				}
				
			}
			catch(NumberFormatException ex) {
				
				System.err.println(ex.getMessage());
				System.out.println("Error! Incorrect format.");
			}
			
			System.out.println("That option does not exist.");
		}
	}
}
