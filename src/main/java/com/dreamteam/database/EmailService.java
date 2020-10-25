package com.dreamteam.database;

import javax.mail.*; // This should cover all mail classes.
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class EmailService {
	private final String HOST2;
	private final String USER; 
	private final String PASSWORD;
	
	public EmailService() {
		this.HOST2 = "pop.gmail.com";
		this.USER = "thedreamteamsoftware+orders@gmail.com";
		this.PASSWORD = "Sch00l2020!";
	}
	public static void main(String[] args) throws MessagingException
	{
		EmailService test = new EmailService();
		System.out.println(test.checkEmail());
		//checkEmail(host2, mailStoreType, toEmail, password);
	}
	
	public static void SendMessage() throws MessagingException
	{
		String host1 = "smtp.gmail.com";
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
		System.out.println("Checking email, please hold...");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		msg1.setFrom(new InternetAddress(fromEmail));
		msg1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		msg1.setSubject("Blah");
		msg1.setSentDate(new Date());
		msg1.setText("blah");
		Transport.send(msg1, fromEmail, password);
		System.out.println("Message sent successfully!");
	}

	public String[] checkEmail() {
		String[] emailContents;
		try {
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
			emailContents = new String[messages.length];

			for (int i = 0; i < messages.length; i++) {
				Message message2 = messages[i];
				System.out.println("-----------------------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Email Subject: " + message2.getSubject());
				System.out.println("From: " + message2.getFrom()[0]);
				System.out.println("Text: " + message2.getContent().toString());
				emailContents[i] = message2.getContent().toString();
			}

			emailFolder.close(false); //TODO Should this be hardcoded?
			store.close();

		} catch (NoSuchProviderException e) { 
			e.printStackTrace();
			emailContents = new String[]{""};
		} catch (MessagingException e) {
			e.printStackTrace();
			emailContents = new String[]{""};
		} catch (Exception e) {
			e.printStackTrace();
			emailContents = new String[]{""};
		}
		return emailContents;
	}
}

