package com.dreamteam.database;

import javax.mail.*;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailService {
	private final String HOST2;
	private final String USER;
	private final String PASSWORD;
	
	public EmailService()
	{
		this.HOST2 = "pop.gmail.com";
		this.USER = "thedreamteamsoftware+orders@gmail.com";
		this.PASSWORD = "Sch00l2020!";
	}
	
	public static void sendConfirmationMessage(Order new_order) throws MessagingException
	{
		String host1 = "smtp.gmail.com";
		Properties props1 = new Properties();
		props1.put("mail.smtp.host", host1);
		props1.put("mail.smtp.port", 587);
		props1.put("mail.smtp.auth", "true");
		props1.put("mail.smtp.starttls.enable", "true");
		Session session1 = Session.getInstance(props1, null);
		MimeMessage msg1 = new MimeMessage(session1);
		String fromEmail = "thedreamteamsoftware@gmail.com";
		String password = "Sch00l2020!";
		String toEmail = "thedreamteamsoftware+orders@gmail.com";
		String confirmationMessage = new_order.prettyPrint() +
		 "Your order has been successfully submitted. Thank you for choosing the Dream Team!";

		final OrderDatabase OD = OrderDatabase.getOrders();
		confirmationMessage += "\nRecommended Products\n" + OD.findRecommendedProducts(new_order).toString();

		try
		{
			TimeUnit.SECONDS.sleep(5);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
		
		msg1.setFrom(new InternetAddress(fromEmail));
		msg1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		msg1.setSubject("Order Confirmation");
		msg1.setSentDate(new Date());
		msg1.setText(confirmationMessage);
		Transport.send(msg1, fromEmail, password);
	}
	
	public static void sendCancellationMessage(Order new_order) throws MessagingException
	{
		String host2 = "smtp.gmail.com";
		Properties props2 = new Properties();
		props2.put("mail.smtp.host", host2);
		props2.put("mail.smtp.port", 587);
		props2.put("mail.smtp.auth", "true");
		props2.put("mail.smtp.starttls.enable", "true");
		Session session2 = Session.getInstance(props2, null);
		MimeMessage msg2 = new MimeMessage(session2);
		String fromEmail = "thedreamteamsoftware@gmail.com";
		String password = "Sch00l2020!";
		String toEmail = "thedreamteamsoftware+orders@gmail.com";
		String cancellationMessage =
		 "We are sorry to hear you need to cancel your order on " + new_order.getDate() + ". " +
		  "Your order of " + new_order.getProductID() +
		  " has been successfully cancelled. We look forward to your business in the " +
		  "future!";
		
		try
		{
			TimeUnit.SECONDS.sleep(5);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
		
		msg2.setFrom(new InternetAddress(fromEmail));
		msg2.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		msg2.setSubject("Order Cancellation");
		msg2.setSentDate(new Date());
		msg2.setText(cancellationMessage);
		Transport.send(msg2, fromEmail, password);
	}
	
	public String[] checkEmail()
	{
		final OrderDatabase od = OrderDatabase.getOrders();
		String[] orderContents;
		String[] bodyText;
		Menu email_menu = new Menu(EnumSet.of(Options.DONE));
		String orderEmail = "";
		String orderProduct = "";
		String orderDate = "";
		
		try
		{
			Properties props2 = new Properties();
			props2.put("mail.pop3.host", HOST2);
			props2.put("mail.pop3.port", "995");
			props2.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getInstance(props2, null);
			
			Store store = emailSession.getStore("pop3s");
			store.connect(HOST2, USER, PASSWORD);
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);
			
			Message[] messages = emailFolder.getMessages();
			email_menu.printMessage("Total number of messages: " + messages.length);
			orderContents = new String[5];
			bodyText = new String[messages.length];
			
			for(int i = 0; i < messages.length; i++)
			{
				Message message2 = messages[i];
				bodyText[i] = getTextFromMessage(message2);
				String[] toArray = bodyText[i].split(" ");
				for(int k = 0; k < toArray.length; k++)
				{
					if(message2.getSubject().contains("Cancel"))
					{
						if(toArray[k].contains("email:"))
						{
							orderEmail = toArray[k + 1];
						}
						if(toArray[k].contains("product:"))
						{
							orderProduct = toArray[k + 1];
						}
						if(toArray[k].contains("date:"))
						{
							orderDate = toArray[k + 1];
						}
					}
					else
					{
						if(toArray[k].contains("Submitted"))
						{
							orderContents[0] = toArray[k + 1]
							 + toArray[k + 2]
							 + toArray[k + 3]
							 + toArray[k + 4]
							 + toArray[k + 5]
							 + toArray[k + 6];
							orderContents[0] = reformatDate(orderContents[0]);
						}
						if(toArray[k].contains("email:"))
						{
							orderContents[1] = toArray[k + 1];
						}
						if(toArray[k].contains("address:"))
						{
							orderContents[2] = toArray[k + 1];
						}
						if(toArray[k].contains("product:"))
						{
							orderContents[3] = toArray[k + 1];
						}
						if(toArray[k].contains("quantity:"))
						{
							orderContents[4] = toArray[k + 1];
						}
						if(!(orderContents[0] == null))
						{
							if(!(orderContents[0].equals("New")))
							{
								System.out.println(Arrays.toString(orderContents));
								od.create(orderContents);
								String regex = ",";
								String order_string = orderContents[0] + regex +
								 orderContents[1] + regex +
								 orderContents[2] + regex +
								 orderContents[3] + regex +
								 orderContents[4];
								
								Order emailOrder = od.read(orderContents[0], order_string);
								if(od.contains(emailOrder))
								{
									email_menu
									 .printMessage(
									  "Order successful! Sending confirmation email" +
									   ".");
									sendConfirmationMessage(emailOrder);
									email_menu.printMessage("Message sent successfully!");
								}
								break;
							}
						}
					}
				}
			}
			if(orderDate != null && !orderDate.equals("New") && !orderDate.isEmpty())
			{
				Order cancelledOrder = od.read(orderEmail, orderProduct, orderDate);
				if(od.delete(cancelledOrder))
				{
					email_menu.printMessage("Sending cancellation email...");
					sendCancellationMessage(cancelledOrder);
					email_menu.printMessage("Cancellation confirmation sent!");
				}
			}
			emailFolder.close(true);
			store.close();
		}
		catch(NoSuchProviderException e)
		{
			e.printStackTrace();
			orderContents = new String[] {""};
		}
		catch(MessagingException e)
		{
			e.printStackTrace();
			orderContents = new String[] {""};
		}
		catch(Exception e)
		{
			e.printStackTrace();
			orderContents = new String[] {""};
		}
		//This is a test of products id that match ids of 1208 to 1212 in the simulation file.
		Order test_order =
		 new Order(new String[] {
		  "2020-03-04", "dschne29@msudenver.edu", "80102", "6HWNDDX9A35J",
		  "10"
		 });
		email_menu.printMessage("Recommended products of " + test_order.toString());
		email_menu.printMessage(od.findRecommendedProducts(test_order).toString());
		if(email_menu.getOption() == Options.DONE)
		{
			email_menu.closeMenu();
		}
		
		return orderContents;
	}
	
	private String getTextFromMessage(Message message) throws IOException, MessagingException
	{
		String result = "";
		if(message.isMimeType("text/plain"))
		{
			result = message.getContent().toString();
		}
		else if(message.isMimeType("multipart/*"))
		{
			MimeMultipart mimeMultipart = (MimeMultipart)message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}
	
	private String getTextFromMimeMultipart(
	 MimeMultipart mimeMultipart) throws IOException, MessagingException
	{
		
		int count = mimeMultipart.getCount();
		if(count == 0)
		{
			throw new MessagingException("Multipart with no body parts not supported.");
		}
		boolean multipartAlt =
		 new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
		if(multipartAlt)
		{
			return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
		}
		String result = "";
		for(int i = 0; i < count; i++)
		{
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			result += getTextFromBodyPart(bodyPart);
		}
		return result;
	}
	
	private String getTextFromBodyPart(
	 BodyPart bodyPart) throws IOException, MessagingException
	{
		
		String result = "";
		if(bodyPart.isMimeType("text/plain"))
		{
			result = (String)bodyPart.getContent();
		}
		else if(bodyPart.isMimeType("text/html"))
		{
			String html = (String)bodyPart.getContent();
			result = org.jsoup.Jsoup.parse(html).text();
		}
		else if(bodyPart.getContent() instanceof MimeMultipart)
		{
			result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
		}
		return result;
	}
	
	private String reformatDate(String date)
	{
		int startIndex = date.indexOf("-");
		int endIndex = date.indexOf(".");
		String orderDay = date.substring(startIndex + 1, startIndex + 3);
		String orderMonth = date.substring(startIndex + 3, endIndex - 4);
		String orderYear = date.substring(endIndex - 4, endIndex);
		if(orderMonth.contains("October"))
		{
			orderMonth = orderMonth.replace("October", "10");
		}
		if(orderMonth.contains("November"))
		{
			orderMonth = orderMonth.replace("November", "11");
		}
		if(orderMonth.contains("December"))
		{
			orderMonth = orderMonth.replace("December", "12");
		}
		date = orderYear + "-" + orderMonth + "-" + orderDay;
		return date;
	}
}
