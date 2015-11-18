package com.replad.helper.user.registration;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class UserVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String password = null;
	private String contactnumber = null;
	private String contactaddress = null;
	private String email = null;
	private int active = 0;
	private String saltkey = null;
	private int passwordchangedflag = 0;
	private Timestamp createddate = new Timestamp(new Date().getTime());
	private Timestamp modifieddate = new Timestamp(new Date().getTime());
	private Timestamp lastloggedin = new Timestamp(new Date().getTime());
	private int loggedincount = 0;
	private String userName = null;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
	public String getContactaddress() {
		return contactaddress;
	}
	public void setContactaddress(String contactaddress) {
		this.contactaddress = contactaddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getSaltkey() {
		return saltkey;
	}
	public void setSaltkey(String saltkey) {
		this.saltkey = saltkey;
	}
	public int getPasswordchangedflag() {
		return passwordchangedflag;
	}
	public void setPasswordchangedflag(int passwordchangedflag) {
		this.passwordchangedflag = passwordchangedflag;
	}
	public Timestamp getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	public Timestamp getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}
	public Timestamp getLastloggedin() {
		return lastloggedin;
	}
	public void setLastloggedin(Timestamp lastloggedin) {
		this.lastloggedin = lastloggedin;
	}
	public int getLoggedincount() {
		return loggedincount;
	}
	public void setLoggedincount(int loggedincount) {
		this.loggedincount = loggedincount;
	}
}
