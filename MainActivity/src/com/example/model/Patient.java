/*
 ** Created by Christian Joseph Dalisay
 ** Created on 05/02/14
 ** Edited by Jose Martin Ipong on 05/07/14, added more patient attributes
 ** Encounter model class 
 */

package com.example.model;

public class Patient {

	private int patient_pid;
	private int patient_age;
	private String patient_name_last;
	private String patient_name_first;
	private String patient_name_middle;
	private String patient_sex;
	private String patient_date_birth;
	private String patient_street;
	private String patient_city;
	private String patient_province;
	private String patient_zipcode;
	private boolean patient_history_drinking;
	private boolean patient_history_smoking;
	
	
	public Patient() {
		this.patient_pid = -1;
		this.patient_name_last = null;
		this.patient_name_first = null;
		this.patient_name_middle = null;
		this.patient_sex = null;
		this.patient_date_birth = null;
		this.patient_street = null;
		this.patient_city = null;
		this.patient_province = null;
		this.patient_zipcode = null;
		this.patient_history_drinking = false;
		this.patient_history_smoking = false;
		this.patient_age = -1;
	}
	
	public Patient(int pid, String lastname, String firstname, String middlename, String sex, String birthdate, int age, String street, String city, String province, String zipcode){
		patient_pid = pid;
		patient_name_last = lastname;
		patient_name_first = firstname;
		patient_name_middle = middlename;
		patient_sex = sex;
		patient_date_birth = birthdate;
		patient_age = age;
		patient_street = street;
		patient_city = city;
		patient_province = province;
		patient_zipcode = zipcode;
	}
	
	public Patient(int pid, String lastname, String firstname, String middlename, String sex, String birthdate, String street, String city, String province, String zipcode){
		patient_pid = pid;
		patient_name_last = lastname;
		patient_name_first = firstname;
		patient_name_middle = middlename;
		patient_sex = sex;
		patient_date_birth = birthdate;
		patient_street = street;
		patient_city = city;
		patient_province = province;
		patient_zipcode = zipcode;
	}
	
	public Patient(int pid, String lastname, String firstname, String middlename, String sex, int age, String street, String city, String province, String zipcode){
		patient_pid = pid;
		patient_name_last = lastname;
		patient_name_first = firstname;
		patient_name_middle = middlename;
		patient_sex = sex;
		patient_age = age;
		patient_street = street;
		patient_city = city;
		patient_province = province;
		patient_zipcode = zipcode;
		
	}
	
	public Patient(int pid, String lastname, String firstname, String middlename, String sex, int age, String street, String city, String province, String zipcode, boolean drinkinghistory, boolean smokinghistory){
		patient_pid = pid;
		patient_name_last = lastname;
		patient_name_first = firstname;
		patient_name_middle = middlename;
		patient_sex = sex;
		patient_age = age;
		patient_street = street;
		patient_city = city;
		patient_province = province;
		patient_zipcode = zipcode;
		patient_history_drinking = drinkinghistory;
		patient_history_smoking = smokinghistory;
	}
	
	public void setPatient(Patient rPatient) {
		setPID(rPatient.patient_pid);
		setNameLast(rPatient.patient_name_last);
		setNameFirst(rPatient.patient_name_first);
	}
	
	public void setPID (int pid) {
		this.patient_pid = pid;
	}
	
	public void setNameLast (String name_last) {
		this.patient_name_last = name_last;
	}
	
	public void setNameFirst (String name_first) {
		this.patient_name_first = name_first;
	}
	
	public void setAge(int patientage){
		patient_age = patientage;
	}
	//get methods
	public Patient getPatient() {
		return this;
	}
	
	public int getPID() {
		return this.patient_pid;
	}
	
	public String getNameLast() {
		return this.patient_name_last;
	}
	
	public String getNameFirst() {
		return this.patient_name_first;
	}
	
	public String getNameMiddle(){
		return this.patient_name_middle;
	}
	
	public String getSex(){
		return this.patient_sex;
	}
	
	public String getBirthdate(){
		return this.patient_date_birth;
	}
	
	public String getFullName()
	{
		return this.patient_name_last + ", " + this.patient_name_first + " " + this.patient_name_middle;
	}
	
	public String getAddress(){
		return this.patient_street + ", " + this.patient_city + " " + this.patient_province; 
	}
	
	public String getStreet(){
		return this.patient_street;
	}
	
	public String getCity(){
		return this.patient_city;
	}
	
	public String getProvince(){
		return this.patient_province;
	}

	public String getZipCode(){
		return this.patient_zipcode;
	}
	
	public int getAge()
	{
		return patient_age;
	}
	
	
	public String toString(){
		return patient_name_first + " " + patient_name_last;
	}
	
	
}


