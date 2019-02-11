package engine.model.terrain;

import engine.graphics.rendering.Shader;
import engine.graphics.rendering.Texture;
import engine.util.Log;

import java.util.*;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.*;

public class TerrainTexturePack {

	private Shader shader;
	private Texture heightMapTexture;
	private Integer heightMap;
	private Integer blendTexture;
	private String locationBlendMap;
	private List<Integer> otherTerrainTexturesIds;
	private List<String> otherTerrainTexturesLocations;
	private Map<Texture, String> otherTerrainTextures;

	public TerrainTexturePack(TerrainSettings settings) {
		this.shader = settings.getShader();
		this.heightMapTexture = settings.getHeightMap();
		this.heightMap = settings.getHeightMap().getID();
		if(settings.getBlendMap() != null) {
			this.locationBlendMap = settings.getLocationBlendMap();
			this.blendTexture = settings.getBlendMap().getID();
		}
		this.otherTerrainTextures = settings.getOtherTerrainTextures();
		this.otherTerrainTexturesLocations = new ArrayList<>();
		this.otherTerrainTexturesIds = new ArrayList<>();
		if(!otherTerrainTextures.isEmpty()) {
			for(Map.Entry<Texture, String> texture : otherTerrainTextures.entrySet()){
				this.otherTerrainTexturesIds.add(texture.getKey().getID());
				this.otherTerrainTexturesLocations.add(texture.getValue());
			}
		}
		connectTextureUnits();
	}

	private void connectTextureUnits(){
		if(blendTexture != null)
			shader.setUniform1i(locationBlendMap, 0);
		if(!otherTerrainTexturesLocations.isEmpty()){
			int current = 1;
			for(String texture : otherTerrainTexturesLocations){
				Log.log(current + "    " + texture);
				shader.setUniform1i(texture, current);
				current++;
			}
		}
	}

	public void bind() {
		if (blendTexture != null) {
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, blendTexture);
		}
		if(!otherTerrainTexturesLocations.isEmpty()) {
			int current = 33985;
			for (Integer id : otherTerrainTexturesIds) {
				glActiveTexture(current);
				glBindTexture(GL_TEXTURE_2D, id);
				current++;
			}
		}
		//TODO: glActiveTexture(GL_TEXTURE0);
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	public Integer getBlendTexture() {
		return blendTexture;
	}

	public void setBlendTexture(Integer blendTexture) {
		this.blendTexture = blendTexture;
	}

	public String getLocationBlendMap() {
		return locationBlendMap;
	}

	public Texture getHeightMapTexture() {
		return heightMapTexture;
	}

	public void setHeightMapTexture(Texture heightMapTexture) {
		this.heightMapTexture = heightMapTexture;
	}

	public Integer getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(Integer heightMap) {
		this.heightMap = heightMap;
	}

	public void setOtherTerrainTextures(Map<Texture, String> otherTerrainTextures) {
		this.otherTerrainTextures = otherTerrainTextures;
	}

	public void setLocationBlendMap(String locationBlendMap) {
		this.locationBlendMap = locationBlendMap;
	}

	public List<Integer> getOtherTerrainTexturesIds() {
		return otherTerrainTexturesIds;
	}

	public void setOtherTerrainTexturesIds(List<Integer> otherTerrainTexturesIds) {
		this.otherTerrainTexturesIds = otherTerrainTexturesIds;
	}

	public List<String> getOtherTerrainTexturesLocations() {
		return otherTerrainTexturesLocations;
	}

	public void setOtherTerrainTexturesLocations(List<String> otherTerrainTexturesLocations) {
		this.otherTerrainTexturesLocations = otherTerrainTexturesLocations;
	}

	public Map<Texture, String> getOtherTerrainTextures() {
		return otherTerrainTextures;
	}

	public void setOtherTerrainTextures(TreeMap<Texture, String> otherTerrainTextures) {
		this.otherTerrainTextures = otherTerrainTextures;
	}

	public void cleanUpMemory() {
		if(blendTexture != null)
			glDeleteTextures(blendTexture);
		if(!otherTerrainTexturesLocations.isEmpty()) {
			for (Texture texture : otherTerrainTextures.keySet() ) {
				if(texture == null) Log.log("Texture in TPack is null");
				texture.cleanUpMemory();
			}
		}
	}
}