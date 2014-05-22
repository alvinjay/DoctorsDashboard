package com.example.parser;

import java.util.ArrayList;

import com.example.model.Encounter;
import com.example.model.ReferralReason;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class ReferralReasonParser extends JSONParser {
	
	private ArrayList<ReferralReason> reasons;
	//private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String ID 		= "id";
	private final String REASON 	= "reason";

	public ReferralReasonParser(String content) throws NullPointerException{
		
		reasons = new ArrayList<ReferralReason>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		//department api returns an array
	}
	
	public ArrayList<ReferralReason> getReasons(){
		
		try{
			reasons = new ArrayList<ReferralReason>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            int    id      	= Integer.parseInt(jsonChildNode.optString(ID).toString());
	            String reason = jsonChildNode.optString(REASON).toString();
	            
				ReferralReason referralReason = new ReferralReason(id,reason);
				reasons.add(referralReason);
				//System.out.println(pid);			
	        }
		}catch(Exception e){System.out.println(e.toString() + "Yu was here");}
        
        return reasons;
	}

}
