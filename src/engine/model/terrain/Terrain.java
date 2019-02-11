package engine.model.terrain;

import engine.graphics.rendering.Model;
import engine.graphics.rendering.Shader;
import engine.graphics.rendering.TerrainModel;
import engine.graphics.rendering.Texture;
import engine.maths.Mat4f;
import engine.model.entity.TerrainEntity;

public class Terrain {

    private Shader shader;
    private TerrainModel terrainModel;
    private Model model;
    private TerrainEntity terrainEntity;
    private TerrainTexturePack terrainTexturePack;
    private String locationViewMatrix, locationTransformationMatrix, locationProjectionMatrix;

    public Terrain(TerrainSettings settings, HeightsGenerator heightsGenerator, Texture texture, int vertexCount, float size){
        this.locationProjectionMatrix = settings.getLocationProjectionMatrix();
        this.locationTransformationMatrix = settings.getLocationTransformationMatrix();
        this.locationViewMatrix = settings.getLocationViewMatrix();
        this.shader = settings.getShader();
        model = new TerrainGenerator().generateTerrain(texture, vertexCount, size, heightsGenerator);
        terrainEntity = new TerrainEntity(model, settings.getPosition(), settings.getScale());
    }

    public Terrain(TerrainSettings settings, float maxHeight, float size){
        this.locationProjectionMatrix = settings.getLocationProjectionMatrix();
        this.locationTransformationMatrix = settings.getLocationTransformationMatrix();
        this.locationViewMatrix = settings.getLocationViewMatrix();
        this.shader = settings.getShader();
        TerrainTexturePack terrainTexturePack = new TerrainTexturePack(settings);
        this.terrainTexturePack = terrainTexturePack;
        terrainModel = new TerrainGenerator().generateTerrain(terrainTexturePack, maxHeight, size);
        terrainEntity = new TerrainEntity(terrainModel, settings.getPosition(), settings.getScale());
    }

    public void begin(){
        shader.bind();
        terrainEntity.bind();
    }

    public void render(){
        shader.setUniformMat4f(locationTransformationMatrix, terrainEntity.getTransformationMatrix());
        terrainEntity.draw();
    }

    public void end(){
        terrainEntity.unbind();
        shader.unbind();
    }

    public void setViewMatrix(Mat4f viewMatrix) {
        shader.setUniformMat4f(locationViewMatrix, viewMatrix);
    }

    public void setProjectionMatrix(Mat4f projection) {
        shader.setUniformMat4f(locationProjectionMatrix, projection);
    }

    public void cleanUpMemory(){
        terrainEntity.cleanUpMemory();
    }
}