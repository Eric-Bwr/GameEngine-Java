package Test;

import Engine.GameEngine;

public class Main {

	//JUST  FOR TESTING PURPOSE; MAY BE MOVED IN SEPERATE FILE FOR HUMAN EYES
	private static final String TITLE = "Engine";
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;

	public static void main(String[] args){
		GameEngine gameEngine = new GameEngine(TITLE, WIDTH, HEIGHT);
		gameEngine.start();
	}
}