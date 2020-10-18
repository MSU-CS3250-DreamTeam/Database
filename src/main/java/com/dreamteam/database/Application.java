package com.dreamteam.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

@SpringBootApplication
public class Application {

	@Autowired
	private SendEmailService sendEmailService;
	// @Autowired
    // private JavaMailSender javaMailSender;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void triggerWhenStarts () {
		sendEmailService.sendEmail("thedreamteamsoftware+customer@gmail.com", "We have Received your order.", "Order Confirmation");
	}

	// @Autowired
	// public void sendEmail(String to, String body, String topic){
    //     SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    //     System.out.println("sending email");
    //     simpleMailMessage.setFrom("thedreamteamsoftware.com");
    //     simpleMailMessage.setTo(to);
    //     simpleMailMessage.setSubject(topic);
    //     simpleMailMessage.setText(body);
    //     javaMailSender.send(simpleMailMessage);
    //     System.out.println("sent email");
    // }

}
