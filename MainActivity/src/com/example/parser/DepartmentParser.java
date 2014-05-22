/*
 * @author: Justine Yu
 * @date created: 05/14/14
 * @description:
 * 			
 */
package com.example.parser;

import java.util.ArrayList;

import com.example.model.Department;
import com.google.resting.json.JSONArray;
import com.google.resting.json.JSONException;
import com.google.resting.json.JSONObject;

public class DepartmentParser extends JSONParser {
	
	private ArrayList<Department> departments;
	//private HashMap<String, String> data = new HashMap<String, String>();
	//private JSONObject json_childNode;
	private JSONArray json_array;
		
	private final String DEPT_NR 	= "deptnr";
	private final String DEPT_ID 	= "deptid";
	private final String DEPT_NAME 	= "dept_name";

	
	public DepartmentParser(String content) throws NullPointerException{
		
		departments = new ArrayList<Department>();
		try {
			json_array = new JSONArray(content);
		} catch (JSONException e) {
			System.out.println("JSON error");
		}
		//department api returns an array
	}
	
	public ArrayList<Department> getDepartments(){
		
		try{
			departments = new ArrayList<Department>();
		
			int lengthJsonArr = json_array.length();  
	   
	        for(int i=0; i < lengthJsonArr; i++) 
	        {
	                         /****** Get Object for each JSON node.***********/
	            JSONObject jsonChildNode = json_array.getJSONObject(i);
				
				
	                           
	                         /******* Fetch node values **********/
	            int    deptnr       = Integer.parseInt(jsonChildNode.optString(DEPT_NR));
	            String deptid       = jsonChildNode.optString(DEPT_ID).toString();
				String dept_name	= jsonChildNode.optString(DEPT_NAME).toString();
				
				Department department = new Department(deptnr, deptid, dept_name);
				departments.add(department);
				
	        }
		}catch(Exception e){System.out.println(e.toString() + "yu was here");}
        
        return departments;
	}
}
