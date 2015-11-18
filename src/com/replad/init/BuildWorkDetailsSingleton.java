package com.replad.init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.replad.bean.work.WorkBean;
import com.replad.sub.work.SubworkBean;
import com.replad.utils.DatabaseUtils;

/**
 * The work detail object created at the time of server startup. The object refreshed on recache.
 * It is singleton object which is the lone object to serve the whole application.
 * 
 * @author prasmit
 *
 */
public class BuildWorkDetailsSingleton implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public static BuildWorkDetailsSingleton INSTANCE = new BuildWorkDetailsSingleton();
	protected BuildWorkDetailsSingleton() {
		// Exists only to thwart instantiation.
	}
	private Object readResolve() {
		return INSTANCE;
	}

	/**
	 * List contains all the workbean.
	 * 
	 * ==========================
	 * |     Work Bean 			|
	 * ==========================
	 * |     Work ID			|
	 * |     Work Description	|
	 * |     Icon File name		|
	 * |.-->>Sub work Bean		|
	 * =|========================
	 * 	|			  
	 *  |		=========================
	 * 	|		|  Sub work Bean		|
	 * 	|		=========================
	 * 	'----->>|  Sub work Id			|
	 * 			|  Sub work Description	|
	 * 			|  Fees					|
	 * 			|  Work Group Id		|
	 * 			=========================
	 * @return
	 */
	public List<WorkBean> getWorkDetails(){
		List<WorkBean> list = new ArrayList<WorkBean>();
		WorkBean bean = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from replad_work_group_details";

		Map<Integer, List<SubworkBean>> map = getSubWorkDetails();
		try{
			conn = new DatabaseUtils().getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				bean = new WorkBean();
				bean.setWorkid(rs.getInt("work_group_id"));
				bean.setWorkname(rs.getString("work_group"));
				bean.setIconfile(rs.getString("icon_file"));
				bean.setListSubworkBean(map.get(new Integer(bean.getWorkid())));
				list.add(bean);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, stmt, null, rs);
		}
		return list;
	}

	public Map<Integer, List<SubworkBean>> getSubWorkDetails(){
		Map<Integer, List<SubworkBean>> map = new HashMap<Integer, List<SubworkBean>>();
		List<SubworkBean> list = new ArrayList<SubworkBean>();
		SubworkBean bean = null;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from replad_sub_work_details";

		try{
			conn = new DatabaseUtils().getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				bean = new SubworkBean();
				bean.setId(rs.getInt("sub_work_id"));
				bean.setDesc(rs.getString("sub_work_desc"));
				bean.setPrice(rs.getInt("fees"));
				bean.setGrpId(rs.getInt("work_group_id"));

				if(map.containsKey(bean.getGrpId())){
					list = map.get(bean.getGrpId());
				}else{
					list = new ArrayList<SubworkBean>();
				}
				list.add(bean);
				map.put(new Integer(bean.getGrpId()), list);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, stmt, null, rs);
		}
		return map;
	}
}
