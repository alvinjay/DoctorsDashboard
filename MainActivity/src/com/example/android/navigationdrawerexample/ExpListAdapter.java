/**
 * @author Jake Randolph B Muncada
 */

package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class ExpListAdapter extends BaseExpandableListAdapter {

	private Activity activity;
	private ArrayList<Object> childItems;
	private LayoutInflater inflater;
	private ArrayList<String> parentItems;
	private ArrayList<Object> child;

	public ExpListAdapter(ArrayList<String> parents, ArrayList<Object> children) {
		this.parentItems = parents;
		this.childItems = children;
	}

	public void setInflater(LayoutInflater inflater, Activity activity) {
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

		//child = (ArrayList<String>) childItems.get(groupPosition);
		child = (ArrayList<Object>) childItems.get(groupPosition);

		TextView textView = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.explist_encounter_group, null);
		}
		
		String out = child.get(childPosition).toString();

		textView = (TextView) convertView.findViewById(R.id.textView1);
		textView.setText(out);
		
		textView.setOnClickListener(new MyOnClickListener(childPosition, child));
		convertView.setOnClickListener(new MyOnClickListener(childPosition, child));
		
		/*
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				
				int eid = child.get(childPosition).getEncounterId();
				Bundle bundle = new Bundle();
				bundle.putInt("EXTRA_ENCOUNTER_ID", eid);
				Intent intent = new Intent(view.getContext(), PatientEncounterActivity.class);
				intent.putExtras(bundle);
				view.getContext().startActivity(intent);
				
				//Toast.makeText(activity, child.get(childPosition).toString(),
				//		Toast.LENGTH_SHORT).show();
			}
			
		});
		*/
		return convertView;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.explist_encounter_row, null);
		}

		((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
		((CheckedTextView) convertView).setChecked(isExpanded);
		
		
		
		return convertView;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return ((ArrayList<String>) childItems.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		//super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		//lastExpandedGroupPosition = groupPosition;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		// Edited from false to true on 5/14/2014
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// Edited from false to true on 5/14/2014
		return true;
	}

}