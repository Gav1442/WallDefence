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
	
	public MainMenuButton(Context myContext, int buttonNumber, int x, int y, float scaleWidth, float scaleHeight) {
		super(myContext, buttonNumber, x, y, scaleWidth, scaleHeight);
		// TODO Auto-generated constructor stub
		this.getBitmaps(this.buttonNumber, myContext, scaleWidth, scaleHeight, x, y);
		this.body = new Rect(this.x, this.y-height, this.x+width, this.y);
	}

	//get actual measurements later
	public void getBitmaps(int buttonNumber, Context myContext, float scaleWidth, float scaleHeight, int x, int y){
		switch(buttonNumber){
		case 1:
			main_button = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.new_game_button_up);
			main_button = Bitmap.createScaledBitmap(main_button,
					(int) (main_button.getWidth() * scaleWidth),
					(int) (main_button.getHeight() * scaleHeight), true);
			//--- Update with proper images ---
			touchedBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.new_game_button_down);
			touchedBitmap = Bitmap.createScaledBitmap(touchedBitmap,
					(int) (touchedBitmap.getWidth() * scaleWidth),
					(int) (touchedBitmap.getHeight() * scaleHeight), true);
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = x/2 - main_button.getWidth()/2;
			this.y = y/2 - main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		case 2:
			main_button = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.continue_button_up);
			main_button = Bitmap.createScaledBitmap(main_button,
					(int) (main_button.getWidth() * scaleWidth),
					(int) (main_button.getHeight() * scaleHeight), true);
			//--- Update with proper images ---
			touchedBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.continue_button_down);
			touchedBitmap = Bitmap.createScaledBitmap(touchedBitmap,
					(int) (touchedBitmap.getWidth() * scaleWidth),
					(int) (touchedBitmap.getHeight() * scaleHeight), true);
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = x/2 - main_button.getWidth()/2;
			this.y = y/2 + 3*main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		case 3:
			main_button = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.settings_button_up);
			main_button = Bitmap.createScaledBitmap(main_button,
					(int) (main_button.getWidth() * scaleWidth),
					(int) (main_button.getHeight() * scaleHeight), true);
			//--- Update with proper images ---
			touchedBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.settings_button_down);
			touchedBitmap = Bitmap.createScaledBitmap(touchedBitmap,
					(int) (touchedBitmap.getWidth() * scaleWidth),
					(int) (touchedBitmap.getHeight() * scaleHeight), true);
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = x/4 - main_button.getWidth()/2;
			this.y = 2*y/3 - main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		case 4:
			main_button = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.high_scores_button_up);
			main_button = Bitmap.createScaledBitmap(main_button,
					(int) (main_button.getWidth() * scaleWidth),
					(int) (main_button.getHeight() * scaleHeight), true);
			//--- Update with proper images ---
			touchedBitmap = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.high_scores_button_down);
			touchedBitmap = Bitmap.createScaledBitmap(touchedBitmap,
					(int) (touchedBitmap.getWidth() * scaleWidth),
					(int) (touchedBitmap.getHeight() * scaleHeight), true);
			//set Main Menu Button positions given screen width and height (current x and y)
			this.x = 3*x/4 - main_button.getWidth()/2;
			this.y = 2*y/3 - main_button.getHeight()/2;
			this.height = main_button.getHeight();
			this.width = main_button.getWidth();
			break;
		default:
			Log.d(TAG,"Unable to load bitmaps for: " + buttonNumber);
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
