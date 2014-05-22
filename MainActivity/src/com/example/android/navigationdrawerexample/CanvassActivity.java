package com.example.android.navigationdrawerexample;

import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CanvassActivity extends Activity implements OnClickListener,
    OnTouchListener {

  ImageView chosenImageView;
  Button choosePicture;
  Button savePicture;
  Button viewCanvas;
  Button eraseCanvas;
  Uri current;

  Bitmap bmp;
  Bitmap alteredBitmap;
  Canvas canvas;
  Paint paint;
  Matrix matrix;
  float downx = 0;
  float downy = 0;
  float upx = 0;
  float upy = 0;
 
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_canvass);

    chosenImageView = (ImageView) this.findViewById(R.id.chosenImageView);
    choosePicture = (Button) this.findViewById(R.id.ChoosePictureButton);
    savePicture = (Button) this.findViewById(R.id.SavePictureButton);
    viewCanvas = (Button) this.findViewById(R.id.ViewImageButton);
    eraseCanvas = (Button) this.findViewById(R.id.eraseButton);

    savePicture.setOnClickListener(this);
    choosePicture.setOnClickListener(this);
    chosenImageView.setOnTouchListener(this);
    viewCanvas.setOnClickListener(this);
    eraseCanvas.setOnClickListener(this);
  }

  public void onClick(View v) {

    if (v == choosePicture) {
      Intent choosePictureIntent = new Intent(
          Intent.ACTION_PICK,
          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(choosePictureIntent, 0);
    } else if (v == savePicture) {

      if (alteredBitmap != null) {
        ContentValues contentValues = new ContentValues(3);
        contentValues.put(Media.DISPLAY_NAME, "Draw On Me");

        Uri imageFileUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        try {
          OutputStream imageFileOS = getContentResolver().openOutputStream(imageFileUri);
          alteredBitmap.compress(CompressFormat.JPEG, 90, imageFileOS);
          Toast t = Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT);
          t.show();

        } catch (Exception e) {
          Log.v("EXCEPTION", e.getMessage());
        }
      }
    }
    else if(v == viewCanvas){
    	Intent myIntent = new Intent(Intent.ACTION_VIEW, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    	startActivityForResult(myIntent, 0);
    }
    else if(v == eraseCanvas){
    	setImage(current);
    }
  }

  protected void onActivityResult(int requestCode, int resultCode,
      Intent intent) {
    super.onActivityResult(requestCode, resultCode, intent);

    if (resultCode == RESULT_OK) {
      setImage(intent.getData());
    }
  }
  public void setImage(Uri uri){
	  //Toast.makeText(this, this.getPackageName(), Toast.LENGTH_SHORT).show();
	  //Uri imageFileUri = Uri.parse("android.resource://"+ this.getPackageName()+"/drawable/body");
	  Uri imageFileUri = uri;
	  current = uri;
      try {  
    	  
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(
                imageFileUri), null, bmpFactoryOptions);

        bmpFactoryOptions.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(
                imageFileUri), null, bmpFactoryOptions);

        bmp = getResizedBitmap(bmp,chosenImageView.getHeight(),chosenImageView.getWidth());
        
        alteredBitmap = Bitmap.createBitmap(chosenImageView.getWidth(), chosenImageView.getHeight(), bmp.getConfig());
        //System.out.println(bmp.getWidth() + " " + bmp.getHeight());
        canvas = new Canvas(alteredBitmap);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        matrix = new Matrix();
        canvas.drawBitmap(bmp, matrix, paint);

        
        
        chosenImageView.setImageBitmap(alteredBitmap);
        //chosenImageView.set
        //chosenImageView.setFitToScreen(true);
        //chosenImageView.setScaleType(ScaleType.MATRIX);
        //chosenImageView.buildDrawingCache();
  	    //Bitmap test = chosenImageView.getDrawingCache();
  	    //canvas.drawBitmap(alteredBitmap,matrix,paint);
        chosenImageView.setOnTouchListener(this);
      } catch (Exception e) {
        Log.v("ERROR", e.toString());
      }
  }
  
  public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);
	    // RECREATE THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
	            matrix, false);
	    return resizedBitmap;

	}
  
  public boolean onTouch(View v, MotionEvent event) {
    int action = event.getAction();
    switch (action) {
    case MotionEvent.ACTION_DOWN:
      downx = event.getX();
      downy = event.getY();
      break;
    case MotionEvent.ACTION_MOVE:
      upx = event.getX();
      upy = event.getY();
      canvas.drawLine(downx, downy, upx, upy, paint);
      chosenImageView.invalidate();
      downx = upx;
      downy = upy;
      break;
    case MotionEvent.ACTION_UP:
      upx = event.getX();
      upy = event.getY();
      canvas.drawLine(downx, downy, upx, upy, paint);
      chosenImageView.invalidate();
      break;
    case MotionEvent.ACTION_CANCEL:
      break;
    default:
      break;
    }
    return true;
  }
  
  public boolean onCreateOptionsMenu(Menu menu){
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.canvas, menu);
	  return super.onCreateOptionsMenu(menu);
  }
  
  public boolean onOptionsItemSelected(MenuItem item){
	  Uri uri;
	  switch(item.getItemId()){
	  	case R.id.action_body:
	  		uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/drawable/body");	
	  	    setImage(uri);
		    return true;
	  	case R.id.action_body2:
	  		uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/drawable/shingeki");
	  		setImage(uri);
	  		return true;
		default: return true;  
	  }
  }
  
}