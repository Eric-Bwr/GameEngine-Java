package engine.graphics.gl.shadow;

import engine.graphics.Shader;
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

    //TODO: Check matrix
    /**
     * you need to call the enable and disable method of @{@link Entity}
     * @see Entity
     * @param entity
     */
    public void drawEntityWithShadow(Entity entity, float near, float far){
        Mat4f mat = Mat4f.orthographic(-10.0F, 10.0F, -10.0F, 10.0F, near, far, null);
        Mat4f lightView = Mat4f.lookAt(new Vec3f(-2.0F, 4.0F, -1.0F), new Vec3f(0, 0, 0), new Vec3f(0, 1, 0), null);
        Mat4f lightSpaceMatrix = Mat4f.mul(mat, lightView, null);

        shader.setUniformMat4f("lightSpaceMatrix", lightSpaceMatrix);
        entity.draw();
    }

    public void unbind(){
        shader.unbind();
    }

}
