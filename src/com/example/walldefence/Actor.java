package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public abstract class Actor {
	// Turn these into wc points and make screen coordinate points as well
	protected int x, y, width, height, range, bitmapRow;
	protected int health, damage;
	protected int xSpeed, ySpeed;
	protected float scaleHeight, scaleWidth;
	protected Bitmap bitmap;
	protected final String TAG = "Actor";
	protected boolean ranged;
	protected Rect srcRect, body;
    protected int numberOfFramesWidth, numberOfFramesHeight;
	protected int currentFrame;
	protected int framePeriod;
	protected long frameTicker;
	protected Context myContext;
	
	//Look into integrating CP317 stuff into development.
	//Figure out attacking delay for animation and damage dealing.
	public Actor(Bitmap bitmap, int x, int y,int fps, int frameCountW, int frameCountH) {
		this.bitmap = bitmap;
		this.currentFrame = 0;
		this.bitmapRow = 0; //Sets bitmap row to top row which is moving (start at 0)
		this.numberOfFramesWidth = frameCountW;
		this.width = this.bitmap.getWidth()/frameCountW;
		this.numberOfFramesHeight = frameCountH;
		this.height = this.bitmap.getHeight()/frameCountH;
		this.srcRect = new Rect(0,0, this.width, this.height); //Rect for selecting the frame
		this.framePeriod = 1000 / fps;
		this.frameTicker = 0l;
		this.x = x;
		this.y = y;
	}
	//Still need to set srcRect
	public void drawBitmap(Canvas canvas) {
		//Body is the position of the person (the encompassing rect) on the screen. Body is also used for collision detection.
		canvas.drawBitmap(bitmap, srcRect, body, null);
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
