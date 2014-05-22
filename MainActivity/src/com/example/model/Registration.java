/*
 ** Created by Alvin Jay Cosare
 ** Created on 05/7/14
 ** Registration model class 
 */

package com.example.model;

public class Registration{

	private String license_nr;
	private String username;
	private String password;
	private String client_id;
	private String server_id;
	private String base_url;
	
	public Registration() {
		super();

		//initialize variable values
		this.license_nr	= null;
		this.username 	= null;
		this.password 	= null;
		this.client_id	= null;

	}
	
	//Setter Methods	
	
	public void setLicenseNumber(String license_nr) {
		this.license_nr = license_nr;	
	}

	public void setUsername(String username) {
		this.username = username;	
	}

	public void setPassword(String password) {
		this.password = password;	
	}

	public void setClientId(String client_id) {
		this.client_id = client_id;	
	}

	public void setBaseURL(String base_url){
		this.base_url = base_url;
		//Add code for setting base_url for a doctor in mobile DB
	}

//Getter Methods	
	
	public String getLicenseNumber(){
		return license_nr;	
	}

	public String getUsername(){
		return username;	
	}

	public String getPassword(){
		return password;	
	}

	public String getClientId(){
		return client_id;	
	}

	public String getBaseURL(){
		return base_url;
	}
	
}
