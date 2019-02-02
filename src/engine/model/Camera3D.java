package engine.model;

import engine.maths.Mat4f;
import engine.maths.Vec3f;

public class Camera3D {

	private Vec3f position;
	private float yaw, pitch, roll, blockUp = 90.0F, blockDown = -90.0F;
	private boolean axis;

	public Camera3D(Vec3f vec, float yaw, float pitch, float roll, boolean axis){
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
		this.position = vec;
		this.axis = axis;
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
		if(axis)
			this.position.add(new Vec3f(0.0F, 0.0F, speed));
		else{
			//TODO: Implement
		}
	}

	public void moveBackwards(float speed){
		if(axis)
			this.position.add(new Vec3f(0.0F, 0.0F, -speed));
		else{
			//TODO: Implement
		}
	}

	public void moveRight(float speed){
		if(axis)
			this.position.add(new Vec3f(-speed, 0.0F, 0.0F));
		else{
			//TODO: Implement
		}
	}

	public void moveLeft(float speed){
		if(axis)
			this.position.add(new Vec3f(speed, 0.0F, 0.0F));
		else{
			//TODO: Implement
		}
	}

	public void moveUp(float speed){
		this.position.add(new Vec3f(0.0F, -speed, 0.0F));
	}

	public void moveDown(float speed){
		this.position.add(new Vec3f(0.0F, speed, 0.0F));
	}

	public void rotate(float mouseDeltaX, float mouseDeltaY, float sensitivity){
		float resultYaw = this.yaw - mouseDeltaY * sensitivity;
		if(!(resultYaw > blockUp || resultYaw < blockDown))
			this.yaw = resultYaw;
		this.pitch = this.pitch - mouseDeltaX * sensitivity;
	}

	public Vec3f getPosition(){
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public Mat4f getViewMatrix(){
        Mat4f view = Mat4f.identity();
        Mat4f.rotation(yaw, new Vec3f(1, 0, 0), view, view);
        Mat4f.rotation(pitch, new Vec3f(0, 1, 0), view, view);
		Mat4f.rotation(roll, new Vec3f(0, 0, 1), view, view);
        Mat4f.translate(position, view, view);
	    return view;
	}
}