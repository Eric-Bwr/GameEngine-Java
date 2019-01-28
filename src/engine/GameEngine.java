package engine;

import engine.graphics.Window;

public class GameEngine implements Runnable{

	private static final int TICKS_PER_SECOND = 60;

	private Window window;
	private boolean running;

	public GameEngine(String title, int width, int height){
		window = new Window(title, width, height);
	}

	public void start(){
		this.running = true;
		new Thread(this).start();
	}

	private void tick(){

	}

	private void render(){
		window.update();
	}

	@Override
	public void run() {
		window.initWindow();
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / TICKS_PER_SECOND;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();

		while (running && !window.shouldClose()){
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
		window.terminate();
	}
}