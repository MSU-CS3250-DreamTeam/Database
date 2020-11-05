package com.dreamteam.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.EnumSet;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class main {
	// Variable Declarations
	static private final ProductDatabase PRODUCT_DATABASE = ProductDatabase.getProducts();
	static private final OrderDatabase ORDER_DATABASE = OrderDatabase.getOrders();
	static private final Scanner MAIN_SCANNER = new Scanner(System.in);

	// ***************************************************************************

	/**
	 * @param args
	 */
	static public void main(String[] args) {

		// Welcome to DreamTeam DataBase
		System.out.println("-------------------------------------------------------------------");
		System.out.println("               Welcome to DreamTeam DataBase                       ");
		System.out.println("-------------------------------------------------------------------");

		// For debugging. Disable in final project.
		demo_database();

		// Call the menu for user to access and modify the database.
		runMenu();

		MAIN_SCANNER.close();
	} // End main method.

	// ***************************************************************************

	/*
	 * A demonstration of how to use the CRUD methods on an active, visible database
	 * object.
	 */
	private static void demo_database() {

		System.out.println("\nTesting the product database.");
		System.out.println("-----------------------------");

		String existing_product_id = "8XXKZRELM2JJ";
		String new_product = "AGEXCVFG3344,3260,370.51,623.94,SASERNVV";
		Product existing_product;
		Product created_product;

		System.out.print("\nRetrieving a product: ");
		existing_product = PRODUCT_DATABASE.read(existing_product_id);
		existing_product.prettyPrint();

		System.out.print("\nRemoving a product: ");
		if (PRODUCT_DATABASE.delete(existing_product_id))
			System.out.println("product removed.");
		PRODUCT_DATABASE.display();

		System.out.print("\nExisting product should not be found: ");
		PRODUCT_DATABASE.read(existing_product_id);

		System.out.print("\nNew product should be found: ");
		PRODUCT_DATABASE.create(new_product);
		PRODUCT_DATABASE.display();
		created_product = PRODUCT_DATABASE.read(new_product.split(",")[0]);

		System.out.print("\nRetrieving a product. ");
		PRODUCT_DATABASE.read(created_product.getProductID()).prettyPrint();

		System.out.print("\nBuy quantity of 4000: ");
		created_product.buyQuantity(4000);

		System.out.print("\nRetrieving updated product in the product database: ");
		PRODUCT_DATABASE.read(created_product.getProductID()).prettyPrint();

		System.out.print("\n\nRemoving dummy product: ");
		if (PRODUCT_DATABASE.delete(created_product.getProductID()))
			System.out.println("product removed.");
		PRODUCT_DATABASE.display();

		PRODUCT_DATABASE.create(existing_product);

		System.out.println("\n\n-----------------------------");
		System.out.println("      Testing complete.      ");
		System.out.println("-----------------------------\n");

	}

	// ***************************************************************************

	/**
	 * Loops through the options of a menu for the user to access and modify the
	 * database.
	 */
	public static void runMenu() {

		// Local Variable Declarations
		Options user_choice;
		final EnumSet<Options> MAIN_MENU = EnumSet.of(Options.CREATE, Options.READ, Options.UPDATE,
								Options.DELETE, Options.PROCESS_ORDERS, Options.REPORTS, Options.CHECK_EMAIL, Options.QUIT);
		Menu menu = new Menu(MAIN_MENU);
		String[] database_header = PRODUCT_DATABASE.get_data_head();
		String[] new_entry = new String[PRODUCT_DATABASE.get_column_size()];
		Product existing_entry;

		System.out.println("\n-----------------------------");
		System.out.println("       Launching Menu        ");
		System.out.println("-----------------------------\n");

		do {

			PRODUCT_DATABASE.display();
			ORDER_DATABASE.display();
			System.out.println();
			user_choice = menu.getOption(MAIN_SCANNER);

			switch (user_choice) {

				case CREATE:

					for (int i = 0; i < database_header.length; i++) {
						System.out.print("Enter " + database_header[i] + ": ");
						new_entry[i] = MAIN_SCANNER.nextLine();
					}
					PRODUCT_DATABASE.create(new_entry);
					
					Product new_product = PRODUCT_DATABASE.read(new_entry[0]); // Prints the object address in memory.
					new_product.prettyPrint();

					break;
				
				case READ:
					
					System.out.print("Enter " + database_header[0] + ": ");
					existing_entry = PRODUCT_DATABASE.read(MAIN_SCANNER.nextLine());
					
					if(existing_entry != null) {
						existing_entry.prettyPrint();
					}
					
					break;
				
				case UPDATE:
					
					System.out.print("Enter existing entry's " + database_header[0] + ": ");
					existing_entry = PRODUCT_DATABASE.read(MAIN_SCANNER.nextLine());
					
					if ((existing_entry != null) && !(existing_entry.getProductID().equals("000"))) {
						PRODUCT_DATABASE.update(existing_entry, MAIN_SCANNER);
						existing_entry.prettyPrint();
					}

					break;
				
				case DELETE:
					
					System.out.print("Enter " + database_header[0] + ": ");
					PRODUCT_DATABASE.delete(MAIN_SCANNER.nextLine());

					break;
				
				case PROCESS_ORDERS:
					ORDER_DATABASE.processOrders();

					System.out.println("Simulation processed.");
					
					break;

				case REPORTS:

					System.out.println("For which date would you like reports?");
					String date = MAIN_SCANNER.nextLine();
					if (ORDER_DATABASE.contains(date)) {
						System.out.println("Reports generating...");

						dailyAssetsReport(date);
						dailyTopTenReport(date);
					}

					break;
					
				case CHECK_EMAIL:
					
					System.out.println("Checking inbox...");
					EmailService email = new EmailService();
					email.checkEmail();
					break;
					
				case QUIT:
					
					System.out.println("\nSaving Database changes...");
					System.out.println("Done!");
					System.out.println("Bye!");
					break;
				
				default:
					System.out.println("The " + user_choice + " option is not in this menu.");
			}
		} while(user_choice != Options.QUIT);
	}

	//	***************************************************************************

	/**
	 *	Writes a daily report of assets and sales to a pdf.
	 * @param date
	 */
	public static void dailyAssetsReport(String date) {

		// Preparing text information.
		NumberFormat formatter = NumberFormat.getCurrencyInstance(); //to format the print statements in dollar form
		String daily_assets = formatter.format(PRODUCT_DATABASE.countAssets());
		String daily_sales = formatter.format(ORDER_DATABASE.countSales(date));
		final String ASSETS_STATEMENT = "The company's total value in assets is " + daily_assets + ".";
		final String ORDERS_STATEMENT = "The total number of customer orders is "
														+ ORDER_DATABASE.countDailyOrders(date) + ".";
		final String SALES_STATEMENT = "The total dollar amount of orders is " + daily_sales + ".";


		// Setting up graph tools
		String report_path = "files/reports/daily-report_" + date;
		String[] plot_daily_data;
		double plot_assets, plot_sales;
		int day_index = 0;
		XYSeries assets_line = new XYSeries("Assets $");
		XYSeries sales_line = new XYSeries("Sales $");
		final XYSeriesCollection ASSETS_SERIES, SALES_SERIES;
		JFreeChart assets_chart, sales_chart;
		double current_assets = PRODUCT_DATABASE.countAssets();


		// Setting up pdf document.
		String annual_data_path = "files/annual-plot-data.csv";
		File annual_data_file = new File(annual_data_path);
		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);


		try (PDPageContentStream pdf_writer = new PDPageContentStream(document, page);
			 FileWriter data_writer = new FileWriter(annual_data_path, true);
			 Scanner annual_data_scanner = new Scanner(annual_data_file)) {

			pdf_writer.beginText();
			pdf_writer.moveTextPositionByAmount(100, 800);
			PDFont font = PDType1Font.HELVETICA;
			pdf_writer.setFont(font, 20);
			pdf_writer.showText("Daily Report of " + date);
			pdf_writer.moveTextPositionByAmount(0, -23);
			pdf_writer.setFont(font, 14);
			pdf_writer.moveTextPositionByAmount(-40, -17);
			pdf_writer.showText(ASSETS_STATEMENT);
			pdf_writer.moveTextPositionByAmount(0, -17);
			pdf_writer.showText(ORDERS_STATEMENT);
			pdf_writer.moveTextPositionByAmount(0, -17);
			pdf_writer.showText(SALES_STATEMENT);
			pdf_writer.endText();

			data_writer.write(date + "," + PRODUCT_DATABASE.countAssets() + "," + ORDER_DATABASE.countSales(date) + "\n");
			System.out.println("Successfully wrote statements to the file.");

			while (annual_data_scanner.hasNextLine()) {
				plot_daily_data = annual_data_scanner.nextLine().split(",");
				plot_assets = Double.parseDouble((plot_daily_data[1]));
				plot_sales = Double.parseDouble((plot_daily_data[2]));

				assets_line.add(day_index, plot_assets);
				sales_line.add(day_index++, plot_sales);
			}

			double min_value = assets_line.getMinY();
			double max_value = assets_line.getMaxY();

			ASSETS_SERIES = new XYSeriesCollection(assets_line);
			SALES_SERIES = new XYSeriesCollection(sales_line);

			assets_chart = ChartFactory.createXYLineChart("Assets", "days",
															"dollars", ASSETS_SERIES);
			sales_chart = ChartFactory.createXYLineChart("Sales", "days",
															"dollars", SALES_SERIES);

			XYPlot plot = (XYPlot) assets_chart.getPlot();
			ValueAxis axis = plot.getRangeAxis();
			axis.setLowerBound(min_value);
			axis.setUpperBound(max_value);

			ChartUtils.saveChartAsJPEG(new File("files/assets-chart.jpg"), assets_chart, 500, 300);
			ChartUtils.saveChartAsJPEG(new File("files/sales-chart.jpg"), sales_chart, 500, 300);

			PDImageXObject asset_img = PDImageXObject.createFromFile("files/assets-chart.jpg", document);
			PDImageXObject sales_img = PDImageXObject.createFromFile("files/sales-chart.jpg", document);

			pdf_writer.drawImage(asset_img, 20, 400);
			pdf_writer.drawImage(sales_img, 20, 40);

			pdf_writer.close();
			document.save(report_path + ".pdf");

			System.out.println("Successfully added charts to the report.");

		} catch (IOException e) {
			System.out.println("Failed to create or write to file.");
			e.printStackTrace();
		}

	}

	
	//	***************************************************************************	

	//	TODO Write the top products and customers (by spending) to a daily report file

	/**
	 *
	 * @param date
	 */
	private static void dailyTopTenReport(String date) {
		ORDER_DATABASE.findTopCustomers(date);
		ORDER_DATABASE.findTopProducts(date);
	}

	//	***************************************************************************
	
	/**
	 * 
	 * @param customer
	 * @param date
	 * @param time
	 */
	protected static void updateCustomerHistory(String customer, String date, String time) {
		String location = "files/buyer_order_history.csv";
		String new_order = customer + ", " + date + ", " + time + '\n';
		
		try (FileWriter writer = new FileWriter(location, true)) {
			writer.append(new_order);
			
			System.out.println("Realtime order appended to file in relative path: " + location);
			writer.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
} // End main class. EOF
