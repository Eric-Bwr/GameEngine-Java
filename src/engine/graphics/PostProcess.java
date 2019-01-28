package engine.graphics;

import static org.lwjgl.opengl.GL30.*;

public class PostProcess {

    private int id;

    public PostProcess(){
        id = glGenFramebuffers();
        //glBindFramebuffer(GL_FRAGMEN);
    }

}
