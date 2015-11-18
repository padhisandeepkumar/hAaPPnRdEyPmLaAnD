package com.replad.helper.user.registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.replad.mail.SendNewUserDetails;
import com.replad.security.PasswordValidator;
import com.replad.utils.DatabaseUtils;
import com.replad.utils.DateUtils;


public class UserDAO {

	public int getUserId(String email){
		int id = 0;
		DatabaseUtils DatabaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id from user_master where email=?";
		
		try{
			conn = DatabaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				id = rs.getInt(1);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return id;
	}
	
	public boolean isUserExists(String email){
		boolean flag = false;
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from user_master where email=?";
		
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)>0){
					flag = true;
				}
			}
		} catch(SQLException e){
			flag = false;
			e.printStackTrace();
		} catch(Exception e){
			flag = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return flag;
	}
	/**
	 * returns false if user/email exists, else returns true.
	 * @param uvo
	 * @return
	 */
	public boolean isUniqueEmail(UserVO uvo){
		boolean flag = true;
		String email = uvo.getEmail();
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from user_master where email=?";
		
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)>0){
					flag = false;
				}
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return flag;
	}
	
	/**
	 * Add a new user.
	 * 
	 * @param uvo
	 * @return
	 */
	public int addUser(UserVO uvo){
		int addStatus = 0;
		String email = uvo.getEmail();
		String number = uvo.getContactnumber();
		String address = uvo.getContactaddress();
		Timestamp createddate = uvo.getCreateddate();
		Timestamp modifieddate = uvo.getModifieddate();
		int activate = uvo.getActive();
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into user_master(contactnumber, contactaddress, email, active, createddate, modifieddate,name) values(?,?,?,?,?,?,?)";
		try{
			conn = databaseUtils.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, number);
			pstmt.setString(2, address);
			pstmt.setString(3, email);
			pstmt.setInt(4, activate);
			pstmt.setTimestamp(5, createddate);
			pstmt.setTimestamp(6, modifieddate);
			pstmt.setString(7, uvo.getUserName());
			
			int updateStatus = pstmt.executeUpdate();
			if(updateStatus>0){
				boolean flag = new SendNewUserDetails().sendNewUserDetails(uvo.getUserName(), email, 1);
				if(flag){
					conn.setAutoCommit(true);
					addStatus = 1;
				}else{
					addStatus = -3;
				}
			}
		} catch(SQLException e){
			addStatus = 0;
			e.printStackTrace();
		} catch(Exception e){
			addStatus = 0;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
	
	/*public boolean resetUser(UserVO uvo){
		boolean addStatus = false;
		int userId = uvo.getId();
		String email = uvo.getEmail();
		String salt = uvo.getSaltkey();
		String password = uvo.getPassword();
		Timestamp modifieddate = uvo.getModifieddate();
		int isactive = uvo.getActive();
		int passwordchangeflag = uvo.getPasswordchangedflag();
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set password=?, email=?, active=?, saltkey=?, passwordchangedflag=?, modifieddate=? where id=?";
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, password);
			pstmt.setString(2, email);
			pstmt.setInt(3, isactive); // on password change only activate
			pstmt.setString(4, salt);
			pstmt.setInt(5, passwordchangeflag); // on creation/reset of user is 0
			pstmt.setTimestamp(6, modifieddate);
			pstmt.setInt(7, userId);
			int updateStatus = pstmt.executeUpdate();
			if(updateStatus>0){
				addStatus = true;
			}
		} catch(SQLException e){
			addStatus = false;
			e.printStackTrace();
		} catch(Exception e){
			addStatus = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}*/
	
	public boolean updateUser(UserVO uvo){
		boolean addStatus = false;
		int userId = uvo.getId();
		String email = uvo.getEmail();
		String number = uvo.getContactnumber();
		String address = uvo.getContactaddress();
		String salt = uvo.getSaltkey();
		String password = uvo.getPassword();
		Timestamp createddate = uvo.getCreateddate();
		Timestamp modifieddate = uvo.getModifieddate();
		Timestamp lastloggedin = uvo.getLastloggedin();
		int isactive = uvo.getActive();
		int passwordchangeflag = uvo.getPasswordchangedflag();
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set password=?, contactnumber=?, contactaddress=?, email=?, active=?, saltkey=?, passwordchangedflag=?, createddate=?, modifieddate=?, lastloggedin=? where id=?";
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, password);
			pstmt.setString(2, number);
			pstmt.setString(3, address);
			pstmt.setString(4, email);
			pstmt.setInt(5, isactive); // on password change only activate
			pstmt.setString(6, salt);
			pstmt.setInt(7, passwordchangeflag); // on creation of user is 0
			pstmt.setTimestamp(8, createddate);
			pstmt.setTimestamp(9, modifieddate);
			pstmt.setTimestamp(10, lastloggedin);
			pstmt.setInt(11, userId);
			int updateStatus = pstmt.executeUpdate();
			if(updateStatus>0){
				addStatus = true;
			}
		} catch(SQLException e){
			addStatus = false;
			e.printStackTrace();
		} catch(Exception e){
			addStatus = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
	
	public UserVO getUserData(int userId){
		UserVO vo = new UserVO();
		DatabaseUtils databaseUtils = new DatabaseUtils();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from user_master where id=?";
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				vo.setId(rs.getInt("id"));
				vo.setPassword(rs.getString("password"));
				vo.setContactnumber(rs.getString("contactnumber"));
				vo.setContactaddress(rs.getString("contactaddress"));
				vo.setEmail(rs.getString("email"));
				vo.setActive(rs.getInt("active"));
				vo.setSaltkey(rs.getString("saltkey"));
				vo.setPasswordchangedflag(rs.getInt("passwordchangedflag"));
				vo.setCreateddate(rs.getTimestamp("createddate"));
				vo.setModifieddate(rs.getTimestamp("modifieddate"));
				vo.setLastloggedin(rs.getTimestamp("lastloggedin"));
				vo.setUserName(rs.getString("name"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return vo;
	}
	
	public int validateUser(String email, String passwprd, String salt){
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from user_master where email=? and password=? and saltkey=?";
		int count = 0;
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, passwprd);
			pstmt.setString(3, salt);
			rs = pstmt.executeQuery();
			while(rs.next()){
				++count;
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return (count);
	}
	
	/**
	 * Update the encrypted password and salt for the user.
	 * 
	 * @param uvo
	 * @return
	 */
	public boolean updatePassword(UserVO uvo){
		boolean addStatus = false;
		int uId = uvo.getId();
		String password = uvo.getPassword();
		String saltKey = uvo.getSaltkey();
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set password=?, saltkey=? where id=?";
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			pstmt.setString(2, saltKey);
			pstmt.setInt(3, uId);
			pstmt.executeUpdate();
			addStatus = true;
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
	
	/** return true if user is active, else false
	 * 
	 * @param name
	 * @return
	 */
	public boolean isActiveUser(String email){
		boolean flag = false;
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select active from user_master where email=?";
		
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt(1)>0){
					flag = true;
				}
			}
		} catch(SQLException e){
			flag = false;
			e.printStackTrace();
		} catch(Exception e){
			flag = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return flag;
	}
	
	/**
	 * Return -1 if the existing password not matched.
	 * If matched then only it will update the new password.
	 * 
	 * @param loginname
	 * @param oldpwd
	 * @param newpwd
	 * @return
	 */
	public int updatePassword(String loginname, String oldpwd, String newpwd){
		int userId = getUserId(loginname);
		UserVO vo = getUserData(userId);
		
		PasswordValidator pv = new PasswordValidator();
		boolean flag = pv.validatePassword(vo, oldpwd);
		if(flag){
			String salt = vo.getSaltkey();
			String finalPwd = new PasswordValidator().getFinalEncryptedPassword(salt, newpwd);
			vo.setPassword(finalPwd);
			vo.setActive(1);
			vo.setPasswordchangedflag(1);
			vo.setModifieddate(new DateUtils().getCurrentDate());
			flag = updateUser(vo);
			return (flag?1:0);
		}else{
			return -1;
		}
	}
	/**
	 * Get all available user data
	 * 
	 * @return
	 */
	public Map<Integer, UserVO> getAllUserData(){
		Map<Integer, UserVO> userDataMap = new HashMap<Integer, UserVO>();
		UserVO vo = null;
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from user_master";
		
		int id = 0;
		String contactnumber = null;
		String contactaddress = null;
		String email = null;
		int active = 0;
		int passwordchangedflag = 0;
		Timestamp createddate = new Timestamp(new Date().getTime());
		Timestamp modifieddate = new Timestamp(new Date().getTime());
		Timestamp lastloggedin = new Timestamp(new Date().getTime());
		
		try{
			conn = databaseUtils.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				vo = new UserVO();
				
				id = rs.getInt("id");
				contactnumber = rs.getString("contactnumber");
				contactaddress = rs.getString("contactaddress");
				email = rs.getString("email");
				active = rs.getInt("active");
				passwordchangedflag = rs.getInt("passwordchangedflag");
				createddate = rs.getTimestamp("createddate");
				modifieddate = rs.getTimestamp("modifieddate");
				lastloggedin = rs.getTimestamp("lastloggedin");

				vo.setId(id);
				vo.setContactnumber(contactnumber);
				vo.setContactaddress(contactaddress);
				vo.setEmail(email);
				vo.setActive(active);
				vo.setPasswordchangedflag(passwordchangedflag);
				vo.setCreateddate(createddate);
				vo.setModifieddate(modifieddate);
				vo.setLastloggedin(lastloggedin);
				
				userDataMap.put(id, vo);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, stmt, null, rs);
		}
		return userDataMap;
	}
	
	/**
	 * Update the user last logged in time.
	 * 
	 * @param userId
	 * @return
	 */
	public boolean updateLoggedInTime(int userId){
		boolean addStatus = false;
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set lastloggedin=? where id=?";
		Timestamp currentDate = new Timestamp(new Date().getTime());
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, currentDate);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			addStatus = true;
		} catch(SQLException e){
			addStatus = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
	
	/**
	 * Update the user last modified time.
	 * 
	 * @param userId
	 * @return
	 */
	public boolean updateLastModifiedTime(int userId){
		boolean addStatus = false;
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set modifieddate=? where id=?";
		Timestamp currentDate = new Timestamp(new Date().getTime());
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, currentDate);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			addStatus = true;
		} catch(SQLException e){
			addStatus = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
	
	
	
	public Timestamp[] getAllTimestamps(String email){
		boolean flag = false;
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select createddate, modifieddate, lastloggedin from user_master where email=?";
		Timestamp[] timeArr = null;
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				timeArr = new Timestamp[] {rs.getTimestamp(1), rs.getTimestamp(2), rs.getTimestamp(3)};
			}
		} catch(SQLException e){
			timeArr = null;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return timeArr;
	}
	
	public String getSaltKey(String email){
		String saltKey = "";
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select saltkey from user_master where email=?";
		
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				saltKey = rs.getString(1);
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return saltKey;
	}
	
	/**
	 * Get the contact details of the user.
	 * 
	 * @param email
	 * @return
	 */
	public Map<String, String> getContactDetails(String email){
		Map<String, String> contactDtlsMap = new HashMap<String, String>();
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select contactnumber,contactaddress,email from user_master where email=?";
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			while(rs.next()){
				contactDtlsMap.put("contactnumber", rs.getString("contactnumber"));
				contactDtlsMap.put("contactaddress", rs.getString("contactaddress"));
				contactDtlsMap.put("email", rs.getString("email"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, rs);
		}
		return contactDtlsMap;
	}
	
	/**
	 * Activate the user
	 * 
	 * @param userId
	 * @return
	 */
	public boolean activateUser(int userId){
		boolean addStatus = false;
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set active=1, modifieddate=? where id=?";
		Timestamp currentDate = new Timestamp(new Date().getTime());
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, currentDate);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			addStatus = true;
		} catch(SQLException e){
			addStatus = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
	
	/**
	 * Activate the user
	 * 
	 * @param userId
	 * @return
	 */
	public boolean deactivateUser(int userId){
		boolean addStatus = false;
		
		DatabaseUtils databaseUtils = new DatabaseUtils();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update user_master set active=0, modifieddate=? where id=?";
		Timestamp currentDate = new Timestamp(new Date().getTime());
		try{
			conn = databaseUtils.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setTimestamp(1, currentDate);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			addStatus = true;
		} catch(SQLException e){
			addStatus = false;
			e.printStackTrace();
		} finally{
			DatabaseUtils.closeConnection(conn, null, pstmt, null);
		}
		return addStatus;
	}
}
