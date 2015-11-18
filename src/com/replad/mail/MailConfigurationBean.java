package com.replad.mail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.replad.utils.DatabaseUtils;

public class MailConfigurationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String vendor = null;
	private String user = null;
	private String pwd  = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * Get all mailing configuration from db
	 * @return
	 */
	public Map<String, MailConfigurationBean> getMailConfigurationList(){
		Map<String, MailConfigurationBean> map = new HashMap<String, MailConfigurationBean>();
		MailConfigurationBean mailConfigurationBean = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id, vendor, user_name, password from replad_email_config_details";
		try{
			con = new DatabaseUtils().getConnection(); 
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				mailConfigurationBean = new MailConfigurationBean();
				mailConfigurationBean.setId(rs.getInt("id"));
				mailConfigurationBean.setVendor(rs.getString("vendor").toLowerCase());
				mailConfigurationBean.setUser(rs.getString("user_name"));
				mailConfigurationBean.setPwd(rs.getString("password"));
				
				if(map.isEmpty() || !map.containsKey(mailConfigurationBean.getVendor())){
					map.put(mailConfigurationBean.getVendor(), mailConfigurationBean);
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return map;
	}
}
