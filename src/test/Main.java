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
import engine.model.Camera3D;
import engine.model.ModelLoader;
import engine.model.entity.Entity;
import org.lwjgl.glfw.GLFW;

public class Main implements EngineCallback {

	private static final float FOV = 70;
	private static final float NEAR = 0.1f;
	private static final float FAR = 1000;
	private static final float SENSITIVITY = 0.1F;
	private float moveSpeed = 0.1F;

	private Shader shader;
	private Entity entity;
	private Model model;
	private ModelLoader modelLoader = new ModelLoader();

	private KeyCallback kc;
	private MouseCallback mouse;
	private GameEngine gameEngine;
	private EngineConfig config = new EngineConfig();
	private Camera3D camera = new Camera3D(new Vec3f(0, 0, 0), 0, 0, 0);

	public Main(){
		config.title = "GameEngine";
		config.width = 800;
		config.height = 450;
		config.windowIconPath = "dog.png";
		config.rezisable = true;
		config.vsync = false;
		config.screenMode = ScreenMode.WINDOWED;

		gameEngine = new GameEngine(this, config, camera);
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
		entity = new Entity(model, new Vec3f(0, 0, -25), 0, 0, 0, 1);
	}

	@Override
	public void tick(float dt) {
		if (kc.isKeyCode(GLFW.GLFW_KEY_ESCAPE)) {
			gameEngine.stop();
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_W)){
			camera.move(new Vec3f(0.0F, 0.0F, moveSpeed));
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_S)){
			camera.move(new Vec3f(0.0F, 0.0F, -moveSpeed));
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_A)){
			camera.move(new Vec3f(moveSpeed, 0.0F, 0.0F));
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_D)){
			camera.move(new Vec3f(-moveSpeed, 0.0F, 0.0F));
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_SPACE)){
			camera.move(new Vec3f(0.0F, -moveSpeed, 0.0F));
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_LEFT_SHIFT)){
			camera.move(new Vec3f(0.0F, moveSpeed, 0.0F));
		}
	}

	//TODO: Cleanup

	@Override
	public void render() {
		camera.setRotY(camera.getRotY() - mouse.getDeltaX() * SENSITIVITY);
		camera.setRotX(camera.getRotX() - mouse.getDeltaY() * SENSITIVITY);
		mouse.reset();
		entity.bind();
		shader.bind();
		shader.setUniformMat4f("projectionMatrix", Mat4f.projection(FOV, 4, 4, NEAR, FAR, null));
		shader.setUniformMat4f("viewMatrix", camera.getViewMatrix());
		//shader.setUniformMat4f("transformationMatrix", entity.getTransformationMatrix());
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
