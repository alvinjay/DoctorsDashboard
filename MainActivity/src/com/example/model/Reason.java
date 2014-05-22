/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 ** This class retrieves the reason for the appointment
 */

package com.example.model;

public class Reason 
{
	private int reason_referral_number;
	private String reason_statement;
	
	public Reason()
	{
		reason_referral_number = 0;
		reason_statement = "";
	}
	
	public Reason(int reasonreferralnumber, String reasonstatement)
	{
		reason_referral_number = reasonreferralnumber;
		reason_statement = reasonstatement;
	}
	
	private void setReasonReferralNumber(int reasonreferralnumber)
	{
		reason_referral_number = reasonreferralnumber;
	}
	
	private void setReasonStatement(String reasonstatement)
	{
		reason_statement = reasonstatement;
	}
	
	private int getReasonReferralNumber()
	{
		return reason_referral_number;
	}
	
	private String getReasonStatement()
	{
		return reason_statement;
	}
}
