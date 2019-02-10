package engine.graphics;

import engine.graphics.gl.VertexArrayObject;

import static org.lwjgl.opengl.GL13.*;

public class Model  {

    private Texture texture;
    private VertexArrayObject vao;
    //TODO: Grass/Transparency could cause problems
    private boolean transparent, useFakeLighting;

    public Model(Texture texture, float[] vpos, float[] texCoords, float[] normals, int[] indices) {
        vao = new VertexArrayObject(vpos, texCoords, normals, indices, false);
        this.texture = texture;
    }

    public void bind() {
        if(transparent) {
            glDisable(GL_CULL_FACE);
        }
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
        vao.bind();
    }

    public void renderToScreen(){
        vao.renderToScreen();
    }

    public void draw(){
        vao.draw();
    }

    public void unbind(){
        if(transparent) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_BACK);
        }
        vao.unbind();
    }

    public void cleanUpMemory(){
        vao.cleanUpMemory();
        texture.cleanUpMemory();
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
}