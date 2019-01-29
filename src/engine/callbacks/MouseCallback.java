package engine.callbacks;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseCallback extends GLFWCursorPosCallback {

    private double mouseX;
    private double mouseY;

    @Override
    public void invoke(long window, double mx, double my) {
        this.mouseX = mx;
        this.mouseY = my;
    }

    public double getMouseY() {
        return mouseY;
    }

    public double getMouseX() {
        return mouseX;
    }
}
