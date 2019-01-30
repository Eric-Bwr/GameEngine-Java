package engine.callbacks;

import engine.maths.Mapper;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseCallback extends GLFWCursorPosCallback {

    private float mouseX;
    private float mouseY;
    private float mappedX;
    private float mappedY;
    private float width;
    private float height;

    @Override
    public void invoke(long window, double mx, double my) {
        this.mouseX = (float) mx;
        this.mouseY = (float) my;
        this.mappedX = Mapper.map(this.mouseX, 0, this.width, -1, 1);
        this.mappedY = Mapper.map(this.mouseY, 0, this.height, 1, -1);
    }

    public void setSize(float w, float h){
        this.width = w;
        this.height = h;
    }

    public float getMappedX() {
        return mappedX;
    }

    public float getMappedY() {
        return mappedY;
    }

    public float getMouseY() {
        return mouseY;
    }

    public float getMouseX() {
        return mouseX;
    }
}
