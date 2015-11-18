package com.replad.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.replad.utils.DatabaseUtils;
import com.replad.utils.LoadProperties;

public class PasswordEncryptorDecryptor {
	/**
	 * Get encrypted password for the given user.
	 * 
	 * @param user
	 * @return
	 */
	public String getEncryptedPassword(String user){
		String encryptPwd = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select password from user_master where user_name=?";
		try{
			con = new DatabaseUtils().getConnection("admin");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user);

			rs = pstmt.executeQuery();

			while(rs.next()){
				encryptPwd = getEncrypterUserPassword(rs.getString(1));
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return encryptPwd;
	}
	
	/**
	 * Selecting the encryption type.
	 * 
	 * @return
	 */
	public static PasswordEncryptor getPasswordEncryptor(){
		PasswordEncryptor passwordEncryptor = null;
		String passwordType = new LoadProperties().getPropValue("strong.pwd.encryption");
		if(passwordType.trim().equalsIgnoreCase("no")){
			passwordEncryptor = new BasicPasswordEncryptor();
		}else{
			passwordEncryptor = new StrongPasswordEncryptor();
		}
		return passwordEncryptor;
	}
	
	/**
	 * Returns the encryption of the given password.
	 * 
	 * @param userPassword
	 * @return
	 */
	public String getEncrypterUserPassword(String userPassword){
		PasswordEncryptor passwordEncryptor = getPasswordEncryptor();
		String newEncryptedPassword = passwordEncryptor.encryptPassword(userPassword);
		return newEncryptedPassword;
	}
}
