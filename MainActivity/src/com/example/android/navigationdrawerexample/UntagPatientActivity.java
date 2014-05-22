/*
 * Created by: Alvin Jay Cosare
 * Date created: 05/14/14
 * 
 * @Editor: Christian Joseph Dalisay
 * @Edited: 05/19/2014
 * @Description:
 * 		added 	checkUntagSuccess
 * 				deleteUntaggedEncounter
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import com.example.database.CanvasAdapter;
import com.example.database.DatabaseAdapter;
import com.example.database.DoctorAdapter;
import com.example.database.DoctorEncounterAdapter;
import com.example.database.EncounterAdapter;
import com.example.database.LabRequestAdapter;
import com.example.database.LabResultAdapter;
import com.example.database.NotesAdapter;
import com.example.database.PatientAdapter;
import com.example.database.ReferralAdapter;
import com.example.model.HelperSharedPreferences;
import com.example.model.Preferences;
import com.example.model.Rest;

import android.os.Bundle;

public class UntagPatientActivity extends InitialActivity {

	private Rest rest;
	
	private int encounter_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		retrieveBundle();
		
		submitUntag();
		
		finish();
	}

	/* process untagging */
	private void submitUntag() {
		
		rest = new Rest("PUT");
		
		/* setup API URL */
		rest.setURL(
					"http://121.97.45.242/segservice"+ //getBaseURL() +
					"/encounter/untagpatient/"
					);
		
		logMessage(rest.getURL());

		rest.addRequestParams("encounter_nr", encounter_id + "");
		rest.addRequestParams("doctor_nr", getPersonnelNumber() + "");
		
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
		
		/* checks if the untagging of encounter is successful */
		if(rest.getResult() && rest.getMessage().equals("Successfully saved")) {
			deleteUntaggedEncounter();
		}
		
	}
	
	/* retrieves base_url */
	private int getPersonnelNumber() {
		return Preferences.getPersonnelNumber(this);
	}

	/* retrieve passed data from parent intent */
	private void retrieveBundle() {
		intent = getIntent();
		extras = intent.getExtras();
		
		encounter_id = extras.getInt("EXTRA_ENCOUNTER_ID");
	}

	private void deleteUntaggedEncounter() {
		Integer personnel = getPersonnelNumber();
		
		DoctorEncounterAdapter doc_enc = new DoctorEncounterAdapter(this);
		/* If an encounter is tagged to many doctors
		 * 	Then, delete lab results, requests and notes associated with that encounter
		 * Else, deletes all remaining data which encounter is associated 
		 */
		if(doc_enc.countDoctorsByEncounter(encounter_id) > 1) {
			LabResultAdapter res = new LabResultAdapter(this);
			res.deleteLabResult(encounter_id);
			LabRequestAdapter req = new LabRequestAdapter(this);
			req.deleteLabRequest(encounter_id, personnel);
			NotesAdapter notes = new NotesAdapter(this);
			notes.deleteNotes(encounter_id, personnel);
			doc_enc.deleteDoctorEncounter(encounter_id,personnel);
		}
		else {
			/* cascading deletion; deletes all data 
			 * associated with the patient 
			 */
			ReferralAdapter ref = new ReferralAdapter(this);
			ref.deleteReferral(encounter_id);
			doc_enc.deleteDoctorEncounter(encounter_id,personnel);
			EncounterAdapter enc = new EncounterAdapter(this);
			Integer patient = enc.getPid(encounter_id);
			enc.deleteEncounter(encounter_id);
			PatientAdapter pat = new PatientAdapter(this);
			pat.deletePatient(patient);
		}
		doc_enc.showDoctorEncounter();
	}	
}
