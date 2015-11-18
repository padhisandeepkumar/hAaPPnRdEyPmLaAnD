package com.replad.helper.user.registration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.replad.mail.SendNewUserDetails;
import com.replad.utils.DateUtils;

public class UserRegistrationHelper {
    
	/**
	 * Add new user details. 
	 * 
	 * @param request
	 * @return 
	 * -1	:	if user is not unique.
	 * -2	:	if user creation failed. 
	 * -3	: 	email sent failed. 
	 * -4	: 	email id already registered.
	 * 0	:	user creation failed due to some reason.
	 * 1	:	successfully user created.
	 */
	public int createUser(HttpServletRequest request){
		int status = 0;
		String email = (StringUtils.isNotEmpty(request.getParameter("email")) ? request.getParameter("email").trim() : null);
		String userName = (StringUtils.isNotEmpty(request.getParameter("newUserName")) ? request.getParameter("newUserName").trim() : null);
		String number = (StringUtils.isNotEmpty(request.getParameter("contactNum")) ? request.getParameter("contactNum").trim() : null);
		String address = (StringUtils.isNotEmpty(request.getParameter("address")) ? request.getParameter("address").trim() : null);
		
		if(StringUtils.isNotBlank(email) && StringUtils.isNotBlank(number) && StringUtils.isNotBlank(address)){
			UserVO vo = new UserVO();
			UserDAO userDAO = new UserDAO();
			try {
				vo.setEmail(email);
				boolean isUniqueEmailid = userDAO.isUniqueEmail(vo);
				if(!isUniqueEmailid){
					return -4; // user name already exists / email id already registered
				}
				vo.setContactnumber(number);
				vo.setContactaddress(address);
				vo.setEmail(email);
				vo.setActive(0); // on password change active the user and set it to 1
				vo.setPasswordchangedflag(0); // on password change flag set it to 1
				vo.setCreateddate(new DateUtils().getCurrentDate());
				vo.setModifieddate(new DateUtils().getCurrentDate());
				vo.setLoggedincount(0);
				vo.setUserName(userName);
				
				status = userDAO.addUser(vo);
			}catch (Exception e) {
				status = -2; // failed due to some other reason
				e.printStackTrace();
			}
		}
		return status;
	}
	
	public int resetUser(HttpServletRequest request){
		int status = 0;
		String email = (StringUtils.isNotEmpty(request.getParameter("email")) ? request.getParameter("email").trim() : null);
		try {
			int userId = new UserDAO().getUserId(email);
			new UserDAO().deactivateUser(userId);
			boolean flag = new SendNewUserDetails().sendNewUserDetails(email, email, 2);
			if(flag){
				status = 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
	}
}
