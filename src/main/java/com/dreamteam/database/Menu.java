package com.dreamteam.database;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final List<Options> OPTIONS;

    public Menu(List<Options> options) {
		this.OPTIONS = options;
    }

    //	***************************************************************************

	/**
	 * Prompt the user for a correct option of the existing menu.
	 *
	 * @return the selected option to menu.
	 */
	public Options getOption() {
        
        // Local Variable Declarations
        Scanner option_scanner = main.main_scanner;
		int user_input = 1;
		
		while(true) {
			
			// Prompt user for a choice from Options.
			System.out.println("Options:");

			for (Options option: OPTIONS) {
				System.out.println(" " + option.getValue() + ": " + option);
			}
			System.out.print("? ");
			
			try {

				user_input = Integer.parseInt(option_scanner.nextLine());
				
				for (Options user_choice : Options.values()) {
					if (user_input == user_choice.getValue()) {
						return user_choice;
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
