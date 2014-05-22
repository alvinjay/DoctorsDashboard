package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DepartmentAdapter;
import com.example.model.Department;
import com.example.model.Preferences;
import com.example.model.ReferralReason;
import com.example.model.Rest;
import com.example.parser.ReferralReasonParser;

public class ReferralActivity extends InitialActivity {

	private int patient_id;
	
	private ArrayList<Department> departments;
	private ArrayList<ReferralReason> reasons;
	
	private Spinner referDepartments;
	private Spinner referReason;
	
	private int encounter_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_referral);

		TextView textView = (TextView) findViewById(R.id.nameLabel);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		patient_id = extras.getInt("EXTRA_PATIENT_ID");
		String name_last = extras.getString("EXTRA_PATIENT_NAME_LAST");
		String name_first= extras.getString("EXTRA_PATIENT_NAME_FIRST");
		encounter_id = extras.getInt("EXTRA_ENCOUNTER_ID");
		
		textView.setText(name_last + ", " + name_first);
		
		referDepartments 	= (Spinner) findViewById(R.id.referDepartment);
		referReason 		= (Spinner) findViewById(R.id.referReason);
		DepartmentAdapter departmentAdapter = new DepartmentAdapter(this);
		
		
		departments = new ArrayList<Department>();	
		departments = departmentAdapter.getDepartments();
		ArrayAdapter<Department> array_adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item, departments);
		referDepartments.setAdapter(array_adapter);      
		reasons = new ArrayList<ReferralReason>();
		

		if(isNetworkAvailable()){
			try{
				Rest rest = new Rest("GET", this, "");
				rest.setURL("http://121.97.45.242/segservice/encounter/reasonreferral/");
				rest.execute();
				while(rest.getContent() == null){}
        		
				if(rest.getResult()){
					String content = rest.getContent();
					ReferralReasonParser referralReasonParser = new ReferralReasonParser(content);
					reasons = referralReasonParser.getReasons();
        		
					ArrayAdapter<ReferralReason> reasonAdapter = new ArrayAdapter<ReferralReason>(this, android.R.layout.simple_spinner_item, reasons);
					referReason.setAdapter(reasonAdapter);
        	  }
			}catch(Exception e){System.out.println(e.toString());}
		}else
			Toast.makeText(this, "Connection unavailable", Toast.LENGTH_SHORT).show();;

	}
        	
	//to check if the phone has access to the internet
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	/* Called when submit button is clicked */
	public void referPatient(View view){
		Intent intent = new Intent(this,PatientInfoActivity.class);
		Bundle extras = new Bundle();
		extras.putInt("EXTRA_PATIENT_ID", patient_id );
		intent.putExtras(extras);
			
		Rest rest = new Rest("POST");
		/* setup API URL */
		rest.setURL("http://121.97.45.242/segservice/encounter/referral/");
		
		rest.addRequestParams("encounter_nr", encounter_id+""); //encounter number
		rest.addRequestParams("doctor_nr", Preferences.getPersonnelNumber(this)+""); //doctor number
		rest.addRequestParams("referred_dept", Integer.toString(departments.get(referDepartments.getSelectedItemPosition()).getDepartmentNumber())); //reffered department
		rest.addRequestParams("reason_referral", Integer.toString(reasons.get(referReason.getSelectedItemPosition()).getId())); //reason number for referral
		
		logMessage("Referral Encounter ID:" + encounter_id);
		logMessage("Referral Personnel ID:" + Preferences.getPersonnelNumber(this));
		logMessage("Referral department ID:" + Integer.toString(departments.get(referDepartments.getSelectedItemPosition()).getDepartmentNumber()));
		logMessage("Referral reason ID:" + Integer.toString(reasons.get(referReason.getSelectedItemPosition()).getId()));
		

		System.out.println(rest.getURL());
		/* process request service request */
		rest.execute();
		
		/* check if connection was successful */
		while(rest.getContent() == null){}
		
		System.out.println("Data Received:\n" + rest.getContent()); 
		
		alertMessage("Referral successfully saved");
		
		finish();
		//System.out.println("processing request..");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.referral, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	/*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_referral,
					container, false);
			return rootView;
		}
	}
	*/

}
