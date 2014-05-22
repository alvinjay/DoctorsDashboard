package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.example.database.EncounterAdapter;
import com.example.database.LaboratoryAdapter;
import com.example.database.NotesAdapter;
import com.example.database.PatientAdapter;
import com.example.model.Encounter;
import com.example.model.LabRequest;
import com.example.model.Notes;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.parser.EncounterParser;
import com.example.parser.LabRequestParser;
import com.example.parser.NotesParser;
import com.example.parser.PatientParser;

public class InitialSyncActivity extends InitialActivity {

	private Rest rest;

	private ArrayList<Encounter> encounters;
	private ArrayList<Patient> patients;
	private ArrayList<LabRequest> requests;
	private ArrayList<Notes> notes;
	
	private ArrayList<String> PIDs;
	private ArrayList<String> EIDs;
	
	private String base_url;
	private String personnel_id;
	
	private final static String TAGGED_URL = "/encounter/tagged/";
	private final static String PATIENT_URL = "/patient/show/";
	private final static String ENCOUNTER_URL = "/encounter/show/";
	private final static String LAB_REQUEST_URL = "/laboratory/vieworder/";
	private final static String NOTES_URL = "/doctor/notes/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		retrieveBundle();
		
		retrieveTaggedByDoctorAPI();
		retrievePatientsAPI();
		retrieveEncountersAPI();
		retrieveLabRequestsAPI();
		retrieveDoctorsNotesAPI();
		
