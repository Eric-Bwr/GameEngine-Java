package test;

import engine.EngineConfig;
import engine.GameEngine;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.callbacks.KeyCallback;
import engine.callbacks.MouseCallback;
import engine.graphics.Model;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.model.ModelLoader;
import engine.model.camera.CameraFPS;
import engine.model.entity.Entity;
import org.lwjgl.glfw.GLFW;

public class Main implements EngineCallback {

	private static final float FOV = 70;
	private static final float NEAR = 0.1f;
	private static final float FAR = 1000;
	private static final float SENSITIVITY = 0.1F;
	private float moveSpeed = 0.2F;

	private Shader shader;
	private Entity entity;
	private Model model;
	private ModelLoader modelLoader = new ModelLoader();

	private KeyCallback kc;
	private MouseCallback mouse;
	private GameEngine gameEngine;
	private EngineConfig config = new EngineConfig();
	private CameraFPS camera = new CameraFPS(new Vec3f(0, 0, 0), 0, 0);

	public Main(){
		config.title = "GameEngine";
		config.width = 1000;
		config.height = 700;
		config.windowIconPath = "dog.png";
		config.rezisable = true;
		config.vsync = false;
		config.screenMode = ScreenMode.WINDOW;

		gameEngine = new GameEngine(this, config);
		gameEngine.start();
	}

	@Override
	public void initCallbacks() {
		kc = new KeyCallback();
		gameEngine.applyCallback(kc);

		mouse = new MouseCallback();
		gameEngine.applyCallback(mouse);
	}

	@Override
	public void init() {
		shader = new Shader("Shaders/Basic.glsl");
		Texture tex = new Texture("Textures/stallTexture.png");
		model = modelLoader.loadModel("Objects/stall.obj", tex);
		entity = new Entity(model, new Vec3f(0, 0, -25), 0, 0, 45, 1);
		gameEngine.showMouse(false);
	}

	boolean rot = true;

	@Override
	public void tick(float dt) {
		if (kc.isKeyCode(GLFW.GLFW_KEY_ESCAPE)) {
			if(rot){
				rot = false;
				gameEngine.showMouse(true);}
			else{
				rot = true;
			    gameEngine.showMouse(false);}
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_W)){
			camera.moveForward(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_S)){
			camera.moveBackwards(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_A)){
			camera.moveLeft(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_D)){
			camera.moveRight(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_SPACE)){
			camera.moveUp(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_LEFT_SHIFT)){
			camera.moveDown(moveSpeed);
		}
	}

	@Override
	public void render() {
		if(rot)
		camera.rotate(mouse.getDeltaX(), mouse.getDeltaY(), SENSITIVITY);
		mouse.reset();
		entity.bind();
		shader.bind();
		shader.setUniformMat4f("projectionMatrix", Mat4f.projection(FOV, 4, 4, NEAR, FAR, null));
		shader.setUniformMat4f("viewMatrix", camera.getViewMatrix());
		shader.setUniformMat4f("transformationMatrix", entity.getTransformationMatrix());
		entity.draw();
		shader.unbind();
		entity.unbind();
	}

	@Override
	public void terminate() {
		shader.cleanUpMemory();
		model.cleanUpMemory();
	}

	public static void main(String[] args){
		new Main();
	}
}