/*
 * @author Jose Martin Ipong 5/14/2014
 */
package com.example.parser;

import java.util.ArrayList;

import com.example.model.Encounter;
import com.example.model.Patient;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class EncounterParser extends JSONParser{
	private ArrayList<Encounter> encounters;
	//private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String CASE_NR 	= "case_nr";
	private final String PID 	= "pid";
	private final String NAME_LAST 	= "name_last";
	private final String NAME_MIDDLE = "name_middle";
	private final String NAME_FIRST = "name_first";
	private final String DEPARTMENT = "department";
	private final String ENCOUNTER_DATE = "encounter_date";
	private final String PATIENT_TYPE = "patient_type";
	private final String OFFICIAL_RECEIPT_NR = "official_receipt_nr";
	private final String CHIEF_COMPLAINT = "chief_complaint";
	private final String IS_CONFIDENTIAL = "is_confidential";
	

	
	public EncounterParser(String content) throws NullPointerException{
		
		encounters = new ArrayList<Encounter>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		//department api returns an array
	}
	
	public ArrayList<Encounter> getEncounters(){
		
		try{
			encounters = new ArrayList<Encounter>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            int    case_nr      	= Integer.parseInt(jsonChildNode.optString(CASE_NR).toString());
	            int    pid       	= Integer.parseInt(jsonChildNode.optString(PID).toString());
	            System.out.println(pid+"");
				String name_last		= jsonChildNode.optString(NAME_LAST).toString();
				String name_first = jsonChildNode.optString(NAME_FIRST).toString();
				String name_middle = jsonChildNode.optString(NAME_MIDDLE).toString();
				String department = jsonChildNode.optString(DEPARTMENT).toString();
				String encounter_date = jsonChildNode.optString(ENCOUNTER_DATE).toString();
				String patient_type = jsonChildNode.optString(PATIENT_TYPE).toString();
				String official_receipt_nr = jsonChildNode.optString(OFFICIAL_RECEIPT_NR).toString();
				String chief_complaint = jsonChildNode.optString(CHIEF_COMPLAINT).toString();
				String is_confidential = jsonChildNode.optString(IS_CONFIDENTIAL).toString();
				
				Encounter encounter = new Encounter(case_nr, patient_type, chief_complaint, encounter_date, pid, department, is_confidential);
				encounters.add(encounter);
				//System.out.println(pid);			
	        }
		}catch(Exception e){System.out.println(e.toString() + "pong was here");}
        
        return encounters;
	}
	
	public ArrayList<String> getPatientIds(){
		
		/* store all unique patient ids here*/
		ArrayList<String> pids = new ArrayList<String>();

		try{
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
	                           
	                         /******* Fetch node values **********/
	            String    pid       	= jsonChildNode.optString(PID).toString();
				
	            /* check if arraylist already contains the pid */
	            if(!pids.contains(pid)){
	            	/* insert if no duplication */
	            	pids.add(pid);
	            }
	        }
	        
		}catch(Exception e){
			System.out.println(e.toString() + "	patient_id error ");
		}
        
        return pids;
	}
	
}
