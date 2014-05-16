package com.example.walldefence;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;

public class UpgradeMenuActivity extends Activity{
	private Button playGame;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_upgrade_menu);
		playGame = (Button)findViewById(R.id.UMPlayGame);
		playGame.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent startGameActivity = new Intent(UpgradeMenuActivity.this,GameActivity.class);
				startActivity(startGameActivity);
			}	
		});
	}
}
