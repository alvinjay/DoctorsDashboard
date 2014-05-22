package com.example.parser;

import java.util.HashMap;

import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class TokenParser extends JSONParser {
	
	private HashMap<String, String> data = new HashMap<String, String>();
	private JSONObject json_childNode;
	
	public TokenParser(String content) throws NullPointerException{
		
		try {
			json_object = new JSONObject(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
	}
	
	public boolean getChild(){
		
		try{
			json_childNode = (JSONObject) json_object.get("data");		
			return true;
		} catch(Exception e){
			System.out.println("Null error");
			return false;
		} 
	}

	public HashMap<String, String> extractData(){ 
		System.out.println("saving data to HashMap..");
		
        data.put("auth_token", json_childNode.optString("auth_token"));        
        data.put("access_token", json_childNode.optString("access_token"));
        data.put("name_first", json_childNode.optString("name_first").toString());
        data.put("name_middle", json_childNode.optString("name_middle").toString());
        data.put("name_last", json_childNode.optString("name_last").toString());
        data.put("sex", json_childNode.optString("sex").toString());
        data.put("location_nr", json_childNode.optString("location_nr"));
        data.put("birth_date", json_childNode.optString("birth_date"));
        data.put("personell_nr", json_childNode.optString("personell_nr"));
        
        return data;
	}
}
