package test;

import engine.GameEngine;
import engine.callbacks.EngineCallback;
import engine.graphics.Shader;
import engine.graphics.gl.VertexArrayObject;

public class Main implements EngineCallback {

	//JUST  FOR TESTING PURPOSE; MAY BE MOVED IN SEPERATE FILE FOR HUMAN EYES
	private static final String TITLE = "Engine";
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;

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
		GameEngine gameEngine = new GameEngine(this, TITLE, WIDTH, HEIGHT);
		gameEngine.start();
	}

	@Override
	public void init() {
		shader = new Shader("res/Shaders/Basic.glsl");
		vao = new VertexArrayObject(vertices, indices);
	}

	@Override
	public void tick(float dt) {
	}

	@Override
	public void render() {
		shader.bind();
		shader.setUniform4f("u_Color", 1.0f, 0.0f, 0.0f, 0.0f);
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
