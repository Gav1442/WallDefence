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
	private final String TAG = "MainGamePanel";
	private ArrayList<Ally> allies = new ArrayList<Ally>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Wall wall;
	private int screenHeight, screenWidth, xBegin, yBegin;
	private float scaleWidth, scaleHeight;
	private Bitmap background;
	private boolean loaded = false, button_touch = false;
	private Context myContext;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		myContext = context;
		/*
		 * allies.add(new Ally(BitmapFactory.decodeResource(
		 * context.getResources(), R.drawable.greenman), 10, 20)); wall = new
		 * Wall(getContext(), 1, 400, 20);
		 */

		// make the GamePanel focusable so it can handle events
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
		background = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.game);
		Log.d(TAG, "Unscaled - Background Width: " + background.getWidth()
				+ ", Background Height: " + background.getHeight() + ".");

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
				for (int i = 0; i < buttons.size(); i++) {
					if (buttons.get(i).touchDownIntersects(event)) {
						buttons.get(i).setTouch_down(true);
						button_touch = true;
					}
				}
				// check for intersect (testing for now)
			}
			// ACTION_MOVE matters when moving the whole screen/view
			// Figure out why button not reselected if touch MOVES back onto it
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
					for (int i = 0; i < buttons.size(); i++) {
						if (buttons.get(i).touchMoveIntersects(event)) {
							buttons.get(i).setTouch_down(true);
						} else {
							buttons.get(i).setTouch_down(false);
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
					for (int i = 0; i < buttons.size(); i++) {
						if (buttons.get(i).touchUpIntersects(event)) {
							buttons.get(i).setTouch_down(false);
							if (buttons
									.get(i)
									.getBody()
									.contains((int) event.getX(),
											(int) event.getY())) {
								createAlly(buttons.get(i).getButtonNumber(),
										false);
							} else {
								createAlly(buttons.get(i).getButtonNumber(),
										true);
							}
						}
					}
					button_touch = false;
				}
			}
		}
		return true;

	}

	public void createAlly(int buttonID, boolean onTopOfWall) {
		// Will have to turn into a switch statement later depending on buttonID
		// GET SPRITESHEET FROM ANDREW AND WORK AROUND THAT
		switch (buttonID) {
		case 1:
			allies.add(new AllySoldier(BitmapFactory.decodeResource(getResources(),
					R.drawable.test_sprite), 10, 2 * screenHeight / 3,
					scaleWidth, scaleHeight, thread.getMaxFps(), onTopOfWall));
			break;
		case 2:
			allies.add(new AllyArcher(BitmapFactory.decodeResource(getResources(),
					R.drawable.test_archer_sprite), 10, 2 * screenHeight / 3,
					scaleWidth, scaleHeight, thread.getMaxFps(), onTopOfWall));
			break;
		default:
			Log.d(TAG, "Error creating enemy - No case for buttonID: " + buttonID);
		}
	}

	public void update() {
		for (int spot = 0; spot < allies.size(); spot++) {
			allies.get(spot).update(enemies, wall, System.currentTimeMillis());
		}
		for (int spot = 0; spot < enemies.size(); spot++) {
			enemies.get(spot).update(allies, wall);
		}
		// should we sort enemy to make highest HP first so you are attacking
		// highest priority target as opposed to what ever happens to be first
		// in the list?
	}

	public void render(Canvas canvas) {
		if (loaded == true) {
			canvas.drawColor(Color.BLACK);
			canvas.drawBitmap(background, 0, 0, null);
			wall.drawBitmap(canvas);
			for (int spot = 0; spot < allies.size(); spot++) {
				allies.get(spot).drawBitmap(canvas);
			}
			for (int spot = 0; spot < enemies.size(); spot++) {
				enemies.get(spot).drawBitmap(canvas);
			}
			for (int spot = 0; spot < buttons.size(); spot++) {
				buttons.get(spot).drawBitmap(canvas);
			}
			Paint p = new Paint();
			p.setColor(Color.BLACK);
			canvas.drawLine(0, 2 * screenHeight / 3, screenWidth,
					2 * screenHeight / 3, p);
		}
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
		Log.d(TAG, "Screen Width: " + screenWidth + ", Screen Height: "
				+ screenHeight + ".");
		// scale reference for height of everything
		scaleHeight = (float) screenHeight / (float) background.getHeight();
		// scale reference for width of everything
		scaleWidth = (float) screenWidth / (float) background.getWidth();
		// --- scale the background ---
		background = Bitmap.createScaledBitmap(background, screenWidth,
				screenHeight, true);
		Log.d(TAG, "Scaled - Background Width: " + background.getWidth()
				+ ", Background Height: " + background.getHeight() + ".");
		Log.d(TAG, "Scale Width: " + scaleWidth + ", Scale Height: "
				+ scaleHeight + ".");

		// initialize buttons
		buttons.add(new Button(myContext, 1, screenWidth / 6,
				7 * screenHeight / 8, scaleWidth, scaleHeight));
		buttons.add(new Button(myContext, 2, screenWidth / 6,
				7 * screenHeight / 8, scaleWidth, scaleHeight));

		// --- initialize and scale wall 1
		wall = new Wall(getContext(), 1, screenWidth / 2, 2 * screenHeight / 3,
				scaleWidth, scaleHeight);
		Log.d(TAG, "Unscaled - Wall Width: " + wall.getWidth()
				+ ", Wall Height: " + wall.getHeight() + ".");

		// -- initialize and scale test ally
		Bitmap testBitmap = BitmapFactory.decodeResource(
				myContext.getResources(), R.drawable.temp_soldier);
		enemies.add(new Enemy(BitmapFactory.decodeResource(
				myContext.getResources(), R.drawable.temp_soldier),
				screenWidth - 10, 2 * screenHeight / 3, scaleWidth,
				scaleHeight, thread.getMaxFps()));
		// call function to resize existing and set scales...
		// or maybe don't initialize walls until now instead of onCreate and
		// pass scale values to them
		// set a boolean in the initial resize to true so that the draw function
		// only gets called after that
		// this may fix the issue of crashing...
		loaded = true;
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
