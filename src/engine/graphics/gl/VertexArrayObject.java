package engine.graphics.gl;

import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject {

    private int id;
    private IndicesBufferObject indices;
    private VertexBufferObject position;
    private VertexBufferObject texCoords;
    private int vertexCount;

    public VertexArrayObject(float[] vpos, float[] texCoords, int[] indices){
        this.id = glGenVertexArrays();
        glBindVertexArray(id);
        this.indices = new IndicesBufferObject(indices);
        this.position = new VertexBufferObject(0, vpos, 3);
        this.texCoords = new VertexBufferObject(1, texCoords, 2);
        glBindVertexArray(0);
        this.vertexCount = indices.length;
    }

    public void bind(){
        glBindVertexArray(id);
        indices.bind();
        position.bind();
        texCoords.bind();
    }

    public void draw(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
    }

    public void unbind(){
        texCoords.unbind();
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
        texCoords.cleanUpMemory();
        position.cleanUpMemory();
        indices.cleanUpMemory();
        unbind();
        glDeleteVertexArrays(id);
    }

}
