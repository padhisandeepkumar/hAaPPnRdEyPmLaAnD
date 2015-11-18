package com.replad.landingPage;

import java.util.Map;

import com.replad.sub.work.UserTicketDbUtils;

public class LandingPageHelper {

	public Map<String, String> getLandingPageCountDetails() {
		UserTicketDbUtils  ticketDbUtils = new UserTicketDbUtils();
		Map<String, String> allCountDetails = ticketDbUtils.getAllCoustmerCountDetails();
		return allCountDetails;
	}
}
