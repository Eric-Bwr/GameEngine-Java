package engine.maths;

import engine.util.StringUtil;
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
        float[] values = getValues();
        FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
        buffer.put(values).flip();
        return buffer;
    }
    
    public float[] getValues(){
        return new float[]{m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33};
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
        float c = Mathf.cos(angle);
        float s = Mathf.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x() * axis.y();
        float yz = axis.y() * axis.z();
        float xz = axis.x() * axis.z();
        float xs = axis.x() * s;
        float ys = axis.y() * s;
        float zs = axis.z() * s;
        float f00 = axis.x() * axis.x() * oneminusc + c;
        float f2 = xy * oneminusc + zs;
        float f3 = xz * oneminusc - ys;
        float f4 = xy * oneminusc - zs;
        float f5 = axis.y() * axis.y() * oneminusc + c;
        float f6 = yz * oneminusc + xs;
        float f7 = xz * oneminusc + ys;
        float f8 = yz * oneminusc - xs;
        float f9 = axis.z() * axis.z() * oneminusc + c;
        float t00 = from.m00 * f00 + from.m10 * f2 + from.m20 * f3;
        float t2 = from.m01 * f00 + from.m11 * f2 + from.m21 * f3;
        float t3 = from.m02 * f00 + from.m12 * f2 + from.m22 * f3;
        float t4 = from.m03 * f00 + from.m13 * f2 + from.m23 * f3;
        float t5 = from.m00 * f4 + from.m10 * f5 + from.m20 * f6;
        float t6 = from.m01 * f4 + from.m11 * f5 + from.m21 * f6;
        float t7 = from.m02 * f4 + from.m12 * f5 + from.m22 * f6;
        float t8 = from.m03 * f4 + from.m13 * f5 + from.m23 * f6;
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

    public static Mat4f lookAt(Vec3f camPos, Vec3f entityPos, Vec3f up, Mat4f dest){
        if(dest == null){
            dest = Mat4f.identity();
        }
        Vec3f a = entityPos.sub(camPos).normalize();
        Vec3f b = a.cross(up.normalize());
        Vec3f c = b.cross(a);

        dest.m00 = b.x();
        dest.m01 = b.y();
        dest.m02 = b.z();

        dest.m10 = c.x();
        dest.m11 = c.y();
        dest.m12 = c.z();

        dest.m20 = -a.x();
        dest.m21 = -a.y();
        dest.m22 = -a.z();

        dest = mul(dest, translate(camPos, dest, dest), dest);

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

    public static Mat4f normalized01(Mat4f source, Mat4f dest){
        if(dest == null){
            dest = Mat4f.identity();
        }
        float min = source.m00;
        float max = source.m01;
        for(float f : source.getValues()){
            if(f < min){
                min = f;
            }
            if(f > max){
                max = f;
            }
        }

        dest.m00 = Mathf.normalize(source.m00, min, max);
        dest.m01 = Mathf.normalize(source.m01, min, max);
        dest.m02 = Mathf.normalize(source.m02, min, max);
        dest.m03 = Mathf.normalize(source.m03, min, max);
        dest.m10 = Mathf.normalize(source.m10, min, max);
        dest.m11 = Mathf.normalize(source.m11, min, max);
        dest.m12 = Mathf.normalize(source.m12, min, max);
        dest.m13 = Mathf.normalize(source.m13, min, max);
        dest.m20 = Mathf.normalize(source.m20, min, max);
        dest.m21 = Mathf.normalize(source.m21, min, max);
        dest.m22 = Mathf.normalize(source.m22, min, max);
        dest.m23 = Mathf.normalize(source.m23, min, max);
        dest.m30 = Mathf.normalize(source.m30, min, max);
        dest.m31 = Mathf.normalize(source.m31, min, max);
        dest.m32 = Mathf.normalize(source.m32, min, max);
        dest.m33 = Mathf.normalize(source.m33, min, max);

        return dest;
    }

    public static Mat4f invertOrthonormal(Mat4f source, Mat4f dest) {
        if (dest == null) {
            dest = Mat4f.identity();
        }
        float nm30 = -(source.m00 * source.m30 + source.m01 * source.m31 + source.m02 * source.m32);
        float nm31 = -(source.m10 * source.m30 + source.m11 * source.m31 + source.m12 * source.m32);
        float nm32 = -(source.m20 * source.m30 + source.m21 * source.m31 + source.m22 * source.m32);
        dest.m00 = source.m00;
        dest.m01 = source.m10;
        dest.m02 = source.m20;
        dest.m03 = 0.0f;
        dest.m10 = source.m01;
        dest.m11 = source.m11;
        dest.m12 = source.m21;
        dest.m13 = 0.0f;
        dest.m20 = source. m02;
        dest.m21 = source.m12;
        dest.m22 = source.m22;
        dest.m23 = 0.0f;
        dest.m30 = nm30;
        dest.m31 = nm31;
        dest.m32 = nm32;
        dest.m33 = 1.0f;
        return dest;
    }

    public static Mat4f transpose(Mat4f source, Mat4f dest){
        if (dest == null) {
            dest = Mat4f.identity();
        }
        float nm00 = source.m00;
        float nm01 = source.m10;
        float nm02 = source.m20;
        float nm03 = source.m30;
        float nm10 = source.m01;
        float nm11 = source.m11;
        float nm12 = source.m21;
        float nm13 = source.m31;
        float nm20 = source.m02;
        float nm21 = source.m12;
        float nm22 = source.m22;
        float nm23 = source.m32;
        float nm30 = source.m03;
        float nm31 = source.m13;
        float nm32 = source.m23;
        float nm33 = source.m33;
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m03 = nm03;
        dest.m10 = nm10;
        dest.m11 = nm11;
        dest.m12 = nm12;
        dest.m13 = nm13;
        dest.m20 = nm20;
        dest.m21 = nm21;
        dest.m22 = nm22;
        dest.m23 = nm23;
        dest.m30 = nm30;
        dest.m31 = nm31;
        dest.m32 = nm32;
        dest.m33 = nm33;
        return dest;
    }



    public static Mat4f mul(Mat4f one, Mat4f two, Mat4f dest) {
        if (dest == null){
            dest = Mat4f.identity();
        }

        float m00 = one.m00 * two.m00 + one.m10 * two.m01 + one.m20 * two.m02 + one.m30 * two.m03;
        float m01 = one.m01 * two.m00 + one.m11 * two.m01 + one.m21 * two.m02 + one.m31 * two.m03;
        float m02 = one.m02 * two.m00 + one.m12 * two.m01 + one.m22 * two.m02 + one.m32 * two.m03;
        float m03 = one.m03 * two.m00 + one.m13 * two.m01 + one.m23 * two.m02 + one.m33 * two.m03;
        float m10 = one.m00 * two.m10 + one.m10 * two.m11 + one.m20 * two.m12 + one.m30 * two.m13;
        float m11 = one.m01 * two.m10 + one.m11 * two.m11 + one.m21 * two.m12 + one.m31 * two.m13;
        float m12 = one.m02 * two.m10 + one.m12 * two.m11 + one.m22 * two.m12 + one.m32 * two.m13;
        float m13 = one.m03 * two.m10 + one.m13 * two.m11 + one.m23 * two.m12 + one.m33 * two.m13;
        float m20 = one.m00 * two.m20 + one.m10 * two.m21 + one.m20 * two.m22 + one.m30 * two.m23;
        float m21 = one.m01 * two.m20 + one.m11 * two.m21 + one.m21 * two.m22 + one.m31 * two.m23;
        float m22 = one.m02 * two.m20 + one.m12 * two.m21 + one.m22 * two.m22 + one.m32 * two.m23;
        float m23 = one.m03 * two.m20 + one.m13 * two.m21 + one.m23 * two.m22 + one.m33 * two.m23;
        float m30 = one.m00 * two.m30 + one.m10 * two.m31 + one.m20 * two.m32 + one.m30 * two.m33;
        float m31 = one.m01 * two.m30 + one.m11 * two.m31 + one.m21 * two.m32 + one.m31 * two.m33;
        float m32 = one.m02 * two.m30 + one.m12 * two.m31 + one.m22 * two.m32 + one.m32 * two.m33;
        float m33 = one.m03 * two.m30 + one.m13 * two.m31 + one.m23 * two.m32 + one.m33 * two.m33;

        dest.m00 = m00;
        dest.m01 = m01;
        dest.m02 = m02;
        dest.m03 = m03;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;
        dest.m13 = m13;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;
        dest.m23 = m23;
        dest.m30 = m30;
        dest.m31 = m31;
        dest.m32 = m32;
        dest.m33 = m33;

        return dest;
    }

    public String toString(int size) {
        return "|" + get(m00, size) + " " + get(m01, size) + " " + get(m02, size) + " " + get(m03, size) + "|\n"
                + "|" + get(m10, size) + " " + get(m11, size) + " " + get(m12, size) + " " + get(m13, size) + "|\n"
                + "|" + get(m20, size) + " " + get(m21, size) + " " + get(m22, size) + " " + get(m23, size) + "|\n"
                + "|" + get(m30, size) + " " + get(m31, size) + " " + get(m32, size) + " " + get(m33, size) + "|";
    }

    private String get(float value, int size){
        value = Mathf.round(value, size);
        return StringUtil.capStringNumber(String.valueOf(value), size);
    }
}