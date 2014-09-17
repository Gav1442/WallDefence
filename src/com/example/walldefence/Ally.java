package com.example.walldefence;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

//Base for all units.
public abstract class Ally extends Actor {
	private enum AllyState {
		moving, atWall, attackingEnemy;
	};

	private AllyState allyState;
	protected final String TAG = "Ally";
	protected boolean onTopOfWall;

	public Ally(Bitmap bitmap, int x, int y, int fps, boolean onTopOfWall) {
		super(bitmap, x, y, fps, 5, 3);
		this.xSpeed = 6;
		this.ySpeed = 0;
		this.onTopOfWall = onTopOfWall;
		this.health = 10000;
		this.damage = 10;
		this.allyState = AllyState.moving;
		this.body = new Rect(x, y - height, x + width, y);
		this.srcRect = new Rect(0, 0, this.width, this.height); // Rect for selecting the frame
	}

	public void update(ArrayList<Enemy> enemies, Wall wall, long gameTime, ArrayList<Projectile> projectiles, Assets assets) {
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
					attackEnemy(enemies, projectiles, assets);
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
		if (allyState == AllyState.moving) {
			if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
					+ width + xSpeed, y - ySpeed), wall.getStructure())) {
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
					//Log.d(TAG, "AHA Range: " + this.range);
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
					//Log.d(TAG, "AHA");
					setAllyAttacking();
					attacked = true;
				}
				spot++;
			}
		}
	}

	// Made actor so usable by both classes. Check for errors but should be alright
	public void attackEnemy(ArrayList<Enemy> enemies, ArrayList<Projectile> projectiles, Assets assets) {
		//Log.d(TAG, "Attacking!");
		boolean attacked = false;
		int spot = 0;
		while (spot < enemies.size() && !attacked) {
			if (Rect.intersects(new Rect(x + xSpeed, y - height - ySpeed, x
					+ width + xSpeed + range, y - ySpeed), enemies.get(spot).getBody())) {
				//Log.d(TAG, "AHA");
				if(!ranged){
					if (enemies.get(spot).takeDamage(damage)) {
						enemies.remove(spot);
						setAllyMoving();
					}
				}
				else{
						projectiles.add(new Projectile(assets.getTestArrow(),
								this.x+this.width, this.y-3*(this.y)/4, 5, 0, damage));
						setAllyMoving();	
				}
				// allyState = AllyState.attackingEnemy; <-- This line would
				// pose a problem, already attacking. Otherwise killed enemy.
				attacked = true;
			}
			spot++;
		}
		if (!attacked && allyState == AllyState.attackingEnemy) {
			setAllyMoving();
			//Log.d(TAG, "Done Attacking!");
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
