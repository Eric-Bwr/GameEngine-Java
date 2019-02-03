package engine;

import engine.callbacks.EngineCallback;
import engine.callbacks.KeyCallback;
import engine.callbacks.MouseCallback;
import engine.graphics.Window;
import engine.graphics.gui.UIText;
import engine.maths.Vec2i;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class GameEngine implements Runnable {

	private static final int TICKS_PER_SECOND = 60;

	public EngineCallback engineCallback;

	private Window window;
	private boolean running;

	private Thread thread;

	public GameEngine(EngineCallback callback, EngineConfig config){
		this.engineCallback = callback;
		window = new Window(callback, config);
	}

	public void start(){
		this.running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		this.running = false;
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
			boolean shouldRender = true;
			lastTime = now;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
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
		try {
			thread.join(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void applyCallback(Object object){
		window.applyCallback(object);
	}

	public void applyUI(Object object) {
		if(object instanceof UIText){
			//TODO: Maybe move this in a "UIMaster" Class
		}
	}
	public void showMouse(boolean show){
		if(show)
			glfwSetInputMode(window.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		else
			glfwSetInputMode(window.window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	public void setMousePosition(float x, float y){
		glfwSetCursorPos(window.window, x, y);
	}

	public Vec2i getScreenSize() {
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		return new Vec2i(vidMode.width(), vidMode.height());
	}
}