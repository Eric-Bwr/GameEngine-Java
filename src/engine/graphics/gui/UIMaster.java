package engine.graphics.gui;

import java.util.ArrayList;
import java.util.List;

public class UIMaster {

	public static List<UIText> textList = new ArrayList<>();
	public static List<UIImage> imageList = new ArrayList<>();

	public void draw(){
		for(UIText text : textList){
			text.draw();
		}
		for(UIImage image : imageList){
			image.draw();
		}
	}
}
