package engine.model.terrain;

import engine.graphics.rendering.Shader;
import engine.graphics.rendering.Texture;
import engine.maths.Vec3f;

import java.util.HashMap;
import java.util.Map;

public class TerrainSettings {

	private float scale = 1;
	private Vec3f position = new Vec3f(0, 0, 0);
	private Shader shader;
	private String locationViewMatrix, locationTransformationMatrix, locationProjectionMatrix, locationBlendMap;
	private Texture heightMap, blendMap;
	private Map<Texture, String> otherTerrainTextures = new HashMap<>();

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public String getLocationViewMatrix() {
		return locationViewMatrix;
	}

	public void setLocationViewMatrix(String locationViewMatrix) {
		this.locationViewMatrix = locationViewMatrix;
	}

	public String getLocationTransformationMatrix() {
		return locationTransformationMatrix;
	}

	public String getLocationBlendMap() {
		return locationBlendMap;
	}

	public void setLocationTransformationMatrix(String locationTransformationMatrix) {
		this.locationTransformationMatrix = locationTransformationMatrix;
	}

	public String getLocationProjectionMatrix() {
		return locationProjectionMatrix;
	}

	public void setLocationProjectionMatrix(String locationProjectionMatrix) {
		this.locationProjectionMatrix = locationProjectionMatrix;
	}

	public Texture getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(Texture heightMap) {
		this.heightMap = heightMap;
	}

	public Texture getBlendMap() {
		return blendMap;
	}

	public void setBlendMap(Texture blendMap, String locationBlendMap) {
		this.blendMap = blendMap;
		this.locationBlendMap = locationBlendMap;
	}

	public Map<Texture, String> getOtherTerrainTextures() {
		return otherTerrainTextures;
	}

	public void setOtherTerrainTextures(HashMap<Texture, String> otherTerrainTextures) {
		this.otherTerrainTextures = otherTerrainTextures;
	}

	public void removeOtherTerrainTexture(Texture texture){
		this.otherTerrainTextures.remove(texture);
	}

	public void addOtherTerrainTexture(Texture texture, String location){
		this.otherTerrainTextures.put(texture, location);
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
}