package com.ece1778.myally.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public abstract class AnimatedView extends View {
	//Frame Rate
	private int _frameRate = 30;
	//Used to start the runnable again at _frameRate milliseconds
	private Handler _h = new Handler();

	private boolean _paused = false;

	public AnimatedView(Context context) {
		super(context);
	}

	public AnimatedView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AnimatedView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public int get_frameRate() {
		return _frameRate;
	}

	public void set_frameRate(int frame_rate) {
		_frameRate = frame_rate;
	}

	protected abstract void onDrawAnimation(Canvas c);

	protected abstract void onUpdate();
	
	protected void set_paused(boolean paused) {
		_paused = paused;
	}
	
	protected boolean isPaused() {
		return _paused;
	}

	protected void onDraw(Canvas c) {
		onDrawAnimation(c);

		//Delay the next update by FRAME_RATE milliseconds
		_h.postDelayed(_game_loop, _frameRate);
	}

	private Runnable _game_loop = new Runnable() {
		@Override
		public void run() {
			if(!_paused) {
				onUpdate(); 		//Update locations/speed
				invalidate();  	//Update visuals (ends up calling onDraw)
			}
		}
	};
}
