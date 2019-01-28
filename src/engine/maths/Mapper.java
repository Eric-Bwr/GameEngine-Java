package engine.maths;

public class Mapper {

    public static float map(float value, float oldMin, float oldMax, float newMin, float newMax){
        return Interpolation.linear(Mathf.normalize(value, oldMin, oldMax), newMin, newMax);
    }

}
