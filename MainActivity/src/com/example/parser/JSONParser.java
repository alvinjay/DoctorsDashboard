package com.example.parser;

import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class JSONParser {
	
	protected String content;
	public JSONObject json_object;
	protected JSONArray  json_mainNode;
	
	/* Setter Methods */

	protected void setContent(String content) {
		this.content = content;
	}
	
	protected void setJSONResponse() {
		
		try {
			json_object = new JSONObject(content);
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void setJSONMainNode() {
		
		try{ 
			json_mainNode = json_object.optJSONArray("data");
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* Getter Methods */
	public JSONArray getJSONMainNode(){
		return json_mainNode;
	}
}
