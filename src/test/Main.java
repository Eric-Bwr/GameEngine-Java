package test;

import com.sun.xml.internal.ws.api.pipe.Engine;
import engine.EngineConfig;
import engine.GameEngine;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.graphics.Shader;
import engine.graphics.gl.VertexArrayObject;

public class Main implements EngineCallback {

	private float state;
	private boolean up;

	float[] vertices = new float[]{
			-0.5F, -0.5F, 1.0F,
			-0.5F, 0.5F, 1.0F,
			0.5F, -0.5F, 1.0F,
			0.5F, 0.5F, 1.0F
	};

	private int[] indices = {
			0, 1, 2,
			1, 2, 3
	};

	private Shader shader;
	private VertexArrayObject vao;

	public Main(){
		EngineConfig config = new EngineConfig();
		config.title = "GameEngine";
		config.width = 700;
		config.height = 500;
		config.rezisable = false;
		config.vsync = false;
		config.screenMode = ScreenMode.WINDOWED;
		GameEngine gameEngine = new GameEngine(this, config);
		gameEngine.start();
	}

	@Override
	public void init() {
		shader = new Shader("res/Shaders/Basic.glsl");
		vao = new VertexArrayObject(vertices, indices);
	}

	@Override
	public void tick(float dt) {
		if(up){
			state += 0.02F;
		}else{
			state -= 0.02F;
		}
		if(state > 1){
			up = false;
		}else if(state < 0){
			up = true;
		}
	}

	@Override
	public void render() {
		shader.bind();
		shader.setUniform1f("state", state);
		shader.setUniform4f("u_Color", 1.0f, 0.0f, 1.0f, 0.0f);
		vao.bind();
		vao.draw();
		vao.unbind();
		shader.unbind();
	}

	@Override
	public void terminate() {
		shader.cleanUpMemory();
		vao.cleanUpMemory();
	}

	public static void main(String[] args){
		new Main();
	}
}
