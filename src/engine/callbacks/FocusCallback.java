package engine.callbacks;

import org.lwjgl.glfw.GLFWWindowFocusCallback;

public class FocusCallback extends GLFWWindowFocusCallback {

	private boolean focused = true;

	@Override
	public void invoke(long window, boolean focused) {
		this.focused = focused;
	}

	public boolean isFocused() {
		return focused;
	}
}