package com.replad.sub.work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.bean.work.WorkBean;
import com.replad.utils.DatabaseUtils;

public class SubworkDbUtils {

	public String getSubWorks(int groupId){
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select sub_work_id, sub_work_desc, fees from replad_sub_work_details where work_group_id=?";
		JSONArray jsonArr = new JSONArray();
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, groupId);
			rs = pstmt.executeQuery();
			
			JSONObject jo = null;
			while(rs.next()){
				jo = new JSONObject();
				jo.put("id", rs.getString(1));
				jo.put("desc", rs.getString(2));
				jo.put("price", rs.getString(3));
				jsonArr.put(jo);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return jsonArr.toString();
	}
	
	public Map<String,String> getSubworks(){
		Map<String,String> map = new HashMap<String, String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT sub_work_id, sub_work_desc FROM replad_sub_work_details";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				map.put(rs.getString(1), rs.getString(2));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return map;
	}
	
	public Map<String, String> workGroupId(int subworkid){
		Map<String, String> map = new HashMap<String, String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT a.work_group_id,b.work_group FROM  replad_sub_work_details a join replad_work_group_details b on a.work_group_id=b.work_group_id and a.sub_work_id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, subworkid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				map.put(rs.getInt(1)+"", rs.getString(2));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return map;
	}
	
	
	public Map<String, WorkBean> workDetails(){
		Map<String, WorkBean> map = new TreeMap<String, WorkBean>();
		WorkBean bean = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT work_group_id,work_group,icon_file FROM replad_work_group_details";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				bean = new WorkBean();
				bean.setWorkid(rs.getInt(1));
				bean.setWorkname(rs.getString(2));
				bean.setIconfile(rs.getString(3));
				map.put(bean.getWorkid()+"", bean);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return map;
	}
	
	public Map<String,String> getSubworksList(String subWorkIds){
		Map<String,String> map = new HashMap<String, String>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT sub_work_id, sub_work_desc FROM replad_sub_work_details where sub_work_id in ("+subWorkIds+")";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				map.put(rs.getString(1), rs.getString(2));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return map;
	}
	
