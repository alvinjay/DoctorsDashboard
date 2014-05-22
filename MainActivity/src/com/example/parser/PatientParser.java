package com.example.parser;

import java.util.ArrayList;

import com.example.model.Age;
import com.example.model.Patient;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class PatientParser extends JSONParser {
	
	private Patient patient;
	
	private ArrayList<Patient> patients;
	private ArrayList<String> PatientIDs;
	private ArrayList<String> EncounterIDs;
	
	//private HashMap<String, String> data = new HashMap<String, String>();
	private JSONObject json_childNode;
	
	private final String PID = "pid";
	private final String NAME_LAST = "name_last";
	private final String NAME_FIRST = "name_first";
	private final String NAME_MIDDLE = "name_middle";
	private final String DATE_BIRTH = "date_birth";
	private final String SEX = "sex";
	private final String STREET_1 = "Street1";
	private final String CITY = "City";
	private final String PROVINCE = "Province";
	private final String COUNTRY = "Country";
	private final String ZIPCODE = "ZipCode";
	private final String MOTHER_OF_PATIENT = "MotherOfPatient";
	private final String FATHER_OF_PATIENT = "FatherOfPatient";
	private final String SPOUSE_OF_PATIENT = "SpouseOfPatient";
	
	private final int FIRST_OBJECT = 0;
	
	public PatientParser(String content) throws NullPointerException{
		
		patients = new ArrayList<Patient>();
		try {
			json_object = new JSONObject(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
	}
	
	public ArrayList<Patient> getPatients(){
		
		try{
			setJSONMainNode();
			patients = new ArrayList<Patient>();
			JSONArray jason_array = getJSONMainNode();
		
		int lengthJsonArr = jason_array.length();  
   
        for(int i=0; i < lengthJsonArr; i++) 
        {
                         /****** Get Object for each JSON node.***********/
            JSONObject jsonChildNode = jason_array.getJSONObject(i);
			
			
                           
                         /******* Fetch node values **********/
            int    pid       			= Integer.parseInt(jsonChildNode.optString(PID).toString());
			String name_last 			= jsonChildNode.optString(NAME_LAST).toString();
			String name_first 			= jsonChildNode.optString(NAME_FIRST).toString();
			String name_middle 			= jsonChildNode.optString(NAME_MIDDLE).toString();
			String date_birth 			= jsonChildNode.optString(DATE_BIRTH).toString();
			String sex 					= jsonChildNode.optString(SEX).toString();
			String street1 				= jsonChildNode.optString(STREET_1).toString();
			String city 				= jsonChildNode.optString(CITY).toString();
			String province 			= jsonChildNode.optString(PROVINCE).toString();
			//String country 				= jsonChildNode.optString(COUNTRY).toString();
			String zipCode 				= jsonChildNode.optString(ZIPCODE).toString();
			//String mother_of_patient 	= jsonChildNode.optString(MOTHER_OF_PATIENT).toString();
			//String father_of_patient 	= jsonChildNode.optString(FATHER_OF_PATIENT).toString();
			//String spouse_of_patient 	= jsonChildNode.optString(SPOUSE_OF_PATIENT).toString();
			
			Patient patient = new Patient(pid,name_last,name_first,name_middle,sex,date_birth,street1,city,province,zipCode);
			patients.add(patient);   
			//System.out.println(pid);			
        }
		}catch(Exception e){System.out.println(e.toString() + "yu was here");}
        
        return patients;
	}
	
	/* retrieve and parse patients tagged to a doctor */
	public ArrayList<String> getTaggedPID(){
		
		try{
			setJSONMainNode();
			PatientIDs = new ArrayList<String>();
			
			JSONArray jason_array = getJSONMainNode();
		
			int lengthJsonArr = jason_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = jason_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            String    pid       			= jsonChildNode.optString(PID).toString();
				
				
				PatientIDs.add(pid);   
				System.out.println(pid);			
	        }
		}catch(Exception e){
			System.out.println(e.toString() + "alvin was here");
		}
        
        return PatientIDs;
	}
	
	/* retrieve and parse encounters tagged to a doctor */
	public ArrayList<String> getTaggedEID(){
		
		try{
			setJSONMainNode();
			EncounterIDs = new ArrayList<String>();
			
			JSONArray jason_array = getJSONMainNode();
		
			int lengthJsonArr = jason_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = jason_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            String    encounter_id      			= jsonChildNode.optString("encounter_nr").toString();
				
				
				EncounterIDs.add(encounter_id);   
				System.out.println(encounter_id);			
	        }
		}catch(Exception e){
			System.out.println(e.toString() + "alvin was here");
		}
        
        return EncounterIDs;
	}
	
	/* parse for only one patient */
	public Patient getPatient(){
		System.out.println("1");
		
//		System.out.println("1");
//		System.out.println("1");
//		System.out.println("1");
//		System.out.println("1");
		Age patient_age = new Age();
		
		try{
			setJSONMainNode();
			System.out.println("2");
			JSONArray jason_array = getJSONMainNode();
	
	                     /****** Get Object for one JSON node.***********/
	        JSONObject jsonChildNode = jason_array.getJSONObject(FIRST_OBJECT);
	                       
	                     /******* Fetch node values **********/
	        int    pid       			= Integer.parseInt(jsonChildNode.optString(PID).toString());
	        System.out.println("patient id:" + pid);
			String name_last 			= jsonChildNode.optString(NAME_LAST).toString();
			String name_first 			= jsonChildNode.optString(NAME_FIRST).toString();
			String name_middle 			= jsonChildNode.optString(NAME_MIDDLE).toString();
			String date_birth 			= jsonChildNode.optString(DATE_BIRTH).toString();
			int age 					= patient_age.getAge(date_birth);
			String sex 					= jsonChildNode.optString(SEX).toString();
			String street 				= jsonChildNode.optString(STREET_1).toString();
			String city 				= jsonChildNode.optString(CITY).toString();
			String province 			= jsonChildNode.optString(PROVINCE).toString();
			//String country 				= jsonChildNode.optString(COUNTRY).toString();
			String zipCode 				= jsonChildNode.optString(ZIPCODE).toString();
			//String mother_of_patient 	= jsonChildNode.optString(MOTHER_OF_PATIENT).toString();
			//String father_of_patient 	= jsonChildNode.optString(FATHER_OF_PATIENT).toString();
			//String spouse_of_patient 	= jsonChildNode.optString(SPOUSE_OF_PATIENT).toString();
			
			patient = new Patient(pid,name_last,name_first,name_middle,sex,date_birth,age,street,city,province,zipCode);	

		}catch(Exception e){System.out.println(e.toString() + "alvin was here");}
        
        return patient;
	}
	
}
