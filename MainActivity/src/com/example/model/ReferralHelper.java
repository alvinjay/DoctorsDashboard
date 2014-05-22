package com.example.model;

public class ReferralHelper {
	private String reason;
	private String dept;
	private String date_referred;
	
	public ReferralHelper() {
		this.reason = null;
		this.dept = null;
		this.date_referred = null;
	}
	
	public ReferralHelper (String reason, String dept, String date_reffered) {
		this.reason = reason;
		this.dept = dept;
		this.date_referred = date_reffered;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setDate_referred(String date_referred) {
		this.date_referred = date_referred;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String getDept() {
		return dept;
	}
	
	public String getDate_referred() {
		return date_referred;
	}
	
	public String toString() {
		return this.dept + " (" + date_referred.substring(0,10) + ")\n" + reason;   
	}
}


