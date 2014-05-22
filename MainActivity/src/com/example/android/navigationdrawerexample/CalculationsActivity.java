package com.example.android.navigationdrawerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculationsActivity extends InitialActivity {

	private String feet;
	private String inches;
	private String pounds;
	
	/* UI layout references */
	private EditText et_feet;
	private EditText et_inches;
	private EditText et_pounds;
	private TextView et_rating;
	private TextView tv_bmi;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculations);

	}
	
	/* retrieves inputted text by user and assigns it to a variable */
	private void setInputText(){
		et_feet = (EditText) findViewById(R.id.feet);
		et_inches = (EditText) findViewById(R.id.inches);
		et_pounds = (EditText) findViewById(R.id.pounds);
	}
	
	/* retrieves inputted text by user and converts/saves it as String */
	private void convertInputText(){
		feet = et_feet.getText().toString();
		inches = et_inches.getText().toString();
		pounds = et_pounds.getText().toString();
	}
	
	/* called when the "Sign in" button is clicked */
	public void handleInputs(View view){
		
		setInputText();
		
		/* Convert data type from EditText -> Editable -> String */ 
		convertInputText();
		
		//* Validate inputs from user (i.e. empty field(s), wrong inputs) */
		if(validateInputs()){
			computeBMI();
		}
			else {
				Toast.makeText(getApplicationContext(), "Failed to Calculate", Toast.LENGTH_SHORT).show();
			}
	}
	
	/* validatesInputs - Validates the input of the user for logging in */		 
	public boolean validateInputs(){
		boolean cancel = false; //for flagging; will be equal to true if there are errors
		View focusView = null; //refers to the EditText View that will be focused if there are errors
		
		/* there must be a value in the field */
		if (feet.isEmpty()){
			et_feet.setError(getString(R.string.error_field_required));
			focusView = et_feet;
			cancel = true;
		}
		
		/* there must be a value in the field */
		if (inches.isEmpty()){
			et_inches.setError(getString(R.string.error_field_required));
			focusView = et_inches;
			cancel = true;
		}
		
		/* there must be a value in the field */
		if (pounds.isEmpty()){
			et_pounds.setError(getString(R.string.error_field_required));
			focusView = et_pounds;
			cancel = true;
		}
		
		if(cancel){
			focusView.requestFocus();
			return false;
		}
		
		else {
			return true;
		}
	}

	/* computes the BMI of the given feet, inches and pounds */
	public Float computeBMI() {
		System.out.println("feet "+feet);
		System.out.println("inches "+inches);
		System.out.println("pounds "+pounds);
		Float dividend = Float.parseFloat(pounds) * 703;
		Float divisor = ((Float.parseFloat(feet)*12)+Float.parseFloat(inches));
		divisor *= divisor;
		System.out.println("");
		
		return dividend/divisor;
	}

	public void displayRating(View v) {
		setInputText();
		
		/* Convert data type from EditText -> Editable -> String */ 
		convertInputText();
	
		if(validateInputs()){
			Float BMI = computeBMI();
			System.out.println("bmi "+BMI);
			if(BMI < 18.5) {
				setCalculation(BMI, "Underweight");
			}	
			else if(BMI < 25 && BMI >= 18.5  ) {
				setCalculation(BMI,"Normal Weight");
			}
			else if(BMI < 30 && BMI >= 25) {
				setCalculation(BMI,"Overweight");
			}
			else if (BMI >= 30) {
				setCalculation(BMI,"Obesity");
			}
		}
	}
	
	public void setCalculation(Float BMI, String rating) {
		tv_bmi = (TextView) findViewById (R.id.bmi);
		tv_bmi.setText(String.valueOf(BMI));
		et_rating = (TextView) findViewById (R.id.rating);
		et_rating.setText(rating);
	}
}
