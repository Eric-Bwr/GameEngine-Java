package engine.graphics.gl.shadow;

import engine.graphics.rendering.Shader;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.model.entity.Entity;

public class ShadowRenderer {

    private Shader shader;

    public ShadowRenderer(){
        shader = new Shader("Shaders/Depth.glsl");
    }

    public void bind(){
        shader.bind();
    }

    public void unbind(){
        shader.unbind();
    }

}
