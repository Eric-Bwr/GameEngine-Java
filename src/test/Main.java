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
import engine.maths.Mapper;
import engine.maths.Vec2f;

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
	private MouseCallback mc;
	private GameEngine gameEngine;
	private EngineConfig config = new EngineConfig();

	public Main(){
		config.title = "GameEngine";
		config.width = 700;
		config.height = 500;
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

		mc = new MouseCallback();
		gameEngine.applyCallback(mc);
	}

	@Override
	public void init() {
		shader = new Shader("Shaders/Basic.glsl");
		Texture tex = new Texture("dog.png");
		model = new Model(tex, vertices, textureCoords, indices);
	}

	@Override
	public void tick(float dt) {

	}

	@Override
	public void render() {
		model.bind();
		shader.bind();
		shader.setUniform2f("mousePos", new Vec2f(mc.getMappedX(), mc.getMappedY()));
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
