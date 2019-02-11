package engine;

import engine.maths.Vec4f;

public class EngineConfig {

    public String title = "Game";
    public int width = 800;
    public int height = 600;
    public String windowIconPath = "";
    public boolean vsync = false;
    public boolean rezisable = true;
    public ScreenMode screenMode = ScreenMode.WINDOW;
    public Vec4f clearColor = new Vec4f(0.5f, 0.5f, 0.5f, 1f);
}