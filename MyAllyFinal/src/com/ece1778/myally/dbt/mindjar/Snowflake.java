package com.ece1778.myally.dbt.mindjar;

public class Snowflake {
	private float _x;
	private float _y;
	private float _z;
	
	private float _xVelocity;
	private float _yVelocity;
	private float _zVelocity;
	
	public Snowflake(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public void setPosition(float x, float y, float z) {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public void setVelocity(float xV, float yV, float zV) {
		_xVelocity = xV;
		_yVelocity = yV;
		_zVelocity = zV;
	}
	
	public void translate() {
		_x += _xVelocity;
		_y += _yVelocity;
		_z += _zVelocity;
	}

	public float get_x() {
		return _x;
	}

	public float get_y() {
		return _y;
	}

	public float get_z() {
		return _z;
	}
	
	public void invert_x_direction() {
		_xVelocity *= -1;
	}
	
	public void invert_y_direction() {
		_yVelocity *= -1;
	}
	
	public void invert_z_direction() {
		_zVelocity *= -1;
	}

	public float get_xVelocity() {
		return _xVelocity;
	}

	public void set_xVelocity(float _xVelocity) {
		this._xVelocity = _xVelocity;
	}

	public float get_yVelocity() {
		return _yVelocity;
	}

	public void set_yVelocity(float _yVelocity) {
		this._yVelocity = _yVelocity;
	}

	public float get_zVelocity() {
		return _zVelocity;
	}

	public void set_zVelocity(float _zVelocity) {
		this._zVelocity = _zVelocity;
	}
}
