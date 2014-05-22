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

public class LabResultAdapter extends Data {
	
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  LabResultAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("LabResultAdapter", "Initialized");
		} catch (Exception e) {
			Log.d("LabResultAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* deletes all lab results based on the encounter id */
	public void deleteLabResult(int encounter_id) {
		db = dbHandler.getWritableDatabase();
		
		try {
			db.delete(TABLE_LAB_RESULT, " request_id IN (SELECT request_id FROM lab_request WHERE encounter_id = "  + encounter_id + ")" , null);
		} catch (Exception se) {
			Log.d("LabResultAdapter deleteLabResult", Log.getStackTraceString(se));
		}
		finally {
			db.close();
			Log.d("deleteLabResult", "Done successfully.");
		}
	}
	
}