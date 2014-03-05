package com.dbt.myally;

import java.util.concurrent.atomic.AtomicBoolean;

import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Build;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class HeartRateDetector {
	private static final String TAG = "HeartRateDetector";

	private static SurfaceView _surfaceView;

	private static PreviewCallback _previewCallback = null;

	private static SurfaceHolder.Callback _surfaceCallback = null;

	private static Camera _camera;

	private static int _heartRate;

	private static long _startTime = 0;
	
	//private static View _image = null;
	
	private PowerManager _powerManager;
	
	private static WakeLock wakeLock = null;


	public HeartRateDetector(PowerManager powerManager, SurfaceView surfaceView) {
		_powerManager = powerManager;
		//_image = image;
		_surfaceView = surfaceView;
		_heartRate = 0;
	}

	public int getHeartRate() {
		return _heartRate;
	}

	@SuppressWarnings("deprecation")
	public void init() {
		_surfaceView.getHolder().addCallback(getSurfaceCallback());
		//Required for Android Versions prior to 3.0
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			_surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}


		//PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = _powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "DoNotDimScreen");
	}	

	public void resume() {
		wakeLock.acquire();
		_camera = Camera.open();
		_startTime = System.currentTimeMillis();
	}

	public void pause() {
		wakeLock.release();
		
		_camera.setPreviewCallback(null);
		_camera.stopPreview();
		_camera.release();
		_camera = null;
	}
	
	private static void setHeartRate(int hr) {
		_heartRate = hr;
	}

	private static PreviewCallback getPreviewCallback() {
		if(_previewCallback == null) {
			_previewCallback = new HRPreviewCallback();
		}

		return _previewCallback;
	}

	private static SurfaceHolder.Callback getSurfaceCallback() {
		if(_surfaceCallback == null) {
			_surfaceCallback = new HRSurfaceCallback();
		}

		return _surfaceCallback;
	}

	private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
		Camera.Size result = null;

		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea < resultArea) result = size;
				}
			}
		}

		return result;
	}

	private static class HRPreviewCallback implements PreviewCallback {
		//Lock for processing heartrate (i.e. decoding image)
		private static final AtomicBoolean _processing = new AtomicBoolean(false);
		
		private static int _averageIndex = 0;
	    private static final int _averageArraySize = 4;
	    private static final int[] _averageArray = new int[_averageArraySize];
	    
	    private static double _beats = 0;
	    private static int _beatsIndex = 0;
	    private static final int _beatsArraySize = 3;
	    private static final int[] _beatsArray = new int[_beatsArraySize];
	    
	    public static enum TYPE {
	        GREEN, RED
	    };

	    private static TYPE currentType = TYPE.GREEN;
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onPreviewFrame(byte[] data, Camera cam) {			
			if (data == null) {
				throw new NullPointerException();
			}
			
			Camera.Size size = cam.getParameters().getPreviewSize();
			if (size == null) {
				throw new NullPointerException();
			}

			//Acquire lock
			if (!_processing.compareAndSet(false, true)) {
				return;
			}

			//Decode image
			int width = size.width;
			int height = size.height;
			int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
			
			if (imgAvg == 0 || imgAvg == 255) {
				//Unlock
				_processing.set(false);
				return;
			}

			int averageArrayAvg = 0;
			int averageArrayCnt = 0;
			for (int i = 0; i < _averageArray.length; i++) {
				if (_averageArray[i] > 0) {
					averageArrayAvg += _averageArray[i];
					averageArrayCnt++;
				}
			}

			int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
			TYPE newType = currentType;
			if (imgAvg < rollingAverage) {
				newType = TYPE.RED;
				if (newType != currentType) {
					_beats++;
					// Log.d(TAG, "BEAT!! beats="+beats);
				}
			} else if (imgAvg > rollingAverage) {
				newType = TYPE.GREEN;
			}

			if (_averageIndex == _averageArraySize) { 
				_averageIndex = 0;
			}
			_averageArray[_averageIndex] = imgAvg;
			_averageIndex++;

			// Transitioned from one state to another to the same
			if (newType != currentType) {
				currentType = newType;
				//_image.postInvalidate();
			}

			long endTime = System.currentTimeMillis();
			double totalTimeInSecs = (endTime - _startTime) / 1000d;
			if (totalTimeInSecs >= 5) {
				double bps = (_beats / totalTimeInSecs);
				int dpm = (int) (bps * 60d);
				if (dpm < 30 || dpm > 180) {
					_beats = 0;
					_processing.set(false);
					return;
				}

				if (_beatsIndex == _beatsArraySize) {
					_beatsIndex = 0;
				}
				_beatsArray[_beatsIndex] = dpm;
				_beatsIndex++;

				int beatsArrayAvg = 0;
				int beatsArrayCnt = 0;
				for (int i = 0; i < _beatsArray.length; i++) {
					if (_beatsArray[i] > 0) {
						beatsArrayAvg += _beatsArray[i];
						beatsArrayCnt++;
					}
				}
				
				setHeartRate(beatsArrayAvg / beatsArrayCnt);
				_startTime = System.currentTimeMillis();
				_beats = 0;
			}
			_processing.set(false);
		}
	}

	private static class HRSurfaceCallback implements SurfaceHolder.Callback {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				_camera.setPreviewDisplay(_surfaceView.getHolder());
				_camera.setPreviewCallback(getPreviewCallback());
			} catch (Throwable t) {
				Log.e("HeartRateDetector-surfaceCallback", "Exception in setPreviewDisplay()", t);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Camera.Parameters parameters = _camera.getParameters();
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			Camera.Size size = getSmallestPreviewSize(width, height, parameters);
			if (size != null) {
				parameters.setPreviewSize(size.width, size.height);
				Log.d(TAG, "Using width=" + size.width + " height=" + size.height);
			}
			_camera.setParameters(parameters);
			_camera.startPreview();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			//Do nothing (ignore)
		}
	}
}
