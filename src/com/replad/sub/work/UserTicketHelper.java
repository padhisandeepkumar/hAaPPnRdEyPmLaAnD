package com.replad.sub.work;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.controllers.ActionHandler;
import com.replad.employee.EmployeeBean;
import com.replad.employee.EmployeeMgmtDbUtils;
import com.replad.helper.user.registration.UserDAO;
import com.replad.init.InitConfiguration;
import com.replad.mail.MailBodyUI;
import com.replad.mail.SendMail;
import com.replad.utils.DateUtils;
import com.replad.utils.RecaptchaUtils;
import com.replad.utils.StringUtilities;

public class UserTicketHelper {

	private static final Logger log = Logger.getLogger(UserTicketHelper.class.getName());
	
	/**
	 * Create the user work mapping bean.
	 * 
	 * @param id
	 * @param user_id
	 * @param sub_work_id
	 * @param opted_week_end
	 * @param priority
	 * @return
	 */
	/*public UserTicketDetailBean createUserTicketBean(int user_id, int sub_work_id, String created_date, String closed_date, String cancel_date, int opted_week_end, int priority, String service_request_date, String custAddress, String extraServiceInfo){
		UserTicketDetailBean bean = new UserTicketDetailBean();
		
		if(user_id>0)						bean.setUser_id(user_id);
		if(sub_work_id>0) 					bean.setSub_work_id(sub_work_id);
		if(null!=created_date)				bean.setCreated_date(created_date);
		if(null!=closed_date)				bean.setClosed_date(null);
		if(null!=cancel_date)				bean.setCancel_date(cancel_date);
		if(opted_week_end>0)				bean.setOpted_week_end(opted_week_end);
		if(priority>0)						bean.setPriority(priority);
		if(null!=service_request_date)		bean.setServiceRequestDate(service_request_date);
		if(null!=custAddress)				bean.setRequestAtAddress(custAddress);
		if(null!=extraServiceInfo)			bean.setExtraServiceInfo(extraServiceInfo);
		
		return bean;
	}
	*/
	/**
	 * Create a new user-order ticket.
	 * 
	 * @param user_id
	 * @param sub_work_id
	 * @param created_date
	 * @param opted_week_end
	 * @param priority
	 * @param service_request_date
	 * @return
	 */
	/*public int createNewTicket(int user_id, String[] subworkidArr, String created_date, int opted_week_end, int priority, String service_request_date, String custAddress, String extraInfo, String promoCode){
		int status = new UserTicketDbUtils().createNewTicket(user_id, subworkidArr, created_date, opted_week_end, priority, service_request_date, custAddress, extraInfo, promoCode);
		return status;
	}*/
	
	/*public int closeTicket(int user_id, int sub_work_id, String created_date){
		int status = 0;
		UserTicketDetailBean bean = new UserTicketDbUtils().getUserTicketBean(user_id, sub_work_id, created_date);
		bean.setClosed_date(new DateUtils().getCurrentDate().toString());
		status = new UserTicketDbUtils().closeUserTicket(bean);
		return status;
	}
	*/
	/*public int cancelTicket(HttpServletRequest request){
		int status = 0;
		HttpSession session = request.getSession();
		int user_id = Integer.parseInt(null!=session.getAttribute("userid")? session.getAttribute("userid").toString() : "0");
		int id = Integer.parseInt(StringUtilities.isNotBlank(request.getParameter("id"))?request.getParameter("id"):"0");
		String created_date = (StringUtilities.isNotBlank(request.getParameter("createddate"))?request.getParameter("createddate"):null);
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(InitConfiguration.commonPropertiesMap.get("time.date.display.format"));
		Date parsedTimeStamp = new Date();
		if(StringUtilities.isNotEmpty(created_date)){
			try {
//				Timestamp ts = new DateUtils().databaseFormats(created_date);
				parsedTimeStamp = dateFormat.parse(created_date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		created_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(parsedTimeStamp);
		status = new UserTicketDbUtils().cancelUserTicket(user_id, id, created_date);
		return status;
	}
	*/
	public int rescheduleTicket(int user_id, int sub_work_id, Timestamp created_date, Timestamp reschedule_date){
		int status = 0;
		/*UserTicketDetailBean bean = new UserTicketDbUtils().getUserTicketBean(user_id, sub_work_id, created_date);
		bean.setService_request_date(reschedule_date);
		status = new UserTicketDbUtils().rescheduleUserTicket(bean);*/
		return status;
	}
	
	/*public List<UserTicketDetailBean> getAllTicketBean4User(HttpServletRequest request){
		HttpSession session = request.getSession();
		int user_id = Integer.parseInt(null!=session.getAttribute("userid")? session.getAttribute("userid").toString() : "0");
		List<UserTicketDetailBean> beanList = new UserTicketDbUtils().getAllTicketBean4User(user_id);
		return beanList;
	}*/
	
