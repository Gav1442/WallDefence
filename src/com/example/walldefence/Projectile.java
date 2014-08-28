package com.example.walldefence;

import android.graphics.Bitmap;
import android.graphics.Canvas;

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
	
	public void update(){
		
	}
	
	public void drawBitmap(Canvas canvas){
		canvas.drawBitmap(bitmap, x, y-height, null);
	}
	
	public void checkCollision(){
		
	}
}
