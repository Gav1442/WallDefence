package com.example.walldefence;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

//Base for all units.
public class Ally extends Actor {
	public enum AllyState {
		moving, atWall, attackingEnemy;
	};

	private AllyState allyState;
	protected final String TAG = "Ally";
	protected boolean onTopOfWall;

	public Ally(Bitmap bitmap, int x, int y, float scaleWidth,
			float scaleHeight, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, scaleHeight, scaleWidth, fps, 5, 3);
		this.xSpeed = 3;
		this.ySpeed = 0;
		this.onTopOfWall = onTopOfWall;
		this.health = 10000;
		this.damage = 10;
		this.allyState = AllyState.moving;
		this.body = new Rect(x, y - height, x + width, y);
		this.srcRect = new Rect(0, 0, this.width, this.height); // Rect for
																// selecting the
																// frame
	}

	public void update(ArrayList<Enemy> enemies, Wall wall, long gameTime) {
		checkEnemyCollision(enemies); // check enemies first so if unit is done
										// attacking they will next check if
										// they are at wall
		checkWallCollision(wall);
		if (allyState == AllyState.moving) {
			x += xSpeed;
			y += ySpeed;
		}
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= numberOfFramesWidth) {
				if(allyState == AllyState.attackingEnemy){
					attackEnemy(enemies);
				}
				currentFrame = 0;
			}
		}
		// define the rectangle to cut out sprite
		this.srcRect.left = currentFrame * width;
		this.srcRect.right = this.srcRect.left + width;
		this.srcRect.top = 0 + this.height * this.bitmapRow;
		this.srcRect.bottom = this.srcRect.top + height;
		body.set(x, y - height, x + width, y);
	}

	public void checkWallCollision(Wall wall) {
		// check to see if actor is colliding with wall.
		// Log.d(TAG,"Front of character: " + (x+xSpeed+width) +
		// ", edge of wall: " + wall.getX());
		if (allyState == AllyState.moving) {
			if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
					+ width + xSpeed, y - ySpeed), wall.getStructure())) {
				// Log.d(TAG,"Character x: " + x);
				// Log.d(TAG,"Front of character: " + (x+xSpeed+width) +
				// ", edge of wall: " + wall.getX());
				// x += (x+width+xSpeed) - wall.getX();
				// Log.d(TAG,"L:" + x + ", T:" + (y-height) + ", R:" + (x+width)
				// + ", B:" + y);
				setAllyAtWall();
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
						+ width + xSpeed + range, y - ySpeed), enemies.get(spot)
						.getBody())) {
					Log.d(TAG, "AHA Range: " + this.range);
					if(x+width+range < enemies.get(spot).getX()){
						x += (x + width + xSpeed + range) - enemies.get(spot).getX();
					}
					setAllyAttacking();
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
						+ width + xSpeed+range, y - ySpeed), enemies.get(spot)
						.getBody())) {
					Log.d(TAG, "AHA");
					setAllyAttacking();
					attacked = true;
				}
				spot++;
			}
		}
	}

	// Made actor so usable by both classes. Check for errors but should be alright
	public void attackEnemy(ArrayList<Enemy> enemies) {
		Log.d(TAG, "Attacking!");
		boolean attacked = false;
		int spot = 0;
		while (spot < enemies.size() && !attacked) {
			if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
					+ width + xSpeed + range, y - ySpeed), enemies.get(spot).getBody())) {
				Log.d(TAG, "AHA");
				if(!ranged){
				if (enemies.get(spot).takeDamage(damage)) {
					enemies.remove(spot);
					setAllyMoving();
				}
				}
				else{
					//Change to adding a projectile to projectile ArrayList
					if (enemies.get(spot).takeDamage(damage)) {
						enemies.remove(spot);
						setAllyMoving();
					}	
				}
				// allyState = AllyState.attackingEnemy; <-- This line would
				// pose a problem, already attacking. Otherwise killed enemy.
				attacked = true;
			}
			spot++;
		}
		if (!attacked && allyState == AllyState.attackingEnemy) {
			setAllyMoving();
			Log.d(TAG, "Done Attacking!");
		}
	}

	public void setAllyMoving() {
		setAllyState(Ally.AllyState.moving);
		bitmapRow = 0;
		numberOfFramesWidth = 5;
		currentFrame = 0;
	}

	public void setAllyAttacking() {
		setAllyState(Ally.AllyState.attackingEnemy);
		bitmapRow = 1;
		numberOfFramesWidth = 5;
		currentFrame = 0;
	}
	
	public void setAllyAtWall() {
		setAllyState(Ally.AllyState.atWall);
		bitmapRow = 2;
		numberOfFramesWidth = 5;
		currentFrame = 0;
	}
	
	public AllyState getAllyState() {
		return allyState;
	}

	public void setAllyState(AllyState allyState) {
		this.allyState = allyState;
	}

}
