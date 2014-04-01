package com.ece1778.myally.dbt.thoughtdiffusion;

import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Thought container class. Contains x, y positions and velocities, as well
 * as the thought message itself and whether or not the thought has been touched.
 *
 */
public class Thought {
	//Random positions and velocities for thoughts
	private Random _rand = new Random();
	
	private int _x = -1;
	private int _y = -1;

	private int _xVelocity;
	private int _yVelocity;

	private String _thought;

	private boolean _touched = false;

	private int _numBounces = 0;
	//Reusable Paint variable
	private Paint _paint;
	//Reusable Rect variable
	private Rect _bounds = new Rect();

	public Thought(String thought) {
		_thought = thought;

		_x = _rand.nextInt(300) + 100;
		_y = _rand.nextInt(300) + 100;

		_xVelocity = _rand.nextInt(2) + 1;
		_yVelocity = _rand.nextInt(2) + 1;	
		
		_paint = new Paint();
		_paint.setColor(Color.BLACK);
		_paint.setTextSize(50);
		_paint.setTextAlign(Paint.Align.CENTER);
		_paint.setStyle(Paint.Style.FILL);
	}

	public void incrementBounces() {
		_numBounces++;
	}

	public int get_numBounces() {
		return _numBounces;
	}

	public void reset_numBounces() {
		_numBounces = 0;
	}

	public void translate() {
		_x = _x + _xVelocity;
		_y = _y + _yVelocity;
	}

	public Paint get_paint() {
		return _paint;
	}

	public Rect get_cloudBounds() {
		Rect bounds = new Rect();
		Rect textBounds = get_bounds();
		
		bounds.set(
				textBounds.left - 100, 
				textBounds.top - 50, 
				textBounds.right + 100, 
				textBounds.bottom + 50);	
		
		return bounds;
	}
	
	public Rect get_bounds() {
		_paint.getTextBounds(get_thought(), 0, get_thought().length(), 
				_bounds);

		Rect bounds = new Rect();
		int fudge_factor = 10;
		bounds.set(
				get_x() - _bounds.width() / 2 - fudge_factor, 
				get_y() - _bounds.height(), 
				get_x() + _bounds.width() / 2, 
				get_y() + _bounds.height()/3 - fudge_factor);


		return bounds;
	}

	public void fling(float velocityX, float velocityY) {		
		set_xVelocity((int) velocityX / 300);
		set_yVelocity((int) velocityY / 300);
	}
	
	public int get_x() {
		return _x;
	}

	public void set_xVelocity(int _xVelocity) {
		this._xVelocity = _xVelocity;
	}

	public void set_yVelocity(int _yVelocity) {
		this._yVelocity = _yVelocity;
	}

	public int get_y() {
		return _y;
	}

	public void set_x(int _x) {
		this._x = _x;
	}

	public void set_y(int _y) {
		this._y = _y;
	}

	public void invert_x_direction() {
		_xVelocity *= -1;
	}

	public void invert_y_direction() {
		_yVelocity *= -1;
	}

	public String get_thought() {
		return _thought;
	}

	public boolean is_touched() {
		return _touched;
	}

	public void set_touched(boolean _touched) {
		this._touched = _touched;
	}
}