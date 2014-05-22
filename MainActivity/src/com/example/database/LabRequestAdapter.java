/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/20/14
 * @Description: manages the CRUD operations
 * 				 regarding table lab result
 */

package com.example.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.database.Data;

public class LabRequestAdapter extends Data {
	
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  LabRequestAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("LabRequestAdapter", "Initialized");
		} catch (Exception e) {
			Log.d("LabRequestAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* deletes all lab requests based on the encounter id */
	public void deleteLabRequest(int encounter_id, int personnel_id) {
		db = dbHandler.getWritableDatabase();
		
		try {
			db.delete(TABLE_LAB_REQUEST, "encounter_id = ? AND personnel_id = ?", 
					new String[] {encounter_id +"",personnel_id+""});
		} catch (Exception se) {
			Log.d("LabRequestAdapter deleteLabRequest", Log.getStackTraceString(se));
		}
		finally {
			db.close();
			Log.d("deleteLabRequest", "Done successfully.");
		}
	}
	
}