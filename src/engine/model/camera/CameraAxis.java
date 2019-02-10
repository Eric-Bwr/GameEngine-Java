package engine.model.camera;

import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.util.Log;

public class CameraAxis {

	private Vec3f position;
	private float rotX, rotY, rotZ, blockUp = 90.0F, blockDown = -90.0F;

	public CameraAxis(Vec3f vec, float rotX, float rotY, float rotZ){
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.position = vec;
	}

	public void blockYawUpwards(float block){
		this.blockUp = block;
	}

	public void blockYawDownwards(float block){
		this.blockDown = block;
	}

	public float getBlockYawUp() {
		return blockUp;
	}

	public float getBlockYawDown() {
		return blockDown;
	}

	public void moveForward(float speed){
		this.position.add(new Vec3f(0.0F, 0.0F, -speed));
	}

	public void moveBackwards(float speed){
		this.position.add(new Vec3f(0.0F, 0.0F, speed));
	}

	public void moveRight(float speed){
		this.position.add(new Vec3f(speed, 0.0F, 0.0F));
	}

	public void moveLeft(float speed){
		this.position.add(new Vec3f(-speed, 0.0F, 0.0F));
	}

	public void moveUp(float speed){
		this.position.add(new Vec3f(0.0F, speed, 0.0F));
	}

	public void moveDown(float speed){
		this.position.add(new Vec3f(0.0F, -speed, 0.0F));
	}

	public void rotate(float mouseDeltaX, float mouseDeltaY, float sensitivity){
		this.rotY -= mouseDeltaX * sensitivity;
		float resultRotX = this.rotX - mouseDeltaY * sensitivity;
		if(!(resultRotX > blockUp || resultRotX < blockDown))
			this.rotX = resultRotX;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public Vec3f getPosition(){
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public Mat4f getViewMatrix(){
		Mat4f view = Mat4f.identity();
		Mat4f.rotation(rotX, new Vec3f(1, 0, 0), view, view);
		Mat4f.rotation(rotY, new Vec3f(0, 1, 0), view, view);
		Mat4f.rotation(rotZ, new Vec3f(0, 0, 1), view, view);
		Mat4f.translate(position.negate(), view, view);
		return view;
	}
}