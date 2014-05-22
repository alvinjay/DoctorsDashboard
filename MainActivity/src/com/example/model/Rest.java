package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;



//	To import Resting, copy the .JAR files in Alvin's Seg Dropbox 
//folder to the 'libs' folder of the chosen project. 
//Go to Project > Properties > Java Build Path > Libraries > Add external JARS
//and select all the JAR files
import com.google.resting.Resting;
import com.google.resting.component.EncodingTypes;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.BasicRequestParams;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.method.post.PostHelper;
import com.google.resting.method.put.PutHelper;

public class Rest extends AsyncTask<String, Void, Void>{


	private ProgressDialog pd;
	
	private final String USERNAME = "emr";
	private final String PASSWORD = "3mrh1s";
	
	private RequestParams 		params;
	private List<Header> 		headers;
	private ServiceResponse 	response;
	
	private String 			    content;
	private String 				url;
	private String 				method;	
	private int 				port;
	private boolean 			result;
	private ProgressDialog      progressdialog; 
	private Context             context;

	private String 				message;
	private String 				success;
	
	
	public Rest(String method){
		params = new BasicRequestParams();
		headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Authorization","Basic " + 
		Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(),Base64.NO_WRAP)));
		port = 80;
		this.method = method;
		this.context = null;
		
		//temp
		System.out.println(method);
	}
	
	public Rest(String method, Context context, String message){
		params = new BasicRequestParams();
		headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Authorization","Basic " + 
		Base64.encodeToString((USERNAME + ":" + PASSWORD).getBytes(),Base64.NO_WRAP)));
		port = 80;
		this.method = method;
		this.context = context;
		
		pd = new ProgressDialog(context);
		pd.setMessage(message);
	    pd.setIndeterminate(true);
	    pd.setCancelable(false);

		
	}
	
	
	//adds key : value params
	public void addRequestParams(String arg0, String arg1){
		params.add(arg0, arg1);
	}
	
	public void addToJSON(String key, String value){
		JSONObject Data = new JSONObject();
		try{			
			Data.put(key,value);
		} catch(JSONException e){
			System.out.println(e.getMessage().toString());
		}
	}
	
	/* Setter methods */
	
	public void setURL(String url){
		this.url = url;
	}

	private void setMessage(){
		
		try {
			message = new JSONObject(content).optString("message");
		} catch (JSONException e) {
			System.out.println(e.toString() + ": no message node");
		}
	}
	
	private void setSuccess(){
		
		try {
			success = new JSONObject(content).optString("success");
		} catch (JSONException e) {
			System.out.println(e.toString() + ": no success node");
		}
	}
	
	/* Getter Methods */
	
	public String getURL(){
		return url;
	}
	
	public String getContent(){
		return content;
	}
	
	public ServiceResponse getResponse(){
		return response;
	}
	
	public boolean getResult(){
		return result;
	}
	
	public String getMessage(){
		return message;
	}

	public String getSuccess(){
		return this.success;
	}
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try{
			pd.show();
		} catch(NullPointerException e){
			System.out.println(e.toString() + ": no message for dialog");
		}
	}

	@Override
	protected Void doInBackground(String... params) {
		System.out.println(this.params);
		
		if(method.equals("GET")){
			try{
				response = Resting.get(url, this.params, EncodingTypes.UTF8, headers, 0);
				result = true;
	        } catch(Exception e){
				result = false;
			}
		}
		else if(method.equals("POST")){
			try{
				response = PostHelper.post(url, port, EncodingTypes.UTF8, this.params,headers, null);
				result = true;
	        } catch(Exception e){
				result = false;
			}
		}else if(method.equals("PUT")){
			try{
				response = PutHelper.put(url, EncodingTypes.UTF8, port, this.params, headers, null);
				result = true;
	        } catch(Exception e){
				result = false;
			}
		}
		
		try{
			content = response.getResponseString();
		}catch(Exception e){
			System.out.println("Empty Content");
		}
		
		if(!content.isEmpty()){
			setMessage();
			result = true;
		}
		
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try{
			pd.dismiss();
		} catch(Exception e){
			
		}
	}
}
