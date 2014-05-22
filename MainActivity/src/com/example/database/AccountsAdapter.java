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
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.database.Data;

public class AccountsAdapter extends Data{
	
	private Cursor cursor;
	private String query;
	
	private  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	//
	public  AccountsAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	public int getAccounts(){
		db = dbHandler.getReadableDatabase();
		//Add code here for query getting number of accounts in mobile DB
		try {
			query = " SELECT count(" + LICENSE_NO + ")" +
				" FROM " + TABLE_DOCTOR;		
			cursor = db.rawQuery(query, null);
			cursor.moveToFirst();
			System.out.println(cursor.getInt(0));
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		} finally {
			db.close();
		}
		
		return cursor.getInt(0);
	}
}