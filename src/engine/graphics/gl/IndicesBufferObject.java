package engine.graphics.gl;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class IndicesBufferObject {

    private int id;
    private IntBuffer buffer;

    public IndicesBufferObject(int[] data){
        this.buffer = BufferUtils.createIntBuffer(data.length);
        this.buffer.put(data).flip();

        id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void bind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void cleanUpMemory(){
        unbind();
        glDeleteBuffers(id);
    }
}