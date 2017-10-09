// This class creates all the shaders for each object
package graphicEngine;

public class ShaderManager {

	public static Shader paddleShader;
	public static Shader ballShader;
	
	public static void loadAll() {
		
		paddleShader = new Shader("src/shaders/paddleVertex.shader", "src/shaders/paddleFragment.shader");
		ballShader = new Shader("src/shaders/ballVertex.shader", "src/shaders/ballFragment.shader");
		
	} // end loadAll
	
} // end class
