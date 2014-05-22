package com.example.walldefence;

import java.util.ArrayList;

import com.example.walldefence.Ally.AllyState;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class Enemy extends Actor {
	public enum EnemyState {
		moving, atWall, attackingAlly
	};

	private EnemyState enemyState;
	private final String TAG = "Enemy";
	public Enemy(Bitmap bitmap, int x, int y, float scaleWidth,
			float scaleHeight, int fps) {
		super(bitmap, x, y, scaleHeight, scaleWidth, fps, 1);
		this.xSpeed = -2;
		this.ySpeed = 0;
		this.enemyState = EnemyState.moving;
		this.health = 50;
		this.damage = 10;
		this.body = new Rect(x, y - height, x + width, y);
	}

	public void update(ArrayList<Ally> allies, Wall wall) {
		checkEnemyCollision(allies);
		checkWallCollision(wall);
		if (enemyState == EnemyState.moving) {
			x += xSpeed;
			y += ySpeed;
		} else if (enemyState == EnemyState.atWall) {
			attackWall(wall);
		}
		body.set(x, y - height, x + width, y);
	}

	// Do these for enemy

	public void checkWallCollision(Wall wall) {
		// check to see if enemy is colliding with wall.
		// Log.d(TAG,"Front of character: " + (x+xSpeed+width) +
		// ", edge of wall: " + wall.getX());
		// check if at wall only when checking isDestroyed (for true value). if
		// moving and wall is destroyed won't pass first if check and won't ever
		// get set to atWall
		if (Rect.intersects(new Rect(x + xSpeed, y - (height + ySpeed), x
				+ width + xSpeed, y - ySpeed), wall.getStructure())
				&& enemyState == EnemyState.moving && !wall.isDestroyed()) {
			Log.d(TAG, "Enemy x: " + x);
			int diff = (x + xSpeed) - (wall.getX() + wall.getWidth());
			Log.d(TAG, "Difference: " + diff);
			x += xSpeed - diff;
			Log.d(TAG, "Enemy new x: " + x);
			Log.d(TAG, "Edge of wall: " + (wall.getX() + wall.getWidth()));
			this.setEnemyState(EnemyState.atWall);
		} else if (Rect.intersects(new Rect(x + xSpeed, y - (height + ySpeed),
				x + width + xSpeed, y - ySpeed), wall.getStructure())
				&& enemyState == EnemyState.atWall && wall.isDestroyed()) {
			this.setEnemyState(EnemyState.moving);
		}
	}

	public void checkEnemyCollision(ArrayList<Ally> allies) {
		boolean attacked = false;
		int spot = 0;
		while (spot < allies.size() && !attacked) {
			// Check if enemy is within next "step" and within range
			if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
					+ width + xSpeed, y - ySpeed), allies.get(spot).getBody())) {
				Log.d(TAG, "Enemy x: " + x);
				int diff = (x + xSpeed) - (allies.get(spot).getX() + allies.get(spot).getWidth());
				Log.d(TAG, "Difference: " + diff);
				x += xSpeed - diff;
				Log.d(TAG, "Enemy new x: " + x);
				if (attackAlly(allies.get(spot))) {
					allies.remove(spot);
					Log.d(TAG,"Ally Destroyed!");
				}
				enemyState = EnemyState.attackingAlly;
				attacked = true;
			}
			spot++;
		}
		if(!attacked){
			enemyState = EnemyState.moving;
		}
	}

	// Made actor so useable by both classes. Check for errors but should be
	// alright
	public boolean attackAlly(Ally target) {
		Log.d(TAG, "Attacking!");
		if (target.takeDamage(damage)) {
			return true;
		} else
			return false;
	}

	public void attackWall(Wall wall) {
		// deal with damaging (and destroying) wall
		wall.takeDamage(5);
	}

	public EnemyState getEnemyState() {
		return enemyState;
	}

	public void setEnemyState(EnemyState enemyState) {
		this.enemyState = enemyState;
	}

}
