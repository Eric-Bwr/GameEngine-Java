package engine.model.camera;

import engine.maths.Mat4f;
import engine.maths.Vec2f;
import engine.maths.Vec3f;
import engine.util.Log;

import static engine.maths.Mathf.*;

public class CameraFPS {

	private Vec3f position;
	private float rotX, rotY, blockUp = 90.0F, blockDown = -90.0F;

	public CameraFPS(Vec3f vec, float rotX, float rotY) {
		this.rotX = rotX;
		this.rotY = rotY;
		this.position = vec;
	}

	public void blockYawUpwards(float block) {
		this.blockUp = block;
	}

	public void blockYawDownwards(float block) {
		this.blockDown = block;
	}

	public float getBlockYawUp() {
		return blockUp;
	}

	public float getBlockYawDown() {
		return blockDown;
	}

	public void moveForward(float speed) {
		position.x(position.x() + speed * sin(rotY));
		position.z(position.z() - speed * cos(rotY));
		position.y(position.y() + -sin(rotX) * speed);
	}

	public void moveBackwards(float speed) {
		position.x(position.x() - speed * sin(rotY));
		position.z(position.z() + speed * cos(rotY));
		position.y(position.y() - -sin(rotX) * speed);
	}

	public void moveRight(float speed){
		position.x(position.x() + speed * sin(rotY + 90));
		position.z(position.z() - speed * cos(rotY + 90));
	}

	public void moveLeft(float speed){
		position.x(position.x() + speed * sin(rotY - 90));
		position.z(position.z() - speed * cos(rotY - 90));
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
		Mat4f.translate(position.negate(), view, view);
		return view;
	}
}
