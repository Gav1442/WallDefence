package com.example.walldefence;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Projectile {
	protected Bitmap bitmap;
	protected int x, y, xSpeed, ySpeed, damage, height, width;
	
	public Projectile(Bitmap bitmap, int x, int y, int xSpeed, int ySpeed, int damage){
		this.bitmap = bitmap;
		this.height = this.bitmap.getHeight();
		this.width = this.bitmap.getWidth();
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.damage = damage;
	}
	
	public void update(ArrayList<Enemy> enemies, Wall wall){
		x = x + xSpeed;
		y = y + ySpeed;
		checkCollision(enemies);
	}
	
	public void drawBitmap(Canvas canvas){
		canvas.drawBitmap(bitmap, x, y-height, null);
	}
	
	public void checkCollision(ArrayList<Enemy> enemies){
		for(int spot = 0; spot < enemies.size(); spot++){
		if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
				+ width + xSpeed, y - ySpeed), enemies.get(spot).getBody())) {
			//Log.d(TAG, "AHA");
				if (enemies.get(spot).takeDamage(damage)) {
					enemies.remove(spot);
				}
			}
		}
	}
}
