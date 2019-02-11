package engine.graphics.rendering;

import engine.maths.Vec3f;
import engine.maths.Vec4f;

public class Light {

	private Shader shader;
	private Vec3f position;
	private Vec4f color;
	private float brightness;

	public Light(Shader shader){
		this.shader = shader;
	}

	public void setPosition(String name, Vec3f position) {
		this.position = position;
		shader.setUniform3f(name, position);
	}

	public void setColor(String name, Vec4f color) {
		this.color = color;
		shader.setUniform4f(name, color);
	}

	public void setBrightness(String name, float brightness){
		this.brightness = brightness;
		shader.setUniform1f(name, brightness);
	}

	public Vec3f getPosition() {
		return position;
	}

	public Vec4f getColor() {
		return color;
	}

	public float getBrightness(){
		return brightness;
	}
}