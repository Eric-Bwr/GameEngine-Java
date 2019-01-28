package engine.graphics;

import engine.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Shader {

	private int id;

	public Shader(String path) {
		String mode = "none";
		StringBuilder vertexBuffer = new StringBuilder();
		StringBuilder fragBuffer = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String buff;
			while ((buff = reader.readLine()) != null) {
				if (buff.equalsIgnoreCase("#shader vertex")){
					mode = "vertex";
				}else if(buff.equalsIgnoreCase("#shader fragment")){
					mode = "fragment";
				}else{
					if(mode == "vertex"){
						vertexBuffer.append(buff).append("\n");
					} else if(mode == "fragment"){
						fragBuffer.append(buff).append("\n");
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String vertexSource = vertexBuffer.toString();
		String fragSource = fragBuffer.toString();
		Log.logInfo(fragSource);
	}
}