package com.dreamteam.database;

public interface Database<E> {
	
	/**
	 * Check to see if we can make a sale
	 *
	 * @param attemptedQuantity which we need to have enough of
	 */
	public boolean canProcessOrder(String id, int attemptedQuantity);

	public void create(E new_entry);
	
	public void create(String entry_string);
	
	public void create(String[] entry_string);
	
	/**
	 * Delete existing entry from database.
	 *
	 * @param id
	 *
	 * @return the old Entry if there was one. otherwise, null
	 */
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
	
	// public boolean update(String string) {
	// 	DatabaseEntry new_entry = read(string);
	// 	return (data_table.put(new_entry.getProductID(), new_entry) != null);
	// }
	
	// @Override public String toString() {
	// 	StringBuilder sb = new StringBuilder();
	// 	String headers = Arrays.toString(data_head);
	// 	headers = headers.substring(1, headers.length() -1);
	// 	sb.append(headers).append("\n");
	// 	Iterator<Entry> itr = data_table.values().iterator();
	// 	while(itr.hasNext()) {
	// 		Entry next = itr.next();
	// 		if(next == null) {
	// 			System.out.println("but why???");
	// 		}
	// 		sb.append(next).append("\n");
	// 	}
	// 	return sb.toString();
	// }
	
	/**
	 * Prints the entire database to console. May want to disable in finished
	 * project. Also prints the number of current entries in database.
	 */
	public void display();

	// public static void printDatabase() throws FileNotFoundException {
	// 	Database db = new Database("inventory_team1.csv");
	// 	Entry entry = db.get("ULSGKCQO385Y");
	// 	System.out.println(entry);
	// 	entry.prettyPrint();
	// }

}
