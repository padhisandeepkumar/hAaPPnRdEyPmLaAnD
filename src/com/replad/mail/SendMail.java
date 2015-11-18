package com.replad.mail;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import com.replad.init.InitConfiguration;
import com.replad.utils.LoadProperties;
import com.replad.utils.StringUtilities;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;

public class SendMail {

	private static final Logger log = Logger.getLogger(SendMail.class.getName());

	private String FROM = InitConfiguration.commonPropertiesMap.get("mail.account.email.id");
	private String USERID = null;
	private String PASSWORD = null;
	private Map<String, String> mailProps = null;

	public SendMail(){
		String fileName = StringUtilities.getConfigPath()+File.separator+"mailsetting.properties";
		File file = new File(fileName);
		this.mailProps = new LoadProperties(file).readProperties();
		String vendor = mailProps.get("replad.email.provider.vendor").toLowerCase();
		
		Map<String,MailConfigurationBean> map = InitConfiguration.mailSettingList;
		if(!map.isEmpty() && map.containsKey(vendor)){
			MailConfigurationBean bean = map.get(vendor);
			this.USERID = bean.getUser();
			this.PASSWORD = bean.getPwd();
		}
		
		if(StringUtilities.isNotBlank(this.USERID) || StringUtilities.isNotBlank(this.PASSWORD)){
			Map<String, String> mailProps = new LoadProperties(file).readProperties();
			if(!mailProps.isEmpty() && mailProps.containsKey("mail.account.email.id")){
				this.FROM = mailProps.get("mail.account.email.id");
			}
		}
	}
	
	public boolean sendEmailToUser(String toMailId, String subject, String body) {
		try {
			Map<String, String> supportdetails = new MailBodyUI().getSupportContactDetails();
			String supportEmailId = supportdetails.get("supportEmailId");
			SendGrid sendGrid = new SendGrid(USERID, PASSWORD);
			SendGrid.Email email = new SendGrid.Email();
			String emailId = "panda.minati@gmail.com";
			String[] bccEmails = {emailId, supportEmailId};
			email.setFrom(FROM);
			email.addTo(toMailId);
			email.addBcc(bccEmails);
			email.setSubject(subject);
			email.setHtml(body);
			SendGrid.Response response = sendGrid.send(email);
			log.info("Email response message: "+response.getMessage());
			log.info("Email status: "+response.getStatus());
			return response.getStatus();
		} catch (SendGridException e) {
			return false;
		}
	}

	public boolean sendEmailToUserWithCC(String toMailId, String subject, String body, String cc) {
		try {
			SendGrid sendGrid = new SendGrid(USERID, PASSWORD);
			SendGrid.Email email = new SendGrid.Email();
			email.setFrom(FROM);
			email.addTo(toMailId);
			email.addCc(cc);
			email.setSubject(subject);
			email.setHtml(body);
			SendGrid.Response response = sendGrid.send(email);
			return response.getStatus();
		} catch (SendGridException e) {
			return false;
		}
	}

	public boolean sendEmailToUserWithBCC(String toMailId, String subject, String body, String bcc) {
		try {
			SendGrid sendGrid = new SendGrid(USERID, PASSWORD);
			SendGrid.Email email = new SendGrid.Email();
			email.setFrom(FROM);
			email.addTo(toMailId);
			email.addBcc(bcc);
			email.setSubject(subject);
			email.setHtml(body);
			SendGrid.Response response = sendGrid.send(email);
			return response.getStatus();
		} catch (SendGridException e) {
			return false;
		}
	}

	public boolean sendEmailToUserWithCCAndBCC(String toMailId, String subject, String body, String cc, String bcc) {
		try {
			SendGrid sendGrid = new SendGrid(USERID, PASSWORD);
			SendGrid.Email email = new SendGrid.Email();
			email.setFrom(FROM);
			email.addTo(toMailId);
			email.addCc(cc);
			email.addBcc(bcc);
			email.setSubject(subject);
			email.setHtml(body);
			SendGrid.Response response = sendGrid.send(email);
			return response.getStatus();
		} catch (SendGridException e) {
			return false;
		}
	}
}
