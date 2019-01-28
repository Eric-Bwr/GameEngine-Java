package engine.maths;

public class Vec2f {

    private float x;
    private float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x(){
        return x;
    }

    public float y(){
        return y;
    }


    public float y(float y){
        this.y = y;
        return y;
    }

    public float x(float v){
        this.x = x;
        return x;
    }
}
