package engine.graphics;

import engine.graphics.gl.VertexArrayObject;

import static org.lwjgl.opengl.GL13.*;

public class Model  {

    private Texture texture;
    private VertexArrayObject vao;

    public Model(Texture texture, float[] vpos, float[] texCoords, float[] normals, int[] indices) {
        vao = new VertexArrayObject(vpos, texCoords, normals, indices, false);
        this.texture = texture;
    }

    public void bind() {
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
        vao.unbind();
    }

    public void cleanUpMemory(){
        vao.cleanUpMemory();
        texture.cleanUpMemory();
    }
}