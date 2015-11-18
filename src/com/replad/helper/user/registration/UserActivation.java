package com.replad.helper.user.registration;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.replad.mail.SendNewUserDetails;
import com.replad.security.PasswordValidator;
import com.replad.utils.DateUtils;
import com.replad.utils.StringUtilities;

public class UserActivation {
	private static final Logger log = Logger.getLogger(UserActivation.class.getName());	

	public enum ActivationFor {
	    RESET (2), REGISTRATION (1); 
	    
	    private final int activationCode;

		ActivationFor(int activationCode) {
	        this.activationCode = activationCode;
	    }
	}
	
	
	/**
	 * Sending email to activate user account on register OR reset.
	 * 
	 * @param username
	 * @param activateFor : 1(Reset) / Registration
	 * @return
	 */
	public boolean activateUser(HttpServletRequest request){
		boolean flag = false;
		String uname = (StringUtilities.isNotEmpty(request.getParameter("username"))?request.getParameter("username"):"");
		int activationCode = Integer.parseInt(StringUtilities.isNotEmpty(request.getParameter("activationCode"))?request.getParameter("activationCode"):"0");
		
		String password1 = (StringUtilities.isNotEmpty(request.getParameter("password1"))?request.getParameter("password1"):"");
		String password2 = (StringUtilities.isNotEmpty(request.getParameter("password2"))?request.getParameter("password2"):"");
		
		if(StringUtilities.isNotEmpty(uname) && activationCode>0 && StringUtilities.equals(password1, password2)){
			String email = uname;
			String password = password1;
			
			try {
				UserVO vo = new UserVO();
				UserDAO userDAO = new UserDAO();
				int id = new UserDAO().getUserId(email);
				vo = new UserDAO().getUserData(id);
				flag = userDAO.isUniqueEmail(vo);// true if the email mot exists else returns false
				if(!flag){
					Map<String, String> pwdDtls = new PasswordValidator().generateRandomPassword(password);
					String salt = pwdDtls.get("SALT");  // Salt key
					String finalPassword = pwdDtls.get("FINALPWD"); // final password "random password + salt key"
					log.info("user detail reset : password-------["+password+"]");

					vo.setPassword(finalPassword);
					vo.setEmail(email);
					vo.setActive(1); // on password change active the user and set it to 1
					vo.setSaltkey(salt);
					vo.setPasswordchangedflag(1); // on password change flag set it to 1
					vo.setModifieddate(new DateUtils().getCurrentDate());

					flag = userDAO.updateUser(vo);
					if(flag){
						flag = new SendNewUserDetails().sendNewUserDetails(email, email, activationCode);
					}
				} 
			}catch (Exception e) {
				flag = false; // failed due to some other reason
				e.printStackTrace();
			}
		}
		return flag;
	}
}
