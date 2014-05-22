/*
 * @Author: Christian Joseph Dalisay
 * @Date: 05/16/14
 * @Description: manages the CRUD operations
 * 				 of the table referral
 */

package com.example.database;


import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.database.Data;
import com.example.model.Referral;

public class ReferralAdapter extends Data {
	
	private ArrayList<Referral> referral_list;
	
	public  SQLiteDatabase db;
	private DatabaseHandler dbHandler;
	
	private static final int 	DATABASE_VERSION	= 1;
	private static final String DATABASE_NAME 		= "localhost";
	
	public  ReferralAdapter(Context context) {
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("ReferralAdapter", "Initialized");
		} catch (Exception e) {
			Log.d("ReferralAdapter Exception", Log.getStackTraceString(e));
		}
	}
	
	/* gets all the referrals based on the encounter_id */
	public ArrayList<Referral> getReferrals(int encounter) {
		db = dbHandler.getWritableDatabase();
		referral_list = new ArrayList<Referral>();
		try {
			String query = 
					"SELECT,referral_id encounter_id,dept_id,reason_id,date_referred FROM " + TABLE_REFERRAL + 
					" WHERE encounter_id != '" + encounter + "'";
			
			Cursor cursor = db.rawQuery(query,  null);
			if(cursor.moveToFirst()){
				do{
					int referral_id = cursor.getInt(cursor.getColumnIndex("referral_id"));
					int encounter_id = cursor.getInt(cursor.getColumnIndex("encounter_id"));
					int dept_id = cursor.getInt(cursor.getColumnIndex("dept_id"));
					int reason_id = cursor.getInt(cursor.getColumnIndex("reason_id"));
					String date_referred = cursor.getString(cursor.getColumnIndex("date_referred"));
					Referral referral = new Referral(referral_id, encounter_id, dept_id, reason_id);
					referral_list.add(referral);
				}while(cursor.moveToNext());
			}
		} catch (Exception e) {
			Log.d("ReferralAdapter getPreviousReferrals", e.getMessage());
		}
		finally {
			db.close();
			Log.d("getPreviousReferrals", "Done successfully.");
		}
		return referral_list;
	}	
	
	/* deletes all referrals based on the encounter id */
	public void deleteReferral(int encounter_id) {
		db = dbHandler.getWritableDatabase();
		
		try {
			db.delete(TABLE_REFERRAL, "encounter_id = " + encounter_id, null);
		} catch (Exception se) {
			Log.d("ReferralAdapter deleteReferral", Log.getStackTraceString(se));
		}
		finally {
			db.close();
			Log.d("deleteReferral", "Done successfully.");
		}
	}
}