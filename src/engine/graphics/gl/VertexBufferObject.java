package engine.graphics.gl;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glVertexAttribPointer;

public class VertexBufferObject {

    private int id;
    private int attribNumber;
    private FloatBuffer buffer;

    public VertexBufferObject(int attribNumber, float[] data, int coordSize){
        this.buffer = BufferUtils.createFloatBuffer(data.length);
        this.buffer.put(data).flip();

        this.attribNumber = attribNumber;

        id = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attribNumber, coordSize, GL_FLOAT, false, 0, 0);
        unbind();
    }

    public void bind(){
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void cleanUpMemory(){
        unbind();
        glDeleteBuffers(id);
    }

}
