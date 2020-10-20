package com.dreamteam.database;

public interface Database<E> {

	public void create(E new_entry);
	
	public void create(String[] entry_string);
		
    public default void create(String entry_string) {
		create(entry_string.split(","));
	}

	public boolean delete(String id);
	
	public int get_column_size();
	
	public String[] get_data_head();
	
	/**
	 * Read existing entry from database.
	 *
	 * @param id
	 *
	 * @return the entry of the database if found.
	 */
	public E read(String id);
	
	public void set_data_head(String[] labels);
	
	/**
	 * Find existing entry in database and update with new entry.
	 *
	 * @param existing_entry the new entry to overwrite the old one
	 *
	 * @return the old entry, in case we want to do something with it
	 */
	public boolean update(E existing_entry);
	
	/**
	 * Prints the entire database to console. May want to disable in finished
	 * project. Also prints the number of current entries in database.
	 */
	public void display();


}
