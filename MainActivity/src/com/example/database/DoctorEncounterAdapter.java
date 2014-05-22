/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/18/14
 * @Description - manages the CRUD operations
 * 				 of the table doctor_encounter
 */

package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.database.Data;

public class DoctorEncounterAdapter extends Data {
	
	
	private SQLiteDatabase db;
	private DatabaseHandler dbHandler;

	private Cursor cursor;
	private String query;
	private ContentValues values;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  DoctorEncounterAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("DoctorEncounterAdapter", "Initialized");
		} catch (Exception e) {
			Log.d("DoctorEncounterAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* counts the doctors involved by an encounter */
	public Integer countDoctorsByEncounter(Integer encounter_id) {
		db = dbHandler.getReadableDatabase();
		try {
			System.out.println("Encounter id: " + encounter_id);
			query = " SELECT DISTINCT personnel_id, encounter_id  FROM " + TABLE_DOC_ENC + 
					" WHERE encounter_id = " + encounter_id ;
			System.out.println(query);
			cursor = db.rawQuery(query,  null);
			System.out.println(" rows: " + (cursor.getCount()));

			return (cursor.getCount());
		} catch (Exception se) {
			Log.d("DoctorEncounter countDoctorsByEncounter", se.getMessage());
			return 0;
		}
		finally {
			db.close();
		}
	}
	
	/* Deletes the row that satisfy the encounter_id and personnel_id */
	public void deleteDoctorEncounter(Integer encounter, Integer personnel) {
		db = dbHandler.getWritableDatabase();
		
		try {
			db.delete(TABLE_DOC_ENC, "encounter_id = ? AND personnel_id = ? ", 
					new String[] {encounter + "", personnel + ""});
		} catch (Exception se) {
			Log.d("deleteDoctorEncounter", Log.getStackTraceString(se));
		}
		finally {
			db.close();
		}
	}
	
	/* displays the rows of the table doctor encounter */
	public void showDoctorEncounter() {
		db = dbHandler.getReadableDatabase();
		try {
			String query = "SELECT DISTINCT personnel_id, encounter_id FROM doctor_encounter";
			cursor = db.rawQuery(query, null);
			if(cursor.moveToFirst() && (cursor.getCount() != 0)){
				do {
					System.out.println(cursor.getInt(cursor.getColumnIndex("personnel_id")));
					System.out.println(cursor.getInt(cursor.getColumnIndex("encounter_id")));
				} while(cursor.moveToNext());
			}
		} catch (Exception se) {
			Log.d("showDoctorEncounter", Log.getStackTraceString(se));
		} finally {
			db.close();
		}	
	}
	
	/* Inserts a row with the given encounter id and personnel id */
	public void insertDoctorEncounter(Integer encounter,Integer personnel) {
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		try {
			values.put(ENCOUNTER_ID, encounter);	
			values.put(PERSONNEL_ID, personnel);	
			
			db.insertWithOnConflict(TABLE_DOC_ENC, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		} catch(SQLException se) {
			Log.d("EncounterAdapter insertDocEnc", Log.getStackTraceString(se));
		} finally {
			db.close();
		}
		
	}
	
}