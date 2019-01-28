package engine.maths;

public class Mat4f {

    public float
            m00, m01, m02, m03,
            m10, m11, m12, m13,
            m20, m21, m22, m23,
            m30, m31, m32, m33
            ;

    public Mat4f(){
        m00 = 0;
        m01 = 0;
        m02 = 0;
        m03 = 0;
        m10 = 0;
        m11 = 0;
        m12 = 0;
        m13 = 0;
        m20 = 0;
        m21 = 0;
        m22 = 0;
        m23 = 0;
        m30 = 0;
        m31 = 0;
        m32 = 0;
        m33 = 0;
    }

    public static Mat4f identity(){
        Mat4f out = new Mat4f();
        out.m00 = 1.0F;
        out.m11 = 1.0F;
        out.m22 = 1.0F;
        out.m33 = 1.0F;
        return out;
    }
}
