package engine.util;

import engine.maths.Mat4f;
import engine.maths.Vec2f;
import engine.maths.Vec3f;

public class Log {

	public static void logError(String input){
		System.err.println("Error: " + input);
	}

	public static void logInfo(String input){
		System.out.println("Info: " + input);
	}

	public static void log(String input){ System.out.println(input); }

	public static void log(Vec3f vector){ System.out.println(String.format("%s, %s, %s", vector.x(), vector.y(), vector.z())); }

	public static void log(Vec2f vector){
		System.out.println(String.format("%s, %s", vector.x(), vector.y()));
	}

	public static void log(float input){ System.out.println(input); }

	//public static void log(Mat4f mat){
	//	StringBuilder sb = new StringBuilder("[");
	//	sb.append(String.format("%s, %s, %s, %s", mat.values[0], mat.values[1], mat.values[2], mat.values[3])).append
	// ("\n ");
	//	sb.append(String.format("%s, %s, %s, %s", mat.values[4], mat.values[5], mat.values[6], mat.values[7])).append
	// ("\n ");
	//	sb.append(String.format("%s, %s, %s, %s", mat.values[8], mat.values[9], mat.values[10], mat.values[11]))
	// .append("\n ");
	//	sb.append(String.format("%s, %s, %s, %s", mat.values[12], mat.values[13], mat.values[14], mat.values[15]));
	//	sb.append("]");
	//	System.out.println(sb.toString());
	//}

	public static void log3FArray(float[] data){
		for(int n = 0; n < data.length; n++){
			float x = data[n];
			float y = data[n + 1];
			float z = data[n + 2];
			System.out.println(String.format("%s, %s, %s", x, y, z));
			n += 3;
		}
	}

	public static void log2FArray(float[] data){
		for(int n = 0; n < data.length - 1; n++){
			float x = data[n];
			float y = data[n + 1];
			System.out.println(String.format("%s, %s", x, y));
			n += 2;
		}
	}

	public static void log1iArray(int[] data){
		for(int n = 0; n < data.length; n++){
			System.out.println(data[n]);
		}
	}
}

