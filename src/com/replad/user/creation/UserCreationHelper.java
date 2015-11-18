package com.replad.user.creation;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.PasswordEncryptor;
import org.json.JSONException;
import org.json.JSONObject;

import com.replad.helper.user.registration.UserDAO;
import com.replad.helper.user.registration.UserVO;
import com.replad.security.PasswordValidator;
import com.replad.utils.StringUtilities;

public class UserCreationHelper {
	/**
	 * If the password of the user matches that of database password then return true else false;
	 * @param request
	 * @return
	 */
	public boolean validateUser(HttpServletRequest request){
		boolean flag = false;
		String uname = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
		String pwd = (StringUtilities.isNotEmpty(request.getParameter("password"))?request.getParameter("password"):"");
		
		if(StringUtilities.isEmpty(uname) || StringUtilities.isEmpty(pwd)){
			return false;
		}
//		System.out.println("User --->"+uname+"<----pwd---->"+pwd);

//		boolean validateCaptcha = new UserRegistrationHelper().validateRecaptcha(request);
		
		PasswordEncryptor encryptedPassword = UserPasswordEncryptor.getPasswordEncryptor();
		String dbPassword = new UserPasswordEncryptor().getPasswordFromDB(uname);
		if(null!=dbPassword && dbPassword.trim().length()>0){
			UserDAO dao = new UserDAO();
			String salt = dao.getSaltKey(uname);
			
			PasswordValidator passwordValidator = new PasswordValidator();
			String finalPassword = passwordValidator.getFinalPassword(salt, pwd);
			System.out.println(salt+"<--------->"+pwd+"<----------->"+finalPassword);
			if(encryptedPassword.checkPassword(finalPassword, dbPassword)){
				flag = true;
				request.getSession().setAttribute("username", uname);
				int user_id  = 0;
				if(null!=uname && StringUtilities.isNotEmpty(uname)){
					user_id = new UserDAO().getUserId(uname);
				}
				request.getSession().setAttribute("userid", user_id);
			}
		}
		return flag;
		
		/*StandardStringDigester digester = new PasswordValidator().getDigester();
		String digest = digester.digest(pwd);
		if (digester.matches(pwd, digest)) {
			return true;
		}
		return false;
		*/
		
	}
	
	public boolean activateUser(HttpServletRequest request){
		boolean flag = false;
		String uname = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
		if(StringUtilities.isEmpty(uname)){
			return false;
		}
		int userId = new UserDAO().getUserId(uname);
		flag = new UserDAO().activateUser(userId);
		if(flag){
			request.getSession().setAttribute("username", uname);					
		}
		return flag;
	}
	
	/*public boolean activateUserAccount(HttpServletRequest request){
		String email = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
		String password1 = (StringUtilities.isNotEmpty(request.getParameter("password1"))?request.getParameter("password1"):"");
		String password2 = (StringUtilities.isNotEmpty(request.getParameter("password2"))?request.getParameter("password2"):"");
		try{
			if(StringUtilities.isNotEmpty(email) && StringUtilities.isNotEmpty(password1) && StringUtilities.isNotEmpty(password2)){
				// passwprds are not matching
				if(!StringUtilities.equals(password1, password2)){

				}else{
					UserVO vo = new UserVO();
					UserDAO userDAO = new UserDAO();
					int id = new UserDAO().getUserId(email);
					vo = new UserDAO().getUserData(id);
					boolean isUserNotAvailable = userDAO.isUniqueEmail(vo);// true if the email mot exists else returns false
					if(!isUserNotAvailable){
						Map<String, String> pwdDtls = new PasswordValidator().generateRandomPassword(password1);
						//				String randpassword = pwdDtls.get("RANDPWD"); // random OR default password
						String salt = pwdDtls.get("SALT");  // Salt key
						password1 = pwdDtls.get("FINALPWD"); // final password "random password + salt key"
						System.out.println("user detail reset : password-------["+password1+"]");

						vo.setPassword(password1);
						vo.setEmail(email);
						vo.setActive(1); // on password change active the user and set it to 1
						vo.setSaltkey(salt);
						vo.setPasswordchangedflag(1); // on password change flag set it to 1
						vo.setModifieddate(new DateUtils().getCurrentDate());

						boolean updateStatus = userDAO.updateUser(vo);
						if(updateStatus){
							new SendNewUserDetails().sendNewUserDetails(email, password1, email, "reset");
							return true; // successfully user account activated
						}
					} 
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return false;
	}*/
	
	
	public String getUserDetails(HttpServletRequest request){
		UserDAO dao = new UserDAO();
		String uname = (StringUtilities.isNotEmpty(request.getSession().getAttribute("username").toString())?request.getSession().getAttribute("username").toString():"");
		int userId = dao.getUserId(uname);
		UserVO vo = dao.getUserData(userId);

		JSONObject obj = new JSONObject();
		try {
//			obj.put("username", vo.getUsername());
			obj.put("email", vo.getEmail());
			obj.put("mobile", vo.getContactnumber());
			obj.put("address", vo.getContactaddress());
//			obj.put("pincode", vo.getPincode());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	public int updateUserDetails(HttpServletRequest request){
		UserDAO dao = new UserDAO();
		String uname = (StringUtilities.isNotEmpty(request.getSession().getAttribute("username").toString())?request.getSession().getAttribute("username").toString():"");
		int userId = dao.getUserId(uname);
		UserVO vo = dao.getUserData(userId);
		
		String pwd1 = (StringUtilities.isNotEmpty(request.getParameter("password")) ? request.getParameter("password") : null);
		String email = (StringUtilities.isNotEmpty(request.getParameter("email")) ? request.getParameter("email") : null);
		String mobile = (StringUtilities.isNotEmpty(request.getParameter("mobile")) ? request.getParameter("mobile") : null);
		String address = (StringUtilities.isNotEmpty(request.getParameter("address")) ? request.getParameter("address") : null);
//		String pincode = (StringUtilities.isNotEmpty(request.getParameter("pincode")) ? request.getParameter("pincode") : null);

		if(StringUtilities.isNotEmpty(pwd1)){
			String salt = new PasswordValidator().generateSalt();  // Salt key
			String finalPwd = new PasswordValidator().getFinalEncryptedPassword(salt, pwd1);
			vo.setPassword(finalPwd);
			vo.setSaltkey(salt);
		}
		if(StringUtilities.isNotEmpty(email)){
			vo.setEmail(email);
		}
		if(StringUtilities.isNotEmpty(mobile)){
			vo.setContactnumber(mobile);
		}
		if(StringUtilities.isNotEmpty(address)){
			vo.setContactaddress(address);
		}
//		if(StringUtilities.isNotEmpty(pincode)){
//			vo.setPincode(pincode);
//		}
		boolean flag = dao.updateUser(vo);
		
		return (flag ? 1 : 0);
		
	}
}
