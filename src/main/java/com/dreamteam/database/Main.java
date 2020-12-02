package com.dreamteam.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

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

public class Main {
	// Variable Declarations
	private static final ProductDatabase PRODUCT_DATABASE = ProductDatabase.getProducts();
	private static final OrderDatabase ORDER_DATABASE = OrderDatabase.getOrders();
	private static final Scanner MAIN_SCANNER = new Scanner(System.in);

	// ***************************************************************************

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		// Call the menu for user to access and modify the database.
		runMenu();

		MAIN_SCANNER.close();
	} // End main method.

	// ***************************************************************************

	public static Scanner getScanner() { return MAIN_SCANNER; }

	// ***************************************************************************

	/**
	 * Loops through the options of a menu for the user to access and modify the
	 * database.
	 */
	public static void runMenu() {

		// Local Variable Declarations
		Options user_choice;
		final EnumSet<Options> MAIN_MENU = EnumSet.of(Options.CREATE, Options.READ, Options.UPDATE,
								Options.DELETE, Options.PROCESS_ORDERS, Options.REPORTS, Options.TOP_CUSTOMERS, Options.TOP_PRODUCTS, Options.CHECK_EMAIL, Options.QUIT);

		Menu menu = new Menu(MAIN_MENU);
		String[] database_header = PRODUCT_DATABASE.get_data_head();
		Product existing_entry;
		String date;

		menu.printMessage("Welcome to DreamTeam DataBase");

		do {
			ArrayList<String> option_fields = new ArrayList<>();
			menu.printMessage(PRODUCT_DATABASE.display());
			menu.printMessage(ORDER_DATABASE.display());
			user_choice = menu.getOption();

			switch (user_choice) {

				case CREATE:

					option_fields.addAll(Arrays.asList(database_header));
					option_fields = menu.runTextReader(Options.CREATE, option_fields);
					String[] new_entry = new String[database_header.length];

					for (String field : option_fields) {
						new_entry[option_fields.indexOf(field)] = field;
					}
					PRODUCT_DATABASE.create(new_entry);
					
					Product new_product = PRODUCT_DATABASE.read(new_entry[0]);
					menu.printMessage("You created the product: " + new_product.toString());
					new_product.prettyPrint();

					break;
				
				case READ:
					
					option_fields.add(database_header[0]);
					option_fields = menu.runTextReader(Options.READ, option_fields);
					existing_entry = PRODUCT_DATABASE.read(option_fields.get(0));
					
					if(existing_entry != null) {
						menu.printMessage(existing_entry.prettyPrint());
					} else {
						menu.printMessage("The product was not found.");
					}
					
					break;
				
				case UPDATE:
					
					option_fields.add(database_header[0]);
					option_fields = menu.runTextReader(Options.UPDATE, option_fields);
					existing_entry = PRODUCT_DATABASE.read(option_fields.get(0));
					
					if ((existing_entry != null) && !(existing_entry.getProductID().equals("000"))) {
						PRODUCT_DATABASE.update(existing_entry, MAIN_SCANNER);
						menu.printMessage(existing_entry.prettyPrint());
					} else {
						menu.printMessage("The product database does not contain an entry for: "
								+ option_fields.get(0));
					}

					break;
				
				case DELETE:

					option_fields.add(database_header[0]);
					option_fields = menu.runTextReader(Options.UPDATE, option_fields);
					if (PRODUCT_DATABASE.delete(option_fields.get(0)))
						menu.printMessage(option_fields.get(0) + " was successfully deleted.");

					break;
				
				case PROCESS_ORDERS:
					menu.printMessage("Processing orders...");
					String order_log_path = "files/customer_orders_A_team1.csv";
					String final_log_path = "files/customer_orders_final_team1.csv";
					ORDER_DATABASE.processOrders(order_log_path);
					ORDER_DATABASE.processOrders(final_log_path);
					menu.printMessage("Simulation processed.");
					
					break;

				case REPORTS:

					menu.printMessage("For which date would you like reports?");
					option_fields.add("Date");
					option_fields = menu.runTextReader(Options.REPORTS, option_fields);

					date = option_fields.get(0);
					if (ORDER_DATABASE.contains(date)) {
						menu.printMessage("Reports generating...");
						File pdfFile = new File("files/reports/daily-report_" + date + ".pdf");
						menu.showFile(pdfFile);
					}

					break;
					
				case CHECK_EMAIL:
					
					menu.printMessage("Checking inbox...");
					EmailService email = new EmailService();
					email.checkEmail();
					break;
					
				case TOP_PRODUCTS:

					menu.printMessage("For which date would you like the top products?");
					option_fields.add("Date");
					option_fields = menu.runTextReader(Options.REPORTS, option_fields);
					date = option_fields.get(0);
					LinkedHashMap<String, Double> top_products = ORDER_DATABASE.findTopProducts(date);

					StringBuilder products = new StringBuilder(date + " Top Products:\n");
					for (String product : top_products.keySet()){
						products.append("\t");
						products.append(product);
						products.append(": ");
						products.append(top_products.get(product));
						products.append("\n");
					}
					menu.printMessage(products.toString());

					break;

				case TOP_CUSTOMERS:

					menu.printMessage("For which date would you like the top products?");
					option_fields.add("Date");
					option_fields = menu.runTextReader(Options.REPORTS, option_fields);
					date = option_fields.get(0);
					LinkedHashMap<String, Double> top_customers = ORDER_DATABASE.findTopCustomers(date);
					StringBuilder customers = new StringBuilder(date + " Top Customers:\n");
					for (String customer : top_customers.keySet()){
						customers.append("\t");
						customers.append(customer);
						customers.append(": ");
						customers.append(top_customers.get(customer));
						customers.append("\n");
					}
					menu.printMessage(customers.toString());
					break;
				
				case QUIT:
					menu.closeMenu();
					break;
				
				default:
					menu.printMessage("The " + user_choice + " option is not in this menu.");
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

		// Setting up pdf document.
		String annual_data_path = "files/annual-plot-data.csv";
		File annual_data_file = new File(annual_data_path);
		PDDocument document = new PDDocument();
		PDPage page = new PDPage(PDRectangle.A4);
		document.addPage(page);
		PDPageContentStream pdf_writer;

		try (FileWriter data_writer = new FileWriter(annual_data_path, true);
			 Scanner annual_data_scanner = new Scanner(annual_data_file)) {
			
			pdf_writer = new PDPageContentStream(document, page);
			pdf_writer.beginText();
			pdf_writer.newLineAtOffset(100, 800);
			PDFont font = PDType1Font.HELVETICA;
			pdf_writer.setLeading(23);
			pdf_writer.setFont(font, 20);
			pdf_writer.showText("Daily Report of " + date);
			pdf_writer.newLine();
			pdf_writer.setLeading(17);
			pdf_writer.setFont(font, 14);
			pdf_writer.newLine();
			pdf_writer.showText(ASSETS_STATEMENT);
			pdf_writer.newLine();
			pdf_writer.showText(ORDERS_STATEMENT);
			pdf_writer.newLine();
			pdf_writer.showText(SALES_STATEMENT);
			pdf_writer.endText();

			data_writer.write(date + "," + PRODUCT_DATABASE.countAssets() + "," + ORDER_DATABASE.countSales(date) + "\n");

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

			PDPage stats_page = new PDPage(PDRectangle.A4);
			document.addPage(stats_page);
			pdf_writer = new PDPageContentStream(document, stats_page);

			pdf_writer.beginText();
			pdf_writer.newLineAtOffset(100, 800);
			pdf_writer.setLeading(23);
			pdf_writer.setFont(font, 20);
			pdf_writer.showText("Top Products ");
			pdf_writer.newLine();
			pdf_writer.setLeading(17);
			pdf_writer.setFont(font, 14);

			LinkedHashMap<String, Double> top_products = ORDER_DATABASE.findTopProducts(date);
			int top_index = 1;
			for (String id : top_products.keySet()) {
				pdf_writer.showText(id + ", $" + top_products.get(id));
				pdf_writer.newLine();
				if (top_index++ >= 10)
				{
					top_index = 1;
					break;
				}
			}
			
			pdf_writer.newLine();
			pdf_writer.newLine();
			pdf_writer.setFont(font, 20);
			pdf_writer.setLeading(23);
			pdf_writer.showText("Top Customers ");
			pdf_writer.newLine();
			pdf_writer.setFont(font, 14);
			pdf_writer.setLeading(17);

			LinkedHashMap<String, Double> top_customers = ORDER_DATABASE.findTopCustomers(date);
			for (String id : top_customers.keySet()) {
				pdf_writer.showText(id + ", $" + top_customers.get(id));
				pdf_writer.newLine();
				if (top_index++ >= 10)
				{
					break;
				}
			}
			
			pdf_writer.endText();
			pdf_writer.close();
			document.save(report_path + ".pdf");
			document.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create or write to a pdf document: " + report_path);
		}

	}

	
	//	***************************************************************************	

	//	TODO Write the top products and customers (by spending) to a daily report file
	
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
			writer.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.println("Failed to append a transaction to buyer history: " + location);
		}
	}
} // End main class. EOF
