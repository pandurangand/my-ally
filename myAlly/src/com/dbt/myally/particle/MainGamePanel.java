/**
 * 
 */
package com.dbt.myally.particle;

import com.dbt.myally.R;
import com.dbt.myally.R.drawable;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author impaler This is the main surface that handles the ontouch events and
 *         draws the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback, SensorEventListener {

	private static final String TAG = MainGamePanel.class.getSimpleName();

	private static final int EXPLOSION_SIZE = 500;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity

	private MainThread thread;
	private Explosion explosion;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	// private ShakeDetector mShakeDetector;

	// the fps to be displayed
	private String avgFps;

	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events

		getHolder().addCallback(this);
		explosion = new Explosion(EXPLOSION_SIZE, 100, 100);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		thread = new MainThread(getHolder(), this);

		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// create the game loop thread
		if (thread.getState() == Thread.State.TERMINATED) {
			thread = new MainThread(getHolder(), this);

		}

		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		terminateThread();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// handle touch
			// check if explosion is null or if it is still active
			// if (explosion == null || explosion.getState() ==
			// Explosion.STATE_DEAD) {
			// explosion = new Explosion(EXPLOSION_SIZE, (int)event.getX(),
			// (int)event.getY());
			// }
		}
		return true;
	}

	public void render(Canvas canvas) {

		canvas.drawColor(Color.parseColor("#DAAA90"));
		canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.masonjarr), 0, 100, null);

		// render explosions
		if (explosion != null) {
			explosion.draw(canvas);
		}

		// display fps
		// displayFps(canvas, avgFps);

	}

	/**
	 * This is the game update method. It iterates through all the objects and
	 * calls their update method if they have one or calls specific engine's
	 * update method.
	 */
	public void update() {
		// update explosions
		if (explosion != null && explosion.isAlive()) {
			explosion.update(getHolder().getSurfaceFrame());
		}
	}

	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}

	public void onResume() {
		mSensorManager.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	public void onPause() {
		// super.onPause();
		Log.d(TAG, "Surface is being paused");

		terminateThread();
		mSensorManager.unregisterListener(this);
	}

	private void terminateThread() {
		thread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		mAccelLast = mAccelCurrent;
		mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
		float delta = mAccelCurrent - mAccelLast;
		mAccel = mAccel * 0.9f + delta; // perform low-cut filter

		if (mAccel > 8) {
			Log.d("SHAKE CHECK", "YUSSSSSS");
			explosion.shake(getHolder().getSurfaceFrame());
		}
	}

}
