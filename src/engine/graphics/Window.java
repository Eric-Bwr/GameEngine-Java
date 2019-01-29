package engine.graphics;

import engine.EngineConfig;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.callbacks.KeyCallback;
import engine.util.Log;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	private long window;

	private EngineConfig config;
	private EngineCallback callback;

	public Window(EngineCallback callback, EngineConfig config){
		this.config = config;
		this.callback = callback;
	}
	
	public void initWindow(){
		if(!glfwInit()){
			Log.logError("Failed to init GLFW!");
			glfwTerminate();
		}
		glfwDefaultWindowHints();
		initHints();
		if (config.screenMode.equals(ScreenMode.FULLSCREEN)) {
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			window = glfwCreateWindow(vidMode.width(), vidMode.height(), config.title, glfwGetPrimaryMonitor(), NULL);
		} else if(config.screenMode.equals(ScreenMode.WINDOWED)) {
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			window = glfwCreateWindow(vidMode.width(),vidMode.height(), config.title, NULL, NULL);
			glfwSetWindowPos(window, 100, 0);
		} else if(config.screenMode.equals(ScreenMode.WINDOW)) {
			window = glfwCreateWindow(config.width, config.height, config.title, NULL, NULL);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vidMode.width() - config.width) / 2, (vidMode.height() - config.height) / 2);
		}
		if(window == NULL){
			Log.logError("Failed to create Window!");
			glfwTerminate();
		}
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		callback.initCallbacks();
		GL.createCapabilities();
		if(config.vsync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
		Log.logInfo(glGetString(GL_VERSION));
	}

	private void initHints(){
		glfwWindowHint(GLFW_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_VERSION_MAJOR, 3);
		if(config.rezisable)
			glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		else
			glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
	}

	public void swapBuffers(){ glfwSwapBuffers(window); }

	public boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}

	public void terminate() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public void applyKeyCall(KeyCallback kc){
		glfwSetKeyCallback(window, kc);
	}
}