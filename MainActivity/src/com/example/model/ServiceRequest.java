/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 ** This class retrieves the service info
 */

package com.example.model;

public class ServiceRequest 
{
	private int service_code;
	private int request_number;
	private int service_quantity;
	private String transaction_type;
	private String request_option;
	private String discount;
	
	public ServiceRequest()
	{
		service_code = 0;
		request_number = 0;
		service_quantity = 0;
		transaction_type = "";
		request_option = "";
		discount = "";
	}
	
	public ServiceRequest(int servicecode, int requestnumber, int servicequantity, String transaction_type, String request_option, String discount)
	{
		service_code = servicecode;
		request_number = requestnumber;
		service_quantity = servicequantity;
		this.transaction_type = transaction_type;
		this.request_option = request_option;
		this.discount = discount;
	}
	
	public void setServiceCode(int servicecode)
	{
		service_code = servicecode;
	}
	
	public void setRequestNumber(int requestnumber)
	{
		request_number = requestnumber;
	}
	
	public void setServiceQuantity(int servicequantity)
	{
		service_quantity = servicequantity;
	}
	
	public void setTransactionType(String transaction_type){
		this.transaction_type = transaction_type;
	}
	
	public void setRequestOption(String request_option){
		this.request_option = request_option;
	}
	
	public void setDiscount(String discount){
		this.discount = discount;
	}
	
	public int getServiceCode()
	{
		return service_code;
	}
	
	public int getRequestNumber()
	{
		return request_number;
	}
	
	public int getServiceQuantity()
	{
		return service_quantity;
	}
}
