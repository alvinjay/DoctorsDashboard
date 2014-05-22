/*
 * @author Jose Martin Ipong 5/14/2014
 */
package com.example.parser;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.model.LabRequest;
import com.example.model.LabService;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class LabRequestParser extends JSONParser{
	private ArrayList<LabRequest> requests;
	private ArrayList<LabService> services;
	
	private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String ENCOUNTER_NR 	= "encounter_nr";
	private final String REFNO 	= "refno";
	private final String SERVICE_CODE 	= "service_code";
	private final String SERVICE_NAME = "service_name";
	private final String QUANTITY = "quantity";

	private final int NULL = 0;
	
	

	
	public LabRequestParser(String content){
		
		requests = new ArrayList<LabRequest>();
		try {
			json_array = new JSONArray(content);
			System.out.println("content:"+content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		
	}
	
	public ArrayList<LabRequest> getLabRequests(){
		
		try{
			requests = new ArrayList<LabRequest>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            int    encounter_nr      	= Integer.parseInt(jsonChildNode.optString(ENCOUNTER_NR).toString());
	            int    refno       	= Integer.parseInt(jsonChildNode.optString(REFNO).toString());
				String service_code		= jsonChildNode.optString(SERVICE_CODE).toString();
				String service_name = jsonChildNode.optString(SERVICE_NAME).toString();
				String quantity = jsonChildNode.optString(QUANTITY).toString();
				
				
				LabRequest labrequest = new LabRequest(refno, encounter_nr, service_code, service_name, Integer.parseInt(quantity),"nothing","nothing","nothing");
				requests.add(labrequest);
							
	        }
		}catch(Exception e){System.out.println(e.toString() + " pong was here too :)");}
        
        return requests;
	}
	
	/* retrieve and parse all lab requests */
	public ArrayList<LabRequest> getRequestService(){
		
		int    encounter_id = NULL;
        int    request_nr   = NULL;    
        
        services = new ArrayList<LabService>();
        
		try{			
			int lengthJsonArr = json_array.length();  
			
			System.out.println("Length JSON: "+lengthJsonArr);
			
			/* gets first ref_no for basis if request has been changed */
			int current_request = NULL;
	        
			for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);

	                         /******* Fetch node values **********/
	            encounter_id      	= Integer.parseInt(jsonChildNode.optString("encounter_nr").toString());
	            request_nr       	= Integer.parseInt(jsonChildNode.optString("refno").toString());
	            
	            if(current_request ==  NULL){
	            	current_request = request_nr;
	            	System.out.println(current_request);
	            }
	            
	            /* check if ref_no has changed, save the new request as object and add to ArrayList */
	            if(current_request != request_nr){

	            	/* instantiate a new request and set necessary values */
	            	LabRequest request = new LabRequest();	
	            	
	            	request.setEncounterNumber(encounter_id);
	            	request.setRequestNumber(current_request);
	            	request.setServices(services);
	            	
	            	requests.add(request);
	            	
	            	/* clear all contents of services */
	            	services.clear();
	            	
	            	/* re assign current request */
	            	current_request = request_nr;
	            }

	            /* retrieve lab service info from JSON object */
				String service_code			= jsonChildNode.optString("service_code").toString();
				String service_name 		= jsonChildNode.optString("service_name").toString();
				int quantity 				= Integer.parseInt(jsonChildNode.optString("quantity"));

				/* instantiate a new service and set values */
				LabService service = new LabService();
				
				service.setServiceCode(service_code);
				service.setLabServiceName(service_name);
				service.setQuantity(quantity);
				
				/* add a new service to "services" ArrayList */
				services.add(service);
	        }
			
			LabRequest request = new LabRequest();	

			request.setEncounterNumber(encounter_id);
        	request.setRequestNumber(request_nr);
        	request.setServices(services);
        	
        	requests.add(request);
        	
		}catch(Exception e){
			System.out.println(e.toString() + " pong was here too :)");
		}
        
		return requests;
	}
}
