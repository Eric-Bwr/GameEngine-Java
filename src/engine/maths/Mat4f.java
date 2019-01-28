package engine.maths;

import com.sun.javafx.geom.Matrix3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Mat4f {

    public float
            m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33
            ;

    public Mat4f(){

    }

    public static Mat4f identity(){
        Mat4f out = new Mat4f();
        out.m00 = 1.0F;
        out.m11 = 1.0F;
        out.m22 = 1.0F;
        out.m33 = 1.0F;
        return out;
    }

    public static Mat4f orthographic(float left, float right, float bottom, float top, float near, float far){
        Mat4f out = identity();
        out.m00 = 2.0F / (right - left);
        out.m11 = 2.0F / (top - bottom);
        out.m22 = 2.0F / (near - far);

        out.m03 = (left + right) / (left - right);
        out.m13 = (bottom + top) / (bottom - top);
        out.m23 = (far + near) / (far - near);
        return out;
    }

    public Mat4f translate(Vec3f vec){
        Mat4f out = identity();
        out.m03 = vec.x();
        out.m13 = vec.y();
        out.m23 = vec.z();
        return out;
    }

    public static Mat4f rotate(float by){
        Mat4f out = identity();
        float r = Mathf.toRadians(by);
        float cos = Mathf.cos(r);
        float sin = Mathf.sin(r);
        out.m00 = cos;
        out.m10 = sin;
        out.m01 = cos;
        out.m11 = sin;
        return out;
    }

    public FloatBuffer toFloatBuffer(){
        float[] values = new float[]{m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33};
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values).flip();
        return buffer;
    }
}
