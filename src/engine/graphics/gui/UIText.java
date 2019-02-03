package engine.graphics.gui;

import engine.maths.Vec2i;

public class UIText {

	private Vec2i position;

	public UIText(Vec2i position){
		this.position = position;
	}

	public Vec2i getPosition() {
		return position;
	}

	public void setPosition(Vec2i position) {
		this.position = position;
	}
}