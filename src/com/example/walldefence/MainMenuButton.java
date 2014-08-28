package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class MainMenuButton extends Button{
	private Bitmap touchedBitmap;
	
	public MainMenuButton(Assets assets, int i, int x, int y){
		super(i, x, y);
		// TODO Auto-generated constructor stub
		this.getBitmaps(this.buttonNumber, assets, i, x, y);
		this.body = new Rect(this.x, this.y-height, this.x+width, this.y);
	}

	//get actual measurements later
	public void getBitmaps(int buttonNumber, Assets assets, int i, int x, int y){
		switch(buttonNumber){
		case 1:
			main_button = assets.getNewGameButtonUp();
			//--- Update with proper images ---
			touchedBitmap = assets.getNewGameButtonDown();
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = x/2 - main_button.getWidth()/2;
			this.y = y/2 - main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		case 2:
			main_button = assets.getContinueButtonUp();
			//--- Update with proper images ---
			touchedBitmap = assets.getContinueButtonDown();
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = x/2 - main_button.getWidth()/2;
			this.y = y/2 + 3*main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		case 3:
			main_button = assets.getSettingsButtonUp();
			//--- Update with proper images ---
			touchedBitmap = assets.getSettingsButtonDown();
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = x/4 - main_button.getWidth()/2;
			this.y = 2*y/3 - main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		case 4:
			main_button = assets.getHighScoresButtonUp();
			//--- Update with proper images ---
			touchedBitmap = assets.getHighScoresButtonDown();
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = 3*x/4 - main_button.getWidth()/2;
			this.y = 2*y/3 - main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		default:
			Log.d(TAG,"ERROR - Number outside of scope of main menu buttons: " + buttonNumber);
			break;
		}
	}
	
	public void drawBitmap(Canvas canvas){
		if(!touch_down){
			canvas.drawBitmap(main_button, x, y-height, null);
		}
		else{
			canvas.drawBitmap(touchedBitmap, x, y-height, null);
		}
	}
}
