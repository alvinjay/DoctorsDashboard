/*
 * @author: Alvin Jay Cosare
 * @date created: 05/19/14
 * @description: 
 * 			Class that handles all database 
 * 			processes related to laboratory 
 * 			requests and services
 * 
 */

package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.LabRequest;
import com.example.model.LabService;

public class LaboratoryAdapter extends Data {

	/* _constructor */
	public  LaboratoryAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Encounter Adapter","Instantiated");
		} catch (Exception e) {
			Log.d("Encounter Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	
	/* inserts lab requests in mobile DB
	 * TABLE = "lab_request" 
	 */
	public void insertLabRequestsEncounter(ArrayList<LabRequest> requests, int personnel_id) {
		
		db = dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		try {
			for(int i = 0; i < requests.size(); i++) {
				values.put(ENCOUNTER_ID, requests.get(i).getEncounterNumber());	
				//Log.d("Lab Request Insert","Encounter ID: "+requests.get(i).getEncounterNumber());
				values.put(REQUEST_ID, requests.get(i).getRequestNumber());				
				//Log.d("Lab Request Insert","Request ID: "+requests.get(i).getRequestNumber());
				values.put(PERSONNEL_ID, personnel_id);				
				//Log.d("Lab Request Insert","Request ID: "+requests.get(i).getRequestNumber());
				values.put(REQUESTED, "2011-00-00 00:00:00");
				
				db.insertWithOnConflict(TABLE_LAB_REQUEST, null, values, SQLiteDatabase.CONFLICT_REPLACE);
				
				insertLabServiceRequest(requests.get(i));
			}
		} catch(SQLException se) {
			Log.d("LABORATORY_ADAPTER", Log.getStackTraceString(se));
		}
		
	}

	/*  inserts into composite table for requests and associated services included
	 *  TABLE = "service_request"
     */
	private void insertLabServiceRequest(LabRequest request) {
		
		db = dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		ArrayList<LabService> services = request.getServices();
		
		try {
			
			for(int i=0; i < services.size(); i++) {
				
				values.put(REQUEST_ID, request.getRequestNumber());			
				//Log.d("Lab Request Insert","Request ID: "+request.getRequestNumber());
				values.put(SERVICE_ID, services.get(i).getServiceCode());
				//Log.d("Lab Request Insert","Service Code: "+services.get(i).getServiceCode());
				values.put(QUANTITY, services.get(i).getQuantity());
				//Log.d("Lab Request Insert","Quantity: "+services.get(i).getQuantity());
				
				db.insertWithOnConflict(TABLE_SERVICE_REQUEST, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			}
			
		} catch(SQLException se) {
			
			Log.d("LABORATORY_ADAPTER", Log.getStackTraceString(se));
	
		}
	}
}
