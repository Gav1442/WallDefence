package com.example.walldefence;

import android.graphics.Bitmap;

public class Projectile {
	protected Bitmap bitmap;
	protected int x, y, xSpeed, ySpeed, damage;
	
	public Projectile(Bitmap bitmap, int x, int y, int xSpeed, int ySpeed, int damage){
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.damage = damage;
	}
}
