package engine.graphics.gui;

import engine.maths.Vec2i;

import java.awt.*;

public class UIText {

	private Vec2i position;
	private String text;
	private Font font;

	public UIText(Vec2i position, String text, Font font){
		this.position = position;
		this.text = text;
		this.font = font;
	}

	public Vec2i getPosition() {
		return position;
	}

	public void setPosition(Vec2i position) {
		this.position = position;
	}

	public void register(){
		UIMaster.textList.add(this);
	}

	public void unregister(){
		UIMaster.textList.remove(this);
	}

	public void draw(){

	}
}