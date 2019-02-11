package engine.model.entity;

import engine.graphics.rendering.Model;
import engine.graphics.rendering.TerrainModel;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.util.Log;

public class TerrainEntity {

	private TerrainModel terrainModel;
	private Model model;
	private Vec3f position;
	private float scale;

	public TerrainEntity(TerrainModel model, Vec3f position, float scale){
		this.terrainModel = model;
		this.position = position;
		this.scale = scale;
	}

	public TerrainEntity(Model model, Vec3f position, float scale){
		this.model = model;
		this.position = position;
		this.scale = scale;
	}

	public void draw() {
		if(model == null)
			terrainModel.draw();
		else
			model.draw();
	}

	public void bind(){
		if(model == null)
			terrainModel.bind();
		else
			model.bind();
	}

	public void unbind(){
		if(model == null)
			terrainModel.unbind();
		else
			model.unbind();
	}

	public TerrainModel getTerrainModel() {
		return terrainModel;
	}

	public void setTerrainModel(TerrainModel terrainModel) {
		this.terrainModel = terrainModel;
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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Mat4f getTransformationMatrix(){
		Mat4f transformation = Mat4f.identity();
		Mat4f.translate(position, transformation, transformation);
		Mat4f.scale(new Vec3f(getScale(), getScale(), getScale()), transformation, transformation);
		return transformation;
	}

	public void cleanUpMemory() {
		if(model == null)
			terrainModel.cleanUpMemory();
		else
			model.cleanUpMemory();
	}
}