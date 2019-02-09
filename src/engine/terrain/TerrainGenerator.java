package engine.terrain;

import engine.graphics.Model;
import engine.graphics.Texture;

public class TerrainGenerator {

    public static Model generateTerrain(Texture texture, float size, int detailed){
        int details = detailed * detailed;
        int detailsMask = (detailed - 1) * (detailed - 1);
        int detailedMask = detailed - 1;

        float[] vertices = new float[details * 3];
        float[] normals = new float[details * 3];
        float[] textureCoords = new float[details * 2];
        int[] indices = new int[6 * detailsMask];
        int counter = 0;
        for(int y = 0; y < detailed; y++){
            for(int x = 0; x < detailed; x++){
                vertices[counter * 3] = x / detailedMask * size;
                vertices[counter * 3 + 1] = 0;
                vertices[counter * 3 + 2] = y / detailedMask * size;
                normals[counter * 3] = 0;
                normals[counter * 3 + 1] = 1;
                normals[counter * 3 + 2] = 0;
                textureCoords[counter * 2] = x / detailedMask;
                textureCoords[counter++ * 2 + 1] = y / detailedMask;
            }
        }
        counter = 0;
        for(int y = 0; y < detailedMask; y++){
            for(int x = 0; x < detailedMask; x++){
                int a = y * detailed + x;
                int b = a + 1;
                int c = (y + 1)* detailed + x;
                int d = c + 1;
                indices[counter++] = a;
                indices[counter++] = c;
                indices[counter++] = b;
                indices[counter++] = b;
                indices[counter++] = c;
                indices[counter++] = d;
            }
        }
        return new Model(texture, vertices, textureCoords, normals, indices);
    }
}