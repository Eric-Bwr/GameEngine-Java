package engine.callbacks;

import engine.maths.Mapper;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import static org.lwjgl.glfw.GLFW.*;

public class MouseCallback extends GLFWCursorPosCallback {

    private float mouseX;
    private float mouseY;
    private float lastMouseX;
    private float lastMouseY;
    private float deltaX;
    private float deltaY;
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
        this.deltaX = lastMouseX - mouseX;
        this.deltaY = lastMouseY - mouseY;
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    public void reset(){
        lastMouseX = mouseX;
        lastMouseY = mouseY;
        deltaX = 0;
        deltaY = 0;
    }

    public void setSize(float w, float h){
        this.width = w;
        this.height = h;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public float getMappedX() { return mappedX; }

    public float getMappedY() { return mappedY; }

    public float getMouseY() {
        return mouseY;
    }

    public float getMouseX() {
        return mouseX;
    }
}