/*
 * @author: Alvin Jay Cosare
 * @date created: 05/13/14
 * @description: 
 * 			Class that handles all database 
 * 			processes related to encounters
 * 
 * @update: Christian Joseph Dalisay
 * @date updated: 05/15/14
 * @Description:
 * 		   added getLatestDateEncountered
 * 		   deleteDoctorEncounter, insertDoctorEncounter,
 * 		   insertEncounters, deleteEncounter, getEncounterIds
 * 
 */

package com.example.database;

import java.util.ArrayList;

import com.example.model.Encounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EncounterAdapter extends Data {
	
	private ContentValues values;
	private Cursor cursor;

	private String query;
	
	/* _constructor */
	public  EncounterAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Encounter Adapter","Instantiated");
		} catch (Exception e) {
			Log.d("Encounter Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* retrieves the encounter_id of the latest encounter of a patient */
	public int getLatestEncounter(int patient_id){
	
		db = dbHandler.getReadableDatabase();
		
		query = 
				"SELECT encounter_id " + 
			    " FROM " + TABLE_ENCOUNTER + 
				" WHERE pid = " + patient_id +
				" ORDER BY date_encountered DESC" +
				" LIMIT 1"; 
		
		cursor = db.rawQuery(query, null);
		
		try{
			cursor.moveToFirst();
			return cursor.getInt(cursor.getColumnIndex("encounter_id"));
		} catch(Exception e) {
			Log.d("Encounter Adapter","0 rows retrieved");
			return -1;
		}
	}
	
	/* Checks if the encounter exists with the given encounter id */
	public boolean isEncounterExists(int encounter_id){

		db = dbHandler.getReadableDatabase();
		
		query = 
				"SELECT encounter_id " + 
			    " FROM " + TABLE_ENCOUNTER + 
				" WHERE  encounter_id = " + encounter_id;
		
		cursor = db.rawQuery(query, null);
		
		try{
			System.out.println("Encounter ID: " + encounter_id);
			return cursor.moveToFirst();
		} catch(Exception e) {
			Log.d("Encounter Adapter","0 rows retrieved");
			return false;
		} 
	}
	
	/* Deletes rows where encounter id exists */
	public void deleteDoctorEncounter(Integer encounter) {
		db = dbHandler.getWritableDatabase();
		try {
			db.delete(TABLE_DOC_ENC, " encounter_id = ?", new String[] {encounter + ""});
		} catch(SQLException se) {
			Log.d("EncounterAdapter deleteDocEnc", Log.getStackTraceString(se));
		} finally {
			db.close();
		}
		
	}
	
	/* Inserts encounters by the given arraylist encounters */
	public void insertEncounters(ArrayList<Encounter> enc) {
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		try {
			for(int i = 0; i < enc.size(); i++) {
				values.put(ENCOUNTER_ID, enc.get(i).getEncounterId());	
				values.put(PID, enc.get(i).getPID());	
				values.put(ENCOUNTERED, enc.get(i).getDateEncountered());	
				values.put(DEPT_ID, enc.get(i).getDepartmentId());	
				values.put(PATIENT, enc.get(i).getTypePatient());	
				values.put(COMPLAINT , enc.get(i).getMessageComplaint());
				db.insertWithOnConflict(TABLE_ENCOUNTER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			  }

			}
			catch (SQLException se) {
				Log.d("DepartmentAdapter insertEncounters", Log.getStackTraceString(se));
		}finally{
        	db.close();
        }
	}
	
	/* insert new encounter_id to doctor-encounter table*/
	public void insertDoctorEncounters(ArrayList<Encounter> encounters, Integer personnel_id) {
		
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		
		try {
			for(int i = 0; i < encounters.size(); i++) {
				values.put(ENCOUNTER_ID, encounters.get(i).getEncounterId());	
				values.put(PERSONNEL_ID, personnel_id);	
			
				db.insertWithOnConflict(TABLE_DOC_ENC, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			}
		} catch (SQLException se) {
			Log.d("DepartmentAdapter insertDoctorEncounters", Log.getStackTraceString(se));
		}finally {
			db.close();
		}
	}

	/* deletes all rows associated with the given encounter_id */
	public void deleteEncounter(int encounter) {
		db = dbHandler.getWritableDatabase();
		try {
			db.delete(TABLE_ENCOUNTER, " encounter_id = " + encounter, null);
		}
		catch (SQLException se) {
			Log.d("DepartmentAdapter deleteEncounter", Log.getStackTraceString(se));
		}
		finally {
		  db.close();
		  Log.d("deleteEncounter", "Done successfully.");
		}
	}
	
	/* Gets the encounter ids based on pid */
	public ArrayList<Integer> getEncounterIds(int patientid){
		ArrayList<Integer> enc_ids = new ArrayList<Integer>();
		db = dbHandler.getReadableDatabase();
		try {
			query = 
				"SELECT encounter_id FROM " + TABLE_ENCOUNTER + 
				" WHERE pid = " + patientid ;
			cursor = db.rawQuery(query,  null);
			if(cursor.moveToFirst()){
				do{
					enc_ids.add(cursor.getInt(cursor.getColumnIndex("encounter_id")));
				}while(cursor.moveToNext());
			}
		} catch (Exception se) {
			Log.d("getEncounterIds", Log.getStackTraceString(se));
			return enc_ids;
		}
		finally {
			db.close();
		}
		Log.d("getEncounterIds", "Done successfully.");
		return enc_ids;
	}
	
	/* retrieves the pids based on the encounter id */
	public Integer getPid(int encounter) {
		db = dbHandler.getReadableDatabase();
		try {
			query = "SELECT pid FROM " + TABLE_ENCOUNTER + 
					" WHERE encounter_id = " + encounter;
			cursor = db.rawQuery(query, null);
			if(cursor.moveToFirst()) {
				return cursor.getInt(cursor.getColumnIndex("pid"));
			} else {
				return -1;
			}
		} catch (Exception se) {
			Log.d("getPid", Log.getStackTraceString(se));
			return -1;
		}
		finally {
			db.close();
			Log.d("getPid", "Done successfully.");
		}

	}
}
