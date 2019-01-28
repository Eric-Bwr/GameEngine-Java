package engine.maths;

public class Mathf {

    public static final float PI = 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865132823066470938446095505822317248111745028410270193852110555964462294895493038196442881097566593344612847564823378678316527120190914564856692F;
    public static final double PIS_LITTLE_BROTHER = 3.14159265D;

    public static float sin(float value){
        return (float) Math.sin(Math.toDegrees(value));
    }

    public static float cos(float value){
        return (float) Math.cos(Math.toDegrees(value));
    }

    public static float tan(float value){
        return (float)Math.tan(Math.toDegrees(value));
    }

    public static float asin(float value){
        return (float) Math.asin(Math.toDegrees(value));
    }

    public static float acos(float value){
        return (float) Math.acos(Math.toDegrees(value));
    }

    public static float atan(float value){
        return (float)Math.atan(Math.toDegrees(value));
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
}
