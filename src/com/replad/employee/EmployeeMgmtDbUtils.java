package com.replad.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.replad.controllers.ActionHandler;
import com.replad.utils.DatabaseUtils;

public class EmployeeMgmtDbUtils {
	private static final Logger log = Logger.getLogger(ActionHandler.class.getName());
	
	public List<EmployeeBean> getAllEmployeesDetails(String category, String status){
		List<EmployeeBean> list = new ArrayList<EmployeeBean>();
		EmployeeBean bean = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.id, a.first_name, a.middle_name, a.last_name, a.mobile_primary, a.mobile_secondary, a.joining_date, a.id_proof, a.address_proof, "
				+ "a.employement_type, a.employment_grade, a.active, a.category_id, b.category, c.type_description "
				+ "from replad_employee_details a, replad_employee_category b , replad_employee_employment_type c where a.category_id=b.id and a.employement_type=c.employement_type ";
		
		if(StringUtils.isNotBlank(category)){
			sql += " and a.category_id=? ";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and a.active=? ";
		}
		sql += " order by a.id asc ";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			if(StringUtils.isNotBlank(category)){
				pstmt.setInt(1, Integer.parseInt(category));
			}
			if(StringUtils.isBlank(category) && StringUtils.isNotBlank(status)){
				pstmt.setInt(1, Integer.parseInt(status));
			}
			if(StringUtils.isNotBlank(category) && StringUtils.isNotBlank(status)){
				pstmt.setInt(1, Integer.parseInt(category));
				pstmt.setInt(2, Integer.parseInt(status));
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				bean = new EmployeeBean();
				
				bean.setId(rs.getInt("id"));
				bean.setFirst_name(rs.getString("first_name"));
				bean.setMiddle_name(rs.getString("middle_name"));
				bean.setLast_name(rs.getString("last_name"));
				bean.setMobile_primary(rs.getString("mobile_primary"));
				bean.setMobile_secondary(rs.getString("mobile_secondary"));
				bean.setJoining_date(rs.getTimestamp("joining_date"));
//				bean.setReleaving_date(rs.getTimestamp("releaving_date"));
				bean.setId_proof(rs.getInt("id_proof"));
//				bean.setId_proof_desc(rs.getString("id_proof_desc"));
				bean.setAddress_proof(rs.getInt("address_proof"));
//				bean.setAddress_proof_desc(rs.getString("address_proof_desc"));
				bean.setEmployement_type(rs.getInt("employement_type"));
				bean.setEmplyment_type_desc(rs.getString("type_description"));
				bean.setEmployment_grade(rs.getInt("employment_grade"));
//				bean.setGrade_desc(rs.getString("grade_desc"));
//				bean.setVerification_done(rs.getInt("verification_done"));
//				bean.setVerification_agency(rs.getInt("verification_agency"));
//				bean.setVerifying_agency_desc(rs.getString("verifying_agency_desc"));
				bean.setActive(rs.getInt("active"));
				bean.setCategory_id(rs.getInt("category_id"));
				bean.setCategory(rs.getString("category"));
				
				list.add(bean);
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return list;
	}
	
	public Map<String, String> populateCombos(String dataFor){
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		
		switch(dataFor){
			case "idCombo":{
				sql = "select id, document_type from replad_employee_id_proof order by id asc";
				break;
			}
			case "addressCombo":{
				sql = "select id, document_type from replad_address_proof order by id asc";
				break;
			}
			case "gradeCombo":{
				sql = "select employment_grade, grade_description from replad_employment_grade order by employment_grade desc";
				break;
			}
			case "typeCombo":{
				sql = "select employement_type, type_description from replad_employee_employment_type order by employement_type asc";
				break;
			}
			case "categoryCombo":{
				sql = "select id, category from replad_employee_category order by id asc";
				break;
			}
			case "employeeCombo":{
				sql = "select id, first_name from replad_employee_details order by id asc";
				break;
			}
			case "editEmployeeCombo":{
				sql = "select id, first_name from replad_employee_details order by id asc";
				break;
			}
		}
		try{
			con = new DatabaseUtils().getConnection(); 
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				map.put(rs.getString(1), rs.getString(2));
			}
			
		} catch(SQLException e){
			log.warning("data for error--->"+dataFor);
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, stmt, null, rs);
		}
		return map;
	}
	
	public int createNewEmployeeProfile(EmployeeBean bean){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String querySQL = "";
		if(bean.getId()>0){
			querySQL = "update replad_employee_details set first_name=?, middle_name=?, last_name=?, mobile_primary=?, mobile_secondary=?, joining_date=?, id_proof=?, address_proof=?, employement_type=?, employment_grade=?, active=?, category_id=? where id=?";
		}else{
			querySQL = "insert into replad_employee_details (first_name, middle_name, last_name, mobile_primary, mobile_secondary, joining_date, id_proof, address_proof, employement_type, employment_grade, active, category_id, id) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(querySQL);
			
			if(bean.getId()>0){
				pstmt.setInt(13, bean.getId());
			}else{
				int id= Integer.parseInt(new DatabaseUtils().getMaxId("replad_employee_details", "id"));
				pstmt.setInt(13, id);
			}
			
			pstmt.setString(1, bean.getFirst_name());
			pstmt.setString(2, bean.getMiddle_name());
			pstmt.setString(3, bean.getLast_name());
			pstmt.setString(4, bean.getMobile_primary());
			pstmt.setString(5, bean.getMobile_secondary());
			pstmt.setTimestamp(6, bean.getJoining_date());
			pstmt.setInt(7, bean.getId_proof());
			pstmt.setInt(8, bean.getAddress_proof());
			pstmt.setInt(9, bean.getEmployement_type());
			pstmt.setInt(10, bean.getEmployment_grade());
			pstmt.setInt(11, bean.getActive());
			pstmt.setInt(12, bean.getCategory_id());
			
			status = pstmt.executeUpdate();
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	
	public int deactivateSelectedEmployeeProfile(String ids){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] arr = (ids.indexOf(",")>0?ids.split(",") : new String[]{ids});
		String sql = new DatabaseUtils().replaceWithPlaceholder("update replad_employee_details set active=0 where id in (#replaceParam#)", Arrays.asList(arr));
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			for(int i=1; i<=arr.length;i++){
				pstmt.setInt(i, Integer.parseInt(arr[i-1]));
			}
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}

	public EmployeeBean getSelectedEmployeeDetails(int selectedEmpId) {
		
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployeeBean bean = null;
		String sql = "select id, first_name, middle_name, last_name, mobile_primary, mobile_secondary, joining_date, id_proof, address_proof, employement_type, employment_grade, category_id, active from replad_employee_details where id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, selectedEmpId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new EmployeeBean();
				
				bean.setId(rs.getInt("id"));
				bean.setFirst_name(rs.getString("first_name"));
				bean.setMiddle_name(rs.getString("middle_name"));
				bean.setLast_name(rs.getString("last_name"));
				bean.setMobile_primary(rs.getString("mobile_primary"));
				bean.setMobile_secondary(rs.getString("mobile_secondary"));
				bean.setJoining_date(rs.getTimestamp("joining_date"));
				bean.setId_proof(rs.getInt("id_proof"));
				bean.setAddress_proof(rs.getInt("address_proof"));
				bean.setEmployement_type(rs.getInt("employement_type"));
				bean.setEmployment_grade(rs.getInt("employment_grade"));
				bean.setActive(rs.getInt("active"));
				bean.setCategory_id(rs.getInt("category_id"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return bean;
	}
}



