package com.replad.sub.work;

import java.io.Serializable;



/**
 * Bean having the requested ticket detail of the user.
 * 
 * @author prasmit
 *
 */
public class UserTicketDetailBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String ticketId = null;
	private int subWorkId = 0;
	private int workId = 0;
	private String createdDate = null;
	private String customerEmail = null;
	private String customerMobile = null;
	private String customerAddress = null;
	private String customerServiceMoreInfo = null;
	private String customerName = null;
	private String rescheduleDate = null;
	private String scheduleDate = null;
	private Double pricePerHour = null;
	private Double totalPrice = null;
	private Double commision = null;
	private int discountPercentage = 0;
	private int totalWorkHour = 0;
	private int employeeId = 0;
	private String employeeName = null;
	private String closedDate = null;
	private String cancelDate = null;
	private String promoCode = null;
	private String ticketStatus = null;
	private String workDesc = null;
	private String subworkDesc = null;
	
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	public String getSubworkDesc() {
		return subworkDesc;
	}
	public void setSubworkDesc(String subworkDesc) {
		this.subworkDesc = subworkDesc;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getCancel_date() {
		return cancelDate;
	}
	public void setCancel_date(String cancel_date) {
		this.cancelDate = cancel_date;
	}
	public int getSubWorkId() {
		return subWorkId;
	}
	public void setSubWorkId(int subWorkId) {
		this.subWorkId = subWorkId;
	}
	public int getWorkId() {
		return workId;
	}
	public void setWorkId(int workId) {
		this.workId = workId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getGuestEmail() {
		return getCustomerEmail();
	}
	public void setGuestEmail(String guestEmail) {
		this.setCustomerEmail(guestEmail);
	}
	public String getRescheduleDate() {
		return rescheduleDate;
	}
	public void setRescheduleDate(String rescheduleDate) {
		this.rescheduleDate = rescheduleDate;
	}
	public Double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public int getTotalWorkHour() {
		return totalWorkHour;
	}
	public void setTotalWorkHour(int totalWorkHour) {
		this.totalWorkHour = totalWorkHour;
	}
	
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerServiceMoreInfo() {
		return customerServiceMoreInfo;
	}
	public void setCustomerServiceMoreInfo(String customerServiceMoreInfo) {
		this.customerServiceMoreInfo = customerServiceMoreInfo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Double getCommision() {
		return commision;
	}
	public void setCommision(Double commision) {
		this.commision = commision;
	}
}

