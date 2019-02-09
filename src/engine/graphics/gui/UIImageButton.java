package engine.graphics.gui;

import engine.maths.Vec2i;

import java.awt.image.BufferedImage;

public class UIImageButton {

	private BufferedImage image;
	private Vec2i position;

	public UIImageButton(Vec2i position, BufferedImage image) {
		this.position = position;
		this.image = image;
	}
}