	/**
	 * Create order details for the user.
	 * 
	 * @param subWorkIds : selected work details
	 * @param userId : requested user id
	 * @param priority : 1/0 1 for urgency need to be addressed immediately.
	 * @param optedDay : 1/0 1 for weekdays and 0 for weekends.
	 * 
	 * @return : 1 for created successfully 0 for failed to create the order for user.
	 */
	public int createOrder(String[] subWorkIds, String userId,int priority, String optedDay){
		int status = 0;
		
		return status;
	}
	/**
	 * Returns the subwork bean list for the given work is,.
	 * 
	 * @param groupId
	 * @return
	 */
	public List<SubworkBean> getSubWorkBeans(int groupId, int subServiceId){
		List<SubworkBean> beanList= new ArrayList<SubworkBean>();
		SubworkBean bean = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.sub_work_id sub_work_id, a.sub_work_desc sub_work_desc, a.fees fees from replad_sub_work_details a join replad_work_group_details b on a.work_group_id=b.work_group_id";
		if(groupId!=0){
			sql+=" and a.work_group_id=?";
		}
		if(subServiceId!=0){
			sql+=" and a.sub_work_id=?";
		}
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			if(groupId!=0){
				pstmt.setInt(1, groupId);	
			}
			if(subServiceId!=0){
				pstmt.setInt(2, subServiceId);	
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				bean = new SubworkBean();
				bean.setId(rs.getInt("sub_work_id"));
				bean.setDesc(rs.getString("sub_work_desc"));
				bean.setPrice(rs.getInt("fees"));
				beanList.add(bean);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return beanList;
	}
	
	/**
	 * Get subwork details of the selected work group id
	 * 
	 * @param workGroupId
	 * @return
	 */
	public List<WorkBean> getAllSubWorkDetailsBeans(int workGroupId){
		List<WorkBean> beanList= new ArrayList<WorkBean>();
		WorkBean workbean = new WorkBean();
		List<SubworkBean> SubworkBeanList = new ArrayList<SubworkBean>();
		SubworkBean bean = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.sub_work_id sub_work_id, b.work_group_id,b.work_group, a.sub_work_desc sub_work_desc, a.fees fees from replad_sub_work_details a join replad_work_group_details b on a.work_group_id=b.work_group_id ";
		if(workGroupId>0){
			sql +=" and b.work_group_id=?";
		}
		sql +=" order by b.work_group_id";
		int previousWorkGroupId = 0;
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			if(workGroupId>0){
				pstmt.setInt(1, workGroupId);
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				if(previousWorkGroupId==0){
					workbean.setWorkid(rs.getInt("work_group_id")); 
					workbean.setWorkname(rs.getString("work_group"));
				}
				
				if(previousWorkGroupId==0 || (rs.getInt("work_group_id")>0 && previousWorkGroupId==rs.getInt("work_group_id"))){
					previousWorkGroupId=rs.getInt("work_group_id");
					bean = new SubworkBean();
					
					bean.setId(rs.getInt("sub_work_id"));
					bean.setDesc(rs.getString("sub_work_desc"));
					bean.setPrice(rs.getInt("fees"));
					
					SubworkBeanList.add(bean);
				}else if(rs.getInt("work_group_id")>0 && previousWorkGroupId!=rs.getInt("work_group_id")){
					workbean.setListSubworkBean(SubworkBeanList);
					beanList.add(workbean);
					
					SubworkBeanList = new ArrayList<SubworkBean>();
					workbean = new WorkBean();
					bean = new SubworkBean();
					previousWorkGroupId=rs.getInt("work_group_id");
					
					workbean.setWorkid(rs.getInt("work_group_id")); 
					workbean.setWorkname(rs.getString("work_group"));
					bean.setId(rs.getInt("sub_work_id"));
					bean.setDesc(rs.getString("sub_work_desc"));
					bean.setPrice(rs.getInt("fees"));
					
					SubworkBeanList.add(bean);
				}
			}
			if(!SubworkBeanList.isEmpty()){
				workbean.setListSubworkBean(SubworkBeanList);
				beanList.add(workbean);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return beanList;
	}
	
	/**
	 * Delete Selected services.
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteSelectedService(String ids){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] arr = (ids.indexOf(",")>0?ids.split(",") : new String[]{ids});
		String sql = new DatabaseUtils().replaceWithPlaceholder("delete from replad_sub_work_details where sub_work_id in (#replaceParam#)", Arrays.asList(arr));
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
	
	/**
	 * Create the services.
	 * 
	 * @param ids
	 * @return
	 */
	public int createSubService(int sub_work_id, int work_group_id, String sub_work_desc, int fees){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into replad_sub_work_details(sub_work_id, work_group_id, sub_work_desc, fees) values(?,?,?,?)";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sub_work_id);
			pstmt.setInt(2, work_group_id);
			pstmt.setString(3, sub_work_desc);
			pstmt.setInt(4, fees);
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	
	/**
	 * Modify the services.
	 * 
	 * @param ids
	 * @return
	 */
	public int modifySubService(int sub_work_id, int work_group_id, String sub_work_desc, int fees){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update replad_sub_work_details set work_group_id=?, sub_work_desc=?, fees=? where sub_work_id=?";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, work_group_id);
			pstmt.setString(2, sub_work_desc);
			pstmt.setInt(3, fees);
			pstmt.setInt(4, sub_work_id);
			status = pstmt.executeUpdate();
		} catch(SQLException e){
			status=0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, null);
		}
		return status;
	}
	
	/**
	 * Insert the data from uploaded .csv file
	 * 
	 * @param workDetailList
	 * @return
	 */
	public int updateWorkDetails(List<List<String>> workDetailList){
		int status = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "insert into replad_sub_work_details values(?,?,?,?)";

		int subworkid = 0;
		String workid="0", subworkDesc="", price="";
		try{
			con = new DatabaseUtils().getConnection();
			con.setAutoCommit(false);
			subworkid = Integer.parseInt(new DatabaseUtils().getMaxId("replad_sub_work_details", "sub_work_id"));
			pstmt = con.prepareStatement(sql);

			for(List<String> list : workDetailList){
				if(!list.isEmpty() && list.size()==3){
					workid = list.get(0);
					subworkDesc = list.get(1);
					price = list.get(2);
					pstmt.setInt(1, subworkid);
					pstmt.setString(2, workid);
					pstmt.setString(3, subworkDesc);
					pstmt.setString(4, price);
					pstmt.addBatch();
					subworkid++;
				}
			}
			pstmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			status = -1;
			e.printStackTrace();
		} catch(Exception e){
			status = -1;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return status;
	}
}
