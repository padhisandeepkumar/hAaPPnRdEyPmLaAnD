package com;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class Test {

	public static void main(String[] args) {
		 SendGrid sendgrid = new SendGrid("replad.bangalore1", "Replad@123#");

		    SendGrid.Email email = new SendGrid.Email();

		    email.addTo("replad-invitation-group@googlegroups.com");
		    email.setFrom("rasmit.parida@gmail.com");
		    email.setSubject("Sending with SendGrid is Fun");
		    email.setHtml("and easy to do anywhere, even with Java");

		    try {
				SendGrid.Response response = sendgrid.send(email);
				System.out.println(response.getStatus()+"<--------->"+response.getMessage());
			} catch (SendGridException e) {
				e.printStackTrace();
			}
	}

}
