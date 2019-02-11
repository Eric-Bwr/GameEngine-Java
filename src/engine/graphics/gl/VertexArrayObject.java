package engine.graphics.gl;

import static org.lwjgl.opengl.GL30.*;

public class VertexArrayObject {

    private int id;
    private IndicesBufferObject indices;
    private VertexBufferObject position;
    private VertexBufferObject texCoords;
    private VertexBufferObject normals;

    private int vertexCount;

    private boolean simple;

    public VertexArrayObject(float[] vpos, float[] texCoords, float[] normals, int[] indices, boolean simple){
        this.id = glGenVertexArrays();
        glBindVertexArray(id);
        this.indices = new IndicesBufferObject(indices);
        this.position = new VertexBufferObject(0, vpos, 3);
        this.texCoords = new VertexBufferObject(1, texCoords, 2);
        if(!simple){
            this.normals = new VertexBufferObject(2, normals, 3);
        }
        glBindVertexArray(0);
        this.vertexCount = indices.length;
        this.simple = simple;
    }

    public void bind(){
        glBindVertexArray(id);
        indices.bind();
        position.bind();
        texCoords.bind();
        if(!simple){
            normals.bind();
        }
    }

    public void draw(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        if(!simple){
            glEnableVertexAttribArray(2);
        }
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        if(!simple){
            glDisableVertexAttribArray(2);
        }
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
    }

    public void unbind(){
        indices.unbind();
        position.unbind();
        texCoords.unbind();
        if(!simple){
            normals.unbind();
        }
        glBindVertexArray(0);
    }

    public void renderToScreen(){
        bind();
        draw();
        unbind();
    }

    public void cleanUpMemory(){
        indices.cleanUpMemory();
        position.cleanUpMemory();
        texCoords.cleanUpMemory();
        if(!simple){
            normals.cleanUpMemory();
        }
        unbind();
        glDeleteVertexArrays(id);
    }
}