package engine.graphics.gl.shadow;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class ShadowDepthBuffer {

    private int width;
    private int height;
    private int id;
    private int shadow;

    public ShadowDepthBuffer(int w, int h){
        this.width = w;
        this.height = h;
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glDrawBuffer(GL_NONE);
        shadow = glGenTextures();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT16, w, h, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        GL32.glFramebufferTexture(GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, shadow, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void cleanUpMemory(){
        glDeleteFramebuffers(id);
        glDeleteTextures(shadow);
    }
}