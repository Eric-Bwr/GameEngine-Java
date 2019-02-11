package engine.maths;

public class Mathf {

    public static final float PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865132823066470938446095505822317248111745028410270193852110555964462294895493038196442881097566593344612847564823378678316527120190914564856692F;
    public static final double PIS_LITTLE_BROTHER = 3.14159265D;

    public static float round(float value, int decimals){
        float f = pow(10, decimals);
        return (float)Math.round(value * f) / f;
    }

    public static float sin(float value){
        return (float) Math.sin(Math.toRadians(value));
    }

    public static float cos(float value){
        return (float) Math.cos(Math.toRadians(value));
    }

    public static float floor(float value){
        return (float) Math.floor(value);
    }

    public static float tan(float value){
        return (float)Math.tan(Math.toRadians(value));
    }

    public static float asin(float value){
        return (float) Math.asin(Math.toRadians(value));
    }

    public static float acos(float value){
        return (float) Math.acos(Math.toRadians(value));
    }

    public static float atan(float value){
        return (float)Math.atan(Math.toRadians(value));
    }

    public static float sqrt(float value){
        return (float) Math.sqrt(value);
    }

    public static float pow(float value, float times){
        return (float) Math.pow(value, times);
    }

    public static float rootOf(float value, float times){
        return (float) Math.pow(value, 1 / times);
    }

    public static float max(float a, float b){
        if(a > b){
            return a;
        }else if(b > a){
            return b;
        }else{
            return a;
        }
    }

    public static float min(float a, float b){
        if(a < b){
            return a;
        }else if(b < a){
            return b;
        }else{
            return a;
        }
    }

    public static float abs(float a, float b){
        return a > b ? a : b;
    }

    public static float normalize(float value, float min, float max){
        return (value - min) / (max - min);
    }

    public static float toRadians(float by) {
        return (float) Math.toRadians(by);
    }

    public static float barryCentric(Vec3f p1, Vec3f p2, Vec3f p3, Vec2f pos) {
        float det = (p2.z() - p3.z()) * (p1.x() - p3.x()) + (p3.x() - p2.x()) * (p1.z() - p3.z());
        float l1 = ((p2.z() - p3.z()) * (pos.x() - p3.x()) + (p3.x() - p2.x()) * (pos.y() - p3.z())) / det;
        float l2 = ((p3.z() - p1.z()) * (pos.x() - p3.x()) + (p1.x() - p3.x()) * (pos.y() - p3.z())) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y() + l2 * p2.y() + l3 * p3.y();
    }
}