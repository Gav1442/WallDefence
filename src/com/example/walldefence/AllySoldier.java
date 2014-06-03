package com.example.walldefence;

import android.graphics.Bitmap;

public class AllySoldier extends Ally{

	public AllySoldier(Bitmap bitmap, int x, int y, float scaleWidth,
			float scaleHeight, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, scaleWidth, scaleHeight, fps, onTopOfWall);
		this.range = 0; //Change from hardcode
		ranged = false;
	}

}
