package com.example.android.navigationdrawerexample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DoctorsNotes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctors_notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctors_notes, menu);
		return true;
	}

}