	/*public String getAllUserTickets(HttpServletRequest request){
		List<UserTicketDetailBean> beanList = getAllTicketBean4User(request);
		String createdDate="", scheduleDate = "", closedDate = "", status = "<span class='pending'>PENDING</span>";
				//, statusClass = "&nbsp;<i class='fa fa-spinner fa-spin fa-sm'></i>&nbsp;";
		DateUtils dateUtils = new DateUtils();
		List<Map<String, String>> allDtls = new ArrayList<Map<String,String>>();
		Map<String, String> innermapMap = null;
		for(UserTicketDetailBean ticketbean : beanList){
			status = "<span class='pending'>PENDING</span>";
			if(null!=ticketbean.getClosed_date()){
				status = "<span class='complete'>COMPLETE</span>";
			}else if(null!=ticketbean.getCancel_date()){
				status = "CANCEL";
			}

			if(null!=ticketbean.getCreated_date())				createdDate = dateUtils.formatDateTime(dateUtils.databaseFormats(ticketbean.getCreated_date()));
			if(null!=ticketbean.getServiceRequestDate())		scheduleDate = ticketbean.getServiceRequestDate();
			if(null!=ticketbean.getClosed_date())				closedDate = dateUtils.formatDateTime(dateUtils.databaseFormats(ticketbean.getClosed_date()));
			innermapMap = new HashMap<String, String>();
			innermapMap.put("chkbx","<input type='radio' id='serviceRadio' name='serviceRadio' value='"+ticketbean.getId()+"' />");
			innermapMap.put("id",ticketbean.getId()+"");
			innermapMap.put("workid",ticketbean.getSub_work_id()+"");
			innermapMap.put("servicedesc",ticketbean.getSub_work_desc());
			innermapMap.put("statusClass", status);
			innermapMap.put("createdDate", createdDate);
			innermapMap.put("scheduleDate", scheduleDate);
			innermapMap.put("closeddate", closedDate);

			allDtls.add(innermapMap);
		}

		String data = new StringUtilities().getJsonObject(allDtls);
		return data;
	}
	*/
	public int getUserTicketCount(HttpServletRequest request){
		int count = 0;
		HttpSession session = request.getSession();
		int user_id  = 0;
		String username = (null!=session.getAttribute("username")? session.getAttribute("username").toString() : null);
		if(null!=username && StringUtilities.isNotEmpty(username)){
			user_id = new UserDAO().getUserId(username);
		}
		count = new UserTicketDbUtils().getUserTicketCount(user_id);
		return count;
	}
	
	public int updateFeedback(HttpServletRequest request){
		int count = 0;
		HttpSession session = request.getSession();
		String feedback = (StringUtilities.isNotBlank(request.getParameter("userFeedback"))?request.getParameter("userFeedback"):null);
		String subworkId = (StringUtilities.isNotBlank(request.getParameter("subworkId"))?request.getParameter("subworkId"):null);
		if(StringUtilities.isNotBlank(feedback) && StringUtilities.isNotBlank(subworkId)){
			int user_id  = 0;
			String username = (null!=session.getAttribute("username")? session.getAttribute("username").toString() : null);
			if(null!=username && StringUtilities.isNotEmpty(username)){
				user_id = new UserDAO().getUserId(username);
			}
			count = new UserTicketDbUtils().updateUserFeedback(user_id, subworkId, feedback);
		}
		return count;
	}
	
