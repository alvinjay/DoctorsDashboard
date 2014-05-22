/**
 ** Creted by Jessie Emmanuel Adante
 ** Created on 5/12/14
 ** This class retreives the age of a patient
 */

package com.example.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Age {
	private int month;
	private int day;
	private int year;
	
	public Age()
	{
		month 	= 0;
		day  	= 0;
		year	= 0;
	}
	
	public Age(int month, int day, int year)
	{
		this.month 	= month;
		this.day  	= day;
		this.year	= year;
	}
	
	public int getAge(String date) throws ParseException
	{
		date = date.trim().substring(0,10);
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = new Date();
		birthdate = dateformat.parse(date);
		Calendar dob = Calendar.getInstance();
		dob.setTime(birthdate);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
		{
			age--;
		}

		return age;
	}
}
