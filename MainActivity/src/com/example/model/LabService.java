/*
 ** Created by Jessie Emmanuel Adante
 ** Created on 4/24/14
 ** This class retrieves the lab's info
 */

package com.example.model;

public class LabService 
{
	private String service_code;
	private String section_code;
	private String lab_service_name;
	private String lab_section_name;
	private String opd;
	private String ipd;
	int quantity;
	
	public LabService()
	{
		service_code = "";
		section_code = "";
		lab_service_name = "";
		lab_section_name = "";
		opd = "";
		setIpd("");
		quantity = 0;
	}
	
	public LabService(String servicecode, String sectioncode, String labservicename, String labsectionname, String opd, String ipd)
	{
		service_code = servicecode;
		section_code = sectioncode;
		lab_service_name = labservicename;
		lab_section_name = labsectionname;
		this.opd = "";
		this.setIpd("");
	}
	
	public void setServiceCode(String servicecode)
	{
		service_code = servicecode;
	}
	
	public void setSectionCode(String sectioncode)
	{
		section_code = sectioncode;
	}
	
	public void setLabServiceName(String labservicename)
	{
		lab_service_name = labservicename;
	}
	
	public void setLabSectionName(String labsectionname)
	{
		lab_section_name = labsectionname;
	}
	
	public void setOpd(String opd){
		this.opd = opd;
	}
	
	public void setIpd(String ipd) {
		this.ipd = ipd;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	
	public String getIpd(){
		return this.ipd;
	}
	
	public String getServiceCode()
	{
		return service_code;
	}
	
	public String getSectionCode()
	{
		return section_code;
	}
	
	public String getLabServiceName()
	{
		return lab_service_name;
	}
	
	public String getLabSectionName()
	{
		return lab_section_name;
	}

	public String getOpd() {
		return ipd;
	}

	public int getQuantity(){
		return quantity;
	}
	
	public String toString(){
		return lab_service_name;
	}
	
	
}
