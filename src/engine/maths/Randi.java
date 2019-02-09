package engine.maths;

import java.util.concurrent.ThreadLocalRandom;

public class Randi {

    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static int rand(){
        return random.nextInt(0, Integer.MAX_VALUE);
    }

    public static int rand(int max){
        return (int)((rand() / (float)Integer.MAX_VALUE) * max);
    }

    public static int rand(int min, int max){
        int diff = max - min;
        return (int)((rand() / (float)Integer.MAX_VALUE) * diff + min);
    }

}
