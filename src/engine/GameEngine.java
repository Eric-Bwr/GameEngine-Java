package engine;

import engine.callbacks.EngineCallback;
import engine.graphics.Window;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameEngine implements Runnable{

	private static final int TICKS_PER_SECOND = 60;

	private EngineCallback engineCallback;

	private Window window;
	private boolean running;

	public GameEngine(EngineCallback callback, EngineConfig config){
		this.engineCallback = callback;
		window = new Window(config);
	}

	public void start(){
		this.running = true;
		new Thread(this).start();
	}

	private void tick(){
		engineCallback.tick(0);
	}

	private void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwPollEvents();
		engineCallback.render();
		window.swapBuffers();
	}

	@Override
	public void run() {
		window.initWindow();
		engineCallback.init();
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / TICKS_PER_SECOND;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (running && !window.shouldClose()){
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			//TODO: Maybe remove it, or not
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
		engineCallback.terminate();
		window.terminate();
	}
}