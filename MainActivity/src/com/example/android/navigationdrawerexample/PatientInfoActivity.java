/*
 * Edited by Jose Martin Ipong 5/15/2014, added online functionalities
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DatabaseAdapter;
import com.example.database.EncounterAdapter;
import com.example.database.PatientAdapter;
import com.example.model.Encounter;
import com.example.model.Notes;
import com.example.model.Patient;
import com.example.model.Preferences;
import com.example.model.ReferralHelper;
import com.example.model.Rest;
import com.example.parser.EncounterParser;
import com.example.parser.PatientParser;

public class PatientInfoActivity extends ExpandableListActivity {
	
	private Intent intent;
	private Bundle extras;
	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private ArrayList<Object> child;
	
	private Encounter encounter;
	private Patient patient;
	
	private ArrayList<Encounter> encounters;
	private ArrayList<Patient> patients;
	
	private EditText HRN;
	private EditText CaseNo;
	private EditText nameEditText;
	private EditText genderEditText;
	private EditText addressEditText;
	private EditText ageEditText;
	
	private CheckBox histOfSmokingCheckBox;
	private CheckBox histOfDrinkingCheckBox;
	
	private Button tag;
	private Button refer;
	
	private String tagText;
	
	private int patient_id = 0;
	private int encounter_id = 0;
	
	final String url_patient = "http://121.97.45.242/segservice/patient/show";
	final String url_encounter = "http://121.97.45.242/segservice/encounter/show";
		
	final static int EMPTY = 0;
	final static int FIRST_PATIENT = 0;
	final static int NULL = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_info);
		// Show the Up button in the action bar.
		setupActionBar();
		initViews();
		retrieveBundle(); 
		
		if(isNetworkAvailable()){
			initOnlineMode();
		}		
		else{
			initOfflineMode();
		}
		
		setViewsContents();
		setupExpandableList();
		
	}
		
	/* retrieve passed data from previous activity */
	private void retrieveBundle() {
			
		intent = getIntent();
		extras = intent.getExtras();
			
		patient_id = extras.getInt("EXTRA_PATIENT_ID");

		/* check if encounter_id was passed from previous intent */
		try{
			encounter_id = extras.getInt("EXTRA_ENCOUNTER_ID");
			System.out.println("encounter_id:" + encounter_id);
			saveEncounterIdPreferences();
			//alertMessage(encounter_id+"");
		}catch(Exception e){
			System.out.println("no encounter_id");
		}
			}
			
	/* called when phone is not connected to a network */
	private void initOfflineMode() {
			
		PatientAdapter db = new PatientAdapter(this);
			
		/* retrieve patient info from mobile DB */
	    patient = db.getPatientProfile(patient_id);
			
	    /* retrieve patient's encounters from mobile DB */
		encounters = db.getPatientEncounter(patient_id);
			
		
		/* sort encounters based on encountered_data*/
			Collections.sort(encounters, new CustomComparator());
		
		//temp
			System.out.println("sorting..");
			
		//print tem
			for(int i=0; i<encounters.size();i++){
				encounter = encounters.get(i);
				
				try{
					System.out.println(encounter.getDateEncountered());
				}catch(Exception e){
					System.out.println("null error");
				}
			}
		
			encounter = encounters.get(encounters.size()-1);
			
		/* set the current encounter_id to be associated with Patient Info Page */ 
		if(encounter_id == EMPTY){
			encounter_id = encounter.getEncounterId();
			saveEncounterIdPreferences();
			//alertMessage(encounter_id+"");
		}

		/* hide tag and refer button on offline mode */
		tag.setVisibility(CONTEXT_RESTRICTED);
		refer.setVisibility(CONTEXT_RESTRICTED);		
				
		}		
		
	/* called when phone is connected to a network */
	private void initOnlineMode() {
		
		PatientAdapter db = new PatientAdapter(this);
		
		//temp
		System.out.println("patient checking in db..");
		
		patient = db.getPatientProfile(patient_id);
		
		if(patient.getPID() == NULL){
			retrievePatientAPI(patient_id);
		
			//temp
			System.out.println("patient not in DB. searching ONLINE..");
			
			tag.setText("Tag Patient");		
		}
		else{
			tag.setText("Undo Tag");
		}
		
		retrieveEncounterAPI(patient_id);
		
		System.out.println("Encounter Dept:" + encounter.getDepartmentId());

		/* check if current encounter belongs to the same department as the doctor */
		if(Preferences.getDepartmentId(this) != encounter.getDepartmentId()){
			
			System.out.println("Different departments");
			/* hide tag and refer button on online mode if location_nr are different */
			tag.setVisibility(CONTEXT_RESTRICTED);
			refer.setVisibility(CONTEXT_RESTRICTED);		
		}
	}
		
		
		/**
		 * @author Jake Randolph B Muncada
		 * @date 5/16/2014
		 * 
		 * Edited: Put the expandable list here, instead of listview
		 */
	private void setupExpandableList() {
		final ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)
		//final ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.list);

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);
		
		setGroupParents();
		
		System.out.println("Encounters Size: "+encounters.size());

		for(int i=0; i<encounters.size();i++){
			encounter = encounters.get(i);
			
		setChildData(patient_id, encounter_id);
			System.out.println(encounter.getEncounterId());
		}
		
		final ExpListAdapter adapter = new ExpListAdapter(parentItems, childItems);

		adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
		expandableList.setAdapter(adapter);
		
		expandableList.setOnGroupExpandListener(new OnGroupExpandListener(){
			@Override
			public void onGroupExpand(int groupPosition){
				MyOnClickListener.setLastExpandedGroupPosition(groupPosition);
				int len = adapter.getGroupCount();
				
				for(int i=0; i<len; i++){
					if(i != groupPosition){
						expandableList.collapseGroup(i);
					}
				}
				
				//Toast.makeText(getApplicationContext(),"groupPosition:"+groupPosition+" ", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	/* set contents of layout elements based on patient info */
	private void setViewsContents() {

		String nametext = patient.getNameLast() + ", " + patient.getNameFirst();
				
		
		if(patient.getSex().equals("M")){
			genderEditText.setText("M");
		}
		else{
			genderEditText.setText("F");
		}
		
		HRN.setText(patient.getPID()+"");
		CaseNo.setText(encounter_id+"");
		
		addressEditText.setText(patient.getAddress());
		nameEditText.setText(nametext);
		
		String age = String.valueOf(patient.getAge());
		
		ageEditText.setText(patient.getAge()+"");
	}

	/* associate layout elements */
	private void initViews() {
		HRN = (EditText) findViewById(R.id.HRN);
		CaseNo = (EditText) findViewById(R.id.CaseNo);
		nameEditText = (EditText) findViewById(R.id.FullName);
		genderEditText = (EditText) findViewById(R.id.Gender);
		addressEditText = (EditText) findViewById(R.id.Address);
		ageEditText = (EditText) findViewById(R.id.Age);

		tag = (Button) findViewById(R.id.TagPatientButton);
		refer = (Button) findViewById(R.id.ReferPatientButton);
	}

	/* retrieve all encounters of a patient thru web service base on pid */
	private void retrieveEncounterAPI(int patient_id2) {

		//Rest for encounter
		Rest rest = new Rest("GET", this, "");
		rest.setURL(url_encounter);
		rest.addRequestParams("pid", Integer.toString(patient_id));
		rest.execute();
		
		/* wait while data is retrieved */
		while(rest.getContent() == null){}
		
		//print data
		System.out.println(rest.getContent());
		
		/* check if retrieving data was successful */
		if(rest.getResult()){
			String content = rest.getContent();
			EncounterParser parser = new EncounterParser(content);
			encounters = parser.getEncounters();
		}
		
		try{
			//print how many encounters
			System.out.println("# of encounters:" + encounters.size());	
			
			/* sort encounters based on encountered_data*/
			Collections.sort(encounters, new CustomComparator());
			
			//temp
			System.out.println("sorting..");
			
			//print to check if sorting was successful
			for(int i=0; i<encounters.size();i++){
				encounter = encounters.get(i);
				
				try{
					System.out.println(encounter.getDateEncountered());
				}catch(Exception e){
					System.out.println("null error");
				}
			}

			
			
			/* get latest encounter if encounter_id is not set */
			if(encounter_id == EMPTY){			
				
				/* retrieve latest encounter of patient */
				encounter = encounters.get(encounters.size()-1);
				
				/* set the current encounter_id to be associated with Patient Info Page */ 
				encounter_id = encounter.getEncounterId();
				
				saveEncounterIdPreferences();
				//alertMessage(encounter_id+"");
				
			}
			
			//print pid and encounter_id
			System.out.println("PID: " + encounter.getPID());
			System.out.println("EID: " + encounter.getEncounterId());
			
			
		} catch(Exception e){
			alertMessage("No encounters");
		}
	}

	/* retrieve patient data from web service based on PID */
	private void retrievePatientAPI(int patient_id2) {
		
		Rest rest = new Rest("GET", this, "Retrieving patient information...");
		rest.setURL(url_patient);
		rest.addRequestParams("id", Integer.toString(patient_id));
		rest.execute();
		
		while(rest.getContent() == null){}
		
		String content = rest.getContent();
		PatientParser patient_parser = new PatientParser(content);
		patient = patient_parser.getPatient();
		
		System.out.println(patient.getSex() + " lol");
	}

	/**
	 * @author Jake Randolph B Muncada
	 * @date 5/16/2014
	 */
	public void setGroupParents() {
		parentItems.add("Medical History");
		parentItems.add("Previous Requests");
		//parentItems.add("Referrals");
		parentItems.add("Notes");
	}
	
	/**
	 * @author Jake Randolph B Muncada
	 * @date 5/16/2014
	 * @param patient_id
	 * @param encounter_id
	 * @param date_encountered
	 */
	public void setChildData(int patient_id, int encounter_id) {

		ArrayList<Object> child = new ArrayList<Object>();
		PatientAdapter db = new PatientAdapter(this);
		DatabaseAdapter db1 = new DatabaseAdapter(this);
		
		// MEDICAL HISTORY / ENCOUNTERS
		//ArrayList<Encounter> encounterList = db.getPatientEncounter(patient_id);
		for (int i = 0; i < encounters.size(); i++) {
			child.add(encounters.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		// PREVIOUS REQUESTS / LAB REQUESTS
		for (int i = 0; i < encounters.size(); i++) {
			child.add(encounters.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		// NOTES
		ArrayList<Notes> noteList = db1.getDoctorNotes(encounter_id);
		Log.d("noteList size", ""+noteList.size());
		child.add("ADD NEW NOTES");
		for (int i = 0; i < noteList.size(); i++) {
			child.add(noteList.get(i));
		}
		childItems.add(child);
		
	
	}
	
	/* called when "refer" button is clicked */
    public void showReferPatient(View view){
	    Intent intent = new Intent(this,ReferralActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient.getPID());
		extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
		extras.putString("EXTRA_PATIENT_NAME_LAST", patient.getNameLast());
		extras.putString("EXTRA_PATIENT_NAME_FIRST", patient.getNameFirst());
		intent.putExtras(extras);
		startActivity(intent);
	}
	
	/* called when the "Tag" button is clicked */
	public void handleTagClick(View view){
		tagText = tag.getText().toString();

		EncounterAdapter db = new EncounterAdapter(this);
		if(tagText.equals("Tag Patient")){
			handleTagPatient();
			//Add here code to save encounter details to mobile DB
		}
		else{
			handleUntagPatient();
			//Add here code to remove encounter details from mobile DB
			db.deleteDoctorEncounter(encounter_id);
			tag.setText("Tag Patient");
		}		
	}
		
	/* handles tagging patient process */
	private void handleTagPatient() {
		
		extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient_id);
		extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
		
		intent = new Intent(this, TagPatientActivity.class);
		intent.putExtras(extras);
		
		startActivity(intent);
		
		tag.setText("Undo Tag");
		alertMessage("Successfully Tagged");
	}
		
	/* handles untagging patient process */
	private void handleUntagPatient() {
		extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient_id);
		extras.putInt("EXTRA_ENCOUNTER_ID", encounter_id);
		
		Intent intent = new Intent(this, UntagPatientActivity.class);
		intent.putExtras(extras);
		
		startActivity(intent);
		
		alertMessage("Successfully Untagged");
	}
	
	/* saves encounter_id in preferences */
	private void saveEncounterIdPreferences() {
		/* sets current encounter by saving the encounter_nr in preferences */
		Preferences.setEncounterId(this, encounter_id); /* key = "key_encounetr_id" */
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

	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	/* displays message dialog */
    protected void alertMessage(String message){
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    public class CustomComparator implements Comparator<Encounter> {
	    @Override
	    public int compare(Encounter o1, Encounter o2) {
	        return o1.getDateEncountered().compareTo(o2.getDateEncountered());
	    }
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

    
    
}
