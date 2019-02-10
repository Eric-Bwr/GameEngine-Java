package engine.graphics;

import engine.model.terrain.TerrainTexturePack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class TerrainTexture {

	private TerrainTexturePack texturePack;

	public TerrainTexture(TerrainTexturePack texturePack) {
		this.texturePack = texturePack;
	}

	public void bind() {
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texturePack.getGroundTexture());
		glActiveTexture(GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texturePack.getrTexture());
		glActiveTexture(GL_TEXTURE2);
		glBindTexture(GL_TEXTURE_2D, texturePack.getgTexture());
		glActiveTexture(GL_TEXTURE3);
		glBindTexture(GL_TEXTURE_2D, texturePack.getbTexture());
		glActiveTexture(GL_TEXTURE4);
		glBindTexture(GL_TEXTURE_2D, texturePack.getBlendTexture());
	}

	public void cleanUpMemory() {
		glDeleteTextures(texturePack.getGroundTexture());
		glDeleteTextures(texturePack.getrTexture());
		glDeleteTextures(texturePack.getgTexture());
		glDeleteTextures(texturePack.getbTexture());
		glDeleteTextures(texturePack.getBlendTexture());
	}
}