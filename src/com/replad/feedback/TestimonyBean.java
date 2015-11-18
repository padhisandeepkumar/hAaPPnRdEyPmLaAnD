package com.replad.feedback;

import java.io.Serializable;

public class TestimonyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customerComment = null;
	private String customerSignature = null;
	private String image = null;
	
	public String getCustomerComment() {
		return customerComment;
	}
	public void setCustomerComment(String customerComment) {
		this.customerComment = customerComment;
	}
	public String getCustomerSignature() {
		return customerSignature;
	}
	public void setCustomerSignature(String customerSignature) {
		this.customerSignature = customerSignature;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
