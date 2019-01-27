package Test;

import static Engine.Rendering.Window.*;

public class Main {

	//JUST  FOR TESTING PURPOSE; MAY BE MOVED IN SEPERATE FILE FOR HUMAN EYES
	private static String title = "Engine";
	private static int width = 600;
	private static int height = 600;

	public static void main(){
		initWindow(title, width, height);
		initCallbacks();
		run();
	}
}