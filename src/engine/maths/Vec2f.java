package engine.maths;

public class Vec2f {

    private float x;
    private float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f add(Vec2f vec2){
        this.x += vec2.x();
        this.y += vec2.y();
        return this;
    }

    public Vec2f sub(Vec2f vec2){
        this.x -= vec2.x();
        this.y -= vec2.y();
        return this;
    }

    public Vec2f add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2f sub(float x, float y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vec2f mult(float f){
        this.x *= f;
        this.y *= f;
        return this;
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vec2f normalize(){
        float xx = (1 / length()) * this.x;
        float yy = (1 / length()) * this.y;
        this.x = xx;
        this.y = yy;
        return this;
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
