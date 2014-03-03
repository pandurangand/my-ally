package com.dbt.myally;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.Object3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.Material;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;

public class EarthRenderer extends RajawaliRenderer {
	public DirectionalLight mLight;
    public Object3D mSphere;
    //public Context context;
    //public Camera camera;

    public EarthRenderer(Context context) {
        super(context);
        //this.context = context;
        setFrameRate(60);
    }

    public void initScene() {
    	mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);
		
		getCurrentScene().addLight(mLight);

		try {
			Material material = new Material();
			material.addTexture(new Texture("earthColors",
					R.drawable.earthtruecolor_nasa_big));
			material.setColorInfluence(0);
			mSphere = new Sphere(1, 24, 24);
			mSphere.setMaterial(material);
			getCurrentScene().addChild(mSphere);
		} catch (TextureException e) {
			e.printStackTrace();
		}

		getCurrentCamera().setZ(4.2f);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mSphere.setRotY(mSphere.getRotY() + 1);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//showLoader();
		super.onSurfaceCreated(gl, config);
		//hideLoader();
	}
}