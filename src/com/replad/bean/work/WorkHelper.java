package com.replad.bean.work;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.helper.user.registration.UserDAO;
import com.replad.init.InitConfiguration;
import com.replad.sub.work.SubworkBean;
import com.replad.sub.work.SubworkDbUtils;
import com.replad.sub.work.SubworkHelper;
import com.replad.sub.work.UserTicketHelper;
import com.replad.utils.DatabaseUtils;
import com.replad.utils.DateUtils;
import com.replad.utils.StringUtilities;

public class WorkHelper {

	/**
	 * returns the work description with comma separated and link.
	 * 
	 * @return
	 */
	public void getWorkDesc(HttpServletRequest request){
		StringBuffer description = new StringBuffer();
		List<WorkBean> workDetailList = InitConfiguration.workDetailList;
		
		for(WorkBean bean : workDetailList){
			description.append("<a>"+bean.getWorkname()+"</a>,&nbsp");
		}
		request.getSession().setAttribute("work-details", description);
	}
	
	public String setSelectedServiceDetails(HttpServletRequest request){
		StringBuffer data = new StringBuffer();
		String selected_sub_work_ids = (StringUtilities.isNotEmpty(request.getParameter("selectedWorkId"))?request.getParameter("selectedWorkId"):null);
		Map<String, String> subWorkDetails = new SubworkDbUtils().getSubworksList(selected_sub_work_ids);
		
		data.append("<ul>");
		for(String subWorkId : subWorkDetails.keySet()){
			data.append("<li>"+subWorkDetails.get(subWorkId)+"</li>");
		}
		data.append("</ul>");
		return (data.toString());
	}
	
	/**
	 * Create a new user workorder ticket.
	 * 
	 * @param request
	 * @return
	 */
	/*public int createOrder(HttpServletRequest request){
		int status = 0;
		DateUtils dateUtil = new DateUtils();
		HttpSession session = request.getSession();
		String subWorkId = (StringUtilities.isNotEmpty(request.getParameter("subWorkId")) ? request.getParameter("subWorkId") : "0");
		String custAddress = (StringUtilities.isNotEmpty(request.getParameter("cusrAddress")) ? request.getParameter("cusrAddress") : null);
		String username = (null!=session.getAttribute("username")? session.getAttribute("username").toString() : null);
		String promoCode = (StringUtilities.isNotEmpty(request.getParameter("couponCode")) ? request.getParameter("couponCode") : "");
		int user_id  = 0;
		if(null!=username && StringUtilities.isNotEmpty(username)){
			user_id = new UserDAO().getUserId(username);
		}
		int opted_week_end = (dateUtil.isWeekend(request.getParameter("serviceReqDate"))?1:0);
		int priority = Integer.parseInt(NumberUtils.isNumber(request.getParameter("priority")) ? request.getParameter("priority") : "0");
		String service_request_date = request.getParameter("serviceReqDate");
		String created_date = new DateUtils().getCurrentDate().toString();
		String[] subworkidArr = (subWorkId.indexOf(",")>0 ? subWorkId.split(",") : new String[]{subWorkId});
		String extraInfo = (StringUtilities.isNotEmpty(request.getParameter("extraInfo")) ? request.getParameter("extraInfo") : "0");
		status = new UserTicketHelper().createNewTicket(user_id, subworkidArr, created_date, opted_week_end, priority, service_request_date, custAddress, extraInfo, promoCode);
		return status;
	}
	*/
	public String renderSubWorkDetails(HttpServletRequest request){
		return new SubworkHelper().getSubworkDetails(request);
	}
	
	public String renderWorkDetailGridData(HttpServletRequest request){
		int workGrpId = Integer.parseInt(StringUtilities.isNotBlank(request.getParameter("selectedWorkGrpId"))?request.getParameter("selectedWorkGrpId"):"0");
		String data = "";
		List<WorkBean> list = new SubworkDbUtils().getAllSubWorkDetailsBeans(workGrpId);
		data = getJsonObjectFromWorkBean(list);
		return data;
	}
	
	public String getJsonObjectFromWorkBean(List<WorkBean> list){
		if(null==list){
			return "";
		}
		try {
			JSONArray jsonArr = new JSONArray();
			JSONObject jo = null;
			 List<SubworkBean> listSubworkBean = null;

			 int workid=0;
			 String workname=null;
			 int id = 0;
			 String desc = null;
			 int price = 0;
				
			for(WorkBean bean : list){
				listSubworkBean = bean.getListSubworkBean();
				
				workid = bean.getWorkid();
				workname = bean.getWorkname();
				
				for(SubworkBean subworkBean : listSubworkBean){
					id = subworkBean.getId();
					desc = subworkBean.getDesc();
					price = subworkBean.getPrice();
					
					jo = new JSONObject();
					jo.put("id", id);
					jo.put("workname", workname);
					jo.put("subworkdesc", desc);
					jo.put("price", price);
					
					jsonArr.put(jo);
				}
			}
			return (jsonArr.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Delete selected services.
	 * 
	 * @param request
	 * @return
	 */
	public int deleteSelectedWorks(HttpServletRequest request){
		String selectedIds = (StringUtilities.isNotEmpty(request.getParameter("selectedIds")) ? request.getParameter("selectedIds") : null);
		int status = new SubworkDbUtils().deleteSelectedService(selectedIds);
		return status;
	}
	
	/**
	 * Create the services.
	 * 
	 * @param request
	 * @return
	 */
	public int createModifyService(HttpServletRequest request){
		String mode = (StringUtilities.isNotEmpty(request.getParameter("mode")) ? request.getParameter("mode") : null);
		int sub_work_id = Integer.parseInt(new DatabaseUtils().getMaxId("replad_sub_work_details", "sub_work_id"));
		int work_group_id = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("selectedWorkId")) ? request.getParameter("selectedWorkId") : "0");
		String sub_work_desc = (StringUtilities.isNotEmpty(request.getParameter("newSubServiceDesc")) ? request.getParameter("newSubServiceDesc") : null);
		int fees = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("newSubServiceFee")) ? request.getParameter("newSubServiceFee") : "0");
		
		int status = 0;
		if(StringUtilities.equalsIgnoreCase(mode, "add")){
			status = new SubworkDbUtils().createSubService(sub_work_id, work_group_id, sub_work_desc, fees);
		}else{
			sub_work_id = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("selectedSubserviceId")) ? request.getParameter("selectedSubserviceId") : "0");
			status = new SubworkDbUtils().modifySubService(sub_work_id, work_group_id, sub_work_desc, fees);
		}
		 
		return status;
	}
}
