package com.replad.controllers;

public enum ActionHandlerEnum {
	getSubWorks,
	checkUser4Duplicate,
	registerUser,
	resetUserDetails,
	login,
	adminLogin,
//	validateLogin,
	forgotPassword,
	autoCompleteSubworks,
	getWorkGroupDetails,
	logout,
	getUserDetails,
	updateUserSettings,
	renderTicketDetailGridData,
	serveWithRegister,
	serveWithSignin,
	removeSessionAttributes,
	setSelectedServiceDetails,
	createOrder,
	getContactDetails,
	getUserTickets,
	populateComboData,
	renderEmployeeDetailGridData,
	renderFilteredEmployeeDetailGridData,
	saveEmployeeData,
	deleteEmployee,
	activateUser,
	populateEmployeDataToEdit,
	renderSubWorkDetails,
	getPendingServiceDetails,
	updateFeedback,
	cancelService,
	rescheduleService,
	populateSubworksCombo,
	guestServiceCreate,
	getAllTickets,
	getWorkDetails,
	downloadServiceDetails,
	validateCoupon,
	validateRegisterUserCoupon,
	submitFeedback,
	renderWorkDetailGridData,
	populateWorksCombo,
	deleteWorkDetails,
	createModifyService,
	populateTicketToEdit,
	cancelTicket,
	deleteTicket,
	updateTicketDetails,
	closeTicket;
	
	public static ActionHandlerEnum getActionName(String str) {
		try {
			return valueOf(str);
		} catch (Exception ex) {
		}
		return null;
	}
}
