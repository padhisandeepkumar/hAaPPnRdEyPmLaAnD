package com.replad.sub.work;

import java.io.Serializable;

public class SubworkBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private int grpId = 0;
	private String desc = null;
	private int price = 0;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrpId() {
		return grpId;
	}
	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
