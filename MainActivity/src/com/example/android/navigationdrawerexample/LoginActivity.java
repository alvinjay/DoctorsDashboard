/*
 ** Created by Alvin Jay Cosare
 ** Created on 05/06/14
 ** Handles processes for the logging in of the user
 **
 ** Updated by Christian Joseph Dalisay
 ** Updated on 05/10/14
 */

 package com.example.android.navigationdrawerexample;

import com.example.api.auth.HMAC_SHA1;
import com.example.api.auth.MD5Hash;
import com.example.database.*;
import com.example.model.Preferences;
import com.example.model.Registration;
import com.example.model.TokenValidate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends InitialActivity {
	
	
	// Values for username and password at the time of the login attempt.
	private String username = "seurinane";
	private String password = "1234";
	
	private boolean success;
	
	// UI references.
	private EditText et_username;
	private EditText et_password;
	
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		intent = getIntent();
		extras = intent.getExtras();
		
		try{
			if(extras.getBoolean("EXTRA_SUCCESS_REGISTER")){
				alertMessage("Successfully registered");
			}
		}catch(NullPointerException e){
			
		}
		
		/* checks if phone is connected to a network */
		checkNetwork();
		/*
		 * Logs into the account automatically if
		 * clicked the remember me
		 */
		if (isAuthtokenExists(Preferences.getAuthenticationPreference(this))
				&& Preferences.getRememberPreference(this)){
			successfulLogin();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		
		return true;
	}
	
	public void showRegisterActivity(View view){
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	public void successfulLogin(){
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);	
	}
	
	/* Retrieves inputted text by user and assigns it to a variable */
	private void setInputText(){
		et_username = (EditText) findViewById(R.id.username);
		et_password = (EditText) findViewById(R.id.password);
	}
	
	/* Retrieves inputted text by user and converts/saves it as String */
	private void convertInputText(){
		username = et_username.getText().toString();
		password = et_password.getText().toString();
	}
	
	
	public void handleLogin(View view){
		
		setInputText();
		
		/* Convert data type from EditText -> Editable -> String */ 
		//convertInputText();
		
		//* Validate inputs from user (i.e. empty field, unequal passwords) */
		if(validateInputs()){
			if (validateAuthtoken()){
				successfulLogin();
			}
			else {
				Toast.makeText(getApplicationContext(), "Failed to Authenticate", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/* validatesInputs - Validates the input of the user for logging in */		 
	public boolean validateInputs(){
			boolean cancel = false; //for flagging; will be equal to true if there are errors
			View focusView = null; //refers to the EditText View that will be focused if there are errors
			
			// must only contain alphanumeric characters
			String regex = "[\\p{Alnum}]+"; 
			if (!username.matches(regex)) {
				et_username.setError(getString(R.string.error_invalid_format));
				focusView = et_username;
				cancel = true;
			} 
			
			if (!password.matches(regex)) {
				et_password.setError(getString(R.string.error_invalid_format));
				focusView = et_password;
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
			
			if(cancel){
				focusView.requestFocus();
				return false;
			}
			
			else {
				// Show a progress spinner, and kick off a background task to
				// perform the user login attempt.
				//mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
				//showProgress(true);
				return true;
			}
		}
	

        	/* 
	       * 	 checks if there is a match to the generated  token in the mobile DB
	 * 	 then stores the authentication token, base_url, and personnel_nr of the user
	 * 	 account into the preferences. 
		 */
	public boolean validateAuthtoken() {
		
		RegistrationAdapter client = new RegistrationAdapter(this);
		String data = client.getClientId() + "\\n" + username;
		String key = "";
		
		try {
			key = MD5Hash.md5(password);
			System.out.println("Key " + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String digest = null;
		try {
			digest = HMAC_SHA1.hmacSha1(data,key);
		} catch (Exception e) {
			// TODO Auto-generated catch block[
			e.printStackTrace();
		}
		if(isAuthtokenExists(digest)) {
			Preferences.setAuthenticationPreference(this,digest);
			setBaseUrl();
			setPersonnel();
			System.out.println("Remember? "+Preferences.getRememberPreference(this).toString());
			if(Preferences.getRememberPreference(this)){
			}
			else {
				Preferences.setAuthenticationPreference(this,"digest");
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isAuthtokenExists(String token) {
		TokenAdapter _token = new TokenAdapter(this);
		 
		if(_token.isAuthtokenExists(token)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void isRemember(View v) {
		Preferences.setRememberPreference(this, !Preferences.getRememberPreference(this));
	}
	
	//saves the key base url in the preferences for API use
	public void setBaseUrl() {
		DoctorAdapter doc = new DoctorAdapter(this);
		Preferences.setBaseUrlPreference(this, doc.getBaseUrl(Preferences.getAuthenticationPreference(this)));
	}
	
	//saves the key personnel_nr in the preferences for API use
	public void setPersonnel() {
		DoctorAdapter doc = new DoctorAdapter(this);
		Preferences.setPersonnelPreference(this, doc.getPersonnelNr(Preferences.getAuthenticationPreference(this)));
	}
}
