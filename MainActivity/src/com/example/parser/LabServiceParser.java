/*
 * @author Jose Martin Ipong 5/14/2014
 */
package com.example.parser;

import java.util.ArrayList;

import com.example.model.LabRequest;
import com.example.model.LabService;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class LabServiceParser extends JSONParser{
	private ArrayList<LabService> labservices;
	//private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String CODE 	= "code";
	private final String TEST 	= "test";
	private final String SECTION_CODE 	= "section_code";
	private final String SECTION = "section";
	private final String OPD = "opd";
	private final String IPD = "ipd";
	
	

	
	public LabServiceParser(String content) throws NullPointerException{
		
		labservices = new ArrayList<LabService>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		
	}
	
	public ArrayList<LabService> getLabServices(){
		
		try{
			labservices = new ArrayList<LabService>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            String    code      	= jsonChildNode.optString(CODE).toString();
	            String    test       	= jsonChildNode.optString(TEST).toString();
				String section_code		= jsonChildNode.optString(SECTION_CODE).toString();
				String service = jsonChildNode.optString(SECTION).toString();
				String opd = jsonChildNode.optString(OPD).toString();
				String ipd = jsonChildNode.optString(IPD).toString();
				
				LabService labservice = new LabService(code, test, section_code, service, opd, ipd);
				
				labservices.add(labservice);
			
							
	        }
		}catch(Exception e){System.out.println(e.toString() + " pong was here :)");}
        
        return labservices;
	}
}