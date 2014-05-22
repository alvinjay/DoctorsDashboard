/*
 ** Created by Christian Joseph Dalisay
 ** Created on 05/02/14
 ** Edited by Jessie Emmanuel Adante - 05/07/14
 ** Edited by Jose Martin Ipong - 05/07/14
 ** Encounter model class 
 */
package com.example.model;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import android.annotation.SuppressLint;
import android.net.ParseException;


public class Encounter implements Comparable<Encounter> {
	private int 	encounter_id;
	private int 	pid;
	private String 	type_patient;
	private String 	message_complaint;
	private String 	date_encountered;
	private String 	department;
	private String 	is_confidential;
	
	public Encounter(){
		encounter_id 		= 0;
		pid 				= 0;
		type_patient 		= "";
		message_complaint 	= "";
		date_encountered 	= "";
		department 			= "";
		is_confidential 	= "";
	}
	
	public Encounter(int eid, String typepatient, String messagecomplaint, String dateencountered, int patientid)
	{
		encounter_id = eid;
		type_patient = typepatient;
		message_complaint = messagecomplaint;
		date_encountered = dateencountered;
		pid = patientid;
	}
	
	public Encounter(int eid, String typepatient, String messagecomplaint, String dateencountered, int patientid, String department, String isconfidential)
	{
		encounter_id = eid;
		type_patient = typepatient;
		message_complaint = messagecomplaint;
		date_encountered = dateencountered;
		pid = patientid;
		this.department = department;
		//is_confidential = isconfidential;
	}
	
	@SuppressLint("SimpleDateFormat")
	public int compareTo(Encounter encounter){
		Date date1 = new Date();
		Date date2 = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
		    
			ParsePosition position = new ParsePosition(0);
	        date1 = dateFormat.parse(this.date_encountered,position); 
	        date2 = dateFormat.parse(encounter.date_encountered,position);
	        //System.out.println(t); 
	    } catch (ParseException e) { 
	        System.out.println(e.toString()); 
	    }

		
		return date1.compareTo(date2);
	}
	
	public void setEncounterId(int rEncounter_id) {
		this.encounter_id = rEncounter_id;
	}
	
	public void setPid(int rPid) {
		this.pid = rPid;
	}
	
	public void setTypePatient(String rType_Patient) {
		this.type_patient = rType_Patient;
	}
	
	public void setMessageComplaint(String rMessage_Complaint) {
		this.message_complaint = rMessage_Complaint;
	}
	
	public void setDateEncountered(String rDate_Encountered) {
		this.date_encountered = rDate_Encountered;
	}
	
	public void setDepartmentId(String department) {
		this.department = department;
	}
	
	//Getter Methods
	
	public Encounter getEncounter() {
		return this;
	}
	
	public int getEncounterId() {
		return this.encounter_id;
	}
	
	public int getPID() {
		return this.pid;
	}
	
	public String getTypePatient() {
		return this.type_patient;
	}
	
	public String getMessageComplaint() {
		return this.message_complaint;
	}
	
	public String getDateEncountered() {
		return this.date_encountered;
	}
	
	public String getDepartmentId() {
		return this.department;
	}
	
	public String toString()
	{
		//return date_encountered + " " + type_patient + " " + message_complaint;
		return message_complaint + " (" + date_encountered.substring(0,10) + ")";
	}

}
