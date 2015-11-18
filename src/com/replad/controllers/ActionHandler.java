package com.replad.controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

//import com.google.appengine.api.utils.SystemProperty;
import com.replad.bean.work.WorkHelper;
import com.replad.employee.EmployeeMgmtHelper;
import com.replad.feedback.TestimonyHelper;
import com.replad.helper.user.registration.UserActivation;
import com.replad.helper.user.registration.UserDAO;
import com.replad.helper.user.registration.UserDetailsHelper;
import com.replad.helper.user.registration.UserRegistrationHelper;
import com.replad.init.InitConfiguration;
import com.replad.mail.MailConfigurationBean;
import com.replad.promotion.PromotionalHelper;
import com.replad.sub.work.SubworkHelper;
import com.replad.sub.work.UserTicketHelper;
import com.replad.user.creation.UserCreationHelper;
import com.replad.utils.StringUtilities;

public class ActionHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ActionHandler.class.getName());	
	private Map<String, String> commonPropertiesMap = InitConfiguration.commonPropertiesMap;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException{
		RequestDispatcher dispatcher = null;
		String forwardAction = null;
		HttpSession session = (null!=request && null!=request.getSession() ? request.getSession() : null);
		
		String characterEncoding = commonPropertiesMap.get("character.encoding");
		request.setCharacterEncoding((StringUtilities.isNotEmpty(characterEncoding)?characterEncoding:"UTF-8"));
		
		StringBuffer formattedString=new StringBuffer();
		
		validateRequest(request, response);
		
		String action = (StringUtilities.isNotEmpty(request.getParameter("nav_action")) ? request.getParameter("nav_action").toString().trim() : "donothing");
		try {
			formattedString = new StringBuffer();
			log.warning("POST action------["+action+"]");
			switch (ActionHandlerEnum.getActionName(action)) {
				case registerUser:{
						request.setAttribute("password_enc_type", commonPropertiesMap.get("new.user.password.encryption"));
						int status = new UserRegistrationHelper().createUser(request); // create user
						formattedString.append(status); // status 1 is success, <=0 is failed
						break;
				}
				case activateUser:{
					boolean flag = new UserActivation().activateUser(request);
					formattedString.append(flag?1:0);
					break;
				}
				case 	resetUserDetails:{
					int status = new UserRegistrationHelper().resetUser(request); // create user
					formattedString.append(status); // status 1 is success, <=0 is failed
					break;
				}
				case updateUserSettings:{
					int data = new UserCreationHelper().updateUserDetails(request);
					formattedString.append(data);
					break;
				}
				case setSelectedServiceDetails:{
					String data = new WorkHelper().setSelectedServiceDetails(request);
					formattedString.append(data);
					break;
				}
				case updateTicketDetails:{
					int status = new UserTicketHelper().updateTicketDetails(request);
					formattedString.append(status);
					break;
				}
				case saveEmployeeData:{
					int status = new EmployeeMgmtHelper().createNewEmployeeProfile(request);
					formattedString.append(status);
					break;
				}
				case deleteEmployee:{
					int status = new EmployeeMgmtHelper().deleteEmployeeProfile(request);
					formattedString.append(status);
					break;
				}
				case updateFeedback:{
					int status = new UserTicketHelper().updateFeedback(request);
					formattedString.append(status);
					break;
				}
				case cancelTicket:{
					int status = new UserTicketHelper().cancelTicket(request);
					formattedString.append(status);
					break;
				}case deleteTicket:{
					int status = new UserTicketHelper().deleteTicket(request);
					formattedString.append(status);
					break;
				}
				case guestServiceCreate:{
					int status = new UserTicketHelper().createGuestTicket(request);
					log.warning("inside AH guestServiceCreate==>"+status);
					formattedString.append(status);
					break;
				}
				case submitFeedback:{
					int status = new TestimonyHelper().submitUserFeedback(request);
					formattedString.append(status);
					break;
				}
				case createModifyService:{
					int status = new WorkHelper().createModifyService(request);
					formattedString.append(status);
					break;
				}
				case closeTicket:{
					int status = new UserTicketHelper().closeTicket(request);
					formattedString.append(status);
					break;
				}
				default: break;
			}
		} catch (Exception ex) {
			formattedString = new StringBuffer();
			session.setAttribute("EXCEPTION_OBJ", (Exception) ex);
			request.setAttribute("errorMsg", ex.getMessage());
			formattedString.append("Error");
			log.warning("Error: "+ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (StringUtilities.isEmpty(forwardAction)) {
					if(action.equalsIgnoreCase("FILTER_POPUP") || action.equalsIgnoreCase("jsondata")){
						response.setContentType("application/json, charset="+characterEncoding);
					}else{
						response.setContentType("text/html; charset="+characterEncoding);
					}
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write(formattedString.toString());
					log.info(formattedString.toString());
					return;
				} else {
					dispatcher = request.getRequestDispatcher(forwardAction);
					dispatcher.forward(request, response);
				}
			} catch (Exception ex) {
				formattedString = new StringBuffer();
				session.setAttribute("EXCEPTION_OBJ", (Exception) ex);
				request.setAttribute("errorMsg", ex.getMessage());
				formattedString.append("Error");
				log.warning("Error: "+ex.getMessage());
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,IOException{
		RequestDispatcher dispatcher = null;
		String forwardAction = null;
		HttpSession session = (null!=request && null!=request.getSession() ? request.getSession() : null);
		String characterEncoding = commonPropertiesMap.get("character.encoding");
		request.setCharacterEncoding((StringUtilities.isNotEmpty(characterEncoding)?characterEncoding:"UTF-8"));
		
		StringBuffer formattedString=new StringBuffer();
		
		validateRequest(request, response);
		
		String action = (StringUtilities.isNotEmpty(request.getParameter("nav_action")) ? request.getParameter("nav_action").toString().trim() : "donothing");
		log.warning("GET action------["+action);
		try {
			formattedString = new StringBuffer();
			log.info("GET action------["+action+"]");
			switch (ActionHandlerEnum.getActionName(action)) {
				case getSubWorks:{
					String username = (null!=request.getSession() && null!=request.getSession().getAttribute("username") && StringUtilities.isNotEmpty(request.getSession().getAttribute("username").toString()) ? request.getSession().getAttribute("username").toString() : null);
					if(null==username){
						formattedString.append(new SubworkHelper().renderSubWorks(request));
					}else{
						formattedString.append("page1");					
					}
					break;
				}
				case getContactDetails:{
					formattedString.append(new UserDetailsHelper().getContactDetails(request));
					break;
				}
				/*case getUserTickets:{
					formattedString.append(new UserTicketHelper().getAllUserTickets(request));
					break;
				}*/
				case populateComboData:{
					formattedString.append(new EmployeeMgmtHelper().populateCombos(request));
					break;
				}
				case renderEmployeeDetailGridData:{
					formattedString.append(new EmployeeMgmtHelper().renderEmployeeDetailGridData(request));
					break;
				}
				case renderFilteredEmployeeDetailGridData:{
					formattedString.append("");
					break;
				}
				case populateEmployeDataToEdit:{
					formattedString.append(new EmployeeMgmtHelper().renderEmployeeDetailsToEdit(request));
					try {
					    Thread.sleep(500);                 //1000 milliseconds is one second.
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
					break;
				}
				case renderSubWorkDetails:{
					formattedString.append(new WorkHelper().renderSubWorkDetails(request));
					break;
				}
				case logout:{
					session.invalidate();
					break;
				}
				case getUserDetails:{
					String data = new UserCreationHelper().getUserDetails(request);
					formattedString.append(data);
					break;
				}
				case removeSessionAttributes:{
					String attributes = (StringUtilities.isNotEmpty(request.getParameter("attributes"))? request.getParameter("attributes") : null);
					removeSessionAttributes(request, attributes);
					break;
				}
				case login:{
					String data="0";
					String uname = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
					session.setAttribute("username", uname);
					boolean isUserExists = new UserDAO().isUserExists(uname);
					if(!isUserExists){
						data = "-2";//User does not exists
					}else{
						boolean isActiveUser = new UserDAO().isActiveUser(uname);
						if(isActiveUser){
							boolean validateUSer = new UserCreationHelper().validateUser(request);
							if(validateUSer){
								int userId = new UserDAO().getUserId(uname);
								new UserDAO().updateLoggedInTime(userId);
								new PromotionalHelper().generatePromoCodes(2);
								data = "checkout.jsp";
							}else{
								data = "-3"; // Given creadentials are not matching
							}
						}else{
							data = "-1"; // is not an active user
						}
					}
					
					formattedString.append(data);
					break;
				}
				case adminLogin:{
					String  data = "-1";
					String username = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
					String password = (StringUtilities.isNotEmpty(request.getParameter("password"))?request.getParameter("password"):"");
//					System.out.println("username---->"+username+"password--->"+password);
					Map<String, MailConfigurationBean> mailSettingList = InitConfiguration.mailSettingList;
					if(null!=mailSettingList && mailSettingList.containsKey("admin")){
						MailConfigurationBean bean = mailSettingList.get("admin");
						String adminUserName = bean.getUser();
						String adminPassword = bean.getPwd();
//						System.out.println("BEAN--->username---->"+adminUserName+"password--->"+adminPassword);
						if(StringUtilities.equals(username, adminUserName) && StringUtilities.equals(password, adminPassword)){
							data = "1";
							session.setAttribute("username", adminUserName);
						}
							
					}
				    formattedString.append(data);
					break;
				}
				case getWorkGroupDetails:{
					String data = new SubworkHelper().workGroupId(request);
					formattedString.append(data);
					break;
				}
				case autoCompleteSubworks:{
					String data = new SubworkHelper().populateStateCombo_AutoComplete();
					formattedString.append(data);
					break;
				}
				// Service Selected before register
				case serveWithRegister:{
					String selectedSubWorks = (StringUtilities.isNotEmpty(request.getParameter("selectedSubWorks"))? request.getParameter("selectedSubWorks") : null);
					int status = new UserRegistrationHelper().createUser(request); // create user
					if(status==1){
						String email = (StringUtils.isNotEmpty(request.getParameter("email")) ? request.getParameter("email").trim() : null);
						session.setAttribute("username", email);
						String url = "checkout.jsp?selectedWorkId="+selectedSubWorks;
						formattedString.append(url);
					}
					break;
				}
				// Service Selected before sign-in
				case serveWithSignin:{
					boolean flag = new UserCreationHelper().validateUser(request);
					if(flag){
						String uname = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
						flag = new UserDAO().isActiveUser(uname);
						/*if(!flag){
							formattedString.append("-1");// not an active user. Send activation email.
							String email = uname;
							String password2 = (StringUtilities.isNotEmpty(request.getParameter("password"))?request.getParameter("password"):"");
							new SendNewUserDetails().sendNewUserDetails(email, password2, email);
						}else{*/
							String selectedSubWorks = (StringUtilities.isNotEmpty(request.getParameter("selectedSubWorks"))? request.getParameter("selectedSubWorks") : null);
							session.setAttribute("username", uname);
							String url = "checkout.jsp?selectedWorkId="+selectedSubWorks;
//							String url = "page1/"+selectedSubWorks;
							if(StringUtilities.isEmpty(selectedSubWorks)){
								url = "welcome.jsp";
							}
							formattedString.append(url);
//						}
					}else{
						formattedString.append("0");// either username/password not provided
					}
					break;
				}
				/*case getPendingServiceDetails:{
					List<UserTicketDetailBean> allServiceDetails = new UserTicketHelper().getAllTicketBean4User(request);
					if(!allServiceDetails.isEmpty()){
						formattedString.append("<p><span class='label label-danger'>Pending Service Details</span></p><ul id='pendingServiceList'>");
					}
					for(UserTicketDetailBean bean : allServiceDetails){
						if(null==bean.getClosedDate() && null==bean.getCancel_date()){
							formattedString.append("<li id='"+bean.getSubWorkId()+"'><span class='regularfont-14px'>"+bean.getSubWorkId()+"</span></li>");
						}
					}
					if(!allServiceDetails.isEmpty()){
						formattedString.append("</ul>");
					}
					break;
				}*/
				case populateSubworksCombo:{
					formattedString.append(new SubworkHelper().populateSubworksCombo(request));
					break;
				}
				case getAllTickets:{
					formattedString.append(new UserTicketHelper().getAllTickets(request));
					break;
				}
				case getWorkDetails:{
					formattedString.append(new SubworkHelper().getSubworkDetails(request));
					break;
				}
				case validateCoupon:{
					int status = new UserTicketHelper().validateCopoun(request);
					formattedString.append(status);
					break;
				}
				case validateRegisterUserCoupon:{
					String promoCoupon = (StringUtils.isNotEmpty(request.getParameter("registerUserPromoCode")) ? request.getParameter("registerUserPromoCode").trim() : "");;
					int status = new PromotionalHelper().isCouponValid(promoCoupon);
					formattedString.append(status);
					break;
				}case renderTicketDetailGridData:{
					formattedString.append(new UserTicketHelper().getAllTickets(request));
					break;
				}
				case renderWorkDetailGridData:{
					formattedString.append(new WorkHelper().renderWorkDetailGridData(request));
					break;
				}
				case populateWorksCombo:{
					formattedString.append(new SubworkHelper().renderWorkDetails());
					break;
				}
				case deleteWorkDetails:{
					int status = new WorkHelper().deleteSelectedWorks(request);
					formattedString.append(status);
					break;
				}
				case populateTicketToEdit:{
					formattedString.append(new UserTicketHelper().populateTicketToEdit(request));
					break;
				}
				default: break;
			}
		} catch (Exception ex) {
			formattedString = new StringBuffer();
			session.setAttribute("EXCEPTION_OBJ", (Exception) ex);
			request.setAttribute("errorMsg", ex.getMessage());
			formattedString.append("Error");
			ex.printStackTrace();
		} finally {
			try {
				if (StringUtilities.isEmpty(forwardAction)) {
					if(action.equalsIgnoreCase("FILTER_POPUP") || action.equalsIgnoreCase("jsondata")){
						response.setContentType("application/json, charset="+characterEncoding);
					}else{
						response.setContentType("text/html; charset="+characterEncoding);
					}
					response.setHeader("Cache-Control", "no-cache");
					response.getWriter().write(formattedString.toString());
					log.info(formattedString.toString());
					//System.out.println(formattedString.toString());
					return;
				} else {
					dispatcher = request.getRequestDispatcher(forwardAction);
					dispatcher.forward(request, response);
				}
			} catch (Exception ex) {
				formattedString = new StringBuffer();
				session.setAttribute("EXCEPTION_OBJ", (Exception) ex);
				request.setAttribute("errorMsg", ex.getMessage());
				formattedString.append("Error");
			}
		}
	}
	
	/**
	 * To remove attributes from session.
	 * 
	 * @param request
	 * @param attributes : attribute names separated by comma.
	 */
	public void removeSessionAttributes(HttpServletRequest request, String attributes){
		/*HttpSession session = request.getSession();
		String[] attrArr = attributes.split(",");
		for(String attr : attrArr){
			if(null!=session.getAttribute(attr.trim())){
				session.removeAttribute(attr.trim());
			}
		}*/
	}
	
	/**
	 * Validating the request with the HTTP_HOST and HTTP_REFERER
	 * 
	 * @param request
	 * @return
	 */
	public void validateRequest(HttpServletRequest request, HttpServletResponse response){
		Map<String, MailConfigurationBean> mailSettingList = InitConfiguration.mailSettingList;
		try {
			if(mailSettingList.containsKey("hostANDreferer")){
				MailConfigurationBean bean = mailSettingList.get("hostANDreferer");
				String host = bean.getUser();
				String referer = bean.getPwd();

				String hostFromRequest = "", refererFromRequest = "";
				Enumeration e = request.getHeaderNames();
				while (e.hasMoreElements()) {
					String name = (String)e.nextElement();
					if(StringUtilities.isNotBlank(name) && name.equals("Referer")){
						refererFromRequest = request.getHeader(name);
					}
					if(StringUtilities.isNotBlank(name) && name.equals("Host")){
						hostFromRequest = request.getHeader(name);
					}
					// IF host and referer matching than allow the request further to process
					if(!host.equals(hostFromRequest) && referer.equals(refererFromRequest)){
						request.getSession().invalidate();
						String domain = "";
						/*if(StringUtilities.equalsIgnoreCase(SystemProperty.environment.value().toString(), "Production")){
							domain = InitConfiguration.commonPropertiesMap.get("replad.production.domain");
						}else{*/
							domain = InitConfiguration.commonPropertiesMap.get("replad.development.domain");
//						}
						String characterEncoding = InitConfiguration.commonPropertiesMap.get("character.encoding");
						response.setContentType("text/html; charset="+characterEncoding);
						response.setHeader("Cache-Control", "no-cache");
						response.getWriter().write(domain);
						return;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
