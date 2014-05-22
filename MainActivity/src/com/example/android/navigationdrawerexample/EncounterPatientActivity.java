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
import com.example.database.EncounterAdapter;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.parser.PatientParser;

public class EncounterPatientActivity extends BaseActivity {
	
	private Patient patient;
	private ArrayList<Patient> patients;
	
	private final String url = "http://121.97.45.242/segservice/patient/show/";
	
	private int encounter_id;
	private int patient_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_encounter_patient);
		super.onCreate(savedInstanceState);
		EditText edittext = (EditText) findViewById(R.id.searchView1);
		// Initialize patient list
		patients = new ArrayList<Patient>();
		if(isNetworkAvailable()){

			Rest rest = new Rest("GET",this,"");
			rest.setURL(url);
			rest.execute();
			while(rest.getContent() == null){}
			
			if(rest.getResult()){
				String content = rest.getContent();
				PatientParser patient_parser = new PatientParser(content);
				patients = patient_parser.getPatients();
			}
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
        	    System.out.println(patient.getSex());
        	    displayname = patient.getNameLast() + ", " + patient.getNameFirst();
        	    if(patient.getSex().equals("M") || patient.getSex().equals("m")){
        	    	displayinfo = displayinfo + "Male";
        	    }
        	    else if(patient.getSex().equals("F") || patient.getSex().equals("f")){
        	    	displayinfo = displayinfo + "Female";
        	    }
        	    //System.out.println(displayinfo);
        	    displayinfo = displayinfo + " : " + patient.getBirthdate().substring(0,10);
        	    
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
				patient_id = patient.getPID();
				//encounter_id = getLatestEncounter(patient_id);
				
				/* saves the patient_id and encounter_id to be passed to the next activity */
				extras = new Bundle();
				extras.putInt("EXTRA_PATIENT_ID", patient_id);
				//extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
				
				//alertMessage(encounter_id+"");
				
				/* start next activity Patient Info (2nd Page) */
				intent = new Intent(getApplicationContext(), EncounterPatientInfoActivity.class);
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
		          
		        			Rest rest = new Rest("GET");
		        			rest.addRequestParams("name_last", last); //adds lastname as parameter to url
		        			rest.addRequestParams("name_first", first); //adds firstname as parameter to url
		        			rest.setURL(url);
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
		            	else //gets patients from the database
		            		patients = adapter.searchPatient(searchtext);
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
			            	    if(patient.getSex().equals("M")){
			            	    	displayinfo = displayinfo + "Male";
			            	    }
			            	    else if(patient.getSex().equals("F")){
			            	    	displayinfo = displayinfo + "Female";
			            	    }
			            	    displayinfo = displayinfo + " : " + patient.getBirthdate().substring(0,10);
			            	    
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
			    				intent = new Intent(getApplicationContext(), EncounterPatientInfoActivity.class);
			    				extras = new Bundle();
			    				extras.putInt("EXTRA_PATIENT_ID", patient_id);
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
	
	/* retrieves latest encounter of the patient */
	private int getLatestEncounter(int patient_id) {
		EncounterAdapter db = new EncounterAdapter(this);
		return db.getLatestEncounter(patient_id);
		
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

