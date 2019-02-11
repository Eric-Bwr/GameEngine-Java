package engine.graphics.rendering;

import engine.graphics.gl.VertexArrayObject;

import static org.lwjgl.opengl.GL13.*;

public class Model  {

    private int texture;
    private VertexArrayObject vao;
    //TODO: Grass/Transparency could cause problems
    private boolean transparent, useFakeLighting;

    public Model(int texture, float[] vpos, float[] texCoords, float[] normals, int[] indices, boolean simple) {
        vao = new VertexArrayObject(vpos, texCoords, normals, indices, simple);
        this.texture = texture;
    }

    public void bind() {
        if(transparent) {
            glDisable(GL_CULL_FACE);
        }
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture);
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
        glDeleteTextures(texture);
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public void setFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
}