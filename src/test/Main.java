package test;

import engine.EngineConfig;
import engine.GameEngine;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.callbacks.KeyCallback;
import engine.graphics.Model;
import engine.graphics.Shader;
import engine.graphics.Texture;
import org.lwjgl.glfw.GLFW;

public class Main implements EngineCallback {

	private float[] vertices = new float[]{
			-0.5F, 0.5F, 1.0F,
			-0.5F, -0.5F, 1.0F,
			0.5F, -0.5F, 1.0F,
			0.5F, 0.5F, 1.0F
	};

	private float[] textureCoords = {
			0, 0,
			0, 1,
			1, 1,
			1, 0
	};

	private int[] indices = {
			0, 1, 3,
			3, 1, 2
	};

	private Shader shader;
	private Model model;

	private KeyCallback kc;
	private GameEngine gameEngine;

	public Main(){
		EngineConfig config = new EngineConfig();
		config.title = "GameEngine";
		config.width = 700;
		config.height = 500;
		config.rezisable = false;
		config.vsync = false;
		config.screenMode = ScreenMode.WINDOW;

		gameEngine = new GameEngine(this, config);
		gameEngine.start();
	}

	@Override
	public void initCallbacks() {
		kc = new KeyCallback();
		gameEngine.applyKeyCallback(kc);
	}

	@Override
	public void init() {
		shader = new Shader("/Shaders/Basic.glsl");
		Texture tex = new Texture("/dog.png");
		model = new Model(tex, vertices, textureCoords, indices);
	}

	@Override
	public void tick(float dt) {
		System.out.println(kc.isKeyCode(GLFW.GLFW_KEY_SPACE));
	}

	@Override
	public void render() {
		model.bind();
		shader.bind();
		model.draw();
		shader.unbind();
		model.unbind();
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
