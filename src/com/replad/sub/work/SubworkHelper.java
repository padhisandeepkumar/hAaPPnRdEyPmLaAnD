package com.replad.sub.work;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.bean.work.WorkBean;
import com.replad.utils.StringUtilities;

public class SubworkHelper {

	/**
	 * Renders the sub-work ui on click of the services. which fill the modal body.
	 * 
	 * @param request
	 * @return
	 */
	public String renderSubWorks(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer();
		HttpSession session = request.getSession();
		int groupId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("grpId")) ? request.getParameter("grpId") :"0");
		String workgrpname = (StringUtilities.isNotEmpty(request.getParameter("workgrpname")) ? request.getParameter("workgrpname") :"");
		String selectedService = (StringUtilities.isNotEmpty(request.getParameter("selectedService")) ? request.getParameter("selectedService") : "");
		
		session.setAttribute("selected_work_group_id", groupId);
		session.setAttribute("selected_work_group_name", workgrpname);
		

			// signin/register section starts.
		buffer.append("		<div class='btn-group form-group col-md-6 pull-left' data-toggle='buttons' data-bind='checkbox: checkboxArray' style='padding-top:10px;'>");
		buffer.append("			<label class='btn btn-success btn-sm active'>");
		buffer.append("				<input type='radio' name='userOption' value='1' onChange=\"userSelection(this);\"/>&nbsp;SignIn");
		buffer.append("			</label>");
		buffer.append("			<label class='btn btn-success btn-sm'>");
		buffer.append("				<input type='radio' name='userOption' value='2' onChange=\"userSelection(this);\"/>&nbsp;Register");
		buffer.append("			</label>");
		buffer.append("		</div>");
		//signin block
		buffer.append("		<div id='signinRow' style='display:block;padding:10px;'>");
		buffer.append("			<div class='input-group col-md-12'>");
		buffer.append("				<span class='input-group-addon'><i class='fa fa-envelope'></i></span>");
		buffer.append("				<input type='text' class='form-control' id='username' placeholder='enter email id'>");
		buffer.append("				<span class='input-group-addon'><i class='fa fa-key'></i></span>");
		buffer.append("				<input type='password' class='form-control' id='password' placeholder='enter password'>");
		buffer.append("			</div>");
		buffer.append(" 	</div>");
		
		buffer.append("		<span class='help-block'></span>");
		// register block
		buffer.append("		<div id='registerRow' style='display:none;padding:10px;'>");
		buffer.append("			<div class='input-group col-md-12'>");
		buffer.append("				<span class='input-group-addon'><i class='fa fa-envelope'></i></span>");
		buffer.append("				<input type='text' class='form-control' id='regEmail' placeholder='enter email id'>");
		buffer.append("				<span class='input-group-addon'><i class='fa fa-mobile'></i></span>");
		buffer.append("				<input type='password' class='form-control' id='regMobile' placeholder='enter mobile number'>");
		buffer.append("			</div>");
		
		buffer.append("			<span class='help-block'></span>");
		buffer.append("			<div class='input-group col-md-12'>");
		buffer.append("				<span class='input-group-addon'><i class='fa fa-home'></i></span>");
		buffer.append("				<textarea class='form-control' rows='3' id='regAddress' placeholder='enter mailing address with landmark'></textarea>");
		buffer.append("			</div>");
		buffer.append(" 	</div>");
		return buffer.toString();
	}
	
	public String populateStateCombo_AutoComplete(){
		Map<String, String> map = new SubworkDbUtils().getSubworks();
		StringUtilities utilities = new  StringUtilities();
		String jsonStr = utilities.getJsonString4AutoComplete(map);
		return (StringUtilities.isNotEmpty(jsonStr) ? jsonStr : "");
	}
	
	public String workGroupId(HttpServletRequest request){
		int subworkId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("subworkId")) ? request.getParameter("subworkId") :"0");
		Map<String, String> map = new SubworkDbUtils().workGroupId(subworkId);
		StringUtilities utilities = new  StringUtilities();
		String jsonStr = utilities.getJsonString4AutoComplete(map);
		return (StringUtilities.isNotEmpty(jsonStr) ? jsonStr : "");
	}
	
	public Map<String, WorkBean> getWorkDetails(){
		Map<String, WorkBean> workDtls = new SubworkDbUtils().workDetails();
		
		return workDtls;
	}
	
	/**
	 * Render work details to populate the work group combo.
	 * 
	 * @return
	 */
	public String renderWorkDetails(){
		Map<String, WorkBean> map = new SubworkDbUtils().workDetails();

		WorkBean bean = null;
		String workname="", iconFile="";
		int i=0;
 
		Map<String, String> workDtlsMap = new HashMap<String, String>();
		
		for(String workId : map.keySet()){
			bean = map.get(workId);
			workname = bean.getWorkname();
			workDtlsMap.put(workId, workname);
		}
		String data = new StringUtilities().getJsonObject(workDtlsMap);
		System.out.println("JSON data--->"+data);
		return (data);
	}
	
	public String getSubworkDetails(HttpServletRequest request){
		int groupId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("selectedServiceId"))?request.getParameter("selectedServiceId"):"0");
		int subServiceId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("selectedSubServiceId"))?request.getParameter("selectedSubServiceId"):"0");
		List<SubworkBean> beanList= new SubworkDbUtils().getSubWorkBeans(groupId, subServiceId);
		
		JSONArray jsonArr = new JSONArray();
		try {
			JSONObject jo = null;
			for(SubworkBean bean : beanList){
				jo = new JSONObject();
					jo.put("id", String.valueOf(bean.getId()));
					jo.put("desc", bean.getDesc());
					jo.put("price", "Rs."+bean.getPrice()+"/hr");
				jsonArr.put(jo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArr.toString();
	}
	
	
	public String populateSubworksCombo(HttpServletRequest request){
		int groupId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("grpId")) ? request.getParameter("grpId") :"0");
		String subWorkDetails = new SubworkDbUtils().getSubWorks(groupId);
		return subWorkDetails;
	}
}
