package com.example.model;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogMaker {
	
	private ProgressDialog ringProgressDialog;
	private Thread thread;
	private Rest rest;
	private boolean stop = false;
	
	public ProgressDialogMaker(Context context, String message){
		final ProgressDialog ringProgressDialog = ProgressDialog.show(context, "Please wait ...",	"Downloading Image ...", true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Here you should write your time consuming task...
					// Let the progress ring for 10 seconds...
					Thread.sleep(10000);
				} catch (Exception e) {

				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}
	
	
}
