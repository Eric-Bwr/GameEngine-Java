package engine.graphics.rendering;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL15.*;

public class SimpleModel {

    private int vao;
    private int indicesVBO;
    private int positionsVBO;
    private int texturesVBO;

    private int textureId;

    private int vertexCount;

    public SimpleModel(int textureId, float[] vpos, float[] tex, int[] indices){
        this.textureId = textureId;
        this.vao = glGenVertexArrays();
        glBindVertexArray(vao);
        this.vertexCount = indices.length;

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();

        indicesVBO = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        FloatBuffer positionsBuffer = BufferUtils.createFloatBuffer(vpos.length);
        positionsBuffer.put(vpos).flip();

        positionsVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, positionsVBO);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);


        FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(tex.length);
        textureBuffer.put(tex).flip();

        texturesVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texturesVBO);
        glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
    }

    public void bind(){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public void draw(){
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
    }

    public void unbind(){
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

}
