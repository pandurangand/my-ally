package com.dbt.myally;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.animation.Animation3D;
import rajawali.animation.Animation3D.RepeatMode;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.math.Matrix4;
import rajawali.math.Quaternion;
import rajawali.math.vector.Vector3;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class EarthRenderer extends RajawaliRenderer {
	public DirectionalLight mLight;
	public Object3D mSphere;

	protected Vector3 mFriction;
	protected FloatBuffer mVelocityBuffer;
	protected int mVelocityBufferHandle;
	protected float mTime;
	private Animation3D mAnim = null;
	private static final int baseDuration = 4000;
	private static final int minDuration = 1000;
	private Vector3 mDirection = new Vector3();
	  private float mCameraDistance = 4.2f; 

	private int mAnimDuration = baseDuration;
	private Quaternion mDragRotation = new Quaternion();
	  private float mRecentCameraDistance; 

	private final float TOUCH_SCALE_FACTOR = 180f / 15000;

	public EarthRenderer(Context context) {
		super(context);
		setFrameRate(60);
		getCurrentScene().setBackgroundColor(0xDAAA90);
	}

	public void initScene() {

		mLight = new DirectionalLight(0, 0, -1);
		mLight.setPower(1);
		getCurrentScene().addLight(mLight);

		Object3D circle;
		try {

			float latTor = 43.7f;
			float longTor = -79.4f;
			float lat = 25.8f;
			float lon = -80.2f;

			// Toronto, Miami, Vancouver, Seattle, Ottawa, Waterloo, Quebec
			// City, Berlin, Munich, Paris, Toulouse, Versailles, New York,
			// Boston, London, Beijing, Hong Kong, Sydney
			float[] latitudes = { 43.7f, 25.8f, 49.25f, 47.6062f, 45.4215f,
					43.46f, 46.80f, 52.52f, 48.14f, 48.86f, 43.61f, 48.80f,
					40.71f, 42.36f, 51.51f, 39.9f, 22.396f, -33.87f };
			float[] longitudes = { -79.4f, -80.2f, -123f, -122.33f, -75.6972f,
					-80.52f, -71.24f, 13.41f, 11.58f, 2.352f, 1.44f, 2.13f,
					-74f, -71.06f, -0.13f, 116.41f, 114.11f, 151.21f };
			int[] moods = { 0xFFA500, 0xFF0000, 0x32CD32, 0x0000CD, 0x800080,
					0xFFFF00, 0xFF0000, 0x32CD32, 0x0000CD, 0x0000CD, 0xFFFF00,
					0xFF0000, 0x32CD32, 0x0000CD, 0x800080, 0xFFFF00, 0xFFA500,
					0xFFA500 };
			Material material = new Material();
			material.addTexture(new Texture("earthColors",
					R.drawable.earth_texture));
			material.setColorInfluence(0);

			for (int i = 0; i < latitudes.length; i++) {
				circle = new Sphere(0.01f, 7, 7);
				Material m = new Material();

				circle.setMaterial(m);
				circle.getMaterial().setColor(moods[i]);

				latitudes[i] -= 90;
				float x = (float) (1 * Math.sin(Math.toRadians(latitudes[i])) * Math
						.cos(Math.toRadians(longitudes[i])));
				float z = (float) (-1 * Math.sin(Math.toRadians(latitudes[i])) * Math
						.sin(Math.toRadians(longitudes[i])));
				float y = (float) (1 * Math.cos(Math.toRadians(latitudes[i])));
				circle.setPosition(x, y, z);
				getCurrentScene().addChild(circle);

			}
			mSphere = new Sphere(1, 24, 24);
			mSphere.setMaterial(material);
			getCurrentScene().addChild(mSphere);
			;
		} catch (TextureException e) {
			e.printStackTrace();
		}

		getCurrentCamera().setZ(4.2f);

		resetAnimation(true);
	}

	public void down_press() {
		cancelAnimation();
		storeRotation();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// showLoader();
		super.onSurfaceCreated(gl, config);
		// hideLoader();
	}

	private void storeRotation() {
		mDragRotation = getCurrentCamera().getOrientation(mDragRotation);
	}

	public void pinchOrDragFinished() {
		mCameraDistance = mRecentCameraDistance;
		resetAnimation(true);
		storeRotation();
	}

	public void drag(double x, double y) {
		// Rotates a short distance
		cancelAnimation();
		mDirection = new Vector3(x, y, 0f);
		// Determine how far to rotate and in which direction

		float rotAng = magnitudeOfRotation(x, y) * -TOUCH_SCALE_FACTOR;
		Vector3 axis = perpendicularAxis(x, y);

		// If our objects have an existing rotation, transform
		// the axis of rotation

		if (mDragRotation != null) {
			Matrix4 mat = mDragRotation.toRotationMatrix().inverse();
			axis = axis.multiply(mat);
		}
		axis.normalize();

		// Get the new rotation as a quaternion

		Quaternion rot = new Quaternion();
		rot.fromAngleAxis(axis, rotAng);

		// Apply any existing rotation to it

		if (mDragRotation != null)
			rot.multiply(mDragRotation);

		getCurrentCamera().setOrientation(rot);
		storeRotation();

	}
	public void pinch(float scale) 
	  { 
	    // Zooms the view 
	    
	    setCameraDistance(scale); 
	  } 
	private void setCameraDistance(float scale) 
	  { 
	    // Set our camera distance 
	    
	    mRecentCameraDistance = mCameraDistance / scale; 
	    getCurrentCamera().setZ(mRecentCameraDistance);    
	  } 
	private void cancelAnimation() {
		if (mAnim != null) {
			mAnim.pause();
		}
	}

	private void resetAnimation(boolean sameOrOpposite) {
		// Start by canceling any existing animation

		cancelAnimation();

		// Get our axis of rotation, perpendicular to the swipe
		// direction

		Vector3 axis = perpendicularAxis(-mDirection.x, mDirection.y);
		axis.normalize();

		Quaternion q = new Quaternion();
		q = mSphere.getOrientation(q);
		Matrix4 mat = q.toRotationMatrix().inverse();
		axis = axis.multiply(mat);
		axis.normalize();

		Vector3 rotationAxis = new Vector3(0f, 1f, 0f);
		rotationAxis.normalize();

		mAnim = new RotateAnimation3D(rotationAxis, 360);
		mAnim.setDuration(36000);
		mAnim.setRepeatMode(RepeatMode.INFINITE);
		mAnim.setTransformable3D(getCurrentCamera());

		registerAnimation(mAnim);
		mAnim.play();

	}

	private float magnitudeOfRotation(double x, double y) {
		return (float) new Vector3(x, y, 0).length();
	}

	private Vector3 perpendicularAxis(double x, double y) {
		// Uses a fairly unsophisticated approach to generating
		// a perpendicular vector

		if (y == 0)
			return new Vector3(y, -x, 0);
		else
			return new Vector3(-y, x, 0);
	}

}