	public int createGuestTicket(HttpServletRequest request){
		int count = 0;
		int work_group_id = Integer.parseInt(StringUtilities.isNotBlank(request.getParameter("work_group_id"))?request.getParameter("work_group_id"):"0");
		int sub_work_id = Integer.parseInt(StringUtilities.isNotBlank(request.getParameter("sub_work_id"))?request.getParameter("sub_work_id"):"0");
		String guestUserName = (StringUtilities.isNotBlank(request.getParameter("guestUserName"))?request.getParameter("guestUserName"):null);
		String guestregEmail = (StringUtilities.isNotBlank(request.getParameter("guestregEmail"))?request.getParameter("guestregEmail"):null);
		String guestregMobile = (StringUtilities.isNotBlank(request.getParameter("guestregMobile"))?request.getParameter("guestregMobile"):null);
		String guestregAddress = (StringUtilities.isNotBlank(request.getParameter("guestregAddress"))?request.getParameter("guestregAddress"):null);
		String extraServiceInfo = (StringUtilities.isNotBlank(request.getParameter("guestMoreInfo"))?request.getParameter("guestMoreInfo"):null);
		String guestPromoCode = (StringUtils.isNotEmpty(request.getParameter("guestPromoCode")) ? request.getParameter("guestPromoCode").trim() : null);
		String scheduleServiceDateId = (StringUtils.isNotEmpty(request.getParameter("scheduleServiceDateId")) ? request.getParameter("scheduleServiceDateId").trim() : null);
		
		String uresponse = request.getParameter("recaptchaResponse");
		if (!RecaptchaUtils.verifyRecaptcha(uresponse)){
			return -2; // captcha validation failed.
		} 
		
		if(StringUtilities.isNotBlank(guestregEmail) && StringUtilities.isNotBlank(guestregMobile) && StringUtilities.isNotBlank(guestregAddress)){
			UserTicketDetailBean bean = new UserTicketDetailBean();
			String currentDate = new DateUtils().getCurrentDate().toString();
			bean.setWorkId(work_group_id);
			bean.setSubWorkId(sub_work_id);
			bean.setCustomerName(guestUserName);
			bean.setCreatedDate(currentDate);
			bean.setClosedDate(null);
			bean.setCancel_date(null);
			bean.setScheduleDate(scheduleServiceDateId);
			bean.setCustomerAddress(guestregAddress);
			bean.setCustomerMobile(guestregMobile);
			bean.setCustomerEmail(guestregEmail);
			bean.setCustomerServiceMoreInfo(extraServiceInfo);
			bean.setPromoCode(guestPromoCode);
			bean.setTotalWorkHour(1);
			bean.setTicketStatus("Pending");
			
			Map<String, Object> ticktPriceDetails = new UserTicketDbUtils().getServicePriceDetails(bean);
			bean.setPricePerHour(Double.parseDouble(ticktPriceDetails.get("service_charges").toString()));
			bean.setDiscountPercentage(Integer.parseInt(ticktPriceDetails.get("discount").toString().trim()));
			bean.setTotalPrice(Double.parseDouble(ticktPriceDetails.get("total_amount").toString()));
			
			
			ticktPriceDetails.put("templateFor", "guestServiceRequest");
			String subject = InitConfiguration.commonPropertiesMap.get("guest.service.create.subject");
			String body = new MailBodyUI().renderEmailBody(ticktPriceDetails);
			boolean emailStatus = new SendMail().sendEmailToUser(guestregEmail, subject, body);
			if(emailStatus){
				count = new UserTicketDbUtils().createCustomerTicket(bean);
			}else{
				count = -99;
			}
		}
		log.warning("service create count: "+count);
		return count;
	}
	
