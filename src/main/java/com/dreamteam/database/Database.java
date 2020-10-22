package com.dreamteam.database;

public interface Database<E> {

	// TODO javadoc; methods in the interface do not require a javadoc where @override is used.
	/**
	 * 
	 * @param new_entry
	 */
	public void create(E new_entry);
	
	/**
	 * 
	 * @param entry_string
	 */
	public void create(String[] entry_string);
	
	/**
	 * 
	 * @param entry_string
	 */
    public default void create(String entry_string) {
		create(entry_string.split(","));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id);
	
	/**
	 * 
	 * @return
	 */
	public default int get_column_size() { return get_data_head().length; }
	
	/**
	 * 
	 * @return
	 */
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
	 *  Prints the number of current entries in the database.
	 */
	public void display();

}
