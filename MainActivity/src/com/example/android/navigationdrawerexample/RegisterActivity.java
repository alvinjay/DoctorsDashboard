/*
* ** Created by Alvin Jay Cosare
 ** Created on 05/06/14
 ** Handles processes for the registration of a new account 
 **
 ** Updated by Christian Joseph Dalisay
 ** Updated on 05/10/14
 */

package com.example.android.navigationdrawerexample;

import java.util.HashMap;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.api.auth.MD5Hash;
import com.example.database.DoctorAdapter;
import com.example.database.RegistrationAdapter;
import com.example.model.Doctor;
import com.example.model.Preferences;
import com.example.model.ProgressDialogMaker;
import com.example.model.Registration;
import com.example.model.Rest;
import com.example.parser.TokenParser;

public class RegisterActivity extends InitialActivity{

	private ProgressDialog pd;
	
	private Preferences pref;
	private Registration reg;
	private Rest rest;
	
	private String license_nr;// = "0117236";
	private String username;// = "seurinane";
	private String password;// = "1234";
	private String confirm_password;// = "1234";
	private String base_url;// = "http://121.97.45.242/segservice";
	
	private HashMap<String, String> data;
	
	private EditText et_license_nr;
	private EditText et_username;
	private EditText et_password;
	private EditText et_confirm_password;
	private EditText et_base_url;	
	
	private View focusView;
	