		showLoginActivity();
		finish();
	}

	/* retrieve all passed information from previous activity*/
	private void retrieveBundle() {
		intent = getIntent();
		extras = intent.getExtras();
		
		base_url = extras.getString("EXTRA_BASE_URL");
		personnel_id = extras.getString("EXTRA_PERSONNEL_ID");
	}

	/* retrieve all the encounter_nr together with the patient_id that is tagged to a doctor*/
	private void retrieveTaggedByDoctorAPI() {
		
		Rest rest = new Rest("GET", this, "Retrieving tagged encounters..");
		
		/* set service URL to call */
		rest.setURL(base_url + TAGGED_URL);
		
		/* add doctor_nr as parameter */
		rest.addRequestParams("doctor_nr", personnel_id);
		
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		System.out.println("processing request..");
			
		/* wait until data is retrieved, there is delay in retrieving data*/
		while(rest.getContent() == null){} 
			
		System.out.println("Data Received:\n" + rest.getContent());
		
		PatientParser parser = new PatientParser(rest.getContent());
		
		PIDs = parser.getTaggedPID();
		EIDs = parser.getTaggedEID();
	}

	/* retrieve all patients that is associated with a tagged encounter to a doctor */
	private void retrievePatientsAPI() {
		
		PatientAdapter db = new PatientAdapter(this);
		patients = new ArrayList<Patient>();
		
		for(int i=0; i<PIDs.size(); i++){
			
			Rest rest = new Rest("GET", this, "Retrieving patient profiles..");
			
			/* set service URL to call */
			rest.setURL(base_url + PATIENT_URL);
			
			/* add pid as parameter */
			rest.addRequestParams("id", PIDs.get(i));
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			System.out.println("processing request..");
				
			/* wait until data is retrieved, there is delay in retrieving data*/
			while(rest.getContent() == null){} 
				
			System.out.println("Data Received:\n" + rest.getContent());
			
			PatientParser parser = new PatientParser(rest.getContent());
			
			patients.add(parser.getPatient());
		}
		
		/* insert retrieved patients to mobile DB */
		db.insertPatients(patients);
	}

	/* retrieve all encounters tagged to a doctor */
	private void retrieveEncountersAPI() {
		
		EncounterAdapter db = new EncounterAdapter(this);
		encounters = new ArrayList<Encounter>();
		
		for(int i=0; i<EIDs.size(); i++){
			
			/* clear contents of ArrayList */
			encounters.clear();
			
			rest = new Rest("GET", this, "");
			
			/* get base_url associated with doctor */
			rest.setURL(base_url + ENCOUNTER_URL);
			
			/* add pid as parameter */
			rest.addRequestParams("id", EIDs.get(i));
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			System.out.println("processing request..");
				
			/* wait until data is retrieved, there is delay in retrieving data*/
			while(rest.getContent() == null){} 
				
			System.out.println("Data Received:\n" + rest.getContent());
			
			EncounterParser parser = new EncounterParser(rest.getContent());
			
			/* retrieve and parse encounters of patient(i) */
			encounters = (parser.getEncounters());
			
			/* insert encounters of patient(i) in mobile DB */
			db.insertEncounters(encounters);
			db.insertDoctorEncounters(encounters, Integer.parseInt(personnel_id));
		}		
	}

	/* retrieve thru web service all the laboratory request for each encounter tagged to a doctor */
	private void retrieveLabRequestsAPI() {
		
		
		requests = new ArrayList<LabRequest>();
		
		for(int i=0; i<EIDs.size(); i++){
			LaboratoryAdapter db = new LaboratoryAdapter(this);
			
			/* clear contents of ArrayList */
			requests.clear();
			
			rest = new Rest("GET", this, "");
			
			/* get base_url associated with doctor */
			rest.setURL(base_url + LAB_REQUEST_URL);
			
			/* add pid as parameter */
			rest.addRequestParams("encounter_nr", EIDs.get(i));
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			System.out.println("processing request..");
				
			/* wait until data is retrieved, there is delay in retrieving data*/
			while(rest.getContent() == null){} 
				
			System.out.println("Data Received:\n" + rest.getContent());
			
			LabRequestParser parser = new LabRequestParser(rest.getContent());
			
			/* retrieve and parse lab requests of each encounter of patient(i) */
			requests = (parser.getRequestService());
		
			//print all encounters and requests
			for(int j=0;j<requests.size();j++){
				System.out.println("Encounter ID: "+requests.get(j).getEncounterNumber());
				System.out.println("Ref No: "+requests.get(j).getRequestNumber());
			}
			
			/* insert encounters of patient(i) in mobile DB */
			db.insertLabRequestsEncounter(requests, Integer.parseInt(personnel_id));
		}
	}
	
	/* retrieve thru web service all the laboratory request for each encounter tagged to a doctor */
	private void retrieveDoctorsNotesAPI() {
		
		
		notes = new ArrayList<Notes>();
		
		for(int i=0; i<EIDs.size(); i++){
			NotesAdapter db = new NotesAdapter(this);
			
			/* clear contents of ArrayList */
			requests.clear();
			
			rest = new Rest("GET", this, "");
			
			/* get base_url associated with doctor */
			rest.setURL(base_url + NOTES_URL);
			
			/* add personnel id and pid as parameter */
			rest.addRequestParams("doctor_nr", personnel_id);
			rest.addRequestParams("encounter_nr", EIDs.get(i));
			
			/* process request service request */
			rest.execute();
			
			/* check if connection was successful */
			System.out.println("processing request..");
				
			/* wait until data is retrieved, there is delay in retrieving data*/
			while(rest.getContent() == null){} 
				
			System.out.println("Data Received:\n" + rest.getContent());
			
			NotesParser parser = new NotesParser(rest.getContent());
			/* retrieve and parse notes of each encounter of patient(i) */
			notes = parser.getNotes();
			//print all encounters and requests
			for(int j=0;j<notes.size();j++){
				System.out.println("Encounter ID: "+notes.get(j).getEncounterId());
				System.out.println("Title: "+notes.get(j).getNotesId());
			}
			/* insert encounters of patient(i) in mobile DB */
			db.insertNotes(notes);
		}
	}
	
	/* starts Login Activity */
	public void showLoginActivity(){
		intent = new Intent(this, LoginActivity.class);
		extras =  new Bundle();
		
		/* pass to next activity that registration is successful */
		extras.putBoolean("EXTRA_SUCCESS_REGISTER", true);
		intent.putExtras(extras);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); 
		
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		finish();
	}

	
		
}
