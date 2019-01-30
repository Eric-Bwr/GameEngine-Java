package engine.graphics.gl;

import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject {

    private int id;
    private IndicesBufferObject indices;
    private VertexBufferObject position;
    private VertexBufferObject texCoords;
    private VertexBufferObject normals;
    private int vertexCount;

    public VertexArrayObject(float[] vpos, float[] texCoords, float[] normals, int[] indices){
        this.id = glGenVertexArrays();
        glBindVertexArray(id);
        this.indices = new IndicesBufferObject(indices);
        this.position = new VertexBufferObject(0, vpos, 3);
        this.texCoords = new VertexBufferObject(1, texCoords, 2);
        this.normals = new VertexBufferObject(2, normals, 3);
        glBindVertexArray(0);
        this.vertexCount = indices.length;
    }

    public void bind(){
        glBindVertexArray(id);
        indices.bind();
        position.bind();
        texCoords.bind();
        normals.bind();
    }

    public void draw(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
    }

    public void unbind(){
        indices.unbind();
        normals.unbind();
        position.unbind();
        texCoords.unbind();
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
        normals.cleanUpMemory();
        unbind();
        glDeleteVertexArrays(id);
    }

}
