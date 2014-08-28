package com.example.walldefence;

import android.content.Context;
import android.graphics.Bitmap;

public class AllySoldier extends Ally{

	public AllySoldier(Bitmap bitmap, int x, int y, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, fps, onTopOfWall);
		this.range = 0; //Change from hardcode
		ranged = false;
	}

}
