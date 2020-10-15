package com.dreamteam.database;

import javax.mail.*; // This should cover all mail classes.
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class OrderSubmission {
	public static void main(String[] args) throws MessagingException
	{
		String host1 = "smtp.gmail.com";
		String host2 = "pop.gmail.com";
		String mailStoreType = "pop3";
		Properties props1 = new Properties();
		props1.put("mail.smtp.host", host1);
		props1.put("mail.smtp.port", 587);
		props1.put("mail.smtp.auth", "true");
		props1.put("mail.smtp.starttls.enable","true");
		Session session1 = Session.getInstance(props1, null);
		MimeMessage msg1 = new MimeMessage(session1);
		String fromEmail = "buyingallofyourthings@gmail.com";
		String password = "#DreamTeam1!";
		String toEmail = "cs3250team1dreamteam@gmail.com";
		SendMessage(msg1, fromEmail, toEmail, "Can I please have your stuff?", password, "SUBJECT: I'm seriously going to need your stuff.");
		System.out.println("Checking email, please hold...");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		checkEmail(host2, mailStoreType, toEmail, password);
	}
	
	public static void SendMessage(MimeMessage msg, String from, String to,
						String text, String password, String subject) throws MessagingException

	{
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setText(text);
		Transport.send(msg, from, password);
		System.out.println("Message sent successfully!");
	}

	public static void checkEmail(String host2, String storeType, String user, String password) {
		try {
			Properties props2 = new Properties();
			props2.put("mail.pop3.host", host2);
			props2.put("mail.pop3.port", "995");
			props2.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getInstance(props2, null);

			Store store = emailSession.getStore("pop3s");
			store.connect(host2, user, password);

			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			System.out.println("Total number of messages: " + messages.length);

			for (int i = 0; i < messages.length; i++) {
				Message message2 = messages[i];
				System.out.println("-----------------------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Email Subject: " + message2.getSubject());
				System.out.println("From: " + message2.getFrom()[0]);
				System.out.println("Text: " + message2.getContent().toString());
			}

			emailFolder.close(false); //TODO Should this be hardcoded?
			store.close();

		} catch (NoSuchProviderException e) { 
			e.printStackTrace(); 
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}

