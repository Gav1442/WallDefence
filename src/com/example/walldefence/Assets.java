package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Assets {
	int screenHeight, screenWidth;
	private float scaleHeight, scaleWidth;
	private Context myContext;
	Bitmap mainMenuBackground, newGameButtonUp, newGameButtonDown, continueButtonUp, continueButtonDown, settingsButtonUp, settingsButtonDown, highScoreButtonUp, highScoresButtonDown;
	Bitmap tempSoldier;
	Bitmap testArcherSprite, testSprite;
	Bitmap testArrow;
	private final String TAG = "Assets";
	
	public Assets(Context myContext, int screenHeight, int screenWidth){
		this.myContext = myContext;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		mainMenuBackground = BitmapFactory.decodeResource(
				myContext.getResources(), R.drawable.main_menu_bg);
		Log.d(TAG,
				"Unscaled - Background Width: " + mainMenuBackground.getWidth()
						+ ", Background Height: "
						+ mainMenuBackground.getHeight() + ".");
		Log.d(TAG, "Screen Width: " + screenWidth + ", Screen Height: "
				+ screenHeight + ".");
		// scale reference for height of everything
		scaleHeight = (float) screenHeight
				/ (float) mainMenuBackground.getHeight();
		// scale reference for width of everything
		scaleWidth = (float) screenWidth
				/ (float) mainMenuBackground.getWidth();
		// --- scale the background ---
		mainMenuBackground = Bitmap.createScaledBitmap(mainMenuBackground, screenWidth,
				screenHeight, true);
		Log.d(TAG,
				"Scaled - Background Width: " + mainMenuBackground.getWidth()
						+ ", Background Height: "
						+ mainMenuBackground.getHeight() + ".");
		Log.d(TAG, "Scale Width: " + scaleWidth + ", Scale Height: "
				+ scaleHeight + ".");
	}
	
	public Bitmap getMainMenuBackground(){
		if(mainMenuBackground == null){
			mainMenuBackground = BitmapFactory.decodeResource(
					myContext.getResources(), R.drawable.main_menu_bg);
			mainMenuBackground = Bitmap.createScaledBitmap(mainMenuBackground, screenWidth,
					screenHeight, true);
		}
		return mainMenuBackground;
	}
	//Main Menu Buttons
	public Bitmap getNewGameButtonUp(){
		if(newGameButtonUp == null){
			newGameButtonUp = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.new_game_button_up);
			newGameButtonUp = Bitmap.createScaledBitmap(newGameButtonUp,
					(int) (newGameButtonUp.getWidth() * scaleWidth),
					(int) (newGameButtonUp.getHeight() * scaleHeight), true);
		}
		return newGameButtonUp;
	}
	public Bitmap getNewGameButtonDown(){
		if(newGameButtonDown == null){
			newGameButtonDown = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.new_game_button_down);
			newGameButtonDown = Bitmap.createScaledBitmap(newGameButtonDown,
					(int) (newGameButtonDown.getWidth() * scaleWidth),
					(int) (newGameButtonDown.getHeight() * scaleHeight), true);
		}
		return newGameButtonDown;
	}
	public Bitmap getContinueButtonUp(){
		if(continueButtonUp == null){
			continueButtonUp = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.continue_button_up);
			continueButtonUp = Bitmap.createScaledBitmap(continueButtonUp,
					(int) (continueButtonUp.getWidth() * scaleWidth),
					(int) (continueButtonUp.getHeight() * scaleHeight), true);
		}
		return continueButtonUp;
	}
	public Bitmap getContinueButtonDown(){
		if(continueButtonDown == null){
			continueButtonDown = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.continue_button_down);
			continueButtonDown = Bitmap.createScaledBitmap(continueButtonDown,
					(int) (continueButtonDown.getWidth() * scaleWidth),
					(int) (continueButtonDown.getHeight() * scaleHeight), true);
		}
		return continueButtonDown;
	}
	public Bitmap getSettingsButtonUp(){
		if(settingsButtonUp == null){
			settingsButtonUp = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.settings_button_up);
			settingsButtonUp = Bitmap.createScaledBitmap(settingsButtonUp,
					(int) (settingsButtonUp.getWidth() * scaleWidth),
					(int) (settingsButtonUp.getHeight() * scaleHeight), true);
		}
		return settingsButtonUp;
	}
	public Bitmap getSettingsButtonDown(){
		if(settingsButtonDown == null){
			settingsButtonDown = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.settings_button_down);
			settingsButtonDown = Bitmap.createScaledBitmap(settingsButtonDown,
					(int) (settingsButtonDown.getWidth() * scaleWidth),
					(int) (settingsButtonDown.getHeight() * scaleHeight), true);
		}
		return settingsButtonDown;
	}
	public Bitmap getHighScoresButtonUp(){
		if(highScoreButtonUp == null){
			highScoreButtonUp = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.high_scores_button_up);
			highScoreButtonUp = Bitmap.createScaledBitmap(highScoreButtonUp,
					(int) (highScoreButtonUp.getWidth() * scaleWidth),
					(int) (highScoreButtonUp.getHeight() * scaleHeight), true);
		}
		return highScoreButtonUp;
	}
	public Bitmap getHighScoresButtonDown(){
		if(highScoresButtonDown == null){
			highScoresButtonDown = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.settings_button_down);
			highScoresButtonDown = Bitmap.createScaledBitmap(settingsButtonDown,
					(int) (highScoresButtonDown.getWidth() * scaleWidth),
					(int) (highScoresButtonDown.getHeight() * scaleHeight), true);
		}
		return highScoresButtonDown;
	}
	//Enemy Sprites
	public Bitmap getTempSoldier(){
		if(tempSoldier == null){
			Log.d(TAG,"Enemy Bitmap is null!");
			tempSoldier = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.temp_soldier);
			tempSoldier = Bitmap.createScaledBitmap(tempSoldier,
					(int) (tempSoldier.getWidth() * scaleWidth),
					(int) (tempSoldier.getHeight() * scaleHeight), true);
		}
		return tempSoldier;
	}
	//Ally Sprites
	public Bitmap getTestArcherSprite(){
		if(testArcherSprite == null){
			testArcherSprite = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.test_archer_sprite);
			testArcherSprite = Bitmap.createScaledBitmap(testArcherSprite,
					(int) (testArcherSprite.getWidth() * scaleWidth),
					(int) (testArcherSprite.getHeight() * scaleHeight), true);
		}
		return testArcherSprite;
	}
	public Bitmap getTestSprite(){
		if(testSprite == null){
			testSprite = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.test_sprite);
			testSprite = Bitmap.createScaledBitmap(testSprite,
					(int) (testSprite.getWidth() * scaleWidth),
					(int) (testSprite.getHeight() * scaleHeight), true);
		}
		return testSprite;
	}
	
	public Bitmap getTestArrow(){
		if(testArrow == null){
			testArrow = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.test_arrow);
			testArrow = Bitmap.createScaledBitmap(testArrow,
					(int) (testArrow.getWidth() * scaleWidth),
					(int) (testArrow.getHeight() * scaleHeight), true);
		}
		return testArrow;
	}
}
