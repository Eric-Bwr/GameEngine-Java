package engine.maths;

public class Vec2i {

    public static final Vec2i I = new Vec2i(1, 0);
    public static final Vec2i J = new Vec2i(0, 1);

    private int x;
    private int y;

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i(Vec2i other){
        this.x = other.x;
        this.y = other.y;
    }

    public Vec2i add(Vec2i vec2){
        this.x += vec2.x();
        this.y += vec2.y();
        return this;
    }

    public Vec2i sub(Vec2i vec2){
        this.x -= vec2.x();
        this.y -= vec2.y();
        return this;
    }

    public Vec2i add(float x, int y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2i sub(int x, int y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vec2i mult(int f){
        this.x *= f;
        this.y *= f;
        return this;
    }

    public int mult(Vec2i vec2){
        return this.x * vec2.x + this.y * vec2.y;
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vec2i normalize(){
        int xx = (int) ((1 / length()) * this.x);
        int yy = (int) ((1 / length()) * this.y);
        this.x = xx;
        this.y = yy;
        return this;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public int y(int y){
        this.y = y;
        return y;
    }

    public int x(int v){
        this.x = x;
        return x;
    }
}
