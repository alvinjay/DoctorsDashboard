/**
 * @author Jake Randolph B Muncada
 */

package com.example.android.navigationdrawerexample;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.example.android.navigationdrawerexample.MyOnClickListener;
import com.example.database.DatabaseAdapter;
import com.example.model.Encounter;

public class PatientEncounterActivity extends ExpandableListActivity{

	private ArrayList<String> parentItems = new ArrayList<String>();
	private ArrayList<Object> childItems = new ArrayList<Object>();
	private ArrayList<Object> child;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());

		// this is not really  necessary as ExpandableListActivity contains an ExpandableList
		//setContentView(R.layout.main);

		final ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

		expandableList.setDividerHeight(2);
		expandableList.setGroupIndicator(null);
		expandableList.setClickable(true);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		int encounter_id = bundle.getInt("EXTRA_ENCOUNTER_ID");
		Encounter encounter = db.getEncounter(encounter_id);
		int patient_id = encounter.getPID();
		String date_encountered = encounter.getDateEncountered().trim().substring(0,10);
		
		setGroupParents();
		setChildData(patient_id, encounter_id, date_encountered);
		//setDoctorsNotes(encounter_id);

		final ExpListAdapter adapter = new ExpListAdapter(parentItems, childItems);

		adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
		expandableList.setAdapter(adapter);
		
		/*
		expandableList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick (ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
				Toast.makeText(getApplicationContext(), "BOOM! " + groupPosition + " " + childPosition, Toast.LENGTH_SHORT).show();
				switch (groupPosition) {
					case INDEX_MEDICAL_HISTORY:
						
						int eid = ((Encounter)child.get(childPosition)).getEncounterId();
						Bundle bundle = new Bundle();
						bundle.putInt("EXTRA_ENCOUNTER_ID", eid);
						Intent intent = new Intent(view.getContext(), PatientEncounterActivity.class);
						intent.putExtras(bundle);
						view.getContext().startActivity(intent);
						
						switch (childPosition){
						case 1:
							Toast.makeText(getApplicationContext(), "med hist! " + groupPosition + " " + childPosition, Toast.LENGTH_SHORT).show();
							break;
						default:
							Toast.makeText(getApplicationContext(), "this is wrong", Toast.LENGTH_SHORT).show();
							break;
						}
						break;
					case INDEX_PREVIOUS_REQUESTS:
						Toast.makeText(getApplicationContext(), "prev req! " + groupPosition + " " + childPosition, Toast.LENGTH_SHORT).show();
						break;
					case INDEX_REFERRALS:
						Toast.makeText(getApplicationContext(), "referral! " + groupPosition + " " + childPosition, Toast.LENGTH_SHORT).show();
						break;
					case INDEX_NOTES:
						Toast.makeText(getApplicationContext(), "notes! " + groupPosition + " " + childPosition, Toast.LENGTH_SHORT).show();
						break;
					default:
						Log.e("onClick groupPosition", "Error on groupPosition: Should not reach default.");
						break;
				}
				return false;
			}
			
		});*/
		
		expandableList.setOnChildClickListener(new OnChildClickListener() {

		    @Override
		    public boolean onChildClick(ExpandableListView parent, View v,
		            int groupPosition, int childPosition, long id) {
		    	Toast.makeText(getApplicationContext(),"groupPosition:"+groupPosition + "childPosition:" + childPosition+ "id:" + id, Toast.LENGTH_SHORT).show();
		        return false;
		    }
		});
		
		expandableList.setOnGroupExpandListener(new OnGroupExpandListener(){
			@Override
			public void onGroupExpand(int groupPosition){
				MyOnClickListener.setLastExpandedGroupPosition(groupPosition);
				int len = adapter.getGroupCount();
				
				for(int i=0; i<len; i++){
					if(i != groupPosition){
						expandableList.collapseGroup(i);
					}
				}
				
				//Toast.makeText(getApplicationContext(),"groupPosition:"+groupPosition+" ", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void setGroupParents() {
		parentItems.add("Medical History");
		parentItems.add("Previous Requests");
		parentItems.add("Referrals");
		parentItems.add("Notes");
	}
	
	public void setChildData(int patient_id, int encounter_id, String date_encountered) {

		ArrayList<Object> child = new ArrayList<Object>();
		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
		
		ArrayList<Encounter> encounterList = db.getPreviousEncounter(patient_id, date_encountered);
		for (int i = 0; i < encounterList.size(); i++) {
			child.add(encounterList.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		for (int i = 0; i < encounterList.size(); i++) {
			child.add(encounterList.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		
		for (int i = 0; i < encounterList.size(); i++) {
			child.add(encounterList.get(i));
		}
		childItems.add(child);
		child = new ArrayList<Object>();
		/*
		ArrayList<Soap> soapList = db.getDoctorNotes(encounter_id);
		child.add("ADD NEW NOTES");
		for (int i = 0; i < soapList.size(); i++) {
			child.add(soapList.get(i));
		}
		childItems.add(child);
		*/
		/*
		// Android
		ArrayList<String> child = new ArrayList<String>();
		child.add("Core");
		child.add("Games");
		childItems.add(child);
		*/

		/*
		// Core Java
		child = new ArrayList<String>();
		child.add("Apache");
		child.add("Applet");
		child.add("AspectJ");
		child.add("Beans");
		child.add("Crypto");
		childItems.add(child);

		// Desktop Java
		child = new ArrayList<String>();
		child.add("Accessibility");
		child.add("AWT");
		child.add("ImageIO");
		child.add("Print");
		childItems.add(child);

		// Enterprise Java
		child = new ArrayList<String>();
		child.add("EJB3");
		child.add("GWT");
		child.add("Hibernate");
		child.add("JSP");
		childItems.add(child);
		*/
	}
	/*
	 * Edited by Jessie Emmanuel Adante
	 * Edited to include doctor's notes

	public void setDoctorsNotes(int eid)
	{
		ArrayList<Soap> child = new ArrayList<Soap>();
		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
		
		ArrayList<Soap> notelist = db.getDoctorNotes(eid);
		for (int i = 0; i < notelist.size(); i++) {
			child.add(notelist.get(i));
		}
		childItems.add(child);
	}*/

}