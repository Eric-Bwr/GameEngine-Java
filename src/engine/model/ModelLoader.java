package engine.model;

import engine.graphics.rendering.Model;
import engine.graphics.rendering.Texture;
import engine.maths.Vec2f;
import engine.maths.Vec3f;
import engine.util.FileUtils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {

    public Model loadModel(String fileName, Texture modelTexture){
        BufferedReader reader = FileUtils.loadBufferedReader(fileName);
        String line;
        List<Vec3f> vertices = new ArrayList<>();
        List<Vec2f> textures = new ArrayList<>();
        List<Vec3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;
        try {
            while (true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")){
                    Vec3f vertex = new Vec3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                }else if (line.startsWith("vt ")){
                    Vec2f texture = new Vec2f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                }else if (line.startsWith("vn ")){
                    Vec3f normal = new Vec3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                }else if (line.startsWith("f ")){
                    textureArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }
            while(line != null){
                if (!line .startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = reader.readLine();
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vec3f vertex:vertices){
            verticesArray[vertexPointer++] = vertex.x();
            verticesArray[vertexPointer++] = vertex.y();
            verticesArray[vertexPointer++] = vertex.z();
        }

        for (int i=0;i<indices.size();i++){
            indicesArray[i] = indices.get(i);
        }
        return new Model(modelTexture.getID(), verticesArray, textureArray, normalsArray, indicesArray, false);
    }

    private void processVertex(String[] vertexData, List<Integer> indices,
                                      List<Vec2f> textures, List<Vec3f> normals, float[] textureArray,
                                      float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vec2f currentTex = textures.get(Integer.parseInt(vertexData[1])-1);
        textureArray[currentVertexPointer*2] = currentTex.x();
        textureArray[currentVertexPointer*2+1] = 1 - currentTex.y();
        Vec3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNorm.x();
        normalsArray[currentVertexPointer*3+1] = currentNorm.y();
        normalsArray[currentVertexPointer*3+2] = currentNorm.z();
    }
}