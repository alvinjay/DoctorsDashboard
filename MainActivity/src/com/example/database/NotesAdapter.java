/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/18/14
 * @Description: manages the CRUD operations
 * 				 of the table notes
 */

package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.database.Data;
import com.example.model.Encounter;
import com.example.model.Notes;

public class NotesAdapter extends Data {
	
	
	private  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private ContentValues values;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  NotesAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("NotesAdapter", "Instantiated");
		} catch (Exception e) {
			Log.d("NotesAdapter Exception", Log.getStackTraceString(e));
		}
	}

	/* deletes all notes based on the encounter id */
	public void deleteNotes(int encounter_id, int personnel_id) {
		db = dbHandler.getWritableDatabase();
		
		try {
			db.delete(TABLE_NOTES, " encounter_id = " + encounter_id + " AND personnel_id = " + personnel_id, null);
		} catch (Exception se) {
			Log.d("NotesAdapter deleteNotes", Log.getStackTraceString(se));
		}
		finally {
			db.close();
			Log.d("deleteNotes", "Done successfully.");
		}
	}

	/* Inserts into doctor notes by the given notes */
	public void insertNotes(ArrayList<Notes> notes) {
		db = dbHandler.getWritableDatabase();
		values = new ContentValues();
		try {
			for(int i = 0; i < notes.size(); i++) {
				values.put(NOTES_ID, notes.get(i).getNotesId());	
				values.put(ENCOUNTER_ID, notes.get(i).getEncounterId());	
				values.put(PERSONNEL_ID, notes.get(i).getPersonnelId());	
				values.put(TITLE, notes.get(i).getTitle());	
				values.put(TYPE, notes.get(i).getType());	
				values.put(BODY, notes.get(i).getBody());	
				values.put(CREATED , notes.get(i).getDateCreated());
				if(notes.get(i).isSync() == true) {
					values.put(SYNC , 1);
				} else{
					values.put(SYNC , 0);
				}
				db.insertWithOnConflict(TABLE_ENCOUNTER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
			  }
			}
			catch (SQLException se) {
				Log.d("NotesAdapter insertNotes", Log.getStackTraceString(se));
		}finally{
        	db.close();
        }
	}
	
}