package com.example.walldefence;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Button {
	private int buttonNumber, x, y, width, height;
	private boolean touch_down;
	private Bitmap main_button, touch_ground, touch_onWall;
	private Rect body;
	private final String TAG = "Button";
	
	//My thoughts here are to base the loaded images from 
	
	public Button(Context myContext, int buttonNumber, int x, int y, float scaleWidth, float scaleHeight){
		this.buttonNumber = buttonNumber;
		this.getBitmaps(this.buttonNumber, myContext, scaleWidth, scaleHeight);
		this.buttonNumber = buttonNumber;
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
		default:
			Log.d(TAG,"Unable to load bitmaps for: " + buttonNumber);
			break;
		}
	}
	
	public void createAlly(int buttonNumber, boolean wallUnit, ArrayList<Ally> allies){
		Log.d(TAG,"Pressed!");
	}
	
	public void drawBitmap(Canvas canvas){
		if(!touch_down){
			canvas.drawBitmap(main_button, x, y-height, null);
		}
		else{
			canvas.drawBitmap(touch_ground, x, y-height, null);
			canvas.drawBitmap(touch_onWall, x, y-(2*height), null);
		}
	}
	
	public boolean touchDownIntersects(MotionEvent e){
		if(body.contains((int)e.getX(), (int)e.getY())) return true;
		else return false;
	}
	
	public boolean touchMoveIntersects(MotionEvent e){
		Rect fullBody = new Rect(body);
		fullBody.top = fullBody.top - this.height;
		//if the button is already 'touched' then the two are visible and touch moving on image should keep image
		if(touch_down){
			if(fullBody.contains((int)e.getX(), (int)e.getY())) return true;
		}else{
			if (body.contains((int)e.getX(), (int)e.getY())) return true;
		}
		return false;
	}
	
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
	
	public int getButtonNumber() {
		return buttonNumber;
	}

	public void setButtonNumber(int buttonNumber) {
		this.buttonNumber = buttonNumber;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isTouch_down() {
		return touch_down;
	}

	public void setTouch_down(boolean touch_down) {
		this.touch_down = touch_down;
	}

	public Bitmap getMain_button() {
		return main_button;
	}

	public void setMain_button(Bitmap main_button) {
		this.main_button = main_button;
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

	public Rect getBody() {
		return body;
	}

	public void setBody(Rect body) {
		this.body = body;
	}
}
