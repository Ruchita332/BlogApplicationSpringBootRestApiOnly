package com.ruchi.utils;


import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class EmailSender {

	// Email configuration
	@Value("${HOST}")
	public String host;
	@Value("${USERNAME}")
	public String username;
	@Value("${PASSWORD}")
	public String password;
	@Value("${PORT}")
	public int port;
	
	public String sendOtp (String email) {
		
		boolean flag = false;
	
		// Sender and recipient email addresses
		String senderEmail = username;
		String recipientEmail = email;
		
		//Email otp information
		
		String subject = "Verification Code";
		String otp = OtpGenerator.getOtp();
		String body = "OTP: " +otp;
		
		// Set the mail properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		
		// Create a Session object with the authentication credentials
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		Session session = Session.getInstance(properties, authenticator);
		
		try {
			//Create a MimeMessage object
			MimeMessage message = new MimeMessage (session);
			
			//Set the send and receipent address
			message.setFrom(new InternetAddress(senderEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
			
			//Set the email subject and body
			message.setSubject(subject);
			message.setText(body);
			
			//send the email
			Transport.send(message);
			
			flag = true;
		}
		catch(MessagingException e){
			e.getMessage();
		}
		
		if (flag) {
			return otp;
		}
		
		
		return null;
}
	
	public String sendMail(String email, String passCode) {
		
		
		boolean flag = false;
		
		//Mail properties and email Configuration
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable",  "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		
		
		//sender and receiptent
		String sender = username;
		String receiver = email;
		
		//Suject and body
		String subject ="Password";
		String body ="Password: " +passCode;
		
		//Session Object with the authentication credentials
		Session session = Session.getInstance(props , new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			//Create a Mime Message object
			MimeMessage message = new MimeMessage(session);
			
			//set sender and receiptent
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			//set message
			message.setSubject(receiver);
			message.setText(body);
			
			//send the email
			Transport.send(message);
			
			//messag sent
			flag = true;
			
		}
		catch (MessagingException e) {
			e.getMessage();
		}
		
		if (flag) {
			return passCode;
		}
		
		
		
		return null;
		
	}
	
}
