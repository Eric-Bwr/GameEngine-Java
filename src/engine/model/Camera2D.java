package engine.model;

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
}