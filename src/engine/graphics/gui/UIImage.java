package engine.graphics.gui;

import engine.graphics.rendering.Texture;
import engine.maths.Vec2i;

public class UIImage {

	private Vec2i position, size;
	private Texture texture;

	public UIImage(Vec2i position, Vec2i size, Texture texture){
		this.position = position;
		this.texture = texture;
	}

	public Vec2i getPosition() {
		return position;
	}

	public void setPosition(Vec2i position) {
		this.position = position;
	}

	public Vec2i getSize() {
		return size;
	}

	public void setSize(Vec2i size) {
		this.size = size;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;

	}

	public void register(){
		UIMaster.imageList.add(this);
	}

	public void unregister(){
		UIMaster.imageList.remove(this);
	}

	public void draw(){

	}
}