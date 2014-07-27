package com.example.walldefence;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class GameButton extends Button{
	protected Bitmap touch_ground, touch_onWall;
	
	public GameButton(Context myContext, int buttonNumber, int x, int y,
			float scaleWidth, float scaleHeight) {
		super(myContext, buttonNumber, x, y, scaleWidth, scaleHeight);
		// TODO Auto-generated constructor stub
		this.getBitmaps(this.buttonNumber, myContext, scaleWidth, scaleHeight);
		this.width = main_button.getWidth();
		this.height = main_button.getHeight();
		this.x = x + (this.buttonNumber-1)*this.width;
		this.y = y;
		this.body = new Rect(this.x, this.y-height, this.x+width, this.y);
	}

	public void getBitmaps(int buttonNumber, Context myContext, float scaleWidth, float scaleHeight){
		switch(buttonNumber){
		case 1:
			main_button = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.button);
			main_button = Bitmap.createScaledBitmap(main_button,
					(int) (main_button.getWidth() * scaleWidth),
					(int) (main_button.getHeight() * scaleHeight), true);
			//--- Update with proper images ---
			touch_ground = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.button);
			touch_ground = Bitmap.createScaledBitmap(touch_ground,
					(int) (touch_ground.getWidth() * scaleWidth),
					(int) (touch_ground.getHeight() * scaleHeight), true);
			touch_onWall = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.button);
			touch_onWall = Bitmap.createScaledBitmap(touch_onWall,
					(int) (touch_onWall.getWidth() * scaleWidth),
					(int) (touch_onWall.getHeight() * scaleHeight), true);
			break;
		case 2:
			main_button = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.button2);
			main_button = Bitmap.createScaledBitmap(main_button,
					(int) (main_button.getWidth() * scaleWidth),
					(int) (main_button.getHeight() * scaleHeight), true);
			//--- Update with proper images ---
			touch_ground = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.button2);
			touch_ground = Bitmap.createScaledBitmap(touch_ground,
					(int) (touch_ground.getWidth() * scaleWidth),
					(int) (touch_ground.getHeight() * scaleHeight), true);
			touch_onWall = BitmapFactory.decodeResource(myContext.getResources(), R.drawable.button2);
			touch_onWall = Bitmap.createScaledBitmap(touch_onWall,
					(int) (touch_onWall.getWidth() * scaleWidth),
					(int) (touch_onWall.getHeight() * scaleHeight), true);
			break;
		default:
			Log.d(TAG,"Unable to load bitmaps for: " + buttonNumber);
			break;
		}
	}
	
	@Override
	public boolean touchMoveIntersects(MotionEvent e){
		Rect fullBody = new Rect(body);
		fullBody.top = fullBody.top - this.height;
		//if the button is already 'touched' then the two are visible and touch moving on image should keep image
		if(touch_down){
			if(fullBody.contains((int)e.getX(), (int)e.getY())){ 
				return true;
			}
			else{
				this.setTouch_down(false);
			}
		}else{
			if (body.contains((int)e.getX(), (int)e.getY())){
				this.setTouch_down(true);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean touchUpIntersects(MotionEvent e){
		Rect fullBody = new Rect(body);
		fullBody.top = fullBody.top - this.height;
		//if the button is already 'touched' then the two are visible and touch moving on image should keep image
		if(touch_down){
			if(fullBody.contains((int)e.getX(), (int)e.getY())){
				Log.d(TAG, "Button: " +buttonNumber + " -> Touched Up");
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Ally> createAlly(Context myContext, ArrayList<Ally> allies, int screenHeight, int screenWidth, 
			float scaleHeight, float scaleWidth, boolean onTopOfWall){
		// Will have to turn into a switch statement later depending on buttonID
				// GET SPRITESHEET FROM ANDREW AND WORK AROUND THAT
				switch (this.buttonNumber) {
				case 1:
					allies.add(new AllySoldier(BitmapFactory.decodeResource(
							myContext.getResources(), R.drawable.test_sprite), 10,
							2 * screenHeight / 3, scaleWidth, scaleHeight, MainThread
									.getMaxFps(), onTopOfWall));
					break;
				case 2:
					allies.add(new AllyArcher(BitmapFactory.decodeResource(
							myContext.getResources(), R.drawable.test_archer_sprite), 10,
							2 * screenHeight / 3, scaleWidth, scaleHeight, MainThread
									.getMaxFps(), onTopOfWall));
					break;
				default:
					Log.d(TAG, "Error creating enemy - No case for buttonID: "
							+ buttonNumber);
				}
				return allies;
	}
	
	public void drawBitmap(Canvas canvas){
		if(!touch_down){
			canvas.drawBitmap(main_button, x, y-height, null);
		}
		else{
			canvas.drawBitmap(touch_ground, x, y-height, null);
			canvas.drawBitmap(touch_onWall, x, y-2*height, null);
		}
	}
	
	public Bitmap getTouch_ground() {
		return touch_ground;
	}

	public void setTouch_ground(Bitmap touch_ground) {
		this.touch_ground = touch_ground;
	}

	public Bitmap getTouch_onWall() {
		return touch_onWall;
	}

	public void setTouch_onWall(Bitmap touch_onWall) {
		this.touch_onWall = touch_onWall;
	}
	
}
