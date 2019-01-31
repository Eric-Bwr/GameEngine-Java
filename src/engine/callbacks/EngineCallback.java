package engine.callbacks;

public interface EngineCallback {

    void initCallbacks();

    //everything related to opengl has to be initialized here
    void init();

    void tick(float dt);

    void render();

    void terminate();
}