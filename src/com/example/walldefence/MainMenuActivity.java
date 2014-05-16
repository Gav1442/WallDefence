package com.example.walldefence;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainMenuActivity extends Activity {
	private Button newGameButton;
	private final String TAG = "MainMenuActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main_menu);
		newGameButton = (Button)findViewById(R.id.MMNewGameButton);
		newGameButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Log.d(TAG,"New Game pressed.");
				Intent upgradeMenuIntent = new Intent(MainMenuActivity.this, UpgradeMenuActivity.class);
				startActivity(upgradeMenuIntent);		
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

}
