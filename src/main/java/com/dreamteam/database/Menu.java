package com.dreamteam.database;

import java.util.EnumSet;
import java.util.Scanner;

public class Menu {

    private final EnumSet<Options> OPTIONS;

    public Menu(EnumSet<Options> options) {
		this.OPTIONS = options;
    }

    //	***************************************************************************

	/**
	 * Prompt the user for a correct option of the existing menu.
	 *
	 * @return the selected option to menu.
	 */
	public Options getOption(Scanner option_scanner) {
		
		while(true) {
			
			// Prompt user for a choice from Options.
			System.out.println("Options:");

			for (Options option: OPTIONS) {
				System.out.println(" " + option.getValue() + ": " + option);
			}
			System.out.print("? ");

			try {

				int user_choice = Integer.parseInt(option_scanner.nextLine());
				
				for (Options choice : Options.values()) {
					if (user_choice == choice.getValue()) {
						return choice;
					}
				}
				
			} catch(NumberFormatException ex) {
				System.err.println(ex.getMessage());
				System.out.println("Error! Incorrect format.");
			}
			
			System.out.println("That option does not exist.");
		}
	}
}
