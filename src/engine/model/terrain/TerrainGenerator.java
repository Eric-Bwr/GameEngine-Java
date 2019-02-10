package engine.model.terrain;

import engine.graphics.Model;
import engine.graphics.Texture;
import engine.maths.Vec3f;
import engine.util.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TerrainGenerator {

	private final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	private float maxHeight;
	private float[][] heights;

	public Model generateTerrain(Texture texture, int vertexCount, float size, HeightsGenerator generator) {
		heights = new float[vertexCount][vertexCount];
		int count = vertexCount * vertexCount;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (vertexCount - 1) * (vertexCount * 1)];
		int vertexPointer = 0;
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) vertexCount - 1) * size;
				float height = getHeight(j, i, generator);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * size;
				Vec3f normal = calculateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x();
				normals[vertexPointer * 3 + 1] = normal.y();
				normals[vertexPointer * 3 + 2] = normal.z();
				textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < vertexCount - 1; gz++) {
			for (int gx = 0; gx < vertexCount - 1; gx++) {
				int topLeft = (gz * vertexCount) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * vertexCount) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return new Model(texture.getTextureId(), vertices, textureCoords, normals, indices, false);
	}

	public Model generateTerrain(Texture texture, String heightMap, float maxHeight, float size) {
		this.maxHeight = maxHeight;
		BufferedImage image = null;
		try {
			image = ImageIO.read(Class.class.getResourceAsStream("/" + heightMap));
		} catch (IOException d) {
			Log.logError("Failed to load heightMap");
		}
		int VERTEX_COUNT = image.getHeight();
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1)];
		int vertexPointer = 0;
		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * size;
				float height = getHeight(j, i, image);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * size;
				Vec3f normal = calculateNormal(j, i, image);
				normals[vertexPointer * 3] = normal.x();
				normals[vertexPointer * 3 + 1] = normal.y();
				normals[vertexPointer * 3 + 2] = normal.z();
				textureCoords[vertexPointer * 2] = (float) j / ((float) VERTEX_COUNT - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return new Model(texture.getTextureId(), vertices, textureCoords, normals, indices, false);
	}

	private Vec3f calculateNormal(int x, int z, BufferedImage image) {
		float heightL = getHeight(x - 1, z, image);
		float heightR = getHeight(x + 1, z, image);
		float heightD = getHeight(x, z - 1, image);
		float heightU = getHeight(x, z + 1, image);

		Vec3f normal = new Vec3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalize();
		return normal;

	}

	private Vec3f calculateNormal(int x, int z, HeightsGenerator generator) {
		float heightL = getHeight(x - 1, z, generator);
		float heightR = getHeight(x + 1, z, generator);
		float heightD = getHeight(x, z - 1, generator);
		float heightU = getHeight(x, z + 1, generator);

		Vec3f normal = new Vec3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalize();
		return normal;
	}

	private float getHeight(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= maxHeight;
		return height;
	}

	private float getHeight(int x, int z, HeightsGenerator generator) {
		return generator.generateHeight(x, z);
	}
}