package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.database.PatientAdapter;
import com.example.model.Patient;
import com.example.model.Rest;
import com.example.parser.PatientParser;

public class LaboratoryActivity extends Activity {
	private ArrayList<Patient> patients;
	private Patient patient;
	final String url_patient = "http://121.97.45.242/segservice/patient/show";
	final static int FIRST_PATIENT = 0;
	Intent intent;
	private Bundle extras;
	int patient_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laboratory);
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
		}		
		else{
		    patient = db.getPatientProfile(patient_id);
			
		}
		EditText pid_edittext = (EditText) findViewById(R.id.PIDLaboratory);
		EditText fullname_edittext = (EditText) findViewById(R.id.fullNameLaboratory);
		
		String pid = Integer.toString(patient.getPID());
		String fullname = patient.getFullName();
		pid_edittext.setText(pid);
		fullname_edittext.setText(fullname);
		
	}
	
	public void showNewRequest(View view){
		Intent intent = new Intent(getApplicationContext(), LaboratoryRequest.class);
		startActivity(intent);
	}
	
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	

}
