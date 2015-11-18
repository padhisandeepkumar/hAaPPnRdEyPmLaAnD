package com.replad.mail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.replad.utils.LoadProperties;
import com.replad.utils.StringUtilities;

public class SendFeedback {
	String username = null;
	String password = null;
	
	public SendFeedback(){
		String file = StringUtilities.getConfigPath()+ File.separator+"mailsetting.properties";
		Properties props = null;
		try {
			props = new LoadProperties(new File(file)).getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.username = props.getProperty("mail.feedback.account.email.id");
		this.password = props.getProperty("mail.feedback.account.email.pwd");
	}

	public boolean SendUserFeedback(String name, String email, String contactNumber, String feedback) throws IOException{
		boolean flag = false;
		String file = StringUtilities.getConfigPath()+ File.separator+"mailsetting.properties";
		Properties props = new LoadProperties(new File(file)).getProperties();

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("rasmit.parida@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(props.getProperty("mail.user.feedback.sending.to.email.id")));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(email));
			message.setSubject(props.getProperty("mail.user.feedback.mail.subject"));
			message.setText(feedback);

			Transport.send(message);
			flag = true;
			System.out.println("User feedback sent...");

		} catch (MessagingException e) {
			flag = false;
			throw new RuntimeException(e);
		}
		return flag;
	}
	
	
	public boolean SendUserConfirmation(String name, String email) throws IOException{
		boolean flag = false;
		String file = StringUtilities.getConfigPath()+ File.separator+"mailsetting.properties";
		Properties props = new LoadProperties(new File(file)).getProperties();

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(props.getProperty("mail.user.feedback.sending.to.email.id")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Received your Feedback/Suggestion");
			message.setText("Hi "+name+",\n\t We are looking into your feedback/suggestions and quickly update you on the same.\n\n\nThanking you.\nARGO Placements.");

			Transport.send(message);
			flag = true;
			System.out.println("User feedback sent...");

		} catch (MessagingException e) {
			flag = false;
			throw new RuntimeException(e);
		}
		return flag;
	}

}
