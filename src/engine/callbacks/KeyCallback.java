package engine.callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyCallback extends GLFWKeyCallback {

    private final boolean[] keys = new boolean[512];

    @Override
    public void invoke(long window, int keyCode, int scanCode, int action, int mods) {
        try {
            keys[keyCode] = action != GLFW.GLFW_RELEASE;
        }catch (ArrayIndexOutOfBoundsException e){}
    }

    public boolean isKeyCode(int keyCode){
        return keys[keyCode];
    }
}