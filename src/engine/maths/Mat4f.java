package engine.maths;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Mat4f {

    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;

    public FloatBuffer toFloatBuffer(){
        float[] values = new float[]{m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33};
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values).flip();
        return buffer;
    }

    public static Mat4f identity() {
        Mat4f m = new Mat4f();
        m.m00 = 1.0f;
        m.m01 = 0.0f;
        m.m02 = 0.0f;
        m.m03 = 0.0f;
        m.m10 = 0.0f;
        m.m11 = 1.0f;
        m.m12 = 0.0f;
        m.m13 = 0.0f;
        m.m20 = 0.0f;
        m.m21 = 0.0f;
        m.m22 = 1.0f;
        m.m23 = 0.0f;
        m.m30 = 0.0f;
        m.m31 = 0.0f;
        m.m32 = 0.0f;
        m.m33 = 1.0f;
        return m;
    }

    public static Mat4f projection(float fov, float width, float height, float near, float far, Mat4f dest){
        if (dest == null) {
            dest = Mat4f.identity();
        }
        float aspectRatio = width / height;
        float yScale = (1f / Mathf.tan(fov * 0.5F)) * aspectRatio;
        float xScale = yScale / aspectRatio;
        float frustum_length = far - near;

        dest.m00 = xScale;
        dest.m11 = yScale;
        dest.m22 = -((far + near) / frustum_length);
        dest.m23 = -1;
        dest.m32 = -((2 * near * far) / frustum_length);
        dest.m33 = 0;
        return dest;
    }
    
    public static Mat4f rotation(float angle, final Vec3f axis, Mat4f from, Mat4f out) {
        if(out == null){
            out = Mat4f.identity();
        }
        final float c = Mathf.cos(angle);
        final float s = Mathf.sin(angle);
        final float oneminusc = 1.0f - c;
        final float xy = axis.x() * axis.y();
        final float yz = axis.y() * axis.z();
        final float xz = axis.x() * axis.z();
        final float xs = axis.x() * s;
        final float ys = axis.y() * s;
        final float zs = axis.z() * s;
        final float f00 = axis.x() * axis.x() * oneminusc + c;
        final float f2 = xy * oneminusc + zs;
        final float f3 = xz * oneminusc - ys;
        final float f4 = xy * oneminusc - zs;
        final float f5 = axis.y() * axis.y() * oneminusc + c;
        final float f6 = yz * oneminusc + xs;
        final float f7 = xz * oneminusc + ys;
        final float f8 = yz * oneminusc - xs;
        final float f9 = axis.z() * axis.z() * oneminusc + c;
        final float t00 = from.m00 * f00 + from.m10 * f2 + from.m20 * f3;
        final float t2 = from.m01 * f00 + from.m11 * f2 + from.m21 * f3;
        final float t3 = from.m02 * f00 + from.m12 * f2 + from.m22 * f3;
        final float t4 = from.m03 * f00 + from.m13 * f2 + from.m23 * f3;
        final float t5 = from.m00 * f4 + from.m10 * f5 + from.m20 * f6;
        final float t6 = from.m01 * f4 + from.m11 * f5 + from.m21 * f6;
        final float t7 = from.m02 * f4 + from.m12 * f5 + from.m22 * f6;
        final float t8 = from.m03 * f4 + from.m13 * f5 + from.m23 * f6;
        out.m20 = from.m00 * f7 + from.m10 * f8 + from.m20 * f9;
        out.m21 = from.m01 * f7 + from.m11 * f8 + from.m21 * f9;
        out.m22 = from.m02 * f7 + from.m12 * f8 + from.m22 * f9;
        out.m23 = from.m03 * f7 + from.m13 * f8 + from.m23 * f9;
        out.m00 = t00;
        out.m01 = t2;
        out.m02 = t3;
        out.m03 = t4;
        out.m10 = t5;
        out.m11 = t6;
        out.m12 = t7;
        out.m13 = t8;
        return out;
    }

    public static Mat4f scale(Vec3f vec, final Mat4f from, Mat4f dest) {
        if (dest == null) {
            dest = Mat4f.identity();
        }
        dest.m00 = from.m00 * vec.x();
        dest.m01 = from.m01 * vec.x();
        dest.m02 = from.m02 * vec.x();
        dest.m03 = from.m03 * vec.x();
        dest.m10 = from.m10 * vec.y();
        dest.m11 = from.m11 * vec.y();
        dest.m12 = from.m12 * vec.y();
        dest.m13 = from.m13 * vec.y();
        dest.m20 = from.m20 * vec.z();
        dest.m21 = from.m21 * vec.z();
        dest.m22 = from.m22 * vec.z();
        dest.m23 = from.m23 * vec.z();
        return dest;
    }

    public static Mat4f translate(final Vec3f vec, final Mat4f from, Mat4f dest) {
        if(dest == null){
            dest = Mat4f.identity();
        }
        dest.m30 += from.m00 * vec.x() + from.m10 * vec.y() + from.m20 * vec.z();
        dest.m31 += from.m01 * vec.x() + from.m11 * vec.y() + from.m21 * vec.z();
        dest.m32 += from.m02 * vec.x() + from.m12 * vec.y() + from.m22 * vec.z();
        dest.m33 += from.m03 * vec.x() + from.m13 * vec.y() + from.m23 * vec.z();
        return dest;
    }

    public static Mat4f orthographic(float left, float right, float bottom, float top, float near, float far, Mat4f dest){
        if(dest == null){
            dest = Mat4f.identity();
        }
        dest.m00 = 2.0F / (right - left);
        dest.m11 = 2.0F / (top - bottom);
        dest.m22 = 2.0F / (far - near);

        dest.m30 = (right + left) / (right - left);
        dest.m31 = (top + bottom) / (top - bottom);
        dest.m32 = (far + near) / (far - near);
        return dest;
    }
}