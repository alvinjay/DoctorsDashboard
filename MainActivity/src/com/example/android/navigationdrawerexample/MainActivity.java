package com.example.android.navigationdrawerexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends InitialActivity {
	private ProgressDialog pd;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
    }
    
    public void showPatientActivity(View view){

    	/* display progress dialog */
		pd.setMessage("Retrieving patients..");
		pd.show();
		
    	Intent intent = new Intent(this, PatientActivity.class);
    	startActivity(intent);
    }
    
    public void showLaboratoryActivity(View view){
    	Intent intent = new Intent(this, LaboratorySearchActivity.class);
    	startActivity(intent);
    }
    
    @Override
	protected void onResume() {
		
		super.onResume();
		pd.dismiss();
	}

	public void showMedicalHistoryActivity(View view){
    	Intent intent = new Intent(this, CanvassActivity.class);
    	startActivity(intent);
    }
    
    public void showResultsActivity(View view){
    	Intent intent = new Intent(this, ResultsActivity.class);
    	startActivity(intent);
    }
    
    public void showCalculationsActivity(View view){
    	Intent intent = new Intent(this, CalculationsActivity.class);
    	startActivity(intent);
    }
    
    public void showLoginActivity(View view){
    	logMessage("logout");
    	resetPreferences();
        intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        startActivity(intent);
    }
    
    public void showCanvassActivity(View view){
    	Intent intent = new Intent(this, CanvassActivity.class);
    	startActivity(intent);
    }
    
}
