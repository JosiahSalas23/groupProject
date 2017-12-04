// This class creates all the shaders for each object
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=_rjSel-6K38&index=7&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
package pongGraphicEngine;

public class ShaderManager {

	public static Shader paddleShader;
	public static Shader ballShader1;
	public static Shader ballShader2;
	public static Shader ballShader3;
	public static Shader ballShader4;
	public static Shader ballShader5;
	
	public static void loadAll() {
		
		paddleShader = new Shader("src/shaders/paddleVertex.shader", "src/shaders/paddleFragment.shader");
		ballShader1 = new Shader("src/shaders/ballVertex1.shader", "src/shaders/ballFragment.shader");
		ballShader2 = new Shader("src/shaders/ballVertex2.shader", "src/shaders/ballFragment.shader");
		ballShader3 = new Shader("src/shaders/ballVertex3.shader", "src/shaders/ballFragment.shader");
		ballShader4 = new Shader("src/shaders/ballVertex4.shader", "src/shaders/ballFragment.shader");
		ballShader5 = new Shader("src/shaders/ballVertex5.shader", "src/shaders/ballFragment.shader");
		
	} // end loadAll
	
} // end class
