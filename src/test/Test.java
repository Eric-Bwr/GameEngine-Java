package test;

import engine.EngineConfig;
import engine.GameEngine;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.callbacks.KeyCallback;
import engine.callbacks.MouseCallback;
import engine.graphics.*;
import engine.graphics.gl.shadow.ShadowDephtBuffer;
import engine.graphics.gl.shadow.ShadowRenderer;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.maths.Vec4f;
import engine.model.ModelLoader;
import engine.model.camera.CameraAxis;
import engine.model.camera.CameraFPS;
import engine.model.entity.Entity;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Test implements EngineCallback {

    private static final float SENSITIVITY = 0.1F;
    private float moveSpeed = 0.6F;

    private static final float FOV = 70;
    private static final float NEAR = 0.1f;
    private static final float FAR = 1000;

    private EngineConfig config = new EngineConfig();
    private GameEngine engine;

    private ShadowRenderer shadowRenderer;
    private SimpleModel model;

    private Entity stallModel;

    private Shader shader;

    private CameraFPS camera;
    private Light light;

    private ShadowDephtBuffer shadowDephtBuffer;

    public Test(){
        config.title = "GameEngine";

        engine = new GameEngine(this, config);
        engine.start();

    }

    @Override
    public void initCallbacks() {

    }

    @Override
    public void init() {
        float[] vpos = new float[]{
                -1f, 0f, 0.0f,
                -1f, -1f, 0.0f,
                0f, -1f, 0.0f,
                0f, 0f, 0.0f,
        };

        float[] tex = new float[]{
                0.0f, 0.0f,
                0.0f, 1f,
                1f, 1f,
                1f, 0.0f,
        };

        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };

        shader = new Shader("Shaders/Basic.glsl");
        light = new Light(shader);
        camera = new CameraFPS(new Vec3f(0, 0, -20), 0f, 180f);

        model = new SimpleModel(0, vpos, tex, indices);
        shadowRenderer = new ShadowRenderer();

        Texture texture = new Texture("Textures/stallTexture.png");
        Model sm = new ModelLoader().loadModel("Objects/stall.obj", texture);
        stallModel = new Entity(sm, new Vec3f(0, 0, 0), 0, 0, 0, 1);

        engine.showMouse(false);

        shadowDephtBuffer = new ShadowDephtBuffer(config, config.width, config.height);
    }

    @Override
    public void tick(float dt) {

    }

    @Override
    public void render() {

        shadowDephtBuffer.bind();
        renderScreen();
        shadowDephtBuffer.unbind();

        renderScreen();

        model.setTextureId(shadowDephtBuffer.getTextureId());
        shadowRenderer.bind();
        model.bind();
        model.draw();
        model.unbind();
        shadowRenderer.unbind();
    }

    private void renderScreen(){
        shader.bind();
        stallModel.bind();
        setBullshit();
        stallModel.draw();
        stallModel.unbind();
        shader.unbind();
    }

    private void setBullshit(){
        light.setPosition("lightPosition", new Vec3f(0.0F, 0.0F, -10.0F));
        light.setBrightness("lightBrightness", 3F);
        light.setColor("lightColor", new Vec4f(1.0F, 1.0F, 1.0F, 1.0F));
        shader.setUniformMat4f("projectionMatrix", Mat4f.projection(FOV, 4, 4, NEAR, FAR, null));
        shader.setUniformMat4f("viewMatrix", camera.getViewMatrix());
        shader.setUniformMat4f("transformationMatrix", stallModel.getTransformationMatrix());
    }

    @Override
    public void terminate() {

    }

    public static void main(String[] args) {
        new Test();
    }
}
