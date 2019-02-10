package engine.model.terrain;

public class TerrainTexturePack {

	private TerrainTexture groundTexture;
	private TerrainTexture rTexture;
	private TerrainTexture gTexture;
	private TerrainTexture bTexture;
	private TerrainTexture blendTexture;

	public TerrainTexturePack(TerrainTexture groundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture, TerrainTexture blendTexture) {
		this.groundTexture = groundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
		this.blendTexture = blendTexture;
	}

	public void setGroundTexture(TerrainTexture groundTexture) {
		this.groundTexture = groundTexture;
	}

	public void setrTexture(TerrainTexture rTexture) {
		this.rTexture = rTexture;
	}

	public void setgTexture(TerrainTexture gTexture) {
		this.gTexture = gTexture;
	}

	public void setbTexture(TerrainTexture bTexture) {
		this.bTexture = bTexture;
	}

	public void setBlendTexture(TerrainTexture blendTexture) {
		this.blendTexture = blendTexture;
	}

	public TerrainTexture getGroundTexture() {
		return groundTexture;
	}

	public TerrainTexture getrTexture() {
		return rTexture;
	}

	public TerrainTexture getgTexture() {
		return gTexture;
	}

	public TerrainTexture getbTexture() {
		return bTexture;
	}

	public TerrainTexture getBlendTexture() {
		return blendTexture;
	}
}