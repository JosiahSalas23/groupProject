package render;
import static org.lwjgl.opengl.GL20.*;

import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
/**
 * <h1>Shader</h1>
 * contains the color shading data
 * 
 * <p>
 * tells of the all textures are to be displayed to the screen
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Shader {
	private int program;
	private int vs;
	private int fs;
	
	/**
	 * Shader - constructor
	 * 
	 * @param filename - String name of a pair of .vs and .fs files
	 */
	public Shader(String filename) {
		program = glCreateProgram();
		
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(filename + ".vs"));
		glCompileShader(vs);
		
		//check to see that shader has no errors. 
		if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			//print what error is (what line it is on)
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}
		
		//Create fragment shader.
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(filename + ".fs"));
		glCompileShader(fs);
		
		//check to see that shader has no errors. 
		if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			//print what error is (what line it is on)
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}
		
		glAttachShader(program, vs);
		glAttachShader(program, fs);
		
		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");
		
		glLinkProgram(program);
		if(glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
		glValidateProgram(program);
		if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}
	}	
	/**
	 * finalize - destroys the object
	 * overloads finalize from Object
	 */
	protected void finalize() {
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		glDeleteShader(vs);
		glDeleteShader(fs);
		glDeleteProgram(program);
	}
	
	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);
		if(location != -1) {
			glUniform1i(location, value);
		}
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = glGetUniformLocation(program, name);
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if(location != -1) {
			glUniformMatrix4fv(location, false, buffer);
		}
	}
	
    public void bind() {
    	glUseProgram(program);
    }
	
	
	/**
	 * readFile - Reads the pair of shader files .vs ,.fs
	 * 
	 * @param filename - directory path string
	 * @return string representation of the current object
	 */
	private String readFile(String filename){
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(new File("./shaders/" +filename)));
			
		
			String line;
			while((line = br.readLine()) != null) {
				string.append(line);
				string.append("\n");
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return string.toString();
	}

}
