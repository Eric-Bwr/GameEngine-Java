package engine.maths;

public class Interpolation {

    public static float linear(float value, float min, float max){
        return min + (max - min) * value;
    }

    public static float cubic(float value, float min, float max){
        float out = (1 - Mathf.cos(value * Mathf.PI)) / 2;
        return (min * (1 - out) + max * out);
    }

}
