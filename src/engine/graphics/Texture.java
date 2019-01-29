package engine.graphics;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private int id;

    public Texture(String path) {
        PNGDecoder decoder = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            decoder = new PNGDecoder(fis);
            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
            glGenerateMipmap(GL_TEXTURE_2D);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void cleanUpMemory() {
        glDeleteTextures(id);
    }
}
