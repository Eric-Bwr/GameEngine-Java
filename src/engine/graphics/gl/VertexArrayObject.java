package engine.graphics.gl;

import static org.lwjgl.opengl.GL30.*;

//TODO: Test with shader
public class VertexArrayObject {

    private int id;
    private IndicesBufferObject indices;
    private VertexBufferObject position;
    private int vertexCount;

    public VertexArrayObject(float[] vpos, int[] indices){
        this.id = glGenVertexArrays();
        bind();
        this.indices = new IndicesBufferObject(indices);
        this.position = new VertexBufferObject(0, vpos, 3);
        unbind();
        this.vertexCount = indices.length;
    }

    public void bind(){
        glBindVertexArray(id);
        indices.bind();
        position.bind();
    }

    public void draw(){
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
    }

    public void unbind(){
        position.unbind();
        indices.unbind();
        glBindVertexArray(0);
    }

    public void renderToScreen(){
        bind();
        draw();
        unbind();
    }

    public void cleanUpMemory(){
        position.cleanUpMemory();
        indices.cleanUpMemory();
        unbind();
        glDeleteVertexArrays(id);
    }

}
