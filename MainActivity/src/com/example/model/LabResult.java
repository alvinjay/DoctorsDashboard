/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 ** This class retrieves the lab results, date received, test name, messages and pathogen
 */

package com.example.model;

public class LabResult 
{
	private int result_number;
	private int request_number;
	private String test_name;
	private String date_received;
	private String pathogen_name;
	
	public LabResult()
	{
		result_number = 0;
		request_number = 0;
		test_name = "";
		date_received = "";
		pathogen_name = "";
	}
	
	public LabResult(int resultnumber, int requestnumber, String testname, 
			String datereceived, String pathogenname)
	{
		result_number = resultnumber;
		request_number = requestnumber;
		test_name = testname;
		date_received = datereceived;
		pathogen_name = pathogenname;
	}
	
	public void setResultNumber(int resultnumber)
	{
		result_number = resultnumber;
	}
	
	public void setRequestNumber(int requestnumber)
	{
		request_number = requestnumber;
	}
	
	public void setTestName(String testname)
	{
		test_name = testname;
	}
	
	public void setDateReceived(String datereceived)
	{
		date_received = datereceived;
	}
	
	public void setPathogenName(String pathogenname)
	{
		pathogen_name = pathogenname;
	}
	
	public int getResultNumber()
	{
		return result_number;
	}
	
	public int getRequestNUmber()
	{
		return request_number;
	}
	
	public String getTestName()
	{
		return test_name;
	}
	
	public String getDateReceived()
	{
		return date_received;
	}
	
	public String getPathogenName()
	{
		return pathogen_name;
	}
	
}
