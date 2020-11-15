package com.dreamteam.database;

import java.util.Scanner;

public interface Database<E> {

	// TODO javadoc; methods in the interface do not require a javadoc where @override is used.
	/**
	 * 
	 * @param new_entry
	 */
	void create(E new_entry);
	
	/**
	 * 
	 * @param entry_string
	 */
	void create(String[] entry_string);
	
	/**
	 * 
	 * @param entry_string
	 */
    default void create(String entry_string) {
		create(entry_string.split(","));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(String id);
	
	/**
	 * 
	 * @return
	 */
	default int get_column_size() { return get_data_head().length; }
	
	/**
	 * 
	 * @return
	 */
	String[] get_data_head();
	
	/**
	 * Read existing entry from database.
	 *
	 * @param id
	 *
	 * @return the entry of the database if found.
	 */
	E read(String id);

	/**
	 * Find existing entry in database and update with new entry.
	 *
	 * @param existing_entry the new entry to overwrite the old one
	 * @param program_scanner the tool used to scan user update choices
	 *
	 * @return the old entry, in case we want to do something with it
	 */
	boolean update(E existing_entry, Scanner program_scanner);
	
	/**
	 *  Prints the number of current entries in the database.
	 */
	String display();

}
