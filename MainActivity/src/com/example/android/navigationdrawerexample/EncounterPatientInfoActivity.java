/*
 * Edited by Jose Martin Ipong 5/15/2014, added online functionalities
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.database.EncounterAdapter;
import com.example.database.PatientAdapter;
import com.example.model.Encounter;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.parser.EncounterParser;
import com.example.parser.PatientParser;

public class EncounterPatientInfoActivity extends InitialActivity {
	
	private Encounter encounter;
	private Patient patient;
	
	private ArrayList<Encounter> encounters;
	private ArrayList<Patient> patients;
	
	private Button tag;
	private String tagText;
	
	private int patient_id;
	private int encounter_id;
	final String url_patient = "http://121.97.45.242/segservice/patient/show";
	final String url_encounter = "http://121.97.45.242/segservice/encounter/show";
	final static int FIRST_PATIENT = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encounter_patient_info);
		// Show the Up button in the action bar.
		setupActionBar();
		
		intent = getIntent();
		extras = intent.getExtras();
		patient_id = extras.getInt("EXTRA_PATIENT_ID");
		PatientAdapter db = new PatientAdapter(this);
		
		if(isNetworkAvailable()){
			
			//Rest for patient
			Rest rest_patient = new Rest("GET", this, "");
			rest_patient.setURL(url_patient);
			rest_patient.addRequestParams("id", Integer.toString(patient_id));
			rest_patient.execute();
			while(rest_patient.getContent() == null){}
			
			if(rest_patient.getResult()){
				String content = rest_patient.getContent();
				PatientParser patient_parser = new PatientParser(content);
				patients = patient_parser.getPatients();
				patient = patients.get(FIRST_PATIENT);
				System.out.println(patient);
			}
			
			//Rest for encounter
			Rest rest_encounter = new Rest("GET", this, "");
			rest_encounter.setURL(url_encounter);
			rest_encounter.addRequestParams("pid", Integer.toString(patient_id));
			rest_encounter.execute();
			while(rest_encounter.getContent() == null){}
			if(rest_encounter.getResult()){
				String content = rest_encounter.getContent();
				EncounterParser encounter_parser = new EncounterParser(content);
				encounters = encounter_parser.getEncounters();
				
			}	
		}		
		else{
		    patient = db.getPatientProfile(patient_id);
			encounters = db.getPatientEncounter(patient_id);
		}
		
		EditText nameEditText = (EditText) findViewById(R.id.FullName);
		String nametext = patient.getNameLast() + ", " + patient.getNameFirst();
		nameEditText.setText(nametext);
		
		
		//encounters = db.getPatientEncounter(patient_id);
		
		/* duplicate code with LINE 41  */
		/*
		patient = db.getPatientProfile(patient_id);
		*/
		ListView listview = (ListView) findViewById(R.id.servicesList);
		ArrayAdapter<Encounter> arrayAdapter = new ArrayAdapter<Encounter>(getApplicationContext(), android.R.layout.simple_list_item_1, encounters){
			@Override
			public View getView(int position, View ConvertView, ViewGroup parent){
				View view = super.getView(position, ConvertView, parent);
				TextView text = (TextView) view.findViewById(android.R.id.text1);
				text.setTextColor(Color.BLACK);
				return view;
			}
		};
		listview.setAdapter(arrayAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
						TextView text 	= (TextView) view.findViewById(android.R.id.text1);
						encounter = encounters.get(position);
						int eid = encounter.getEncounterId();
						Bundle extras = new Bundle();
						extras.putInt("EXTRA_ENCOUNTER_ID", eid);
						
						Intent intent = new Intent(getApplicationContext(), PatientEncounterActivity.class);
						intent.putExtras(extras);
						startActivity(intent);
					}
				});
				
				
	}
		
	private boolean isEncounterExists(int encounter_id){
		EncounterAdapter db = new EncounterAdapter(this);
		
		return db.isEncounterExists(encounter_id);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
