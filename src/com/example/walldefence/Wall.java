package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Wall{
	final private String TAG = "Wall";
	private Bitmap bitmap_standing; //create a bitmap_destroyed for when the gate is destroyed
	private int x, y, width, height, health, wallNumber;
	private boolean destroyed;
	private Rect structure;
	
	public Wall(Context context, int wallNumber, int xPos, int yPos, float scaleWidth, float scaleHeight){
		bitmap_standing = BitmapFactory.decodeResource(context.getResources(), R.drawable.wall_image1);
		bitmap_standing = Bitmap.createScaledBitmap(bitmap_standing, (int)(scaleWidth * bitmap_standing.getWidth()), 
				(int)(scaleHeight * bitmap_standing.getHeight()), true);
		x = xPos;
		y = yPos;
		this.wallNumber = wallNumber;
		width = bitmap_standing.getWidth();
		height = bitmap_standing.getHeight();
		destroyed = false;
		health = 200;
		structure = new Rect(x, y-height, x+width, y);
		Log.d(TAG,"Wall number: " + this.wallNumber + ", xPos: "+ x + ", yPos: " + y + ".");
	}
	
	//Remove damage from total hp
	public void takeDamage(int damage){
		if(health > 0 && health > damage){ //No issues with health would happen, remove normally
			health -= damage;
			Log.d(TAG, "Wall Health is : " + health);
		} else if(health > 0 && health <= damage){ //if health is the same or less than damage, HP will become 0 and wall will be destroyed
			health = 0;
			destroyed = true;
			Log.d(TAG, "Wall Health is : " + health);
			Log.d(TAG, "Wall is destroyed!");
		}

	}
	
	//draw wall function
	public void drawBitmap(Canvas canvas){
		//Log.d(TAG,"Screen height is: 768. Object onScreen y is: " + (y-height) +", height: " + height+ ",bottom: " + y);
		if(!destroyed){
			canvas.drawBitmap(bitmap_standing,x,y-height,null);
		}
	}
	
	//When the user swipes the screen, all objects on screen must move. X-direction motion.
	public void shiftWall(int dX){
		x+= dX;
	}

	public Bitmap getBitmap() {
		return bitmap_standing;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap_standing = bitmap;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getWallNumber() {
		return wallNumber;
	}

	public void setWallNumber(int wallNumber) {
		this.wallNumber = wallNumber;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public Bitmap getBitmap_standing() {
		return bitmap_standing;
	}

	public void setBitmap_standing(Bitmap bitmap_standing) {
		this.bitmap_standing = bitmap_standing;
	}

	public Rect getStructure() {
		return structure;
	}

	public void setStructure(Rect structure) {
		this.structure = structure;
	}

	public String getTAG() {
		return TAG;
	}
}
