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
	protected int buttonNumber, x, y, width, height;
	protected boolean touch_down;
	protected Bitmap main_button;
	protected Rect body;
	protected final String TAG = "Button";
	
	//My thoughts here are to base the loaded images from 
	
	public Button(Context myContext, int buttonNumber, int x, int y, float scaleWidth, float scaleHeight){
		this.buttonNumber = buttonNumber;
	}
	
	public boolean touchDownIntersects(MotionEvent e){
		if(body.contains((int)e.getX(), (int)e.getY())){
			this.setTouch_down(true);
			return true;
		}
		else{
			this.setTouch_down(false);
		return false;
		}
	}
	
	public boolean touchMoveIntersects(MotionEvent e){		
			if (body.contains((int)e.getX(), (int)e.getY())){
				this.setTouch_down(true);
				return true;
			}
			else{
				this.setTouch_down(false);
				return false;
			}
	}
	
	public boolean touchUpIntersects(MotionEvent e){
			if(body.contains((int)e.getX(), (int)e.getY())){
				Log.d(TAG, "Button: " +buttonNumber + " -> Touched Up");
				this.setTouch_down(false);
				return true;
			}
			else{
				this.setTouch_down(false);
				return false;
			}
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

	public Rect getBody() {
		return body;
	}

	public void setBody(Rect body) {
		this.body = body;
	}
}
