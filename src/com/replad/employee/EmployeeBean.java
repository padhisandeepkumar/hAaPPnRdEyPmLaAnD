package com.replad.employee;

import java.io.Serializable;
import java.sql.Timestamp;

public class EmployeeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private String first_name = null;
	private String middle_name = null;
	private String last_name = null;
	private String mobile_primary = null;
	private String mobile_secondary = null;
	private Timestamp joining_date = null;
	private Timestamp releaving_date = null;
	private int id_proof = 0;
	private int address_proof = 0;
	private int employement_type = 0;
	private int employment_grade = 0;
	private int verification_done = 0;
	private int verification_agency = 0;
	private int active = 0;
	private int category_id = 0;
	private String category = null;
	private String emplyment_type_desc = null;
	private String id_proof_desc = null;
	private String address_proof_desc = null;
	private String grade_desc = null;
	private String verifying_agency_desc = null;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getMobile_primary() {
		return mobile_primary;
	}
	public void setMobile_primary(String mobile_primary) {
		this.mobile_primary = mobile_primary;
	}
	public String getMobile_secondary() {
		return mobile_secondary;
	}
	public void setMobile_secondary(String mobile_secondary) {
		this.mobile_secondary = mobile_secondary;
	}
	public Timestamp getJoining_date() {
		return joining_date;
	}
	public void setJoining_date(Timestamp joining_date) {
		this.joining_date = joining_date;
	}
	public Timestamp getReleaving_date() {
		return releaving_date;
	}
	public void setReleaving_date(Timestamp releaving_date) {
		this.releaving_date = releaving_date;
	}
	public int getId_proof() {
		return id_proof;
	}
	public void setId_proof(int id_proof) {
		this.id_proof = id_proof;
	}
	public int getAddress_proof() {
		return address_proof;
	}
	public void setAddress_proof(int address_proof) {
		this.address_proof = address_proof;
	}
	public int getEmployement_type() {
		return employement_type;
	}
	public void setEmployement_type(int employement_type) {
		this.employement_type = employement_type;
	}
	public int getEmployment_grade() {
		return employment_grade;
	}
	public void setEmployment_grade(int employment_grade) {
		this.employment_grade = employment_grade;
	}
	public int getVerification_done() {
		return verification_done;
	}
	public void setVerification_done(int verification_done) {
		this.verification_done = verification_done;
	}
	public int getVerification_agency() {
		return verification_agency;
	}
	public void setVerification_agency(int verification_agency) {
		this.verification_agency = verification_agency;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEmplyment_type_desc() {
		return emplyment_type_desc;
	}
	public void setEmplyment_type_desc(String emplyment_type_desc) {
		this.emplyment_type_desc = emplyment_type_desc;
	}
	public String getId_proof_desc() {
		return id_proof_desc;
	}
	public void setId_proof_desc(String id_proof_desc) {
		this.id_proof_desc = id_proof_desc;
	}
	public String getAddress_proof_desc() {
		return address_proof_desc;
	}
	public void setAddress_proof_desc(String address_proof_desc) {
		this.address_proof_desc = address_proof_desc;
	}
	public String getGrade_desc() {
		return grade_desc;
	}
	public void setGrade_desc(String grade_desc) {
		this.grade_desc = grade_desc;
	}
	public String getVerifying_agency_desc() {
		return verifying_agency_desc;
	}
	public void setVerifying_agency_desc(String verifying_agency_desc) {
		this.verifying_agency_desc = verifying_agency_desc;
	}
}
