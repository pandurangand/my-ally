package com.ece1778.myally.dbt.mindjar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.ece1778.myally.detectors.ShakeDetector.ShakeListener;
import com.ece1778.myally.ui.AnimatedView;

public class MindJarView extends AnimatedView implements ShakeListener {
	private ArrayList<Snowflake> _snowflakes;

	private Random _random = new Random();

	private Map<PosBounds, Float> _pParams;

	private static boolean _shaken = false;

	private static float _force;

	private enum PosBounds {
		MAX_X, MAX_Y, MAX_Z, MIN_X, MIN_Y, MIN_Z;
	}

	private Map<VelBounds, Float> _vParams;

	private enum VelBounds {
		MAX_X, MAX_Y, MAX_Z, MIN_X, MIN_Y, MIN_Z;
	}

	// Reusable Paint variable
	private Paint _paint;

	public MindJarView(Context context, AttributeSet attrs) {
		super(context, attrs);

		_paint = new Paint();
		
		_pParams = new HashMap<PosBounds, Float>();
		_pParams.put(PosBounds.MAX_X, 570f);
		_pParams.put(PosBounds.MAX_Y, 770f);
		_pParams.put(PosBounds.MAX_Z, 17f);
		_pParams.put(PosBounds.MIN_X, 155f);
		_pParams.put(PosBounds.MIN_Y, 350f);
		_pParams.put(PosBounds.MIN_Z, 3f);

		_vParams = new HashMap<VelBounds, Float>();
		_vParams.put(VelBounds.MAX_X, 1f);
		_vParams.put(VelBounds.MAX_Y, 1f);
		_vParams.put(VelBounds.MAX_Z, 0.5f);
		_vParams.put(VelBounds.MIN_X, -2f);
		_vParams.put(VelBounds.MIN_Y, -2f);
		_vParams.put(VelBounds.MIN_Z, -0.5f);

		createSnowflakes(500);
	}

	// Return an integer that ranges from min inclusive to max inclusive.
	static int rndInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	@Override
	public void onShake(float force) {
		System.out.println("SHAKEN 1!");
		_shaken = true;
		_force = force / 5;
		System.out.println("SHAKEN 1! " + _force);
	}

	@Override
	protected void onDrawAnimation(Canvas c) {
		for (Snowflake s : _snowflakes) {
			_paint.setColor(Color.argb(255, rndInt(205, 255), rndInt(201, 250),
					rndInt(201, 250)));

			c.drawCircle(s.get_x(), s.get_y(), s.get_z() / 5, _paint);
		}

	}

	@Override
	protected void onUpdate() {
		if (_shaken) {
			System.out.println("SHAKEN 2!");
			for (Snowflake s : _snowflakes) {
				float x = genRandom(_pParams.get(PosBounds.MAX_X),
						_pParams.get(PosBounds.MIN_X));
				float y = genRandom(_pParams.get(PosBounds.MAX_Y),
						_pParams.get(PosBounds.MIN_Y));
				float z = genRandom(_pParams.get(PosBounds.MAX_Z),
						_pParams.get(PosBounds.MIN_Z));
				float xV = genRandom(_vParams.get(VelBounds.MAX_X),
						_vParams.get(VelBounds.MIN_X));
				float yV = genRandom(_vParams.get(VelBounds.MAX_Y),
						_vParams.get(VelBounds.MIN_Y));
				float zV = genRandom(_vParams.get(VelBounds.MAX_Z),
						_vParams.get(VelBounds.MIN_Z));

				s.setPosition(x, y, z);
				s.setVelocity(xV, yV, zV);
			}

			_shaken = false;
		} else {
			for (Snowflake s : _snowflakes) {
				s.translate();

				if (s.get_x() >= _pParams.get(PosBounds.MAX_X)
						|| s.get_x() <= _pParams.get(PosBounds.MIN_X)) {
					s.invert_x_direction();
				}

				if (s.get_xVelocity() > _vParams.get(VelBounds.MAX_X)
						|| s.get_xVelocity() < _vParams.get(VelBounds.MIN_X)) {
					s.set_xVelocity((float) (0.9 * s.get_xVelocity()));
				}

				if (s.get_y() <= _pParams.get(PosBounds.MIN_Y)) {
					s.invert_y_direction();
				}

				if (s.get_yVelocity() > _vParams.get(VelBounds.MAX_Y)
						|| s.get_yVelocity() < _vParams.get(VelBounds.MIN_Y)) {
					s.set_yVelocity((float) (0.9 * s.get_yVelocity()));
				}

				if (s.get_z() >= _pParams.get(PosBounds.MAX_Z)
						|| s.get_z() <= _pParams.get(PosBounds.MIN_Z)) {
					s.invert_z_direction();
				}

				if (s.get_y() >= _pParams.get(PosBounds.MAX_Y)) {
					s.setVelocity(0, 0, 0);
				}
			}
		}

	}

	private void createSnowflakes(int numSnowflakes) {
		_snowflakes = new ArrayList<Snowflake>();
		for (int i = 0; i < numSnowflakes; i++) {
			float x = genRandom(_pParams.get(PosBounds.MAX_X),
					_pParams.get(PosBounds.MIN_X));
			float y = genRandom(_pParams.get(PosBounds.MAX_Y),
					_pParams.get(PosBounds.MIN_Y));
			float z = genRandom(_pParams.get(PosBounds.MAX_Z),
					_pParams.get(PosBounds.MIN_Z));

			Snowflake s = new Snowflake(x,_pParams.get(PosBounds.MAX_Y),z);

			float xV = genRandom(_vParams.get(VelBounds.MAX_X),
					_vParams.get(VelBounds.MIN_X));
			float yV = genRandom(_vParams.get(VelBounds.MAX_Y),
					_vParams.get(VelBounds.MIN_Y));
			float zV = genRandom(_vParams.get(VelBounds.MAX_Z),
					_vParams.get(VelBounds.MIN_Z));

			s.setVelocity(0, 0, 0);

			_snowflakes.add(s);
		}
	}

	private float genRandom(float max, float min) {
		return (_random.nextFloat() * (max - min) + min);
	}
}
