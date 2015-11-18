package com.replad.helper.user.registration;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.replad.utils.StringUtilities;

public class UserDetailsHelper {

	/**
	 * Returns the user contact details.
	 * 
	 * @param request
	 * @return
	 */
	public String getContactDetails(HttpServletRequest request){
		String userContactDetails = null;
		HttpSession session = request.getSession();
		String username = (null!=session.getAttribute("username")? session.getAttribute("username").toString() : null);
		
		Map<String, String> userDtlsMap = new UserDAO().getContactDetails(username);
		userContactDetails = new StringUtilities().getJsonObject(userDtlsMap);
		return userContactDetails;
	}
}
