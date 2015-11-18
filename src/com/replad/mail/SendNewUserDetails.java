package com.replad.mail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

//import com.google.appengine.api.utils.SystemProperty;
import com.replad.init.InitConfiguration;
import com.replad.utils.StringUtilities;

public class SendNewUserDetails {
	private static final Logger log = Logger.getLogger(SendNewUserDetails.class.getName());	
	private Map<String, String> commonProperties = null;

	public SendNewUserDetails(){
		this.commonProperties =  InitConfiguration.commonPropertiesMap;
	}
	/**
	 * Send email with user account details.
	 * 
	 * @param userName
	 * @param loginPassword
	 * @param toEmailId
	 * @param activationCode
	 * @return
	 * @throws IOException
	 */
	public boolean sendNewUserDetails(String userName, String toEmailId,int activationCode) throws IOException{
		final String emailSubject = commonProperties.get("mail.new.user.login.detail.mail.subject");
		String domain = "";
//		if(StringUtilities.equalsIgnoreCase(SystemProperty.environment.value().toString(), "Production")){
//			domain = commonProperties.get("replad.production.domain");
//		}else{
			domain = commonProperties.get("replad.development.domain");
//		}
		
		String ativationURL = domain + commonProperties.get("replad.activation.page")+"?userName="+toEmailId+"&activationCode="+activationCode;
		Map<String, Object> maildetails = new HashMap<String, Object>();
		maildetails.put("userName", userName);
		maildetails.put("emailId", toEmailId);
		maildetails.put("ativationURL", ativationURL);
		maildetails.put("templateFor", "userActivation");
		String emailBody = new MailBodyUI().renderEmailBody(maildetails);
		SendMail sendMail = new SendMail();
		return sendMail.sendEmailToUser(toEmailId, emailSubject, emailBody);
	}
	
	
	public boolean sendNewServiceCreationDetails(String userName, String toEmailId, String requested_date,int ticket_number, List<String> service_description, String other_details, String templateName){
		final String emailSubject = commonProperties.get("mail.new.user.login.detail.mail.subject");
		Map<String, Object> maildetails = new HashMap<String, Object>();
		maildetails.put("userName", userName);
		maildetails.put("requested_date", requested_date);
		maildetails.put("ticket_number", ticket_number+"");
		maildetails.put("service_description", service_description);
		maildetails.put("other_details", other_details);
		maildetails.put("templateFor", "newServiceRequest");
		String emailBody = new MailBodyUI().renderEmailBody(maildetails);
		SendMail sendMail = new SendMail();
		return sendMail.sendEmailToUser(toEmailId, emailSubject, emailBody);
	}
	
}