	public String getAllTickets(HttpServletRequest request){
		String jsonData = null;
		String ticketNumber = StringUtilities.isNotBlank(request.getParameter("ticketNumber"))?request.getParameter("ticketNumber"):"";
		int categoryCombo = Integer.parseInt(StringUtilities.isNotBlank(request.getParameter("categoryCombo"))?request.getParameter("categoryCombo"):"0");
		String employeeId = (StringUtilities.isNotBlank(request.getParameter("employeeId"))?request.getParameter("employeeId"):null);
		String fromDate = (StringUtilities.isNotBlank(request.getParameter("fromDate"))?request.getParameter("fromDate"):null);
		String toDate = (StringUtilities.isNotBlank(request.getParameter("toDate"))?request.getParameter("toDate"):null);
		
		List<UserTicketDetailBean> beanList = new UserTicketDbUtils().getAllTickets(ticketNumber, categoryCombo, employeeId, fromDate, toDate);
		
		JSONArray json_arr = new JSONArray();
		JSONObject json_obj=null;
		try {
			for (UserTicketDetailBean bean : beanList) {
				json_obj=new JSONObject();
				if(null!=bean){
					json_obj.put("id", bean.getId()+"");
					json_obj.put("work_group_id", bean.getWorkId()+"");
					json_obj.put("sub_work_id", bean.getSubWorkId());
					json_obj.put("guest_email", bean.getCustomerEmail()+"");
					json_obj.put("guest_mobile", bean.getCustomerMobile());
					json_obj.put("guest_address", bean.getCustomerAddress());
					json_obj.put("createddate", bean.getCreatedDate().split(" ")[0]);
					json_obj.put("guest_service_more_info", bean.getCustomerServiceMoreInfo()+"");
					json_obj.put("ticket_id", bean.getTicketId());
					json_obj.put("name", bean.getCustomerName()+"");
					json_obj.put("extra_information", bean.getCustomerServiceMoreInfo());
					json_obj.put("service_request_date", StringUtils.isBlank(bean.getScheduleDate())?" ":bean.getScheduleDate().split(" ")[0]);
					json_obj.put("price_per_hour", bean.getPricePerHour()+"");
					json_obj.put("discount_percentage", bean.getDiscountPercentage()+"");
					json_obj.put("total_price", bean.getTotalPrice()+"");
					json_obj.put("total_work_hour", bean.getTotalWorkHour()+"");
					json_obj.put("mst_employee_id", bean.getEmployeeId());
					json_obj.put("promo_code", StringUtils.isBlank(bean.getPromoCode())?"":bean.getPromoCode());
					json_obj.put("ticket_status", StringUtils.isBlank(bean.getTicketStatus())?"":bean.getTicketStatus());
					json_obj.put("employee_name", StringUtils.isBlank(bean.getEmployeeName())?"":bean.getEmployeeName());
					json_obj.put("commision", bean.getCommision());
					json_arr.put(json_obj);
				}
			}
			jsonData = json_arr.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonData;
	}

	public int validateCopoun(HttpServletRequest request) {
		int status = 0;
		String guestPromoCode = (StringUtils.isNotEmpty(request.getParameter("guestPromoCode")) ? request.getParameter("guestPromoCode").trim() : null);
		status = new UserTicketDbUtils().validateCopounCode(guestPromoCode);
		return status;
	}

	public String populateTicketToEdit(HttpServletRequest request) {
		int selectedTicketId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("selectedTicketId"))?request.getParameter("selectedTicketId"):"0");
		UserTicketDetailBean ticketDetailBean = new UserTicketDbUtils().getSelectedTicketDetails(selectedTicketId);
		if(null == ticketDetailBean){
			return "";
		}
		return getJsonObject(ticketDetailBean);
	}
	
	public String getJsonObject(UserTicketDetailBean ticketDetailBean){
		
		try {
			JSONArray jsonArr = new JSONArray();
			JSONObject jo = null;
			jo = new JSONObject();

			jo.put("id", ticketDetailBean.getId());
			jo.put("workGroupId", ticketDetailBean.getWorkId());
			jo.put("workDesc", ticketDetailBean.getWorkDesc());
			jo.put("subWorkGroup", ticketDetailBean.getSubWorkId());
			jo.put("subWorkDesc", ticketDetailBean.getSubworkDesc());
			jo.put("email", ticketDetailBean.getCustomerEmail());
			jo.put("mobile", ticketDetailBean.getCustomerMobile());
			jo.put("address", ticketDetailBean.getCustomerAddress());
			jo.put("createdDate", ticketDetailBean.getCreatedDate());
			jo.put("moreInfo", ticketDetailBean.getCustomerServiceMoreInfo());
			jo.put("ticketId", ticketDetailBean.getTicketId());
			jo.put("name", ticketDetailBean.getCustomerName());
			jo.put("scheduleDate", ticketDetailBean.getScheduleDate().split(" ")[0]);
			jo.put("pricePerHour", ticketDetailBean.getPricePerHour());
			jo.put("discount", ticketDetailBean.getDiscountPercentage());
			jo.put("totalPrice", ticketDetailBean.getTotalPrice());
			jo.put("totalWorkHour", ticketDetailBean.getTotalWorkHour());
			jo.put("employeeId", ticketDetailBean.getEmployeeId());
			jo.put("promoCode", ticketDetailBean.getPromoCode());
			jo.put("commision", ticketDetailBean.getCommision());
			jsonArr.put(jo);
		return (jsonArr.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int cancelTicket(HttpServletRequest request) {
		String selectedId = (StringUtilities.isNotEmpty(request.getParameter("selectedId")) ? request.getParameter("selectedId") : null);
		int status = new UserTicketDbUtils().cancelSelectedTicket(selectedId);
		return status;
	}

	public int deleteTicket(HttpServletRequest request) {
		String selectedId = (StringUtilities.isNotEmpty(request.getParameter("selectedId")) ? request.getParameter("selectedId") : null);
		int status = new UserTicketDbUtils().deleteSelectedTicket(selectedId);
		return status;
	}
	
	public int closeTicket(HttpServletRequest request) {
		String selectedId = (StringUtilities.isNotEmpty(request.getParameter("selectedId")) ? request.getParameter("selectedId") : null);
		int status = new UserTicketDbUtils().closeSelectedTicket(selectedId);
		return status;
	}
	
	public int updateTicketDetails(HttpServletRequest request) {
		int status = 0;
		
		int ticketId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("ticketId"))?request.getParameter("ticketId"):"0");
		if(ticketId<=0){
			return status;
		}
		Double totalPrice = Double.parseDouble(StringUtilities.isNotEmpty(request.getParameter("totalPrice"))?request.getParameter("totalPrice"):"0");
		Double commision = Double.parseDouble(StringUtilities.isNotEmpty(request.getParameter("commision"))?request.getParameter("commision"):"0");
		int totalWorkHours = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("totalWorkHours"))?request.getParameter("totalWorkHours"):"0");
		int employeeId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("employeeId"))?request.getParameter("employeeId"):"0");
		status = new UserTicketDbUtils().updateTicketDetails(ticketId, totalPrice, totalWorkHours, employeeId, commision);
		
		return status;
	}
}

