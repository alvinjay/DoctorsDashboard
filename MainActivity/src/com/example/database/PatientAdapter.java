package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.Age;
import com.example.model.Encounter;
import com.example.model.Patient;

public class PatientAdapter extends Data {
	
		
	private ArrayList<Encounter> encounters;
	
	private SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private Cursor cursor;
	private String query;
	private ContentValues values;

	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";

	/* _constructor */
	public PatientAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Patient Adapter","Instantiated");
		} catch (Exception e) {
			Log.d("Patient Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	public void insertPatient(Patient patient){
		
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		
		try {
			values.put(PID, patient.getPID());	
			values.put(NAME_FIRST, patient.getNameFirst());	
			values.put(NAME_MIDDLE, patient.getNameMiddle());	
			values.put(NAME_LAST, patient.getNameLast());	
			values.put(SEX, patient.getSex());
			values.put(BIRTH, patient.getBirthdate());
			values.put(STREET, patient.getStreet());
			values.put(CITY, patient.getCity());
			values.put(PROVINCE, patient.getProvince());
			values.put(ZIPCODE, patient.getZipCode());
			
			db.insertWithOnConflict(TABLE_PATIENT, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		} catch(SQLException se) {
			Log.d("PatientAdapter insert patient", Log.getStackTraceString(se));
		} finally {
			db.close();
		}
	}
	
	/* gets information about specific patient based on patient_id */
	public Patient getPatientProfile(int patient_id){
		
		db 				= dbHandler.getWritableDatabase();
		String query 	= "SELECT pid, name_last, name_first, name_middle, sex, date_birth, street, city, province, zipcode  FROM patient WHERE pid = " + patient_id;
		Cursor cursor 	= db.rawQuery(query, null);
		//patients 		= new ArrayList<Patient>();
		Age patientage = new Age();
		Patient patient = new Patient();
		
		try{
			if(cursor.moveToFirst())
			{
				int pid 			= cursor.getInt(cursor.getColumnIndex("pid"));
				System.out.println("PID:"+pid);
				String lastname 	= cursor.getString(cursor.getColumnIndex("name_last"));
				String firstname 	= cursor.getString(cursor.getColumnIndex("name_first"));
				String middlename 	= cursor.getString(cursor.getColumnIndex("name_middle"));
				String sex 			= cursor.getString(cursor.getColumnIndex("sex"));
				String date 		= cursor.getString(cursor.getColumnIndex("date_birth"));
				int age 			= patientage.getAge(date);
				String street 		= cursor.getString(cursor.getColumnIndex("street"));
				String city 		= cursor.getString(cursor.getColumnIndex("city"));
				String province 	= cursor.getString(cursor.getColumnIndex("province"));
				String zipcode 		= cursor.getString(cursor.getColumnIndex("zipcode"));
				
				patient = new Patient(pid, lastname, firstname, middlename, sex, age, street, city, province, zipcode);
				
				System.out.println("found");
			}
			else{
				patient = new Patient(); 
				System.out.println("not found");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}finally {
			db.close();
		}
		
		return patient;
	}
	
	/* get all encounters based on patient_id */
	public ArrayList<Encounter> getPatientEncounter(int patient_id){
		db = dbHandler.getWritableDatabase();
		encounters = new ArrayList<Encounter>();
		try {
			String query = "SELECT " 
								+ ENCOUNTER_ID + ","
								+ PATIENT + ","
								+ COMPLAINT + ","
								+ ENCOUNTERED + " " + 
							"FROM "
								+ TABLE_ENCOUNTER + " " +
							"WHERE "
								+ PID + "=" + patient_id;
			
			Cursor cursor = db.rawQuery(query,  null);
			
			if(cursor.moveToFirst()){
				do{
					int pid = patient_id;
					int encounter_id = cursor.getInt(cursor.getColumnIndex(ENCOUNTER_ID));
					String date_encountered = cursor.getString(cursor.getColumnIndex(ENCOUNTERED));
					String patient_type = cursor.getString(cursor.getColumnIndex(PATIENT));
					String complaint = cursor.getString(cursor.getColumnIndex(COMPLAINT));
					
					Encounter encounter = new Encounter(encounter_id, patient_type, complaint, date_encountered, pid);
					
					encounters.add(encounter);
				}while(cursor.moveToNext());
			}
		} catch (Exception e) {
			Log.d("getPatientEncounter", e.getMessage());
		}finally {
			db.close();
		}
	
		Log.d("getPatientEncounter", "Done successfully.");
		return encounters;
	}
	
	/* insert ArrayList of patient profiles to mobile DB */
	public void insertPatients(ArrayList<Patient> patients) {
		
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		
		try {
			for(int i = 0; i < patients.size(); i++) {
				values.put(PID, patients.get(i).getPID());	
				values.put(NAME_FIRST, patients.get(i).getNameFirst().toUpperCase());	
				values.put(NAME_MIDDLE, patients.get(i).getNameMiddle().toUpperCase());	
				values.put(NAME_LAST, patients.get(i).getNameLast().toUpperCase());	
				values.put(STREET , patients.get(i).getStreet());
				values.put(BIRTH , patients.get(i).getBirthdate());
				values.put(CITY, patients.get(i).getCity());	
				values.put(PROVINCE, patients.get(i).getProvince());
				values.put(ZIPCODE, patients.get(i).getZipCode());
				values.put(SEX , patients.get(i).getSex());
				
				db.insertWithOnConflict(TABLE_PATIENT, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			 }
		}
		catch (SQLException se) {
			Log.d("PatientAdapter", Log.getStackTraceString(se));
		}finally {
			db.close();
		}
	}
	
	/* gets pids tagged by a specific doctor */
	public ArrayList<Integer> getPids(int personnel) {
		db = dbHandler.getReadableDatabase();
		query = "SELECT pid FROM " + TABLE_PATIENT + 
				" JOIN " + TABLE_ENCOUNTER + " USING (" + PID + ")" + 
				" JOIN " + TABLE_DOC_ENC + " USING (" + ENCOUNTER_ID + ")" + 
				" JOIN " + TABLE_DOCTOR + " USING (" + PERSONNEL_ID + ")" + 
				" WHERE doctor.personnel_id = '" + personnel + "'";
		
		cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			ArrayList<Integer> pids = new ArrayList<Integer>();
			do {
				pids.add(cursor.getInt(cursor.getColumnIndex("pid")));
			} while(cursor.moveToNext());
			return pids;
		}
		Log.d("PatientAdapter getPids", "0 rows retrieved.");
		return null;
	}
	
	public void deletePatient(int patient) {
		db = dbHandler.getWritableDatabase();
		try {
			db.delete(TABLE_PATIENT, " pid = " + patient, null);
		}  catch (Exception se) {
			Log.d("deletePatient", Log.getStackTraceString(se));
		}
		finally {
			db.close();
		}
	}
	
	
}
