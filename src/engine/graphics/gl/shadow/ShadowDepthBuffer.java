package engine.graphics.gl.shadow;

import java.nio.ByteBuffer;

import engine.EngineConfig;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class ShadowDepthBuffer {

    private int frameBufferId;
    private int frameBufferTexture;

    private EngineConfig engineConfig;
    private int width;
    private int height;

    public ShadowDepthBuffer(EngineConfig config, int widht, int height) {
        this.engineConfig = config;
        this.width = widht;
        this.height = height;

        frameBufferId = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

        frameBufferTexture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, frameBufferTexture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, widht, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, frameBufferTexture, 0);
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferId);
        GL11.glViewport(0, 0, width, height);
    }

    public void unbind() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, engineConfig.width, engineConfig.height);
    }

    public int getTextureId(){
        return frameBufferTexture;
    }
}