package com.dreamteam.database;

import javax.mail.*;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.Calendar.*;

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
	
	public static void sendMessage(Order new_order) throws MessagingException
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
		System.out.println("Checking email, please hold...");
		String confirmationMessage = new_order.prettyPrint() +
		 "Your order has been successfully submitted. Thank you for choosing the Dream Team!";
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
		System.out.println("Message sent successfully!");
	}
	
	public String[] checkEmail()
	{
		final OrderDatabase od = OrderDatabase.getOrders();
		String[] orderContents;
		String[] bodyText;
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
			System.out.println("Total number of messages: " + messages.length);
			orderContents = new String[5];
			bodyText = new String[messages.length];
			
			for(int i = 0; i < messages.length; i++)
			{
				Message message2 = messages[i];
				bodyText[i] = getTextFromMessage(message2);
				String[] toArray = bodyText[i].split(" ");
				//System.out.println(Arrays.toString(toArray));
				for(int k = 0; k < toArray.length; k++)
				{
					if(toArray[k].contains("Submitted"))
					{
						orderContents[0] = toArray[k + 1]
						 + toArray[k + 2]
						 + toArray[k + 3]
						 + toArray[k + 4]
						 + toArray[k + 5]
						 + toArray[k + 6];
						//System.out.println(orderContents[0]);
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
							Order emailOrder = od.read(orderContents[0], Arrays.toString(orderContents));
							if (od.contains(emailOrder)) {
								sendMessage(emailOrder);
							}
							break;
						}
					}
				}
				
				//if(message2.getSubject().contains("order"))
				//{
				//orderContents[i] = message2.getContent().toString();
				//System.out.println(orderContents[i]);
				//}
				//System.out.println("-----------------------------------------------");
				//System.out.println("Email Number " + (i + 1));
				//System.out.println("Email Subject: " + message2.getSubject());
				//System.out.println("From: " + message2.getFrom()[0]);
				//System.out.println("Text: " + message2.getContent().toString());
			}
			
			emailFolder.close(false); //TODO Should this be hardcoded?
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
		// alternatives appear in an order of increasing 
		// faithfulness to the original content. Customize as req'd.
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
}

