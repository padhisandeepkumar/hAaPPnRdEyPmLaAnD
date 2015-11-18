package com.replad.user.creation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.PasswordEncryptor;

import com.replad.utils.DatabaseUtils;

public class UserPasswordEncryptor {
	public static PasswordEncryptor getPasswordEncryptor(){
		PasswordEncryptor passwordEncryptor = null;
		/*String passwordType = new LoadProperties().getPropValue("new.user.password.encryption");
		if(passwordType.trim().equalsIgnoreCase("strong")){
			passwordEncryptor = new StrongPasswordEncryptor();
		}else{
			passwordEncryptor = new BasicPasswordEncryptor();
		}*/
		passwordEncryptor = new BasicPasswordEncryptor();
		return passwordEncryptor;
	}

	public String getPasswordFromDB(String user){
		String password = "";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select password from user_master where email=?";
		try{
			con = new DatabaseUtils().getConnection("admin");
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user);

			rs = pstmt.executeQuery();

			while(rs.next()){
				password = rs.getString(1);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			DatabaseUtils.closeConnection(con, null, pstmt, rs);
		}
		return password;
	}
	
	public String getEncrypterUserPassword(String userPassword){
		PasswordEncryptor passwordEncryptor = getPasswordEncryptor();
		String newEncryptedPassword = passwordEncryptor.encryptPassword(userPassword);
		return newEncryptedPassword;
	}
}

