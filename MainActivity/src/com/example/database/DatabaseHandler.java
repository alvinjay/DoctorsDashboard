/*
 *  Created By: Christian Joseph Dalisay
 *  Created On: 05/01/14
 *  Edited On: 05/06/14
 *  DataHandler Class - class handles the creation, & upgrade of database
 */

package com.example.database;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	public DatabaseHandler(Context context, String name, CursorFactory factory, int version)  {
	    super(context, name, factory, version);
	    Log.d("DatabaseHandler", "Database Created");
	}
	
	//	----------------TABLE CREATION METHODS----------------
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
			db.execSQL(Data.CREATE_TABLE_CLIENT);
			db.execSQL(Data.CREATE_TABLE_DOCTOR);
			db.execSQL(Data.CREATE_TABLE_DEPARTMENT);
			db.execSQL(Data.CREATE_TABLE_PATIENT);
			db.execSQL(Data.CREATE_TABLE_ENCOUNTER);
			db.execSQL(Data.CREATE_TABLE_DOC_ENC);
			db.execSQL(Data.CREATE_TABLE_REFERRAL);
			db.execSQL(Data.CREATE_TABLE_REASON);
			db.execSQL(Data.CREATE_TABLE_LAB_REQUEST);
			db.execSQL(Data.CREATE_TABLE_LAB_SERVICE);
			db.execSQL(Data.CREATE_TABLE_SERVICE_REQUEST);
			db.execSQL(Data.CREATE_TABLE_LAB_RESULT);
			db.execSQL(Data.CREATE_TABLE_NOTES);
			
			//onCreateDummy(db);
			Log.d("DatabaseHandler","Database onCreateTables Successful");
		} catch (SQLException se) {
			System.out.println("did not");
			Log.d("onCreateTables SQLException",Log.getStackTraceString(se));
		} catch (Exception e) {
			Log.d("onCreateTables Exception",Log.getStackTraceString(e));
		}

		try {//initial indexing
			db.execSQL("CREATE INDEX idx_encounter ON " + Data.TABLE_ENCOUNTER + " (" + Data.ENCOUNTER_ID + " )");
			db.execSQL("CREATE INDEX idx_license ON " + Data.TABLE_DOCTOR + " (" + Data.LICENSE_NO + " )");
			db.execSQL("CREATE INDEX idx_authtoken ON " + Data.TABLE_DOCTOR + " (" + Data.AUTH + " )");
			Log.d("DatabaseHandler","Database onCreateIndexes Successful");
		} catch (SQLException se) {
			Log.d("onCreateIndexes SQLException",Log.getStackTraceString(se));
		} catch (Exception e) {
			Log.d("onCreateIndexes Exception",Log.getStackTraceString(e));
		} 
	}
	
	/* Creates dummy data for testing purposes */
	public void onCreateDummy(SQLiteDatabase db) {
		try	{ 
			
			/*
			db.execSQL("insert into [department] values(106, '', 'Med-Rheuma', 'Medicine-Rheumatology'), " +
					" (107, '', 'Med-Neuro', 'Medicine-Neurology'), " +
					" (131, '', 'Opthal', 'Opthalmology'), " +
					" (133, '', 'FaMed', 'Family Medicine'), " +
					" (136, '', 'ENT-HNS', 'ENT-HNS'), " +
					" (153, '', 'Anesthesia', 'Anesthesia')");
			*/
			db.execSQL("insert into [patient] values(1, 'Cosare', 'Alvin', 'Ceniza', 'M', '1994-08-27', 'Cool Street', 'Davao', 'Davao', '8000', 0, null), " +
					"(2, 'Adante', 'Jessie', 'Boi', 'M', '1992-12-13', 'That Street', 'Davao', 'Davao', '8000', null, null), " +
					"(3, 'Ipong', 'Jose Martin', 'dela Rosa', 'M', '1994-08-29', 'The Street', 'Davao', 'Davao', '8000', null, null), " +
					"(4, 'Adande', 'Jek', 'Portree', 'M', '1990-05-17', 'Wew Street', 'Davao', 'Davao', '8000', null, null), " +
					"(5, 'Ryan', 'Caleb', 'Kaka', 'M', '1987-09-12', 'Super Street', 'Davao', 'Davao', '8000', null, null), " +
					"(6, 'Izmailova', 'Sabine', 'Vaike', 'F', '1983-05-21', 'Dark Street', 'Davao', 'Davao', '8000', null, null), " +
					"(7, 'Alekseyeva', 'Chariton', 'Sirguvere', 'M', '1992-06-21', 'That Street', 'Davao', 'Davao', '8000', null, null), " +
					"(8, 'Vinogradov', 'Jaroslav', 'Tammemae', 'M', '1981-02-12', 'That Street', 'Davao', 'Davao', '8000', null, null), " +
					"(9, 'Nevzorova', 'Valeriya', 'Purila', 'F', '1984-12-16', 'That Street', 'Davao', 'Davao', '8000', null, null), " +
					"(10, 'Kikoku', 'Koku', 'Shimasaki', 'F', '1983-11-02', 'That Street', 'Davao', 'Davao', '8000', null, null), " +
					"(11, 'Ricci', 'Massimo', 'Piazza', 'M', '1986-12-04', 'Corsagna', 'Bologna', 'Bologna', '8000', null, null), " +
					"(12, 'Bordeaux', 'Rabican', 'Bellefeuille', 'M', '1991-10-13', 'Boulevard de Prague', 'Nimes', 'Nimes', '25223', null, null), " +
					"(1000000, 'Montelibano', 'Julie', 'Himo', 'f', '2007-07-07', 'Commonal', 'Davao City', 'Davao del Sur', '8000', null, null), " +
					"(1000001, 'Diamona', 'Francis', 'Garong', 'm', '2003-04-30', 'Tuban', 'Davao City', 'Davao del Sur', '8000', null, null), " +
					"(1086278, 'Sarael', 'Val', 'Teofilo', 'm', '1974-10-08', '#250 BLK III SIR', 'Davao City', 'Davao del Sur', '8000', null, null), " +
					"(1158851, 'Satinitigan', 'Val', 'Repalda', 'f', '2004-02-14', 'KM. 13, ZONE II', 'Davao City', 'Davao del Sur', '8000', null, null), " +
					"(1234567, 'Sayman', 'Val Anthony', 'Garong', 'm', '2006-07-19', 'Kabacan Ecoland', 'Davao City', 'Davao del Sur', '8000', null, null)");
		    /*db.execSQL("INSERT INTO 'doctor' ('personnel_id','license_no','dept_id','name_last','name_first','name_middle') " +
				" VALUES (100022,'1234560',133,'Aquino','Aljun','Galaura'), " +
				"	(100027,'1234567',133,'Gaurino','Marc','Gonzales'), " +
				"	(100040,'1234568',133,'Caralos','Rex Arnold','Mesiona'), " +
					"	(100054,'1234569',131,'Chin','Elizabeth May','Tan') "  );*/
			/*
			 * db.execSQL("INSERT INTO doctor values(100055, '0117236', 133, 'ESPA￑OLA', 'SEURINANE SEAN', 'BORRA', '97ffcb8e6d1014d2752cadd03b1ac6ca8b4a63d0', '28b0f836cc41361b90ce15a7a28ac8b3a9363f5b', 'm', '1981-05-12', '121.97.45.242')");
			 */
			db.execSQL("insert into [encounter] (encounter_id, pid, type_patient,message_complaint, date_encountered,official_receipt_nr,is_confidential)" + 
						" values(1, 1, 'In-Patient', 'Fever', '2012-07-01 00:00:00.000',null,null)," +
						"(2, 2, 'In-Patient', 'Headache', '2013-06-02 00:08:00.000',null,null)," +
						"(3, 1, 'In-Patient', 'Stomach ache', '2014-01-03 00:00:00.000',null,null)," +
						"(4, 1, 'In-Patient', 'Flu', '2014-05-12 00:00:00.000',null,null)," +
						"(5, 1, 'In-Patient', 'Kalibanga', '2014-05-11 00:00:00.000',null,null)," +
						"(2008000000, 1000000, 'Inpatient from ER',null, '2009-02-09 11:47:00.000',null,null)," +
						"(2008000001, 1000000, 'OPD',null, '2008-11-24 16:05:43.000',null,null)," +
						"(2008000002, 1000000, 'OPD',null, '2008-12-24 08:07:43.000',null,null)");
			db.execSQL("insert into doctor_encounter (personnel_id,encounter_id) values ('0117236',2),('0117236',2),('0117236',3)");
			db.execSQL("insert into [client] values(1, '06778975-75e3-4da2-9e1e-866b222e0fa6')");
			/*db.execSQL("INSERT INTO 'reason' ('reason_id','name_reason') " +
				" VALUES (1,'Surgery'), " + 
				"   (2,'Dialysis'), " +
				"	(3,'ER')"); 
			db.execSQL("INSERT INTO lab_request ('request_id',encounter_id,personnel_id,date_requested) VALUES (1,1,100055,'2011-01-01 00:00:00'),(2,1,100055,'2011-02-01 00:00:00')");
			db.execSQL("INSERT INTO lab_result ('result_id','request_id','date_received','name_test','message_hl7','name_patho') " + 
					"VALUES(1,1,'2011-01-01 00:00:00','asd','asd','asd'), " + 
					"(2,1,'2011-01-01 00:00:00','asd','asd','asd')");
			db.execSQL("INSERT INTO 'referral' ('referral_id','encounter_id','dept_id','reason_id','date_referred') " + 
				" VALUES (1,1,107,1,'2013-06-02 00:12:00' ), " + 
				" (2,2,131,2,'2013-08-10 12:12:00' ), " + 	
				" (3,3,136,3,'2014-02-20 07:08:00' ) " );
			db.execSQL("INSERT INTO 'notes' ('notes_id','encounter_id','title','body','date_created','sync') " + 
					" VALUES ('1',1,'First Encounter','Encountered for the first time','2013-06-02 00:12:00',0 ), " + 
					" ('2',2,'Second Encounter','Encountered for the first time','2013-08-10 12:12:00',0 ), " + 	
					" ('3',3,'Third Encounter','Encountered for the first time','2014-02-20 07:08:00',0 ) " );*/
			Log.d("DatabaseHandler","onCreateDummy successful");
			
		} catch (SQLException se) {
			Log.d("onCreateDummy SQLException",Log.getStackTraceString(se));
		} catch (Exception e) {
			Log.d("onCreateDummy Exception",Log.getStackTraceString(e));
		} finally {
			db.close();
		}
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_CLIENT);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_DOCTOR);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_DEPARTMENT);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_PATIENT);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_ENCOUNTER);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_DOC_ENC);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_REFERRAL);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_REASON);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_LAB_REQUEST);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_LAB_SERVICE);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_SERVICE_REQUEST);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_LAB_RESULT);
			db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_NOTES);
			
			onCreate(db);
			//better if there is a backup of a table
			Log.d("DatabaseHandler","Database onDropTables Successful");
		} catch (SQLException se) {
			Log.d("onDropTables SQLException",Log.getStackTraceString(se));
		} catch (Exception e) {
			Log.d("onDropTables Exception",Log.getStackTraceString(e));
		}
		
		try {
			db.execSQL("DROP INDEX " + Data.TABLE_DOCTOR + ".idx_encounter" );
			db.execSQL("DROP INDEX " + Data.TABLE_DOCTOR + ".idx_license" );
			db.execSQL("DROP INDEX " + Data.TABLE_DOCTOR + ".idx_authtoken" );
			
			onCreate(db);
			Log.d("DatabaseHandler","Database onDropIndexes Successful");
		} catch (SQLException se) {
			Log.d("onDropIndexes SQLException",Log.getStackTraceString(se));
		} catch (Exception e) {
			Log.d("onDropIndexes Exception",Log.getStackTraceString(e));
		}
	}
}
