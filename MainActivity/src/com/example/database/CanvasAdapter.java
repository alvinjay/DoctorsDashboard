/*
 ** Created by Alvin Jay Cosare
 ** Created on 05/07/14
 ** This class handles all database processes (SELECT,POST,UPDATE,DELETE)
 *  related to Accounts
 *  
 *  Updated by Christian Joseph Dalisay
 ** Updated on 05/10/14
 */

package com.example.database;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.database.Data;

public class CanvasAdapter extends Data{
	
	Cursor cursor;
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	//
	public  CanvasAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	public void deleteCanvas(Integer encounter) {
		db = dbHandler.getWritableDatabase();
		try {
			db.delete(TABLE_NOTES, ENCOUNTER_ID + "='" + encounter + "'" , null);		
		} catch (SQLException se) {
			Log.d("CanvasAdapter deleteCanvas", Log.getStackTraceString(se));
		} finally{
			db.close();
		}
	}
}