package com.example.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import com.example.model.Department;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DepartmentAdapter extends Data {

	public  DepartmentAdapter(Context context) 
	{
		try {
			dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.d("DatabaseHandler", "Database Created");
		} catch (Exception e) {
			Log.d("DatabaseHandler Exception", Log.getStackTraceString(e));
		}
	}
	
	public boolean checkDepartments(){
		//Add code for query getting number of accounts in mobile DB
		
		db = dbHandler.getReadableDatabase();
		
		String query = 
			"SELECT count(" + DEPT_ID + ")" +
			" FROM " + TABLE_DEPARTMENT;
		
		
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.moveToFirst()){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/* Inserts the departments by the given departments */
	public void insertDepartments(ArrayList<Department> dept){
		db = dbHandler.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		try {
			for(int i = 0; i < dept.size(); i++) {
				values.put(DEPT_ID, dept.get(i).getDepartmentNumber());	
				values.put(DEPT, dept.get(i).getDepartmentName());	
				values.put(SHORT_DEPT, dept.get(i).getDepartmentId());	
			    db.insertWithOnConflict(TABLE_DEPARTMENT, null, values,SQLiteDatabase.CONFLICT_IGNORE);
			  }
			  db.setTransactionSuccessful();
				Log.d("DepartmentAdapter insertDepartments", "setTransactionSuccessful");
			}
			catch (SQLException se) {
				Log.d("DepartmentAdapter insertDepartments", Log.getStackTraceString(se));
			}
			finally {
			  db.close();
			}
	}
	
	/* Gets all departments */
	public ArrayList<Department> getDepartments() {
		db = dbHandler.getWritableDatabase();
		ArrayList<Department> deptlist = new ArrayList<Department>();
		String query = "SELECT dept_id, short_dept, name_dept FROM department";
		try {
			Cursor cursor = db.rawQuery(query, null);
			/*If there exists a department
			 * Then, the each row is inserted into the ArrayList of Deparments
			 */
			if(cursor.moveToFirst()){
				do {
					Department dept = new Department(cursor.getInt(cursor.getColumnIndex(DEPT_ID)),
							cursor.getString(cursor.getColumnIndex(SHORT_DEPT)),
							cursor.getString(cursor.getColumnIndexOrThrow(DEPT)));
					deptlist.add(dept);
				}while(cursor.moveToNext());
				Log.d("DepartmentAdapter getDepartments", "successful");
				return deptlist;
			}
			
		}
		catch(SQLException se) {
			Log.d("DepartmentAdapter getDepartments",Log.getStackTraceString(se));
		}finally {
			db.close();
		}
		
		return null;	
	}
	
}
