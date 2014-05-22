package com.example.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Preferences extends HelperSharedPreferences{
	
	private final static int NULL = 0;
	private  ConnectivityManager connectivityManager;
	
	public boolean isNetworkAvailable(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    
	    return false;
	} 
	
	/* retrieve base_url for a specific doctor from preferences */
	public static String getBaseURL(Context context){
		try{
			return getSharedPreferencesString(context, "key_base_url", "");			
		} catch(Exception e){
			System.out.println("getBaseURL Error");
			return null;
		}
	}
	
	/* retrieve personnel_nr for a specific doctor from preferences */
	public static int getPersonnelNumber(Context context){
		try{
			return getSharedPreferencesInt(context, "key_personnel_nr", 0);			
		} catch(Exception e){
			System.out.println("getPersonnelNumber Error");
			return NULL;
		}
	}

	/* retrieves the client id of the application from preferences */
	public static String getInitializePreference(Context context) {
		return getSharedPreferencesString(context, "key_client", "client");
	}
	
	/* stores the client id of the application into the preferences */
	public static void setInitializePreference(Context context, String pref) {
		putSharedPreferencesString(context, "key_client", pref);
	}
	
	public static String getAuthenticationPreference(Context context) {
		return getSharedPreferencesString(context, "key_authentication", "authtoken");
	}
	
	/* stores the authentication token id for a specific doctor into the preferences */
	public static void setAuthenticationPreference(Context context, String pref) {
		putSharedPreferencesString(context, "key_authentication", pref);
	}	
	
	/* retrieves remember me of login for a specific doctor from preferences */
	public static Boolean getRememberPreference(Context context) {
		return getSharedPreferencesBoolean(context, "key_remember", false);
	}
	
	/* stores the remember me of login for a specific doctor into the preferences */
	public static void setRememberPreference(Context context, Boolean pref) {
		putSharedPreferencesBoolean(context, "key_remember", pref);
	}
	
	/* retrieves the base url for a specific doctor from preferences */
	public static String getBaseUrlPreference(Context context) {
		return getSharedPreferencesString(context, "key_base_url", "base_url");
	}
	
	/* stores the authentication token for a specific doctor into the preferences */
	public static void setBaseUrlPreference(Context context, String pref) {
		putSharedPreferencesString(context, "key_base_url", pref);
	}
	
	/* retrieves personnel number for a specific doctor from preferences */
	public static Integer getPersonnelPreference(Context context) {
		return getSharedPreferencesInt(context, "key_personnel_nr", 0);
	}
	
	/* stores the personnel number for a specific doctor into the preferences */
	public static void setPersonnelPreference(Context context, Integer pref) {
		putSharedPreferencesInt(context, "key_personnel_nr", pref);
	}
	
	public static int getPatientId(Context context){
		return getSharedPreferencesInt(context, "key_patient_id", NULL);
	}

	public static void setPatientId(Context context, int pref){
		putSharedPreferencesInt(context, "key_patient_id", pref);
	}
	public static int getEncounterId(Context context){
		return getSharedPreferencesInt(context, "key_encounter_id", NULL);
	}
	
	public static void setEncounterId(Context context, int pref){
		putSharedPreferencesInt(context, "key_encounter_id", pref);
	}

	/* department name */
	public static String getDepartmentName(Context context){
		return getSharedPreferencesString(context, "key_dept_name", "");
	}
	
	public static void setDepartmentName(Context context,	String pref) {
		putSharedPreferencesString(context, "key_dept_name", pref);
	}
	
	/* department ID */
	public static String getDepartmentId(Context context){
		return getSharedPreferencesString(context, "key_dept_id", "");
	}
	
	public static void setDepartmentId(Context context,	String pref) {
		putSharedPreferencesString(context, "key_dept_id", pref);
	}
	
	/* short name for department */
	public static String getDepartmentShort(Context context){
		return getSharedPreferencesString(context, "key_short_dept", "");
	}
	
	public static void setDepartmentShort(Context context,	String pref) {
		putSharedPreferencesString(context, "key_short_dept", pref);
	}
	
	
}
