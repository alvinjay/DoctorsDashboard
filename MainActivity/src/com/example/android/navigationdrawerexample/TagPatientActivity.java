/*
 * Created by: Alvin Jay Cosare
 * Date created: 05/14/14
 * 
 * Updated By: Christian Joseph Dalisay\
 * Updated On: 05/15/14
 * 
 * Description:
 * 		This activity is created when doctor clicks the "tag" button
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.os.Bundle;

import com.example.database.EncounterAdapter;
import com.example.database.NotesAdapter;
import com.example.database.PatientAdapter;
import com.example.database.DoctorEncounterAdapter;
import com.example.model.Encounter;
import com.example.model.Notes;
import com.example.model.Patient;
import com.example.model.Preferences;
import com.example.model.Rest;
import com.example.parser.EncounterParser;
import com.example.parser.NotesParser;
import com.example.parser.PatientParser;

public class TagPatientActivity extends InitialActivity {

	private Rest rest;
	private Patient patient;
	
	private int patient_id;
	private int encounter_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		retrieveBundle();
		
		//print encounter_id
		System.out.println(encounter_id);
		
		/* process tagging */
		submitTag();
		
		finish();
	}

	/* retrieve passed data from parent intent */
	private void retrieveBundle() {
		intent = getIntent();
		extras = intent.getExtras();
		
		patient_id = extras.getInt("EXTRA_PATIENT_ID");
		encounter_id = extras.getInt("EXTRA_ENCOUNTER_ID");
	}
	
	/* submit tag patient POST request */
	private void submitTag() {
		
		rest = new Rest("POST");
		
		/* setup API URL */
		rest.setURL(
					"http://121.97.45.242/segservice"+ //getBaseURL() +
					"/encounter/tagpatient/"
					);
		
		logMessage(rest.getURL());
		
		rest.addRequestParams("encounter_nr", encounter_id + "");
		rest.addRequestParams("doctor_nr", "102354"); //getPersonnelNumber() + "");
		
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
		
		/* checks if the untagging of encounter is successful */
//		if(rest.getResult() && rest.getMessage().equals("Successfully saved")) {
//			getPatientFromTag();
//			getEncounterFromTag();
//			//TODO Create method to retrieve notes of specific encounter
//		}
			
	}

	/* retrieves base_url from preferences */
	private String getBaseURL() {
		return Preferences.getBaseURL(this);
	}
	
	/* retrieves doctor_nr from preferences */
	private int getPersonnelNumber() {
		return Preferences.getPersonnelNumber(this);
	}

	private void getPatientFromTag() {
		
		if(isNetworkAvailable()){
			
			Rest rest = new Rest("GET", this, "");
			
			/* setup API URL */
			rest.setURL(
						"http://121.97.45.242/segservice"+ 
						"/patient/show/"
						);
			
			rest.addRequestParams("id", patient_id + "");
			
			/* process request service request */
			rest.execute();
			
						
			/* check if connection was successful */
			while(rest.getContent() == null){}
		
			
			System.out.println("Data Received:\n" + rest.getContent()); 
			
			if(rest.getResult()) {
				String content = rest.getContent();
				PatientParser parser = new PatientParser(content);
				patient = parser.getPatient();
				PatientAdapter db = new PatientAdapter(this);
				
				db.insertPatient(patient);
			}
		} 
		else{
			alertMessage("Network Unavailable");
		}
		
	}
	
	/* Gets specific encounter from the API by encounter_id */
	public void getEncounterFromTag() {
		
		if(isNetworkAvailable()){
			Rest rest = new Rest("GET",this, "Retrieving encounter of patient..");
			/* setup API URL */
			rest.setURL(
						"http://121.97.45.242/segservice"+ 
						"/encounter/show/"
						);
			
			rest.addRequestParams("id", encounter_id + "");
			
			/* process request service request */
			rest.execute();
			
						
			/* check if connection was successful */
			while(rest.getContent() == null){}
		
			
			System.out.println("Data Received:\n" + rest.getContent()); 
			
			if(rest.getResult()) {
				String content = rest.getContent();
				EncounterParser encounter_parser = new EncounterParser(content);
				ArrayList<Encounter> encounters = encounter_parser.getEncounters();
				EncounterAdapter enc = new EncounterAdapter(this);
				enc.insertEncounters(encounters);
				DoctorEncounterAdapter doc_enc = new DoctorEncounterAdapter(this);
				doc_enc.insertDoctorEncounter(encounters.get(0).getEncounterId(),Preferences.getPersonnelPreference(this));
				  
			}
		} 
		else{
			alertMessage("Network Unavailable");
		}
		
	}
	
	/* Gets specific doctors notes from the API by encounter_id */
	public void getNoteFromTag() {
		
		if(isNetworkAvailable()){
			Rest rest = new Rest("GET",this, "Retrieving notes of encounter..");
			/* setup API URL */
			rest.setURL(
						"http://121.97.45.242/segservice"+ 
						"/doctor/notes/"
						);

			rest.addRequestParams("doctor_nr", Preferences.getPersonnelPreference(this) + "");
			rest.addRequestParams("encounter_nr", Preferences.getEncounterId(this)+ "");
			
			/* process request service request */
			rest.execute();
			
						
			/* check if connection was successful */
			while(rest.getContent() == null){}
		
			
			System.out.println("Data Received:\n" + rest.getContent()); 
			
			if(rest.getResult()) {
				String content = rest.getContent();
				NotesParser notes_parser = new NotesParser(content);
				ArrayList<Notes> notes = notes_parser.getNotes();
				NotesAdapter note_adp = new NotesAdapter(this);
				note_adp.insertNotes(notes);
				  
			}
		} 
		else{
			alertMessage("Network Unavailable");
		}
		
	}

	

		
}
