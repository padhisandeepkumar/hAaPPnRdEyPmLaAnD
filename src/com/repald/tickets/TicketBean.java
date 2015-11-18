package com.repald.tickets;

import java.io.Serializable;

public class TicketBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ticket_id = null;
	private int employee_id = 0;
	private int customer_id = 0;

	public String getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
}