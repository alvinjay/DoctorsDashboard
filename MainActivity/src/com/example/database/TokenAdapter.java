/*Created By: Christian Joseph Dalisay
 * Created On: 05/08/14
 * TokenAdapter - this class handles the database functions for the
 * 					authtoken and accesstoken from the table doctor
 */
	
package com.example.database;

import com.example.model.TokenValidate;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TokenAdapter extends Data {
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	Cursor cursor;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  TokenAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("TokenAdapter", "Database Created");
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	/* This function gets the authentication  token from the table doctor */
	public String getAuthToken() {
		db = dbHandler.getWritableDatabase();
		String sql = "SELECT authtoken LIMIT '1' FROM 'doctor'";
		
		try {
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
		}catch(Exception e){
        	Log.d("TokenAdapter getAuthentication", Log.getStackTraceString(e));
        	return null;
        }finally{
        	db.close();
        }
		
		System.out.println("Token " + cursor.getString(0));
		return cursor.getString(0);
	}
	
	/*
	 * This function gets the authentication and access token from the table doctor 
	 * using his/her license no
	 */
	public TokenValidate getTokens(String rLicense){
		db = dbHandler.getWritableDatabase();
		String sql = "SELECT 'authtoken','accesstoken' LIMIT '1' FROM 'doctor' WHERE 'license_no' = " + rLicense;
		
		try{
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
        }catch(Exception e) {
         Log.d("Token Adapter getTokens", Log.getStackTraceString(e));
         return null;
        }finally{
        	db.close();
        }
		
		TokenValidate token = new TokenValidate();
        token.setTokens(cursor.getString(0), cursor.getString(1));
        return token;
	}
	
	/*
	 * This function sets the authentication and access token from the table doctor 
	 * using his/her license no
	 */
	public void setTokens(String[] rToken,String license){
		db = dbHandler.getWritableDatabase();
		String query = "UPDATE " + TABLE_DOCTOR + 
        				" SET '" + AUTH + "' = " + rToken[0] + ", "	 +
        						   ACCESS + "' = " + rToken[1] + " " +
        				" WHERE '"+ LICENSE_NO + "' = " + license;
        try {
        	db.execSQL(query);
        } catch(SQLException se) {
        	Log.d("TokenAdapter setTokens", Log.getStackTraceString(se));
        }finally{
        	db.close();
        }
    }
	
	/* This function checks if the given authentication token exists */
	public boolean isAuthtokenExists(String token){
		System.out.println("Token " + token);
		db = dbHandler.getWritableDatabase();
		String query=
				" SELECT count(" + AUTH + ")" +
				" FROM " + TABLE_DOCTOR + 
				" WHERE authtoken = '" + token + "'";
		try {
			 cursor = db.rawQuery(query, null);
			 cursor.moveToFirst();
			 System.out.println("No of AuthTokens: " + cursor.getInt(0));
		} catch(SQLException se) {
	        Log.d("TokenAdapter isAuthtokenExists", Log.getStackTraceString(se));
	        return false;
	    }finally{
        	db.close();
        }
		
		if(cursor.getInt(0) > 0)
			return true;
		else
			return false;
	}
}


