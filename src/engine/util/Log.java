package engine.util;

public class Log {

	public static void logError(String input){
		System.err.println("Error: " + input);
	}

	public static void logInfo(String input){
		System.out.println("Info: " + input);
	}
}