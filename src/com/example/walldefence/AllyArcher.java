package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;

public class AllyArcher extends Ally{
	private static int arrowRange = 400; //Static so upgrades affect all of archers' ranges.
	
	public AllyArcher(Bitmap bitmap, int x, int y, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, fps, onTopOfWall);
		// TODO Auto-generated constructor stub
		this.ranged = true;
		this.xSpeed = 8;
		this.range = arrowRange; 
	}

}
