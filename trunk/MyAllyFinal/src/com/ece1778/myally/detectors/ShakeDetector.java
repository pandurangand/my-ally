package com.ece1778.myally.detectors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeDetector implements SensorEventListener {
	public interface ShakeListener {
    	public void onShake(float force);
    }
	
	private float _threshold  = 8.0f;
    private int _interval     = 1000;
    
	private long _now = 0;
    private long _timeDiff = 0;
    private long _lastUpdate = 0;
    private long _lastShake = 0;

    private float _accelNow = 0;
    private float _accelLast = 0;
    private float _accel = 0;
    
    private SensorManager _sensorManager;
    private Sensor _accelerometer;
    private ShakeListener _listener;

    public ShakeDetector(Context context) {
    	_sensorManager = (SensorManager) context.getSystemService(
    			Context.SENSOR_SERVICE);
		_accelerometer = _sensorManager.getDefaultSensor(
				Sensor.TYPE_ACCELEROMETER);
    }
	
	public void resume() {
		_sensorManager.registerListener(
				this, _accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void pause() {
		_sensorManager.unregisterListener(this);
	}

	public void listenForShakes(ShakeListener who) {
		_listener = who;
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		//Do nothing
	}

	public void set_threshold(float _threshold) {
		this._threshold = _threshold;
	}

	public void set_interval(int _interval) {
		this._interval = _interval;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			//_now = event.timestamp;
			_now = System.currentTimeMillis();

			//Get X, Y, and Z accelerations
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];

			if (_lastUpdate == 0) {
				_lastUpdate = _now;
				_lastShake = _now;
			} else {
				_timeDiff = _now - _lastUpdate;

				if (_timeDiff > 0) {

					_accelLast = _accelNow;
					_accelNow = (float) Math.sqrt(
							(double) (x * x + y * y + z * z));
					
					float delta = _accelNow - _accelLast;
					_accel = _accel*0.9f + delta;					

					if (Float.compare(_accel, _threshold) > 0 ) {
						if (_now - _lastShake >= _interval) {
							// trigger shake event
							_listener.onShake(_accel);
						}
						_lastShake = _now;
					}
					
					_lastUpdate = _now;
				}
			}
		}	
	}

}
