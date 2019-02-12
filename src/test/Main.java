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
import engine.graphics.gl.shadow.ShadowDepthBuffer;
import engine.graphics.rendering.Light;
import engine.graphics.rendering.Model;
import engine.graphics.rendering.Shader;
import engine.graphics.rendering.Texture;
import engine.graphics.gl.shadow.ShadowRenderer;
import engine.maths.Mat4f;
import engine.maths.Vec3f;
import engine.maths.Vec4f;
import engine.model.ModelLoader;
import engine.model.terrain.HeightsGenerator;
import engine.model.terrain.Terrain;
import engine.model.camera.CameraFPS;
import engine.model.entity.Entity;
import engine.model.terrain.TerrainSettings;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main implements EngineCallback {

	private static final float FOV = 70;
	private static final float NEAR = 0.1f;
	private static final float FAR = 1000;
	private static final float SENSITIVITY = 0.1F;
	private float moveSpeed = 1F;

	private ShadowRenderer shadowRenderer;
	private ShadowDepthBuffer shadowDepthBuffer;

	private Shader shader;
	private Terrain terrain;
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
	private Shader terrainShader;
	private List<Entity> entityList = new ArrayList<>();

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
		terrainShader = new Shader("Shaders/Terrain.glsl");

		TerrainSettings terrainSettings = new TerrainSettings();
		terrainSettings.setShader(terrainShader);
		terrainSettings.setLocationProjectionMatrix("projectionMatrix");
		terrainSettings.setLocationTransformationMatrix("transformationMatrix");
		terrainSettings.setLocationViewMatrix("viewMatrix");
		//HeightsGenerator generator = new HeightsGenerator(70, 3, 0.3f);
		//terrain = new Terrain(0, 0, terrainSettings, generator, new Texture("Textures/grass.png"),
		//	50, 500);
		terrainSettings.setHeightMap(new Texture("Textures/heightMap.png"));
		terrainSettings.setBlendMap(new Texture("Textures/blendMap.png"), "blendMap");
		terrainSettings.addOtherTerrainTexture(new Texture("Textures/mud.png"), "otherTexture");
		terrainSettings.addOtherTerrainTexture(new Texture("Textures/grass.png"), "groundTexture");
		terrain = new Terrain(0, 0, terrainSettings, 50, 500);

		Texture texture = new Texture("Textures/stall.png");
		Model model = modelLoader.loadModel("Objects/tree.obj", texture);

		Random random = new Random();
		for(int i = 0; i < 200; i++){
			int x = random.nextInt(200);
			int z = random.nextInt(200);
			//TODO: NO ROTATION AROUND AXIS
			entityList.add(new Entity(model, new Vec3f(x, terrain.getHeightOfTerrain(x, z), z), 0, 1, 0, 2));
		}

		//shadowRenderer = new ShadowRenderer();
		//shadowDepthBuffer = new ShadowDepthBuffer(config.width, config.height);
	}

	private boolean ground, pressed;

	@Override
	public void tick(float dt) {
		if(kc.isKeyCode(GLFW.GLFW_KEY_RIGHT_SHIFT)){
			if(!pressed) {
				ground = !ground;
				pressed = true;
			}

		}
		pressed = false;
/*		audioMaster.setPosition(camera.getPosition());
		audioSource.setPosition(audioSource.getPosition().add(0.05f, 0, 0));
		Log.log(audioSource.getPosition().x());*/

		if (kc.isKeyCode(GLFW.GLFW_KEY_ESCAPE)) {
			gameEngine.stop();
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
		if(ground)
			camera.setPosition(new Vec3f(camera.getPosition().x(), terrain.getHeightOfTerrain(camera.getPosition().x(),
				camera.getPosition().z()) + 20, camera.getPosition().z()));
		camera.rotate(mouse.getDeltaX(), mouse.getDeltaY(), SENSITIVITY);
		mouse.reset();

		//shadowRenderer.bind();
		//shadowDepthBuffer.bind();
		for(Entity entity : entityList)entity.bind();
		shader.bind();
		light.setPosition("lightPosition", new Vec3f(40.0F, 10.0F, 40.0F));
		light.setBrightness("lightBrightness", 3F);
		light.setColor("lightColor", new Vec4f(1.0F, 1.0F, 1.0F, 1.0F));
		shader.setUniformMat4f("projectionMatrix", Mat4f.projection(FOV, 4, 4, NEAR, FAR, null));
		shader.setUniformMat4f("viewMatrix", camera.getViewMatrix());
		for(Entity entity : entityList) {
			shader.setUniformMat4f("transformationMatrix", entity.getTransformationMatrix());
			entity.draw();
		}
		//shadowRenderer.drawEntityWithShadow(entity, 1.0F, 7.5F);
		shader.unbind();
		for(Entity entity : entityList) entity.unbind();

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
		for(Entity entity : entityList)entity.cleanUpMemory();
		terrain.cleanUpMemory();
	}

	public static void main(String[] args){
		new Main();
	}
}