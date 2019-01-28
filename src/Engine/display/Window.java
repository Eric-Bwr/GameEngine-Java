package engine.display;

import engine.util.Log;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	private long window;

	private String title;
	private int width;
	private int height;

	public Window(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public void initWindow(){
		if(!glfwInit()){
			Log.logError("Failed to init GLFW!");
			glfwTerminate();
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
		glfwWindowHint(GLFW_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL){
			Log.logError("Failed to create Window!");
			glfwTerminate();
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		initCallbacks();
		GL.createCapabilities();
		Log.logInfo(glGetString(GL_VERSION));
	}

	private void initCallbacks(){
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});
	}

	public void update(){
		glfwPollEvents();
	}

	public boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}

	public void terminate() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
}
