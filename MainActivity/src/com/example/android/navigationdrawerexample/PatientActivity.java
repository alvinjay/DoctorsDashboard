/*
 * Created by Eclipse
 * Edited by Jose Martin Ipong on 5/7/2014, added event handler for search button
 * Edited by Alvin jay Cosare on 5/14/2014, added method to automatically get latest encounter of patient
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.database.DatabaseAdapter;
import com.example.model.Age;
import com.example.model.Patient;
import com.example.model.Preferences;
import com.example.model.Rest;
import com.example.parser.EncounterParser;
import com.example.parser.PatientParser;

public class PatientActivity extends BaseActivity {
	
	private Patient patient;
	private ArrayList<Patient> patients;
	
	
	private String patients_url = "/patient/show/";

	//private final String patients_url = "http://121.97.45.242/segservice/patient/show/";
	private final String encounters_url = "/encounter/show/";
	
	private int patient_id;
	
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_patient);
		super.onCreate(savedInstanceState);
		
		context = this;
		
		EditText edittext = (EditText) findViewById(R.id.searchView1);
		// Initialize patient list
		patients = new ArrayList<Patient>();
		if(isNetworkAvailable()){

			retrieveDepartmentPatients();
		} 
		else{
		
			DatabaseAdapter adapter = new DatabaseAdapter(getApplicationContext());
			patients = adapter.searchPatient("");
		}
		
		ListView listview = (ListView) findViewById(R.id.servicesList);
		ArrayAdapter<Patient> arrayAdapter = new ArrayAdapter<Patient>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, patients){
        	//method to override the getView method of ArrayAdapter, this changes the color of the text view
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
        		View view = super.getView(position, convertView, parent);
        	    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        	    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
        	    text1.setTextColor(Color.BLACK);
        	    text2.setTextColor(Color.BLACK);
        	    String displayname = "";
        	    String displayinfo = "";
        	    Patient patient = patients.get(position);
        	  
        	    displayname = patient.getNameLast() + ", " + patient.getNameFirst();
        	    Age age = new Age();
        	    if(patient.getSex().equals("M") || patient.getSex().equals("m")){
        	    	displayinfo = displayinfo + "Gender: Male";
        	    	//text1.setBackgroundColor(Color.parseColor("#4C8BFF"));
        	    	//text2.setBackgroundColor(Color.parseColor("#4C8BFF"));
        	    }
        	    else if(patient.getSex().equals("F") || patient.getSex().equals("f")){
        	    	displayinfo = displayinfo + "Gender: Female";
        	    	//text1.setBackgroundColor(Color.parseColor("#FF99CC"));
        	    	//text2.setBackgroundColor(Color.parseColor("#FF99CC"));
        	    }
        	   
        	    try{
					displayinfo = "HRN: " + Integer.toString(patient.getPID()) +
							", " + displayinfo + ", Age: " + age.getAge(patient.getBirthdate().substring(0,10));
				} catch (java.text.ParseException e) {
					logMessage(e.toString());
        	    }

        	    	
        	    text1.setText(displayname);
        	    text2.setText(displayinfo);
        	    
        	  
        	    return view;
        	  }
        	};
        	
        listview.setAdapter(arrayAdapter); 
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				/* getting values from selected ListItem */
				TextView text = (TextView) view.findViewById(android.R.id.text1);
				
				// Starting single contact activity
				patient = patients.get(position);
				patient_id = patient.getPID();

				
				/* saves the patient_id and encounter_id to be passed to the next activity */
				extras = new Bundle();
				extras.putInt("EXTRA_PATIENT_ID", patient_id);
				
				/* sets current patient by saving the pid of the selected patient in preferences */
				Preferences.setPatientId(context, patient_id); /* key = "key_patient_id" */
				
				/* start next activity Patient Info (2nd Page) */
				intent = new Intent(getApplicationContext(), PatientInfoActivity.class);
				intent.putExtras(extras);
				
				startActivity(intent);
			}
		});
   
		//event handler for pressing the search button on soft keyboard
		edittext.setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        boolean handled = false;
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		        	EditText edittext = (EditText) findViewById(R.id.searchView1);
		        	
		        	String searchtext = edittext.getText().toString();
		        	
		            DatabaseAdapter adapter = new DatabaseAdapter(getApplicationContext());
		            patients = new ArrayList<Patient>();
		            
		            StringTokenizer t = new StringTokenizer(searchtext, ",");
		            String last = "";
		            String first = "%";
		            if(t.countTokens() == 1){
		            	last = t.nextToken();
		            }
		            else{
		            	last = t.nextToken();
		            	first = t.nextToken();
		            }
		            try{
		            	
		            	if(isNetworkAvailable()){ //checks if device is connected to the internet
		          
		        			Rest rest = new Rest("GET", context, "Searching..");
		        			rest.addRequestParams("name_last", last); //adds lastname as parameter to url
		        			rest.addRequestParams("name_first", first); //adds firstname as parameter to url
		        			rest.setURL(patients_url);
		        			rest.addRequestParams("name_last", last);
		        			rest.addRequestParams("name_first", first);
		        			rest.execute();
		        			while(rest.getContent() == null){}
		        			
		        			if(rest.getResult()){
		        				String content = rest.getContent();
		        				System.out.println(content);
		        				PatientParser patient_parser = new PatientParser(content);
		        				patients = patient_parser.getPatients(); //get patients from online source
		        			}
		        		}
		            	else{ //gets patients from the database
		            		patients = adapter.searchPatient(searchtext);
		            	}
		            	
			            ListView listview = (ListView) findViewById(R.id.servicesList);
			            ArrayAdapter<Patient> arrayAdapter = new ArrayAdapter<Patient>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, patients){
			            	//method to override the getView method of ArrayAdapter, this changes the color of the text view
			            	@Override
			            	public View getView(int position, View convertView, ViewGroup parent) {
			            		View view = super.getView(position, convertView, parent);
			            	    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			            	    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			            	    text1.setTextColor(Color.BLACK);
			            	    text2.setTextColor(Color.BLACK);
			            	    String displayname = "";
			            	    String displayinfo = "";
			            	    Patient patient = patients.get(position);
		                	  
			            	    displayname = patient.getNameLast() + ", " + patient.getNameFirst();
		                	    Age age = new Age();
		                	    if(patient.getSex().equals("M") || patient.getSex().equals("m")){
		                	    	displayinfo = displayinfo + "Gender: Male";
		                	    	//text1.setBackgroundColor(Color.parseColor("#4C8BFF"));
		                	    	//text2.setBackgroundColor(Color.parseColor("#4C8BFF"));
		                	    }
		                	    else if(patient.getSex().equals("F") || patient.getSex().equals("f")){
		                	    	displayinfo = displayinfo + "Gender: Female";
		                	    	//text1.setBackgroundColor(Color.parseColor("#FF99CC"));
		                	    	//text2.setBackgroundColor(Color.parseColor("#FF99CC"));
			            	    }
		                	   
		                	    try{
		        					displayinfo = "HRN: " + Integer.toString(patient.getPID()) +
		        							", " + displayinfo + ", Age: " + age.getAge(patient.getBirthdate().substring(0,10));
		        				} catch (java.text.ParseException e) {
		        					logMessage(e.toString());
		                	    }
		                	    
			            	    text1.setText(displayname);
			            	    text2.setText(displayinfo);
		                	    
		                	  
			            	    return view;
			            	  }
			            	};
			            	
			            listview.setAdapter(arrayAdapter); 
			            listview.setOnItemClickListener(new OnItemClickListener() {

			    			@Override
			    			public void onItemClick(AdapterView<?> parent, View view,
			    					int position, long id) {
			    				// getting values from selected ListItem
			    				TextView text = (TextView) view.findViewById(android.R.id.text1);
			    				String patientname = text.getText().toString();
			    				// Starting single contact activity
			    				patient = patients.get(position);
			    				int patient_id = patient.getPID();
			    				//Toast.makeText(getApplicationContext(), "Clicked " + patient_id, Toast.LENGTH_SHORT).show();
			    				intent = new Intent(getApplicationContext(), PatientInfoActivity.class);
			    				extras = new Bundle();
			    				extras.putInt("EXTRA_PATIENT_ID", patient_id);
			    				
			    				/* sets current patient by saving the pid of the selected patient in preferences */
			    				Preferences.setPatientId(context, patient_id); /* key = "key_patient_id" */
			    				
			    				intent.putExtras(extras);
			    				startActivity(intent);

			    			}
			    		});
			            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
			            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			            
		            }
		            catch(Exception e){
		            	System.out.println(e);
		            	alertMessage(e.toString());
		            }
		            handled = true;
		        }
		        return handled;
		    }
		});
		
	}

	/* retrieve all patients on the same department as the doctor */
	private void retrieveDepartmentPatients() {

		logMessage("1");
		ArrayList<String> pids = retrieveDepartmentEncounters();
		logMessage("2");		
		patients = retrievePatientAPI(pids);
		logMessage("3");		
		
		for(int i=0;i<patients.size();i++){
			logMessage("Patient ID: " + patients.get(i).getFullName());
		}

//		Rest rest = new Rest("GET",this, "Retrieving patients...");
//		
//		rest.setURL(patients_url);
//		
//		rest.execute();
//		
//
//		while(rest.getContent() == null){}
//		
//		logMessage(rest.getContent());
//		
//		if(rest.getResult()){
//			String content = rest.getContent();
//			PatientParser patient_parser = new PatientParser(content);
//			patients = patient_parser.getPatients();
//		}
	}

	/* retrieve all encounters based on department of doctor*/
	private ArrayList<String> retrieveDepartmentEncounters() {
		
		Rest rest = new Rest("GET",this, "Retrieving patients...");
		
		rest.setURL(Preferences.getBaseURL(this) + encounters_url);
		
		rest.addRequestParams("deptid", Preferences.getDepartmentShort(this));
		
		rest.execute();

		while(rest.getContent() == null){}
		
		//logMessage("Data receiveds: " + rest.getContent());
		
		EncounterParser parser = new EncounterParser(rest.getContent());
		ArrayList<String> pids = parser.getPatientIds();
	
		return pids;
	}
	
	/* retrieve patient data from web service based on PID */
	private ArrayList<Patient> retrievePatientAPI(ArrayList<String> pids) {
		
		ArrayList<Patient> patients =  new ArrayList<Patient>();
		
		for(int i=0;i<pids.size();i++){
			
			Rest rest = new Rest("GET", this, "Retrieving patient information...");
			rest.setURL(Preferences.getBaseURL(this) + patients_url);
			
			rest.addRequestParams("id", pids.get(i));
			logMessage("PID: "+pids.get(i));
			rest.execute();
			
			while(rest.getContent() == null){}
			
			String content = rest.getContent();
			
			PatientParser parser = new PatientParser(content);
			patient = parser.getPatient();
			
			try{
				if(!patient.equals(null)){
					patients.add(patient);
				}
				else{
					logMessage("NULL ERROR");
				}
			} catch(Exception e){
				logMessage(e.toString() + ": patient not found in patients API");
			}
		}
		
		return patients;
	}

	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	public void showPatientInfo(View view){
    	Intent intent = new Intent(this, PatientInfoActivity.class);
    	startActivity(intent);
    }
}

