package engine.util;

import engine.maths.Vec4f;

public class ColorUtil {

    public static Vec4f getColor(int color){
        float red = color >> 16 & 255;
        float green = color >> 8 & 255;
        float blue = color & 255;
        float alpha = color >> 24 & 255;
        return new Vec4f(red, green, blue, alpha);
    }
}