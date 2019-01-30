package engine.maths;

public class Vec3f {

    private float x;
    private float y;
    private float z;

    public Vec3f(float w){
        this.x = w;
        this.y = w;
        this.z = w;
    }

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3f add(Vec3f vec2){
        this.x += vec2.x();
        this.y += vec2.y();
        this.z += vec2.z();
        return this;
    }

    public Vec3f sub(Vec3f vec2){
        this.x -= vec2.x();
        this.y -= vec2.y();
        this.z -= vec2.z();
        return this;
    }

    public Vec3f add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3f sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3f mult(float f){
        this.x *= f;
        this.y *= f;
        this.z *= z;
        return this;
    }

    public float length(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vec3f normalize(){
        float xx = (1 / length()) * this.x;
        float yy = (1 / length()) * this.y;
        float zz = (1 / length()) * this.z;
        this.x = xx;
        this.y = yy;
        this.z = zz;
        return this;
    }

    public float x(){
        return x;
    }

    public float y(){
        return y;
    }

    public float z(){
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

    public float z(float z){
        this.z = z;
        return z;
    }

    public float dot(Vec3f vec2){
        return this.x * vec2.x + this.y * vec2.y + this.z * vec2.z;
    }

    public Vec3f cross(Vec3f vec3){
        float y = this.y * vec3.z - this.z * vec3.y;
        float z = this.z * vec3.x - this.x * vec3.z;
        float x = this.x * vec3.y - this.y * vec3.x;
        this.x = x;
        this.y = y;
        this.z = z;
        return new Vec3f(x, y, z);
    }

    public float distance(Vec3f other){
        return distance(other.x, other.y, other.z);
    }

    public float distance(float x, float y, float z){
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        return Mathf.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
