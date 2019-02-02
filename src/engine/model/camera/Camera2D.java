package engine.model.camera;

import engine.maths.Vec2f;

public class Camera2D {

	private Vec2f position;

	public Camera2D(Vec2f position){
		this.position = position;
	}

	public Vec2f getPosition(){
		return position;
	}

	public void setPosition(Vec2f position){
		this.position = position;
	}

	public void moveRight(float speed){
		position.add(new Vec2f(speed, 0.0F));
	}

	public void moveLeft(float speed){
		position.add(new Vec2f(-speed, 0.0F));
	}

	public void moveUp(float speed){
		position.add(new Vec2f(0.0F, speed));
	}

	public void moveDown(float speed){
		position.add(new Vec2f(0.0F, -speed));
	}
}