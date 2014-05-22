/*
 ** Created by Alvin Jay Cosare
 ** Created on 05/07/14
 ** This class handles all database processes (SELECT,POST,UPDATE,DELETE)
 *  related to Registration process
 */

package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.database.Data;

public class RegistrationAdapter extends Data{

	public  RegistrationAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("DatabaseHandler", "Database Created");
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	public String getClientId(){
		//Add code for query getting number of accounts in mobile DB
		
		db = dbHandler.getWritableDatabase();
		
		String query = 
			"SELECT " + CLIENT_ID +
			" FROM " + TABLE_CLIENT + 
			" WHERE id = 1";
		
		
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToFirst();
		
		System.out.println(cursor.getString(0));
		
		db.close();
		return cursor.getString(0);
	}
	
	
	public String getBaseURL(String license_nr){

		db = dbHandler.getWritableDatabase();
		
		String query = 
			"SELECT " + URL + 
			" FROM " + TABLE_DOCTOR + 
			" WHERE " + LICENSE_NO + " = '" + license_nr + "'";
		
		Cursor cursor = db.rawQuery(query, null);
		System.out.println(cursor.getString(0));
		
		db.close();
		return cursor.getString(0);
	}
}