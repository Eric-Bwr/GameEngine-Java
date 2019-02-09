package engine.maths;

public class Vec4f {

    private float x;
    private float y;
    private float z;
    private float w;

    public Vec4f(float w){
        this.x = w;
        this.y = w;
        this.z = w;
        this.w = w;
    }

    public Vec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4f add(Vec4f vec2){
        this.x += vec2.x();
        this.y += vec2.y();
        this.z += vec2.z();
        this.w += vec2.w();
        return this;
    }

    public Vec4f sub(Vec4f vec2){
        this.x -= vec2.x();
        this.y -= vec2.y();
        this.z -= vec2.z();
        this.w -= vec2.w();
        return this;
    }

    public Vec4f add(float x, float y, float z, float w){
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        return this;
    }

    public Vec4f sub(float x, float y, float z, float w){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        this.w -= w;
        return this;
    }

    public Vec4f mult(float f){
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
        return this;
    }

    public float x(){
        return x;
    }

    public float y(){
        return y;
    }

    public float z(){
        return z;
    }

    public float w(){
        return w;
    }

    public float y(float y){
        this.y = y;
        return y;
    }

    public float x(float x){
        this.x = x;
        return x;
    }

    public float z(float z){
        this.z = z;
        return z;
    }

    public float w(float w){
        this.w = w;
        return w;
    }

    public float dot(Vec4f vec2){
        return this.x * vec2.x() + this.y * vec2.y() + this.z * vec2.z() + this.w * vec2.w();
    }

    public float distance(Vec4f other){
        return distance(other.x, other.y, other.z);
    }

    public float distance(float x, float y, float z){
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;
        float dw = this.w - w;
        return Mathf.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    public Vec3f xyz(){
        return new Vec3f(x, y, z);
    }

    public Vec2f xy(){
        return new Vec2f(x, y);
    }

}
