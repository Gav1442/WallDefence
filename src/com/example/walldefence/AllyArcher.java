package com.example.walldefence;

import android.graphics.Bitmap;

public class AllyArcher extends Ally{
	private static int arrowRange = 400; //Static so upgrades affect all of archers' ranges.
	
	public AllyArcher(Bitmap bitmap, int x, int y, float scaleWidth,
			float scaleHeight, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, scaleWidth, scaleHeight, fps, onTopOfWall);
		// TODO Auto-generated constructor stub
		this.ranged = true;
		this.xSpeed = 8;
		this.range = arrowRange; 
	}

}
