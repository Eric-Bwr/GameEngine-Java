package engine.model.terrain;

public class TerrainTexturePack {

	private int groundTexture;
	private int rTexture;
	private int gTexture;
	private int bTexture;
	private int blendTexture;

	public TerrainTexturePack(int groundTexture, int rTexture, int gTexture, int bTexture, int blendTexture) {
		this.groundTexture = groundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
		this.blendTexture = blendTexture;
	}

	public void setGroundTexture(int groundTexture) {
		this.groundTexture = groundTexture;
	}

	public void setrTexture(int rTexture) {
		this.rTexture = rTexture;
	}

	public void setgTexture(int gTexture) {
		this.gTexture = gTexture;
	}

	public void setbTexture(int bTexture) {
		this.bTexture = bTexture;
	}

	public void setBlendTexture(int blendTexture) {
		this.blendTexture = blendTexture;
	}

	public int getGroundTexture() {
		return groundTexture;
	}

	public int getrTexture() {
		return rTexture;
	}

	public int getgTexture() {
		return gTexture;
	}

	public int getbTexture() {
		return bTexture;
	}

	public int getBlendTexture() {
		return blendTexture;
	}
}