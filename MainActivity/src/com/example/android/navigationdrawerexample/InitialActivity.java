/*
 * Created by: Alvin Jay Cosare
 * Date: 05/14/14
 */
package com.example.android.navigationdrawerexample;

import com.example.model.Preferences;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

public class InitialActivity extends Activity{
	
	protected Intent intent;
    protected Bundle extras;
    
    @SuppressWarnings("static-access")
    
    /* checks if phone is connected to a network */
	protected  void checkNetwork() {
    	
		Preferences pref =  new Preferences();
		System.out.println("network?");
		if(pref.isNetworkAvailable(this)){
			//Add code to change value of preference for checking connectivity (Online or Offline)
			pref.putSharedPreferencesBoolean(this, "network", true);

			System.out.println(pref.getSharedPreferencesBoolean(this, "network", false));
		}
		else{
			System.out.println("OFFline");
		}
			
	}
    
    /* checks if mobile device is connected to a network */
    protected boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
    
    public void resetPreferences() {
    	/*Created by: Christian Joseph Dalisay
    	 *Created On: 5/14/14 
    	 *ResetPreferences = resets the stored account preferences to blank
    	 */
    	Preferences.setRememberPreference(this, false);
    	Preferences.setBaseUrlPreference(this, "");
    	Preferences.setPersonnelPreference(this, 0);
    }
    
    /* displays message dialog */
    protected void alertMessage(String message){
    	Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    
    /* displays message dialog */
    protected void logMessage(String message){
    	System.out.println(message);
    }
    
    
}
