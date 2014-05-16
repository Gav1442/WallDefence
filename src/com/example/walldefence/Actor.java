package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Actor {
	// Turn these into wc points and make screen coordinate points as well
	protected int x, y, width, height, range;
	protected int health, damage;
	protected int xSpeed, ySpeed;
	protected Bitmap bitmap;
	protected final String TAG = "Actor";
	protected boolean ranged;
	protected Rect body; // Implement Rect in the morning. Probably makes
							// collision detection easier!

	public Actor(Bitmap bitmap, int x, int y) {
		this.x = x;
		this.y = y;
		this.bitmap = bitmap;
	}

	//find out what is required for drawing and if this needs to be moved to a seperate draw function for each.
	// visit obviam.net for a detailed tutorial. 
	//Testing Git
	public void drawBitmap(Canvas canvas) {
		// Log.d(TAG,"Screen height is: 768. Object onScreen y is: " +
		// (y-height) +", height: " + height+ ",bottom: " + y);
		canvas.drawBitmap(bitmap, x, y - height, null);
	}

	public boolean checkIntersection(int eventX, int eventY) {
		// x,y is top left corner, therefore this is currently correct. May want
		// to change x,y to bottom left...
		if ((x <= eventX && (x + width) >= eventX)
				&& (y <= eventY && (y + height) >= eventY)) {
			Log.d(TAG, "Touch intersects!");
			return true;
		}
		return false;
	}

	// return true if 'target' killed
	public boolean takeDamage(int damage) {
		health -= damage;
		if (health > 0) {
			return false;
		} else
			return true;
	}

	public void shiftActor(int dX) {
		x += dX;
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

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
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

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}

	public Rect getBody() {
		return body;
	}

	public void setBody(Rect body) {
		this.body = body;
	}

	public String getTAG() {
		return TAG;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isRanged() {
		return ranged;
	}

	public void setRanged(boolean ranged) {
		this.ranged = ranged;
	}
}
