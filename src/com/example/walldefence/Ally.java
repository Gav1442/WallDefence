package com.example.walldefence;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class Ally extends Actor {
	public enum AllyState {
		moving, atWall, attackingEnemy;
	};

	private AllyState allyState;
	protected final String TAG = "Ally";
	protected boolean onTopOfWall;

	public Ally(Bitmap bitmap, int x, int y, float scaleWidth, float scaleHeight, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, scaleHeight, scaleWidth, fps);
		this.xSpeed = 3;
		this.ySpeed = 0;
		this.onTopOfWall = onTopOfWall;
		this.health = 10000;
		this.damage = 30;
		this.allyState = AllyState.moving;
		this.body = new Rect(x, y - height, x + width, y);
	}

	public void update(ArrayList<Enemy> enemies, Wall wall) {
		checkEnemyCollision(enemies); // check enemies first so if unit is done attacking they will next check if they are at wall
		checkWallCollision(wall);
		if (allyState == AllyState.moving) {
			x += xSpeed;
			y += ySpeed;
		}
		body.set(x, y - height, x + width, y);
	}

	public void checkWallCollision(Wall wall) {
		// check to see if actor is colliding with wall.
		// Log.d(TAG,"Front of character: " + (x+xSpeed+width) +
		// ", edge of wall: " + wall.getX());
		if (allyState != AllyState.attackingEnemy) {
			if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
					+ width + xSpeed, y - ySpeed), wall.getStructure())) {
				// Log.d(TAG,"Character x: " + x);
				// Log.d(TAG,"Front of character: " + (x+xSpeed+width) +
				// ", edge of wall: " + wall.getX());
				// x += (x+width+xSpeed) - wall.getX();
				// Log.d(TAG,"L:" + x + ", T:" + (y-height) + ", R:" + (x+width)
				// + ", B:" + y);
				this.setAllyState(AllyState.atWall);
			}
		}
	}

	// Not finished this function. Continue tomorrow
	// --------------------------------
	public void checkEnemyCollision(ArrayList<Enemy> enemies) {
		// Check for the collision in this function
		boolean attacked = false;
		if (allyState == AllyState.moving) {
			int spot = 0;
			while (spot < enemies.size() && !attacked) {
				// Check if enemy is within next "step" and within range
				if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
						+ width + xSpeed, y - ySpeed), enemies.get(spot)
						.getBody())) {
					Log.d(TAG, "AHA");
					x += (x + width + xSpeed) - enemies.get(spot).getX();
					if (attackEnemy(enemies.get(spot))) {
						enemies.remove(spot);
						Log.d(TAG,"Enemy Destroyed!");
					}
					allyState = AllyState.attackingEnemy;
					attacked = true;
				}
				spot++;
			}
		} else if (allyState == AllyState.atWall) {
			int spot = 0;
			while (spot < enemies.size() && !attacked) {
				// Means wall is destroyed (or on top ofwall) as enemy is coming
				// through
				if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
						+ width + xSpeed, y - ySpeed), enemies.get(spot)
						.getBody())) {
					Log.d(TAG, "AHA");
					if (attackEnemy(enemies.get(spot))) {
						enemies.remove(spot);
					}
					allyState = AllyState.attackingEnemy;
					attacked = true;
				}
				spot++;
			}
		} else if (allyState == AllyState.attackingEnemy) {
			int spot = 0;
			while (spot < enemies.size() && !attacked) {
				if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
						+ width + xSpeed, y - ySpeed), enemies.get(spot)
						.getBody())) {
					Log.d(TAG, "AHA");
					if (attackEnemy(enemies.get(spot))) {
						enemies.remove(spot);
					}
					allyState = AllyState.attackingEnemy;
					attacked = true;
				}
				spot++;
			}
		}
		if (!attacked && allyState == AllyState.attackingEnemy) {
			allyState = AllyState.moving;
			Log.d(TAG, "Done Attacking!");
		}

	}

	// Made actor so useable by both classes. Check for errors but should be
	// alright
	public boolean attackEnemy(Enemy target) {
		Log.d(TAG, "Attacking!");
		if (target.takeDamage(damage)) {
			return true;
		} else
			return false;
	}

	public AllyState getAllyState() {
		return allyState;
	}

	public void setAllyState(AllyState allyState) {
		this.allyState = allyState;
	}

}
