package engine.maths;

import java.util.concurrent.ThreadLocalRandom;

public class Randf {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static float rand(){
        return random.nextFloat();
    }

    public static float rand(float max){
        return rand() * max;
    }

    public static float rand(float min, float max){
        float diff = max - min;
        return rand() * diff + min;
    }

}
