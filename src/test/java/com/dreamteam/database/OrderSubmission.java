package com.dreamteam.database;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class OrderSubmission {
	public static void main(String[] args) throws MessagingException
	{
		String host = "smtp.gmail.com";	
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable","true");
		Session session = Session.getInstance(props, null);
		MimeMessage msg = new MimeMessage(session);
		String from = "cs3250team1dreamteam@gmail.com";
		String to = "alex.b.sanford@gmail.com";
		SendMessage(msg, from, to, "testing123cananybodyhearme", "#DreamTeam1!", "This is a test email");
	}
	
	public static void SendMessage(MimeMessage msg, String from, String to, String text, String password, String subject) throws MessagingException
	{
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setText(text);
		Transport.send(msg, from, password);
		System.out.println("Message sent successfully!");
	}
}
