package engine.graphics;

import engine.EngineConfig;
import engine.ScreenMode;
import engine.callbacks.EngineCallback;
import engine.callbacks.KeyCallback;
import engine.callbacks.MouseCallback;
import engine.callbacks.FocusCallback;
import engine.util.Log;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static engine.util.BufferUtil.ioResourceToByteBuffer;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	private ArrayList<MouseCallback> mouseCallbacks = new ArrayList<>();

	public long window;

	private EngineConfig config;
	private EngineCallback callback;

	public Window(EngineCallback callback, EngineConfig config){
		this.config = config;
		this.callback = callback;
	}

	public void initWindow() {
		if (!glfwInit()) {
			Log.logError("Failed to init GLFW!");
			glfwTerminate();
		}
		initHints();
		if (config.screenMode.equals(ScreenMode.FULLSCREEN)) {
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			window = glfwCreateWindow(vidMode.width(), vidMode.height(), config.title, glfwGetPrimaryMonitor(), NULL);
		} else if(config.screenMode.equals(ScreenMode.BORDERLESS)) {
			glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			window = glfwCreateWindow(vidMode.width(),vidMode.height(), config.title, NULL, NULL);
			glfwSetWindowPos(window, 0, 0);
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
		glfwFocusWindow(window);
		callback.initCallbacks();
		for(MouseCallback mc : mouseCallbacks){
			mc.setSize(config.width, config.height);
		}
		GL.createCapabilities();
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);
		initCallbacks();
		setIcon();
		if(config.vsync)
			glfwSwapInterval(1);
		else
			glfwSwapInterval(0);
		Log.logInfo(glGetString(GL_VERSION));
	}

	public void setIcon() {
		if(!(config.windowIconPath.equals(""))) {
			ByteBuffer icon16;
			ByteBuffer icon32;
			icon16 = ioResourceToByteBuffer(config.windowIconPath, 2048);
			icon32 = ioResourceToByteBuffer(config.windowIconPath, 4096);
			IntBuffer w = memAllocInt(1);
			IntBuffer h = memAllocInt(1);
			IntBuffer comp = memAllocInt(1);
			try ( GLFWImage.Buffer icons = GLFWImage.malloc(2) ) {
				ByteBuffer pixels16 = stbi_load_from_memory(icon16, w, h, comp, 4);
				icons.position(0).width(w.get(0)).height(h.get(0)).pixels(pixels16);
				ByteBuffer pixels32 = stbi_load_from_memory(icon32, w, h, comp, 4);
				icons.position(1).width(w.get(0)).height(h.get(0)).pixels(pixels32);
				icons.position(0);
				glfwSetWindowIcon(window, icons);
				stbi_image_free(pixels32);
				stbi_image_free(pixels16);
			}
		}
	}

	private void initHints(){
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long temp = glfwCreateWindow(1, 1, "", NULL, NULL);
		glfwMakeContextCurrent(temp);
		GL.createCapabilities();
		GLCapabilities caps = GL.getCapabilities();
		glfwDestroyWindow(temp);
		glfwDefaultWindowHints();
		if (caps.OpenGL46) {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
			Log.logInfo("Loading OpenGL 3.2");
		} else if (caps.OpenGL21) {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
			Log.logInfo("Loading OpenGL 2.1");
		} else {
			Log.logInfo("Neither OpenGL 3.2 nor OpenGL 2.1 is supported");
		}
		if(config.rezisable)
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		else
			glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
	}

	private void initCallbacks(){
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				config.width = width;
				config.height = height;
				for(MouseCallback mc : mouseCallbacks){
					mc.setSize(width, height);
				}
				glViewport(0, 0, width, height);
			}
		});
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

	public void applyCallback(Object object) {
		if(object instanceof MouseCallback){
			glfwSetCursorPosCallback(window, (MouseCallback)object);
			mouseCallbacks.add((MouseCallback)object);
		}else if(object instanceof KeyCallback){
			glfwSetKeyCallback(window, (KeyCallback)object);
		}else if(object instanceof FocusCallback){
			glfwSetWindowFocusCallback(window, (FocusCallback)object);
		}
	}
}