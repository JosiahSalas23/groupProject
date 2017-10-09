// This class creates a shader for the given object
package graphicEngine;

import static utils.Utilities.*;

import utils.Vector3f;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Shader {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public Shader(String vertexFile, String fragmentFile) {
		
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		glLinkProgram(programID);
		glValidateProgram(programID);
		
	} // end shader
	
	public int getID() {
		
		return this.programID;
		
	} // end getID
	
	public void start() {
		
		glUseProgram(programID);
		
	} // end start
	
	public void stop() {
		
		glUseProgram(0);
		
	} // end stop
	
	public int getUniform(String name) {
		
		int result = glGetUniformLocation(programID, name);
		if (result == -1) {
			
			System.err.println("Uniform location not found for: " + name);
			
		} // end if
		
		return result;
		
	} // end getUniform
	
	public void setUniform3f(String name, Vector3f position) {
		
		glUniform3f(getUniform(name), position.x, position.y, position.z);
		
	} // end setUniform3f
	
} // end class
