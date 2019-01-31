package engine.model.entity;

import engine.graphics.Model;
import engine.maths.Mat4f;
import engine.maths.Vec3f;

public class Entity {

	private Model model = null;
	private Vec3f position = null;
	private float rotX, rotY, rotZ;
	private float scale;

	public Entity(Model model, Vec3f position, float rotX, float rotY, float rotZ, float scale){
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public void draw() {
		model.draw();
	}
	
	public void bind(){
		model.bind();
	}
	
	public void unbind(){
		model.unbind();
	}
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}
	
	public float getRotX() {
		return rotX;
	}

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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Mat4f getTransformationMatrix(){
		Mat4f transformation = Mat4f.identity();
		Mat4f.translate(position, transformation, transformation);
		Mat4f.rotation(rotX, new Vec3f(1, 0, 0), transformation, transformation);
		Mat4f.rotation(rotY, new Vec3f(0, 1, 0), transformation, transformation);
		Mat4f.rotation(rotZ, new Vec3f(0, 0, 1), transformation, transformation);
		Mat4f.scale(new Vec3f(getScale(), getScale(), getScale()), transformation, transformation);
		return transformation;
	}
}