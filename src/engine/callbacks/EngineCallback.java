package engine.callbacks;

public interface EngineCallback {

    void initCallbacks();

    void init();

    void tick(float dt);

    void render();

    void terminate();
}