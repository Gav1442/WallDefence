package com.example.walldefence;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity{
	private final String TAG = "GameActivity";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// set our MainGamePanel as the View
		setContentView(new MainGamePanel(this));
		Log.d(TAG, "View added");
		
	}
	
	public void onPause(){
		
	}
	
	protected void onDestroy(){
		Log.d(TAG,"Destroying...");
	}
	protected void onStop(){
		Log.d(TAG,"Stopping");
	}
}
