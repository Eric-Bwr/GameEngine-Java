package engine.callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyCallback extends GLFWKeyCallback {

    private final boolean[] keys = new boolean[512];

    @Override
    public void invoke(long window, int keyCode, int scanCode, int action, int mods) {
        keys[keyCode] = action != GLFW.GLFW_RELEASE;
    }

    public boolean isKeyCode(int keyCode){
        return keys[keyCode];
    }
}