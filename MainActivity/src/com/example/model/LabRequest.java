/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 ** This class retrieves the laboratory request 
 */

package com.example.model;

import java.util.ArrayList;

public class LabRequest 
{
	private ArrayList<LabService> services;
	
	private int reference_number;
	private int encounter_number;
	private String service_code;
	private String service_name;
	private int quantity;
	
	
	public LabRequest()
	{
		reference_number = 0;
		encounter_number = 0;
		service_code ="";
		service_name = "";
		quantity = 0;
		
		
	}
	
	public LabRequest(int referencenumber, int encounternumber, String servicecode, String servicename, int q, String transactiontype, String requestoption, String discount)
	{
		reference_number = referencenumber;
		encounter_number = encounternumber;
		service_code =servicecode;
		service_name = servicename;
		quantity = q;
		
	}
	
	public void setRequestNumber(int reference_number)
	{
		this.reference_number = reference_number;
	}
	
	public void setEncounterNumber(int encounter_number)
	{
		this.encounter_number = encounter_number;
	}
	
	public void setServiceCode(String service_code)
	{
		this.service_code =service_code;
	}
	
	public void setServiceName(String service_name)
	{
		this.service_name = service_name;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public void setServices(ArrayList<LabService> services){
		System.out.println("Setting services...");
		
		this.services = new ArrayList<LabService>();
		
		for(int i=0;i<services.size();i++){
			this.services.add(services.get(i));
			
			System.out.println("Service Code: "+this.services.get(i).getServiceCode());
//			this.services.get(i).setServiceCode(services.get(i).getServiceCode();
//			this.services.get(i).setLabServiceName(services.get(i).getLabServiceName();
//			this.services.get(i).setQuantity(services.get(i).getServiceCode();
		}
		
	}
	
	public int getRequestNumber()
	{
		return reference_number;
	}
	
	public int getEncounterNumber()
	{
		return encounter_number;
	}
	
	public String getServiceCode()
	{
		return service_code;
	}
	
	public String getServiceName()
	{
		return service_name;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public ArrayList<LabService> getServices(){
		return services;
	}
	

}
