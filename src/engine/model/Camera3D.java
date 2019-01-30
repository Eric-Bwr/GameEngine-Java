package engine.model;

import engine.maths.Mat4f;
import engine.maths.Vec3f;

public class Camera3D {

	private Vec3f position;
	private float rotX, rotY, rotZ;

	public Camera3D(Vec3f vec, float rotX, float rotY, float rotZ){
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.position = vec;
	}

	public Vec3f getPosition(){
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public float getRotX() { return rotX; }

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void move(Vec3f direction){
		this.setPosition(getPosition().add(direction));
	}

	public Mat4f getViewMatrix(){
        Mat4f view = Mat4f.identity();
        Mat4f.rotation(rotX, new Vec3f(1, 0, 0), view, view);
        Mat4f.rotation(rotY, new Vec3f(0, 1, 0), view, view);
        Mat4f.rotation(rotZ, new Vec3f(0, 0, 1), view, view);
        Mat4f.translate(position, view, view);
	    return view;
	}
}