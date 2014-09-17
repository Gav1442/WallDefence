package com.example.walldefence;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {
	private MainThread thread;
	private final int NUMBER_OF_MAIN_MENU_BUTTONS = 4;
	private final String TAG = "MainGamePanel";
	private ArrayList<Ally> allies = new ArrayList<Ally>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ArrayList<GameButton> gameButtons = new ArrayList<GameButton>();
	private ArrayList<MainMenuButton> mainMenuButtons = new ArrayList<MainMenuButton>();
	private Wall wall;
	private int screenHeight, screenWidth, xBegin, yBegin;
	private float scaleWidth = 1, scaleHeight = 1;
	private Bitmap background;
	private boolean loaded = false, button_touch = false, blnMainMenu = true,
			blnUpgradeMenu = false, blnInGame = false; //button_touch is to differentiate button from shifting screen.
	private Context myContext;
	private long waveStartTime, lastEnemyTime;
	public Assets assets;
	
	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		myContext = context;
		// make the GamePanel focusable so it can handle events
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	// Handle touch events here.
	public boolean onTouchEvent(MotionEvent event) {
		if (loaded) {
			Log.d(TAG, "x: " + event.getX() + ", Y: " + event.getY() + ".");
			// ACTION_DOWN matters when calling a new unit.
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// set beginning touch point
				xBegin = (int) event.getX();
				yBegin = (int) event.getX();
				if (blnMainMenu) {
					for (int i = 0; i < mainMenuButtons.size(); i++) {
						if (mainMenuButtons.get(i).touchDownIntersects(event)) {
							button_touch = true;
						}
					}
				} else if (blnInGame) {
					for (int i = 0; i < gameButtons.size(); i++) {
						if (gameButtons.get(i).touchDownIntersects(event)) {
							button_touch = true;
						}
					}
				}
				// check for intersect (testing for now)
			}
			// ACTION_MOVE matters when moving the whole screen/view
			// ******
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				int xCurrent = (int) event.getX();
				int yCurrent = (int) event.getY();
				/*
				 * // move screen Log.d(TAG, "Screen is moving!"); int xCurrent
				 * = (int) event.getX(); // ------ Create function that cycles
				 * through all on the field and // shifts properly (calling
				 * shitWall etc...)---- wall.shiftWall(xCurrent - xBegin);
				 * xBegin = xCurrent; Log.d(TAG, "New position is x:" +
				 * wall.getX() + " and y:" + wall.getY() + "!");
				 */
				if (button_touch) {
					if (blnMainMenu) {
						for (int i = 0; i < mainMenuButtons.size(); i++) {
							mainMenuButtons.get(i).touchMoveIntersects(event);
						}
					} else if (blnInGame) {
						for (int i = 0; i < gameButtons.size(); i++) {
							gameButtons.get(i).touchMoveIntersects(event);
						}
					}
				}
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// clear x and y begins
				xBegin = 0;
				yBegin = 0;
				// check for intersect (testing for now)
				if (button_touch) {
					if (blnMainMenu) {
						for (int i = 0; i < mainMenuButtons.size(); i++) {
							if (mainMenuButtons.get(i).touchUpIntersects(event)) {
								setUpWave();
							}
						}
					} else if (blnInGame) {
						for (int i = 0; i < gameButtons.size(); i++) {
							if (gameButtons.get(i).touchUpIntersects(event)) {
								gameButtons.get(i).setTouch_down(false);
								if (gameButtons.get(i)
										.getBody()
										.contains((int) event.getX(),
												(int) event.getY())) {
									gameButtons.get(i).createAlly(assets, allies, screenHeight, screenWidth, false);
								} else {
									gameButtons.get(i).createAlly(assets, allies, screenHeight, screenWidth, true);
								}
							}
						}
					}
					button_touch = false;
				}
			}
		}
		return true;
	}

	public void update() {
		long currentSystemTime = System.currentTimeMillis();
		if (blnInGame) {
			if (currentSystemTime - waveStartTime >= 120000) {
				endWave();
			} else {
				for (int spot = 0; spot < allies.size(); spot++) {
					allies.get(spot).update(enemies, wall, currentSystemTime, projectiles, assets);
				}
				for (int spot = 0; spot < projectiles.size(); spot++) {
					projectiles.get(spot).update(enemies, wall);
				}
				for (int spot = 0; spot < enemies.size(); spot++) {
					enemies.get(spot).update(allies, wall);
				}
				if (currentSystemTime - lastEnemyTime >= 7000) {
					enemies.add(new Enemy(assets.getTempSoldier(),
							screenWidth - 10, 2 * screenHeight / 3, thread.getMaxFps()));
					lastEnemyTime = currentSystemTime;
				}
			}
		}
	}

	public void render(Canvas canvas) {
		if (loaded == true) {
			canvas.drawColor(Color.BLACK);
			canvas.drawBitmap(background, 0, 0, null);
			if (blnMainMenu) {
				for (int spot = 0; spot < mainMenuButtons.size(); spot++) {
					mainMenuButtons.get(spot).drawBitmap(canvas);
				}
			} else if (blnInGame) { // draw InGame entities
				wall.drawBitmap(canvas);
				for (int spot = 0; spot < allies.size(); spot++) {
					allies.get(spot).drawBitmap(canvas);
				}
				for (int spot = 0; spot < projectiles.size(); spot++) {
					projectiles.get(spot).drawBitmap(canvas);
				}
				for (int spot = 0; spot < enemies.size(); spot++) {
					enemies.get(spot).drawBitmap(canvas);
				}
				for (int spot = 0; spot < gameButtons.size(); spot++) {
					gameButtons.get(spot).drawBitmap(canvas);
				}
				Paint p = new Paint();
				p.setColor(Color.BLACK);
				canvas.drawLine(0, 2 * screenHeight / 3, screenWidth,
						2 * screenHeight / 3, p);
			}
		} else {
			if (blnMainMenu) {
				assets = new Assets(this.getContext(), screenHeight, screenWidth);
				background = assets.getMainMenuBackground();
				for (int i = 1; i <= NUMBER_OF_MAIN_MENU_BUTTONS; i++) {
					mainMenuButtons
							.add(new MainMenuButton(assets, i, screenWidth,
									screenHeight));
				}
				loaded = true;
			}
			// Is this if ever called? What about loading back into the game?
			else if (blnInGame) {
				background = BitmapFactory.decodeResource(
						myContext.getResources(), R.drawable.game);
				Log.d(TAG,
						"Unscaled - Background Width: " + background.getWidth()
								+ ", Background Height: "
								+ background.getHeight() + ".");
				Log.d(TAG, "Screen Width: " + screenWidth + ", Screen Height: "
						+ screenHeight + ".");
				// scale reference for height of everything
				scaleHeight = (float) screenHeight
						/ (float) background.getHeight();
				// scale reference for width of everything
				scaleWidth = (float) screenWidth
						/ (float) background.getWidth();
				// --- scale the background ---
				background = Bitmap.createScaledBitmap(background, screenWidth,
						screenHeight, true);
				Log.d(TAG,
						"Scaled - Background Width: " + background.getWidth()
								+ ", Background Height: "
								+ background.getHeight() + ".");
				Log.d(TAG, "Scale Width: " + scaleWidth + ", Scale Height: "
						+ scaleHeight + ".");

				// initialize buttons
				gameButtons.add(new GameButton(myContext, 1, screenWidth / 6,
						7 * screenHeight / 8, scaleWidth, scaleHeight));
				gameButtons.add(new GameButton(myContext, 2, screenWidth / 6,
						7 * screenHeight / 8, scaleWidth, scaleHeight));

				// --- initialize and scale wall 1
				wall = new Wall(getContext(), 1, screenWidth / 2,
						2 * screenHeight / 3, scaleWidth, scaleHeight);
				Log.d(TAG, "Unscaled - Wall Width: " + wall.getWidth()
						+ ", Wall Height: " + wall.getHeight() + ".");

				enemies.add(new Enemy(assets.getTempSoldier(),
						screenWidth - 10, 2 * screenHeight / 3, thread.getMaxFps()));

				Log.d(TAG, "Loaded is: " + loaded);
				loaded = true;
			}
		}
	}

	// after New Wave is pushed, this function will initialize all of the
	// necessary game entities.
	public void setUpWave() {
		background = BitmapFactory.decodeResource(myContext.getResources(),
				R.drawable.game);
		// --- scale the background ---
		background = Bitmap.createScaledBitmap(background, screenWidth,
				screenHeight, true);
		// initialize buttons
		gameButtons.add(new GameButton(myContext, 1, screenWidth / 6,
				7 * screenHeight / 8, scaleWidth, scaleHeight));
		gameButtons.add(new GameButton(myContext, 2, screenWidth / 6,
				7 * screenHeight / 8, scaleWidth, scaleHeight));
		// --- initialize and scale wall 1
		wall = new Wall(getContext(), 1, screenWidth / 2, 2 * screenHeight / 3,
				scaleWidth, scaleHeight);
		blnMainMenu = false;
		blnInGame = true;
		//waveStartTime = System.currentTimeMillis();
		enemies.add(new Enemy(assets.getTempSoldier(),
				screenWidth - 10, 2 * screenHeight / 3, thread.getMaxFps()));
		lastEnemyTime = waveStartTime = System.currentTimeMillis();
	}

	public void endWave() {
		blnMainMenu = true;
		blnInGame = false;
		// --- scale the background ---
		background = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				myContext.getResources(), R.drawable.main_menu_bg),
				screenWidth, screenHeight, true);
		// initialize buttons
		gameButtons.clear();
		// --- initialize and scale wall 1
		wall = null;
		enemies.clear();
		allies.clear();
	}

	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		screenHeight = height;
		screenWidth = width;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "Surface created. Thread starting!");
		// TODO Auto-generated method stub
		thread.setRunning(true);
		if (thread.getState() == Thread.State.NEW) {
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}

}
