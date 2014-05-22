/*
 * Created by Jose Martin Ipong on 5/19/2014
 */

package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.model.Department;
import com.example.model.LabService;

public class LabServiceAdapter extends Data {

	public  LabServiceAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("DatabaseHandler", "Database Created");
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	public boolean checkLabServices(){
		//Add code for query getting number of accounts in mobile DB
		
		db = dbHandler.getReadableDatabase();
		
		String query = 
			"SELECT count(" + SERVICE_ID + ")" +
			" FROM " + TABLE_LAB_SERVICE;
		
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * @author: Jose Martin Ipong
	 * 
	 */
	
	public void insertLabServices(ArrayList<LabService> lab_service){
		db = dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		System.out.println("Number of lab services: " + lab_service.size());
		try {
			
			for(int i = 0; i < lab_service.size(); i++) {
				System.out.println("new: " + lab_service.get(i).getOpd() + " " + lab_service.get(i).getIpd());
				values.put(SERVICE_ID, lab_service.get(i).getServiceCode());	
				values.put(SECTION, lab_service.get(i).getLabSectionName());	
				values.put(SERVICE, lab_service.get(i).getLabServiceName());
				values.put(SECTION_CODE, lab_service.get(i).getSectionCode());	
				values.put(OPD, lab_service.get(i).getOpd());	
				values.put(IPD, lab_service.get(i).getIpd());	
			    db.insertWithOnConflict(TABLE_LAB_SERVICE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
			  }
			  
				Log.d("LabServiceAdapter insertLabServices", "setTransactionSuccessful");
			}
			catch (SQLException se) {
				Log.d("LabServiceAdapter insertLabServices", Log.getStackTraceString(se));
			}
			finally
			{
			  
			  db.close();
			}
	}
	
	/*
	 * @Author: Jose Martin Ipong
	 * 
	 */
	public ArrayList<LabService> getLabServices() {
		db = dbHandler.getWritableDatabase();
		ArrayList<LabService> labservicelist = new ArrayList<LabService>();
		String query = "SELECT service_id, name_section, name_service, section_code, opd, ipd FROM lab_service";
		try {
			Cursor cursor = db.rawQuery(query, null);
			
			if(cursor.moveToFirst()){
				do {
					LabService lab_service = new LabService(cursor.getString(cursor.getColumnIndex(SERVICE_ID)),
							cursor.getString(cursor.getColumnIndex(SERVICE)),
							cursor.getString(cursor.getColumnIndex(SECTION_CODE)),
							cursor.getString(cursor.getColumnIndex(SECTION)),
							cursor.getString(cursor.getColumnIndex(OPD)),
							cursor.getString(cursor.getColumnIndex(IPD))
							);
					//System.out.println(lab_service);
					//System.out.println(cursor.getString(cursor.getColumnIndexOrThrow(DEPT)));
					labservicelist.add(lab_service);
				}while(cursor.moveToNext());
				Log.d("LabServiceAdapter getLabServices method", "successful");
				db.close();
				return labservicelist;
			}
			
		}
		catch(SQLException se) {
			Log.d("LabServiceAdapter getLabServices method",Log.getStackTraceString(se));
		} finally {
			db.close();
		}
		return null;	
	}
	
	public ArrayList<String> getSectionNames(){
		db = dbHandler.getReadableDatabase();
		ArrayList<String> sectionnames = new ArrayList<String>();
		String query = "SELECT DISTINCT " + SECTION + " FROM " + TABLE_LAB_SERVICE;
		try{
			Cursor cursor = db.rawQuery(query, null);
			if(cursor.moveToFirst()){
				do{
					String section_name = cursor.getString(cursor.getColumnIndex(SECTION));
					sectionnames.add(section_name);
				}while(cursor.moveToNext());
				Log.d("LabServiceAdapter getSectionNames method", "successful");
				db.close();
				return sectionnames;
			}
		}
		catch(SQLException se) {
			Log.d("LabServiceAdapter getSectionNames method",Log.getStackTraceString(se));
		} finally {
			db.close();
		}
		return sectionnames;
	}
	
	
	
}
