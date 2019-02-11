package engine.graphics.rendering;

import engine.graphics.gl.VertexArrayObject;
import engine.model.terrain.TerrainTexturePack;

public class TerrainModel {

	private TerrainTexturePack terrainTexturePack;
	private VertexArrayObject vao;

	public TerrainModel(TerrainTexturePack texturePack, float[] vpos, float[] texCoords, float[] normals, int[] indices) {
		vao = new VertexArrayObject(vpos, texCoords, normals, indices, false);
		this.terrainTexturePack = texturePack;
	}

	public void bind() {
		terrainTexturePack.bind();
		vao.bind();
	}

	public void draw(){
		vao.draw();
	}

	public void unbind(){
		vao.unbind();
	}

	public void cleanUpMemory(){
		vao.cleanUpMemory();
		terrainTexturePack.cleanUpMemory();
	}
}