	private Button register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		/* checks if phone is connected to a network */
		if(!isConnected()){
			setContentView(R.layout.activity_offline_registration);
		}
		else{
			setContentView(R.layout.activity_register);
			//getActionBar().setDisplayHomeAsUpEnabled(false);
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			initViews();
		}
	}
	
	/* checks if phone is connected to a network */
	private boolean isConnected(){
		pref = new Preferences();
		
		if(pref.isNetworkAvailable(this)){
			return true;
		}
		
		return false;
	}
	
	/* Called when "Register" button is clicked (refer to activity_register layout) */
	public void processRegistration(View view){
		
		 /* if inputs are all valid, submits them thru API */
		if(prepareCredentials()){
			if(submitCredentials()){
				if(insertDoctor()){					
					startInitialSync();
					setLastSync();
				}
			}
		}
	}
	
	/* Saves inputted data by user and checks if they are valid */
	public boolean prepareCredentials(){
		
		/* Convert data type from EditText -> Editable -> String */ 
		convertInputText();
		
		/* Validate inputs from user (i.e. empty field, unequal passwords) */
		if(!validateInputs()){
			return false;
		}
		
		reg = new Registration();
		
		/* Retrieve inputted data in textbox */ 
		reg.setLicenseNumber(license_nr);
		reg.setUsername(username);
		reg.setPassword(password);
		reg.setBaseURL(base_url);
		
		/* Retrieve client_id from mobile DB */
		reg.setClientId(getClientId());
		
		return true;
	}
	
	/* stores the retrieved doctor info and tokens to the database */
	private void setRetrievedCredentials(Doctor rDoctor){
		DoctorAdapter doctor = new DoctorAdapter(this);
		doctor.addDoctor(rDoctor);
	}
	
	/* associate layout elements with class variables */
	private void initViews(){
		et_license_nr = (EditText) findViewById(R.id.license_number);
		et_username = (EditText) findViewById(R.id.username);
		et_password = (EditText) findViewById(R.id.password);
		et_confirm_password = (EditText) findViewById(R.id.confirm_password);
	    et_base_url = (EditText) findViewById(R.id.base_url);
	    
	    register = (Button)findViewById(R.id.register_button);
	    register.requestFocus();
	}
	
	/* retrieves inputted text by user and converts/saves it as String */
	private void convertInputText(){
		license_nr = et_license_nr.getText().toString();
		username = et_username.getText().toString();
		password = et_password.getText().toString();
		confirm_password = et_confirm_password.getText().toString();
		base_url = et_base_url.getText().toString();
	}
	
	/* validate each input of user in case or missing fields and etc.*/
	public boolean validateInputs(){
		
		/* for flagging; will be equal to true if there are errors */
		boolean cancel = false;
		
		/* refers to the EditText View that will be focused if there are errors */
		focusView = null; 
		
		if (license_nr.length() != 7) {
			et_license_nr.setError(getString(R.string.error_invalid_length));
			focusView = et_license_nr;
			cancel = true;
		} 
		
		/* must only contain numeric characters */
		String regex = "\\d+"; 
		if (!license_nr.matches(regex)) {
			et_license_nr.setError(getString(R.string.error_invalid_format));
			focusView = et_license_nr;
			cancel = true;
		} 
		
		/* must only contain alphanumeric characters */
		regex = "[\\p{Alnum}]+"; 
		if (!username.matches(regex)) {
			et_username.setError(getString(R.string.error_invalid_format));
			focusView = et_username;
			cancel = true;
		}  
		
		if (!username.matches(regex)) {
			et_username.setError(getString(R.string.error_invalid_format));
			focusView = et_username;
			cancel = true;
		} 
		
		if (!confirm_password.matches(regex)) {
			et_confirm_password.setError(getString(R.string.error_invalid_format));
			focusView = et_confirm_password;
			cancel = true;
		} 
		
		if(!password.equals(confirm_password) && !cancel){
			Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if (base_url.isEmpty()){
			et_base_url.setError(getString(R.string.error_field_required));
			focusView = et_base_url;
			cancel = true;
		}
		
		if (confirm_password.isEmpty()){
			et_confirm_password.setError(getString(R.string.error_field_required));
			focusView = et_confirm_password;
			cancel = true;
		} 
		
		if (password.isEmpty()){
			et_password.setError(getString(R.string.error_field_required));
			focusView = et_password;
			cancel = true;
		}
		
		if (username.isEmpty()){
			et_username.setError(getString(R.string.error_field_required));
			focusView = et_username;		
			cancel = true;
		} 
		if (license_nr.isEmpty()) {
			et_license_nr.setError(getString(R.string.error_field_required));
			focusView = et_license_nr;
			cancel = true;
		} 
		
		/* if there are invalid inputs, show notice */
		if(cancel){
			focusView.requestFocus();
			return false;
		}
		else{
			return true;
		}
	}
	
	/* checks if doctor already exists in the mobile DB */
	private boolean isDoctorExists(String license_nr) {
		DoctorAdapter _doctor = new DoctorAdapter(this);
		if(_doctor.isDoctorExists(license_nr)) {
			return true;
		} else {
			return false;
		}
	}
	
	/* retrieves client_id of mobile device and returns it as string */
	private String getClientId(){
		RegistrationAdapter db = new RegistrationAdapter(this);
		
		System.out.println("getting client_id..");
		return db.getClientId().toString();
	}
	
	/* submits credentials to server via API */
	private boolean submitCredentials(){
		
		rest = new Rest("GET", this, "Verifying credentials...");
		/* setup API URL */
		try {
			rest.setURL(
						reg.getBaseURL() + 
					 	"/registration/doctor?" + 
						"login_id="+reg.getUsername()+
						"&password="+MD5Hash.md5(reg.getPassword())+
						"&license_nr="+reg.getLicenseNumber()+
						"&client_id="+reg.getClientId());
			
			logMessage(rest.getURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/* process service request */
		rest.execute();
		
		/* check if connection was successful */
		
		System.out.println("processing request..");
			
		/* wait until data is retrieved, there is delay in retrieving data*/
		while(rest.getContent() == null){} 
		
		//pd.dismiss();
		System.out.println("Data Received:\n" + rest.getContent());
			
		if(parseJSONResponse(rest.getContent())){
			return true;
		}
		
		return false;
		
	} 

	/* parses data in JSON format to String type */
	private boolean parseJSONResponse(String content){

		TokenParser parser = new TokenParser(content);
		System.out.println("Parsing data..\n" + content);
		
		if(!parser.getChild()){ 
			Toast.makeText(getApplicationContext(), "Unauthorized Access. Please try again.", Toast.LENGTH_LONG).show();
			return false;
		}
		else{
			System.out.println("Parsing data..");
			data = parser.extractData();
			return true;
		}
		
	}
	
	/* starts Login Activity */
	public void showLoginActivity(){
		Intent intent = new Intent(this, LoginActivity.class);
		
		/* clears all activities running prior to this call */
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
		startActivity(intent);
	}
	
	/* starts Register Activity */
	public void showRegisterActivity(View view){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	/* insert newly registered doctor's details to mobile DB */
	private boolean insertDoctor() {
		
		System.out.println("license_nr: " + license_nr);
		
		if(!isDoctorExists(license_nr)){
			String[] DoctorData = {
					license_nr,
					data.get("name_last"),
					data.get("name_first"),
					data.get("name_middle"),
					data.get("auth_token"),
					data.get("access_token"),
					data.get("birth_date"),
					data.get("sex"),
					base_url 
			};
			
			System.out.println("pasok"); 
			
			Doctor doctor = new Doctor();
			
			/* inserting integer data to the model */
			doctor.setDeptId(Integer.parseInt(data.get("location_nr")));
			doctor.setPersonnelId(Integer.parseInt(data.get("personell_nr")));
			
			/* inserting string data to the model */
			doctor.setDoctorCredentials(DoctorData);
			setRetrievedCredentials(doctor);
			
			return true;
		}
		else{
			alertMessage("Account Already Exists");
			return false;
		}
	}
	
	private void startInitialSync() {
		// TODO Auto-generated method stub
		extras = new Bundle();
		extras.putString("EXTRA_BASE_URL", base_url);
		extras.putString("EXTRA_PERSONNEL_ID", data.get("personell_nr"));
		
		intent = new Intent(this, InitialSyncActivity.class);
		intent.putExtras(extras);
		
		startActivityForResult(intent, 1);
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

	/*
	 * A placeholder fragment containing a simple view.
	 */

	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	/* sets the last sync date of the doctor */
	private void setLastSync() {
		DoctorAdapter doc = new DoctorAdapter(this);
		doc.setLastSync(Integer.parseInt(data.get("personell_nr")));
	}
}
