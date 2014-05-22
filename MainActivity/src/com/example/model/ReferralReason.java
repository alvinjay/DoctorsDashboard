package com.example.model;

public class ReferralReason {
	private int id;
	private String reason;
	
	public ReferralReason(){
		id = 0;
		reason = "";
	}
	
	public ReferralReason(int id, String reason){
		this.id = id;
		this.reason = reason;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getReason(){
		return reason;
	}
	
	public void setReason(String reason){
		this.reason = reason;
	}
	
	public String toString(){
		return reason;
	}
}
