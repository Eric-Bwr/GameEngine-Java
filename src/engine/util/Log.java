package engine.util;

import engine.maths.Mat4f;
import engine.maths.Vec2f;
import engine.maths.Vec3f;

public class Log {

	public static void log(Vec3f vector){
		System.out.println(vector.toString());
	}

	public static void log(Vec2f vector){
		System.out.println(vector.toString());
	}

	public static void log(Mat4f mat, int size){
		System.out.println("4x4 Matrix\n" + "{");
		System.out.println(mat.toString(size));
		System.out.println("}");
	}

	public static void log(Mat4f mat){
		log(mat, 1);
	}

	public static void logError(String input){
		System.err.println("Error: " + input);
	}

	public static void logInfo(String input){
		System.out.println("Info: " + input);
	}

	public static void log(String input) {
		System.out.println(input);
	}

	public static void log(float input){
		System.out.println(input);
	}

	public static void log(boolean input){
		System.out.println(input ? "True" : "False");
	}

	public static void log3FArray(float[] data){
		for(int n = 0; n < data.length; n++){
			float x = data[n];
			float y = data[n + 1];
			float z = data[n + 2];
			System.out.println(String.format("%s, %s, %s", x, y, z));
			n += 3;
		}
	}
}

