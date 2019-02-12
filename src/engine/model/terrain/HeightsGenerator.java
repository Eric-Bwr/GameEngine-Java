package engine.model.terrain;

import java.util.Random;
import static engine.maths.Mathf.*;

public class HeightsGenerator {

	private float amplitude, roughness;
	private int octaves;
	private int vertexCount;
	private int gridX, gridZ;

	private Random random = new Random();
	private int seed;

	public HeightsGenerator(float amplitude, int octaves, float roughness, int seed){
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.seed = seed;
	}

	public HeightsGenerator(float amplitude, int octaves, float roughness){
		this.amplitude = amplitude;
		this.octaves = octaves;
		this.roughness = roughness;
		this.seed = random.nextInt(1000000000);
	}

	public float generateHeight(int x, int z){
		int xOffset = gridX * (vertexCount - 1);
		int zOffset = gridZ * (vertexCount - 1);


		float total = 0;
		float d = pow(2, octaves - 1);
		for(int i = 0; i < octaves; i++){
			float freq = pow(2, i) / d;
			float amp = pow(roughness, i) * amplitude;
			total += getInterpolatedNoise((x + xOffset) * freq, (z + zOffset) * freq) * amp;
		}
		return total;
	}

	private float getInterpolatedNoise(float x, float z){
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;

		float v1 = getSmoothNoise(intX, intZ);
		float v2 = getSmoothNoise(intX + 1, intZ);
		float v3 = getSmoothNoise(intX, intZ + 1);
		float v4 = getSmoothNoise(intX + 1, intZ + 1);
		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);
		return interpolate(i1, i2, fracZ);
	}

	private float interpolate(float a, float b, float blend){
		double theta = blend * PIS_LITTLE_BROTHER;
		float f = (float)(1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	private float getSmoothNoise(int x, int z) {
		float corners = (getNoise(x - 1, z - 1) + getNoise(x + 1, z - 1) + getNoise(x - 1, z + 1)
			+ getNoise(x + 1, z + 1)) / 16f;
		float sides = (getNoise(x - 1, z) + getNoise(x + 1, z) + getNoise(x, z - 1)
			+ getNoise(x, z + 1)) / 8f;
		float center = getNoise(x, z) / 4f;
		return corners + sides + center;
	}

	private float getNoise(int x, int z) {
		random.setSeed(x * 49632 + z * 325176 + seed);
		return random.nextFloat() * 2f - 1f;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public void setGridZ(int gridZ) {
		this.gridZ = gridZ;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridZ() {
		return gridZ;
	}

	public int getSeed(){
		return seed;
	}
}