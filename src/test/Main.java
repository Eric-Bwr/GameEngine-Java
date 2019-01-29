package test;

import com.sun.xml.internal.ws.api.pipe.Engine;
import engine.EngineConfig;
import engine.GameEngine;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.graphics.Model;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.graphics.gl.VertexArrayObject;
import org.lwjgl.stb.STBImage;

import java.io.File;
import java.io.FileInputStream;

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

	public Main(){
		EngineConfig config = new EngineConfig();
		config.title = "GameEngine";
		config.width = 700;
		config.height = 500;
		config.rezisable = false;
		config.vsync = false;
		config.screenMode = ScreenMode.WINDOW;
		GameEngine gameEngine = new GameEngine(this, config);
		gameEngine.start();
	}

	@Override
	public void init() {
		shader = new Shader("res/Shaders/Basic.glsl");
		Texture tex = new Texture(new File(this.getClass().getResource("textures/dog.png").getPath()).getAbsolutePath());
		model = new Model(tex, vertices, textureCoords, indices);
	}

	@Override
	public void tick(float dt) {

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
