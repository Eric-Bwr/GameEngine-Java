package engine.graphics;

import engine.maths.Mat4f;

import static org.lwjgl.opengl.GL30.*;

public class PostProcess {

    private int id;
    private int textureID;
    private int renderID;

    private Shader shader;

    public PostProcess(Shader shader, int width, int height){
        this.shader = shader;
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureID, 0);

        renderID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, renderID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, renderID);

        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, renderID);

        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind(){
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void render(){

    }

    public void cleanUp(){
        shader.cleanUpMemory();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDeleteFramebuffers(id);
    }
}