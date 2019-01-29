package engine.callbacks;

public interface EngineCallback {

    void initCallbacks();

    /**
     * Everything you have to load related to opengl has to get loaded here
     */
    void init();

    void tick(float dt);

    void render();

    void terminate();
}