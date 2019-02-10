package test;

import engine.graphics.Model;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.model.entity.Entity;
import engine.model.terrain.HeightsGenerator;
import engine.model.terrain.TerrainGenerator;

public class Terrain {

    private Shader shader;
    private Model model;
    private Entity entity;

    private float rot;

    public Terrain(float size, int detailed){
        shader = new Shader("Shaders/Terrain.glsl");
        Texture texture = new Texture("Textures/grass.png");
        TerrainGenerator terrainGenerator = new TerrainGenerator();
        //model = terrainGenerator.generateTerrain(texture, "Textures/heightmap.png", 80, 500);
        model = terrainGenerator.generateTerrain(texture, 100, 500, new HeightsGenerator(50, 4, 0F));
        entity = new Entity(model, new Vec3f(0, 0, 0), 0, 0, 0, 1);
    }

    public void begin(){
        shader.bind();
        entity.bind();
    }

    public void render(){
        rot += 4F;
        shader.setUniformMat4f("transformationMatrix", entity.getTransformationMatrix());
        entity.draw();
    }

    public void end(){
        entity.unbind();
        shader.unbind();
    }

    public void setViewMatrix(Mat4f viewMatrix) {
        shader.setUniformMat4f("viewMatrix", viewMatrix);
    }

    public void setProjectionMatrix(Mat4f projection) {
        shader.setUniformMat4f("projectionMatrix", projection);
    }

    public void cleanUp(){
        entity.cleanUpMemory();
    }
}