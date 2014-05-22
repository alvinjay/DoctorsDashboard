/*
 * @author: Alvin Jay Cosare
 * @date created: 05/08/14
 * @description:
 * 			Class that handles all database processes
 *  		related to Client ID generation process
 */

package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.util.Log;

import com.example.database.Data;

public class ClientAdapter extends Data{
	
	private Cursor cursor;
	
	public  ClientAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("Client Adapter", "Instantiated");
		} catch (Exception e) {
			Log.d("Client Adapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* Checks if a client_id already exists */
	public boolean clientIdExists(){
	
		db = dbHandler.getReadableDatabase();
		String query = 
				"SELECT " + CLIENT_ID + 
			    " FROM " + TABLE_CLIENT + 
				" WHERE id = 1"; 
		
		cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			db.close();
			return true;
		}
		else{
			db.close();
			return false;
		}
		
		
	}
	
	/*  generated client_id to mobile DB */
	public void insertClientId(String client_id){
		System.out.println("inserting client_id..");
		db = dbHandler.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put(CLIENT_ID, client_id);
		
		db.insert(TABLE_CLIENT, null, values);
		db.close();
	}
	
	public String getClientId(){
		
		db = dbHandler.getReadableDatabase();
		String query = 
				"SELECT " + CLIENT_ID + 
			    " FROM " + TABLE_CLIENT + 
				" WHERE id = 1"; 
		
		cursor = db.rawQuery(query, null);
		if(cursor.moveToFirst()){
			db.close();
			return cursor.getString(0);
		}
		else{
			db.close();
			return "";
		}
	}
}