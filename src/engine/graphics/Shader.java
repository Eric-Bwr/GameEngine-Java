package engine.graphics;

import engine.util.Log;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;

public class Shader {

	public int id;
	private String fragmentSource;
	private String vertexSource;

	public Shader(String path) {
		String mode = "none";
		StringBuilder vertexBuffer = new StringBuilder();
		StringBuilder fragBuffer = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String buff;
			while ((buff = reader.readLine()) != null) {
				if (buff.toLowerCase().contains("vertex")){
					mode = "vertex";
				}else if(buff.toLowerCase().contains("fragment")){
					mode = "fragment";
				}else{
					if(mode.equals("vertex")){
						vertexBuffer.append(buff).append("\n");
					} else if(mode.equals("fragment")){
						fragBuffer.append(buff).append("\n");
					}
				}
			}
			reader.close();
		} catch (Exception e) {
			Log.logError("Failed to read Shader");
			e.printStackTrace();
		}
		vertexSource = vertexBuffer.toString();
		fragmentSource = fragBuffer.toString();
		glAttachShader(id, compileShader(vertexSource, GL_VERTEX_SHADER));
		glAttachShader(id, compileShader(fragmentSource, GL_FRAGMENT_SHADER));

		glLinkProgram(id);
		if(glGetProgrami(id, GL_LINK_STATUS) == GL11.GL_FALSE){
			Log.logError("Failed to link Shader");
		}
		glValidateProgram(id);
		if (glGetProgrami(id, GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			Log.logError("Failed to validate Shader");
		}
	}

	private int compileShader(String source, int shaderType){
		int shader = glCreateShader(shaderType);
		if(shader == 0){
			Log.logError("Failed to create Shader of type " + shaderType);
		}
		glShaderSource(id, source);
		glCompileShader(shader);
		int status = glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
		if(status == GL11.GL_FALSE){
			Log.logError("Failed to compile Shader of type " + shaderType);
		}
		return shader;
	}
}