package engine.maths;

public class Vec3i {

    private int x;
    private int y;
    private int z;

    public Vec3i(int w){
        this.x = w;
        this.y = w;
        this.z = w;
    }

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i add(Vec3i vec2){
        this.x += vec2.x();
        this.y += vec2.y();
        this.z += vec2.z();
        return this;
    }

    public Vec3i sub(Vec3i vec2){
        this.x -= vec2.x();
        this.y -= vec2.y();
        this.z -= vec2.z();
        return this;
    }

    public Vec3i add(int x, int y, int z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3i sub(int x, int y, int z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3i mult(int f){
        this.x *= f;
        this.y *= f;
        this.z *= z;
        return this;
    }

    public int mult(Vec3i vec2){
        return this.x * vec2.x + this.y * vec2.y + this.z * vec2.z;
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vec3i normalize(){
        int xx = (int) ((1 / length()) * this.x);
        int yy = (int) ((1 / length()) * this.y);
        int zz = (int) ((1 / length()) * this.z);
        this.x = xx;
        this.y = yy;
        this.z = zz;
        return this;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public int z(){
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

    public int z(int z){
        this.z = z;
        return z;
    }

    public Vec3i dot(Vec3i vec3){
        int y = this.y * vec3.z - this.z * vec3.y;
        int z = this.z * vec3.x - this.x * vec3.z;
        int x = this.x * vec3.y - this.y * vec3.x;
        this.x = x;
        this.y = y;
        this.z = z;
        return new Vec3i(x, y, z);
    }

}
