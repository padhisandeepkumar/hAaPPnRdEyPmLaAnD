package com.replad.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.replad.init.InitConfiguration;
import com.replad.utils.LoadProperties;
import com.replad.utils.StringUtilities;
import com.x5.template.Chunk;
import com.x5.template.Theme;

public class MailBodyUI {
	private Map<String, String> mailProps = new HashMap<String, String>();
	private Theme theme = null;
	public MailBodyUI(){
		String fileName = StringUtilities.getConfigPath()+File.separator+"mailsetting.properties";
		File file = new File(fileName);
		this.mailProps = new LoadProperties(file).readProperties();
		
		String templatePath = StringUtilities.getConfigPath()+File.separator+"emailTemplates";
		this.theme = new Theme(templatePath,"");
	}
	public String renderEmailBody(Map<String, Object> mailDetailsMap) {
		 String output = "";
			 
		 try {
			 if(!mailDetailsMap.isEmpty()){
				 String templateFor = (null!=mailDetailsMap.get("templateFor") && StringUtilities.isNotBlank(mailDetailsMap.get("templateFor").toString()) ? mailDetailsMap.get("templateFor").toString() : "");
				switch(templateFor){
				 case "userActivation" 			: 	{
						 								String userName = (null!=mailDetailsMap.get("userName") && StringUtilities.isNotBlank(mailDetailsMap.get("userName").toString()) ? mailDetailsMap.get("userName").toString() : "There!");
						 								String activationURL = (!mailDetailsMap.isEmpty() && null!=mailDetailsMap.get("ativationURL") && StringUtilities.isNotBlank(mailDetailsMap.get("ativationURL").toString()) ? mailDetailsMap.get("ativationURL").toString() : "www.replad.com");
						 								
						 								String templateName = mailProps.get("replad.user.activation.email.template.name");
						 								output = renderActivationEmail(userName, activationURL, templateName);	
//						 								System.out.println("Email Body-->"+output);
						 								break;
				 									}
				 case "newServiceRequest"		:	{
														String userName = (null!=mailDetailsMap.get("userName") && StringUtilities.isNotBlank(mailDetailsMap.get("userName").toString()) ? mailDetailsMap.get("userName").toString() : "There!");
														String requested_date = (null!=mailDetailsMap.get("createdDate") && StringUtilities.isNotBlank(mailDetailsMap.get("createdDate").toString()) ? mailDetailsMap.get("createdDate").toString() : "");
														List<String> service_description = (List<String>) (null!=mailDetailsMap.get("serviceDesc") ? mailDetailsMap.get("serviceDesc") : new ArrayList<String>());
														String other_details = (null!=mailDetailsMap.get("otherDtls") && StringUtilities.isNotBlank(mailDetailsMap.get("otherDtls").toString()) ? mailDetailsMap.get("otherDtls").toString() : "");
														
														String templateName = mailProps.get("replad.new.service.creation.email.template.name");
														output = renderServiceCreationEmail(userName, requested_date, service_description, other_details, templateName);	
//														System.out.println("Email Body-->"+output);
														break;
													}
				 case "guestServiceRequest"		:	{
														String userName = (null!=mailDetailsMap.get("userName") && StringUtilities.isNotBlank(mailDetailsMap.get("userName").toString()) ? mailDetailsMap.get("userName").toString() : "There!");
														String requested_date = (null!=mailDetailsMap.get("requested_date") && StringUtilities.isNotBlank(mailDetailsMap.get("requested_date").toString()) ? mailDetailsMap.get("requested_date").toString() : "");
														String address = (null!=mailDetailsMap.get("address") && StringUtilities.isNotBlank(mailDetailsMap.get("address").toString()) ? mailDetailsMap.get("address").toString() : "Not Provided");
														String service_details = (null!=mailDetailsMap.get("service_details") && StringUtilities.isNotBlank(mailDetailsMap.get("service_details").toString()) ? mailDetailsMap.get("service_details").toString() : "Not Available");
														String other_details = (null!=mailDetailsMap.get("other_details") && StringUtilities.isNotBlank(mailDetailsMap.get("other_details").toString()) ? mailDetailsMap.get("other_details").toString() : "");
														String service_charges = (null!=mailDetailsMap.get("service_charges") && StringUtilities.isNotBlank(mailDetailsMap.get("service_charges").toString()) ? mailDetailsMap.get("service_charges").toString() : "0");
														String discount = (null!=mailDetailsMap.get("discount") && StringUtilities.isNotBlank(mailDetailsMap.get("discount").toString()) ? mailDetailsMap.get("discount").toString() : "");
														String total_amount = (null!=mailDetailsMap.get("total_amount") && StringUtilities.isNotBlank(mailDetailsMap.get("total_amount").toString()) ? mailDetailsMap.get("total_amount").toString() : "");
														String service_support_mail_id = "", service_support_number = "";
														Map<String, String> supportDetailsMap = getSupportContactDetails();
														if(null!=supportDetailsMap){
															service_support_mail_id = (null!=supportDetailsMap.get("supportEmailId") && StringUtilities.isNotBlank(supportDetailsMap.get("supportEmailId").toString()) ? supportDetailsMap.get("supportEmailId").toString() : "");
															service_support_number = (null!=supportDetailsMap.get("supportContactNumber") && StringUtilities.isNotBlank(supportDetailsMap.get("supportContactNumber").toString()) ? supportDetailsMap.get("supportContactNumber").toString() : "");
														}
														String templateName = mailProps.get("replad.guest.service.creation.email.template.name");
														output = renderGuestServiceEmail(userName, requested_date, address, service_details, other_details, service_charges, discount, total_amount, service_support_mail_id, service_support_number, templateName);	
//														System.out.println("Email Body-->"+output);
														break;
													}
				 case "userPromotionCoupon"		:	{
														String userName = (null!=mailDetailsMap.get("userName") && StringUtilities.isNotBlank(mailDetailsMap.get("userName").toString()) ? mailDetailsMap.get("userName").toString() : "There!");
														String coupon_code = (null!=mailDetailsMap.get("coupon_code") && StringUtilities.isNotBlank(mailDetailsMap.get("coupon_code").toString()) ? mailDetailsMap.get("coupon_code").toString() : "");
														String promotion_start_date = (null!=mailDetailsMap.get("promotion_start_date") && StringUtilities.isNotBlank(mailDetailsMap.get("promotion_start_date").toString()) ? mailDetailsMap.get("promotion_start_date").toString() : "");
														String promotion_end_date = (null!=mailDetailsMap.get("promotion_end_date") && StringUtilities.isNotBlank(mailDetailsMap.get("promotion_end_date").toString()) ? mailDetailsMap.get("promotion_end_date").toString() : "");
														String promotion_email = "", promotion_number = "";
														Map<String, String> promotionDetailsMap = getPromotionContactDetails();
														if(null!=promotionDetailsMap){
															promotion_email = (null!=promotionDetailsMap.get("promotion_email") && StringUtilities.isNotBlank(promotionDetailsMap.get("promotion_email").toString()) ? promotionDetailsMap.get("promotion_email").toString() : "");
															promotion_number = (null!=promotionDetailsMap.get("promotion_number") && StringUtilities.isNotBlank(promotionDetailsMap.get("promotion_number").toString()) ? promotionDetailsMap.get("promotion_number").toString() : "");
														}
														String templateName = mailProps.get("replad.register.user.promotion.coupon.email.template.name");
														output = renderUserPromotionCouponEmail(userName, coupon_code, promotion_start_date, promotion_end_date, promotion_email, promotion_number, templateName);	
														System.out.println("Email Body-->"+output);
														break;
													}
				 
				 default						: 	break;
				 }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * Dynamically rendering the User Activation Email
	 * @param userName
	 * @param emailId
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public String renderActivationEmail(String userName, String activationURL, String templateName) throws Exception{
		Chunk html = theme.makeChunk("accountActivation#"+templateName);
		html.set("name", userName);
		html.set("activation_url", activationURL);
		String output = html.toString();
		return output;
	}
	
	/**
	 * Dynamically rendering the New Service Creation Email
	 * 
	 * @param userName
	 * @param emailId
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public String renderServiceCreationEmail(String userName, String requested_date, List<String> service_description, String other_details, String templateName) throws Exception{
		Chunk html = theme.makeChunk("newServiceRequest#"+templateName);
		html.set("name", userName);
		html.set("requested_date", requested_date);
		String[] service_descriptionArray = service_description.toArray(new String[service_description.size()]);
		html.set("subServiceDtlsList", service_descriptionArray);
		html.set("other_details", other_details);
		String output = html.toString();
		return output;
	}
	
	/**
	 * Dynamically rendering the Guest service creatino email
	 * 
	 * @param userName
	 * @param requested_date
	 * @param address
	 * @param service_details
	 * @param other_details
	 * @param service_charges
	 * @param discount
	 * @param total_amount
	 * @param service_support_mail_id
	 * @param service_support_number
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public String renderGuestServiceEmail(String userName, String requested_date, String address, String service_details, String other_details, String service_charges, String discount, String total_amount, String service_support_mail_id, String service_support_number, String templateName) throws Exception{
		Chunk html = theme.makeChunk("guestServiceRequestEmail#"+templateName);
		html.set("name", userName);
		html.set("requested_date", requested_date);
		html.set("address", address);
		html.set("service_details", service_details);
		html.set("other_details", other_details);
		html.set("service_charges", service_charges);
		html.set("discount", discount);
		html.set("total_amount", total_amount);
		html.set("service_support_mail_id", service_support_mail_id);
		html.set("service_support_number", service_support_number);
		String output = html.toString();
		return output;
	}
	
	/**
	 * Dynamically rendering the Promotion Coupon Code for the Registered User.
	 * 
	 * @param userName
	 * @param coupon_code
	 * @param promotion_start_date
	 * @param promotion_end_date
	 * @param promotion_email
	 * @param promotion_number
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public String renderUserPromotionCouponEmail(String userName, String coupon_code, String promotion_start_date, String promotion_end_date, String promotion_email, String promotion_number, String templateName) throws Exception{
		Chunk html = theme.makeChunk("registerUserPromotionEmail#"+templateName);
		html.set("name", userName);
		html.set("coupon_code", coupon_code);
		html.set("promotion_start_date", promotion_start_date);
		html.set("promotion_end_date", promotion_end_date);
		html.set("promotion_email", promotion_email);
		html.set("promotion_number", promotion_number);
		String output = html.toString();
		return output;
	}
	
	/**
	 * Get Support Details.
	 * 
	 * @return
	 */
	public Map<String, String> getSupportContactDetails(){
		Map<String, String> supportDetailsMap = new HashMap<String, String>();
		String supportEmailId = "", supportContactNumber = "";
		
		Map<String, MailConfigurationBean> mailSettingList = InitConfiguration.mailSettingList;
		
		if(mailSettingList.containsKey("supportdetails")){
			MailConfigurationBean bean = mailSettingList.get("supportdetails");
			supportEmailId = bean.getUser();
			supportContactNumber = bean.getPwd();
		}
		supportDetailsMap.put("supportEmailId", supportEmailId);
		supportDetailsMap.put("supportContactNumber", supportContactNumber);
		return supportDetailsMap;
	}
	
	/**
	 * Get Support Details.
	 * 
	 * @return
	 */
	public Map<String, String> getPromotionContactDetails(){
		Map<String, String> supportDetailsMap = new HashMap<String, String>();
		String supportEmailId = "", supportContactNumber = "";
		
		Map<String, MailConfigurationBean> mailSettingList = InitConfiguration.mailSettingList;
		
		if(mailSettingList.containsKey("promotiondetails")){
			MailConfigurationBean bean = mailSettingList.get("promotiondetails");
			supportEmailId = bean.getUser();
			supportContactNumber = bean.getPwd();
		}
		supportDetailsMap.put("promotion_email", supportEmailId);
		supportDetailsMap.put("promotion_number", supportContactNumber);
		return supportDetailsMap;
	}
}
