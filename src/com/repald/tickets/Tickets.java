package com.repald.tickets;

import com.replad.utils.RandomString;

public class Tickets {

	private int ticketLength = 0;
	public Tickets(int ticketLength){
		this.ticketLength = ticketLength;
	}
	public String getTicketId(){
		String tickedId = getNanoTime()+new RandomString(ticketLength-3).generateRandomString();
		return tickedId;
	}

	/**
	 * Returns the last 3 digits of the nano seconds of the current system time.
	 *  
	 * @return
	 */
	public String getNanoTime() {
		String str = System.nanoTime()+"";
		String last3 = str == null || str.length() < 3 ? str : str.substring(str.length() - 3);
		return last3;
	}
}
