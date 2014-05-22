package com.example.model;

public class Referral 
{
	private int referral_id;
	private int encounter_number;
	private int referred_department_number;
	private int reason_referral_number;
	
	public Referral()
	{
		referral_id = 0;
		encounter_number = 0;
		referred_department_number = 0;
		reason_referral_number = 0;
	}
	
	public Referral(int referralid, int encounternumber, 
			int referreddepartmentnumber,int reasonreferralnumber)
	{
		referral_id = referralid;
		encounter_number = encounternumber;
		referred_department_number = referreddepartmentnumber;
		reason_referral_number = reasonreferralnumber;
	}
	
	public void setReferralId(int referralid)
	{
		referral_id = referralid;
	}
	
	public void setEncounterNumber(int encounternumber)
	{
		encounter_number = encounternumber;
	}
	
	public void setReferredDepartmentNumber(int referreddepartmentnumber)
	{
		referred_department_number = referreddepartmentnumber;
	}
	
	public void setReasonReferralNumber(int reasonreferralnumber)
	{
		reason_referral_number = reasonreferralnumber;
	}
	
	public int getReferralId()
	{
		return referral_id;
	}
	
	public int getEncounterNumber()
	{
		return encounter_number;
	}
	
	public int getReferredDepartmentNumber()
	{
		return referred_department_number;
	}
	
	public int getReasonReferralNumber()
	{
		return reason_referral_number;
	}
}
