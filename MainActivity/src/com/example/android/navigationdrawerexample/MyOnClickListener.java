/**
 * @author Jake Randolph B Muncada
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.model.Encounter;
import com.example.model.Notes;


public class MyOnClickListener implements OnClickListener {
	
	private final int INDEX_MEDICAL_HISTORY = 0;
	private final int INDEX_PREVIOUS_REQUESTS = 1;
	private final int INDEX_NOTES = 2;
	
	private static int lastExpandedGroupPosition;
	
	int childPos = -1;
	ArrayList<Object> child;
	
	public MyOnClickListener(int childPosition, ArrayList<Object> child) { 
		this.child = child;
		this.childPos = childPosition;
	}
	
	@Override
	public void onClick(View view) {
		
		Bundle bundle = new Bundle();
		Intent intent;
		int eid, pid, sid;
		
		switch (lastExpandedGroupPosition) {
			case INDEX_MEDICAL_HISTORY:
				
				pid = ((Encounter)child.get(childPos)).getPID();
				eid = ((Encounter)child.get(childPos)).getEncounterId();
				bundle = new Bundle();
				bundle.putInt("EXTRA_PATIENT_ID", pid);
				bundle.putInt("EXTRA_ENCOUNTER_ID", eid);
				intent = new Intent(view.getContext(), PatientInfoActivity.class);
				intent.putExtras(bundle);
				view.getContext().startActivity(intent);
				
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			case INDEX_PREVIOUS_REQUESTS:
				
				eid = ((Encounter)child.get(childPos)).getEncounterId();
				bundle = new Bundle();
				bundle.putInt("EXTRA_ENCOUNTER_ID", eid);
				//intent = new Intent(view.getContext(), PatientInfoActivity.class);
				//intent.putExtras(bundle);
				//view.getContext().startActivity(intent);
				
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			case INDEX_NOTES:
				
				if (0 == childPos) {
					//eid = ((Encounter)child.get(childPos)).getEncounterId();
					//bundle = new Bundle();
					//bundle.putInt("EXTRA_ENCOUNTER_ID", eid);
					//intent = new Intent(view.getContext(), PatientInfoActivity.class);
					//intent.putExtras(bundle);
					//view.getContext().startActivity(intent);
					Toast.makeText(view.getContext(),"Going to NEW NOTE PAGE", Toast.LENGTH_SHORT).show();
					break;
				}
				
				//sid = ((Notes)child.get(childPos)).getNotes_id();
				//bundle = new Bundle();
				//bundle.putInt("EXTRA_ENCOUNTER_ID", sid);
				//intent = new Intent(view.getContext(), PatientInfoActivity.class);
				//intent.putExtras(bundle);
				//view.getContext().startActivity(intent);
				
				Toast.makeText(view.getContext(),"groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos, Toast.LENGTH_SHORT).show();
				break;
			default:
				Log.e("onClick groupPosition", "Error on groupPosition: Should not reach default.");
				Log.e("onClick groupPosition", "groupPos:"+lastExpandedGroupPosition+"\n"+"childPos:"+childPos);
				break;
		}
	}
	
	public static void setLastExpandedGroupPosition(int pos) {
		lastExpandedGroupPosition = pos;
	}
}