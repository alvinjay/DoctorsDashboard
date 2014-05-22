package com.example.android.navigationdrawerexample;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.database.LabServiceAdapter;
import com.example.model.Department;
import com.example.model.LabService;
import com.example.model.Rest;
import com.example.parser.DepartmentParser;

public class LaboratoryRequestAddService extends Activity {
	ArrayList<Department> departments;
	String url_department = "http://121.97.45.242/segservice/department/show/";
	String url_service = "http://121.97.45.242/segservice/laboratory/show/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_laboratory_request_add_service);
		// Show the Up button in the action bar.
		setupActionBar();
		//department spinner
		
		Rest rest = new Rest("GET",this, "");
		rest.setURL(url_department);
		rest.execute();
		while(rest.getContent() == null){}
		
		if(rest.getResult()){
			String content = rest.getContent();
			DepartmentParser department_parser = new DepartmentParser(content);
			departments = department_parser.getDepartments();
		}
		//Populate department spinner
		Spinner spinner_department = (Spinner) findViewById(R.id.requestingDepartmentSpinner);
		ArrayAdapter<Department> array_adapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item, departments);
		spinner_department.setAdapter(array_adapter);
		LabServiceAdapter db = new LabServiceAdapter(this);
		
		//Populate section name spinner
		Spinner spinner_lab_service = (Spinner) findViewById(R.id.laboratoryServiceSectionSpinner);
		ArrayList<String> sectionnames = new ArrayList<String>();
		sectionnames = db.getSectionNames();
		ArrayAdapter<String> array_adapter_service_name = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sectionnames);
		spinner_lab_service.setAdapter(array_adapter_service_name);
		
		//Populate ListView with services
		ListView listview_services = (ListView) findViewById(R.id.servicesList);
		final ArrayList<LabService> labservices;
		labservices = db.getLabServices();
		ArrayAdapter<LabService> array_adapter_lab_service = new ArrayAdapter<LabService>(this, android.R.layout.simple_list_item_2, android.R.id.text1, labservices){
        	//method to override the getView method of ArrayAdapter, this changes the color of the text view
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent) {
        		View view = super.getView(position, convertView, parent);
        	    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        	    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
        	    text1.setTextColor(Color.BLACK);
        	    text2.setTextColor(Color.BLACK);
        	    LabService labservice = labservices.get(position);
        	    text1.setText(labservice.toString());
        	    text2.setText(labservice.getLabSectionName());
        	    
        	    return view;
        	  }
        	};
		listview_services.setAdapter(array_adapter_lab_service);
		//System.out.println(labservices);
	
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.laboratory_request_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
