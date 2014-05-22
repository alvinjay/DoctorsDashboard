/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/17/14
 * @Description - this class handles the manual syncing
 * 				  of the application
 */

package com.example.android.navigationdrawerexample;

import java.sql.Date;
import java.util.ArrayList;

import com.example.database.EncounterAdapter;
import com.example.database.PatientAdapter;
import com.example.model.Preferences;
import com.example.model.Rest;

import android.os.Bundle;

public class ManualSync extends InitialActivity {

	private ArrayList<Integer> pids;
	private int personnel = Preferences.getPersonnelPreference(this);
	private String url = Preferences.getBaseURL(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PatientAdapter pat = new PatientAdapter(this);
		pids = pat.getPids(personnel);
		
		getLatestEncounters();
		
	}
	
	/* Retrieves the latest encounters made */
	public void getLatestEncounters() {
		
		/* gets the latest encounter from each patient */
		EncounterAdapter enc = new EncounterAdapter(this);
		ArrayList<Date> enc_date = new ArrayList<Date>();
		for(int i = 0 ; i < pids.size(); i++) {
			//enc_date.get(Date.parse()enc.getLatestDateEncountered(pids.get(i),personnel));
			//executeREST(enc.getLatestDateEncountered(pids.get(i),personnel),url + "/","GET");
		}
		
		
	}
	
	public void executeREST(Date encountered, String new_url, String type){
		if(isNetworkAvailable()){
			Rest rest = new Rest(type);
			logMessage(new_url);
			rest.setURL(new_url);
			rest.execute();
			while(rest.getContent() == null){}
			 
			if(rest.getResult()){
				String content = rest.getContent();
				System.out.println(content);
				
				//DepartmentParser department_parser = new DepartmentParser(content);
				//departments = department_parser.getDepartments();
				//da.insertDepartments(departments);
				//da.getDepartments();
			}
		}
	}
}
