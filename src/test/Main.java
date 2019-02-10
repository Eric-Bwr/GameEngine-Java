package test;

import engine.EngineConfig;
import engine.GameEngine;
import engine.ScreenMode;
import engine.audio.AudioMaster;
import engine.audio.AudioSource;
import engine.callbacks.EngineCallback;
import engine.callbacks.FocusCallback;
import engine.callbacks.KeyCallback;
import engine.callbacks.MouseCallback;
import engine.graphics.Light;
import engine.graphics.Model;
import engine.graphics.Shader;
import engine.graphics.Texture;
import engine.graphics.gl.shadow.ShadowDepthBuffer;
import engine.graphics.gl.shadow.ShadowRenderer;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.maths.Vec4f;
import engine.model.ModelLoader;
import engine.model.camera.CameraFPS;
import engine.model.entity.Entity;
import org.lwjgl.glfw.GLFW;

public class Main implements EngineCallback {

	private static final float FOV = 70;
	private static final float NEAR = 0.1f;
	private static final float FAR = 1000;
	private static final float SENSITIVITY = 0.1F;
	private float moveSpeed = 0.6F;

	private ShadowRenderer shadowRenderer;
	private ShadowDepthBuffer shadowDepthBuffer;

	private Shader shader;
	private Terrain terrain;
	//private Model model;
	private ModelLoader modelLoader = new ModelLoader();

	private KeyCallback kc;
	private FocusCallback fc;
	private MouseCallback mouse;
	private GameEngine gameEngine;
	private AudioMaster audioMaster = new AudioMaster();
	private EngineConfig config = new EngineConfig();
	private CameraFPS camera;

	private Entity entity;
	
	public Main(){
		config.title = "GameEngine";
		config.width = 1000;
		config.height = 700;
		config.windowIconPath = "Textures/Icon.png";
		config.rezisable = true;
		config.vsync = false;
		config.screenMode = ScreenMode.WINDOW;

		gameEngine = new GameEngine(this, config);
		gameEngine.start();
	}

	@Override
	public void initCallbacks() {
		kc = new KeyCallback();
		gameEngine.applyCallback(kc);

		mouse = new MouseCallback();
		gameEngine.applyCallback(mouse);

		fc = new FocusCallback();
		gameEngine.applyCallback(fc);
	}

	private Light light;
	private AudioSource audioSource;

	@Override
	public void init() {
/*		audioMaster.init(new Vec3f(0, 0, 0), new Vec3f(0, 0, 0));
		audioMaster.setDistanceAttenuation(AL10.AL_INVERSE_DISTANCE);
		audioSource = new AudioSource("Audio/Randomize20.ogg");
		audioSource.setPosition(new Vec3f(0,0,0));
		audioSource.setRollOffFactor(2);
		audioSource.setReferenceDistance(6);
		audioSource.setMaxDistance(30);
		audioSource.setLooping(true);
		audioSource.setRelative(true);
		audioSource.setPitch(1.5f);
		audioSource.setVolume(0.007F);
		audioSource.play();*/
		camera = new CameraFPS(new Vec3f(0, 50, 20), 0f, 0f);
		shader = new Shader("Shaders/Basic.glsl");
		gameEngine.showMouse(false);
		gameEngine.setMousePosition(config.width/2, config.height/2);
		light = new Light(shader);

		terrain = new Terrain();

		Texture texture = new Texture("Textures/stallTexture.png");
		Model model = modelLoader.loadModel("Objects/Stall.obj", texture);
		entity = new Entity(model, new Vec3f(40, 0, 40), 0, 0, 0, 2);

		//shadowRenderer = new ShadowRenderer();
		//shadowDepthBuffer = new ShadowDepthBuffer(config.width, config.height);
	}

	boolean rot = true;

	@Override
	public void tick(float dt) {
/*		audioMaster.setPosition(camera.getPosition());
		audioSource.setPosition(audioSource.getPosition().add(0.05f, 0, 0));
		Log.log(audioSource.getPosition().x());*/
		if (kc.isKeyCode(GLFW.GLFW_KEY_ESCAPE)) {
			if(rot){
				rot = false;
				gameEngine.showMouse(true);
			} else {
				rot = true;
			    gameEngine.showMouse(false);
			}
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_W)){
			camera.moveForward(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_S)){
			camera.moveBackwards(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_A)){
			camera.moveLeft(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_D)){
			camera.moveRight(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_SPACE)){
			camera.moveUp(moveSpeed);
		}
		if(kc.isKeyCode(GLFW.GLFW_KEY_LEFT_SHIFT)){
			camera.moveDown(moveSpeed);
		}
	}


	@Override
	public void render() {
		if(rot)
			camera.rotate(mouse.getDeltaX(), mouse.getDeltaY(), SENSITIVITY);
		mouse.reset();

		//shadowRenderer.bind();
		//shadowDepthBuffer.bind();
		entity.bind();
		shader.bind();
		light.setPosition("lightPosition", new Vec3f(40.0F, 10.0F, 40.0F));
		light.setBrightness("lightBrightness", 3F);
		light.setColor("lightColor", new Vec4f(1.0F, 1.0F, 1.0F, 1.0F));
		shader.setUniformMat4f("projectionMatrix", Mat4f.projection(FOV, 4, 4, NEAR, FAR, null));
		shader.setUniformMat4f("viewMatrix", camera.getViewMatrix());
		shader.setUniformMat4f("transformationMatrix", entity.getTransformationMatrix());
		entity.draw();
		//shadowRenderer.drawEntityWithShadow(entity, 1.0F, 7.5F);
		shader.unbind();
		entity.unbind();

		terrain.begin();
		terrain.setViewMatrix(camera.getViewMatrix());
		terrain.setProjectionMatrix(Mat4f.projection(FOV, 4, 4, NEAR, FAR, null));
		terrain.render();
		terrain.end();
		//shadowDepthBuffer.unbind();
		//shadowRenderer.unbind();


	}

	@Override
	public void terminate() {
		shader.cleanUpMemory();
		entity.cleanUpMemory();
		terrain.cleanUp();
	}

	public static void main(String[] args){
		new Main();
	}
}