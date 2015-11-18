package com.replad.employee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.utils.DateUtils;
import com.replad.utils.StringUtilities;

public class EmployeeMgmtHelper {

	public String renderEmployeeDetailGridData(HttpServletRequest request){
		String data = "";
		String categoryStatus = ((StringUtilities.isNotEmpty(request.getParameter("categoryStatus")) && !StringUtils.equalsIgnoreCase("-1", request.getParameter("categoryStatus").toString())) ? request.getParameter("categoryCombo") : "");
		String categoryCombo = ((StringUtilities.isNotEmpty(request.getParameter("categoryCombo")) && !StringUtils.equalsIgnoreCase("0", request.getParameter("categoryCombo").toString())) ? request.getParameter("categoryCombo") : "");
		
		List<EmployeeBean> list = new EmployeeMgmtDbUtils().getAllEmployeesDetails(categoryCombo, categoryStatus);
		data = getJsonObject(list);
		return data;
	}
	
	public String renderFilteredEmployeeDetailGridData(HttpServletRequest request){
		String data = "";
		/*List<EmployeeBean> list = new EmployeeMgmtDbUtils().getAllEmployeesDetails();
		data = getJsonObject(list);*/
		return data;
	}
	
	public String populateCombos(HttpServletRequest request){
		String data = "";
		String dataFor = (StringUtilities.isNotEmpty(request.getParameter("dataFor")) ? request.getParameter("dataFor") : null);
		if(StringUtilities.isNotEmpty(dataFor)){
			Map<String,String> map = new EmployeeMgmtDbUtils().populateCombos(dataFor);
			data = new StringUtilities().getJsonObject(map);
		}
		return data;
	}
	
	public String getJsonObject(List<EmployeeBean> list){
		if(null==list){
			return "";
		}
		try {
			JSONArray jsonArr = new JSONArray();
			JSONObject jo = null;
			String id = "";
			for(EmployeeBean bean : list){
				jo = new JSONObject();

				jo.put("id", bean.getId());
				String firstName = StringUtils.isBlank(bean.getFirst_name())?"":bean.getFirst_name();
				String middleName = StringUtils.isBlank(bean.getMiddle_name())?"":bean.getMiddle_name();
				String lastName = StringUtils.isBlank(bean.getLast_name())?"":bean.getLast_name();
				
				String employeeName = firstName+" "+middleName+" "+lastName;
				jo.put("name", employeeName);
				jo.put("first_name", bean.getFirst_name());
				jo.put("middle_name", bean.getMiddle_name());
				jo.put("last_name", bean.getLast_name());
				jo.put("mobile_primary", bean.getMobile_primary());
				jo.put("mobile_secondary", bean.getMobile_secondary());
				jo.put("joining_date", bean.getJoining_date());
				jo.put("releaving_date", bean.getReleaving_date());
				jo.put("id_proof_desc", bean.getId_proof_desc());
				jo.put("id_proof", bean.getId_proof());
				jo.put("address_proof_desc", bean.getAddress_proof_desc());
				jo.put("address_proof", bean.getAddress_proof());
				jo.put("type_description", bean.getEmplyment_type_desc());
				jo.put("employee_type", bean.getEmployement_type());
				jo.put("category_type", bean.getCategory_id());
				jo.put("employment_grade", bean.getEmployment_grade());
				jo.put("status", bean.getActive());
				jo.put("verification_agency", bean.getVerification_agency());
				jo.put("category", bean.getCategory());
				
				jsonArr.put(jo);
			}
			return (jsonArr.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int createNewEmployeeProfile(HttpServletRequest request){
		String eId = (StringUtilities.isNotEmpty(request.getParameter("employeeId")) ? request.getParameter("employeeId") : "0");
		String employeeStatus = (StringUtilities.isNotEmpty(request.getParameter("employeeStatus")) ? request.getParameter("employeeStatus") : "-1");
		String fname = (StringUtilities.isNotEmpty(request.getParameter("fname")) ? request.getParameter("fname") : null);
		String mname = (StringUtilities.isNotEmpty(request.getParameter("mname")) ? request.getParameter("mname") : null);
		String lname = (StringUtilities.isNotEmpty(request.getParameter("lname")) ? request.getParameter("lname") : null);
		String primaryMobile = (StringUtilities.isNotEmpty(request.getParameter("primaryMobile")) ? request.getParameter("primaryMobile") : null);
		String secondaryMobile = (StringUtilities.isNotEmpty(request.getParameter("secondaryMobile")) ? request.getParameter("secondaryMobile") : null);
		String userInputDate = (StringUtilities.isNotEmpty(request.getParameter("joiningDate")) ? request.getParameter("joiningDate") : null);
		Timestamp joiningDate = new DateUtils().getDate(userInputDate);
		int idProof = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("idProof")) ? request.getParameter("idProof") : "0");
		int addressProof = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("addressProof")) ? request.getParameter("addressProof") : "0");
		int gradeOfEmployment = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("gradeOfEmployment")) ? request.getParameter("gradeOfEmployment") : "0");
		int typeOfEmployment = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("typeOfEmployment")) ? request.getParameter("typeOfEmployment") : "0");
		int categoryId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("categoryOfEmployment")) ? request.getParameter("categoryOfEmployment") : "0");
		
		EmployeeBean bean = new EmployeeBean();
		bean.setId(Integer.parseInt(eId));
		bean.setFirst_name(fname);
		bean.setMiddle_name(mname);
		bean.setLast_name(lname);
		bean.setMobile_primary(primaryMobile);
		bean.setMobile_secondary(secondaryMobile);
		bean.setJoining_date(joiningDate);
		bean.setId_proof(idProof);
		bean.setAddress_proof(addressProof);
		bean.setEmployement_type(typeOfEmployment);
		bean.setEmployment_grade(gradeOfEmployment);
		bean.setCategory_id(categoryId);
		bean.setActive(Integer.parseInt(employeeStatus));
		
		int status = new EmployeeMgmtDbUtils().createNewEmployeeProfile(bean);
		return status;
	}
	
	public int deleteEmployeeProfile(HttpServletRequest request){
		String selectedIds = (StringUtilities.isNotEmpty(request.getParameter("selectedIds")) ? request.getParameter("selectedIds") : null);
		int status = new EmployeeMgmtDbUtils().deactivateSelectedEmployeeProfile(selectedIds);
		return status;
	}

	public String renderEmployeeDetailsToEdit(HttpServletRequest request) {
		int selectedEmpId = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("selectedEmpId"))?request.getParameter("selectedEmpId"):"0");
		EmployeeBean employeeBean = new EmployeeMgmtDbUtils().getSelectedEmployeeDetails(selectedEmpId);
		if(null == employeeBean){
			return "";
		}
		List<EmployeeBean> employeeBeans = new ArrayList<EmployeeBean>();
		employeeBeans.add(employeeBean);
		return getJsonObject(employeeBeans);
	}
}
