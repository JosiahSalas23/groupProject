// This class creates all the shaders for each object for pong
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=_rjSel-6K38&index=7&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas
package pongGraphicEngine;

public class ShaderManager {

	public static Shader paddleShader;
	public static Shader paddleSlowShader;
	public static Shader paddleBigShader;
	public static Shader paddleSmallShader;
	public static Shader paddleDizzyShader;
	public static Shader ballShader1;
	public static Shader ballShader2;
	public static Shader ballShader3;
	public static Shader ballShader4;
	public static Shader ballShader5;
	public static Shader powerUpBallShader;
	
	public static Shader whitePaddle;
	public static Shader whiteBall;
	
	public static void loadAll() {
		
		paddleShader = new Shader("src/shaders/paddleVertex.shader", "src/shaders/paddleFragment.shader");
		paddleSlowShader = new Shader("src/shaders/paddleSlowVertex.shader", "src/shaders/paddleFragment.shader");
		paddleBigShader = new Shader("src/shaders/paddleBigVertex.shader", "src/shaders/paddleFragment.shader");
		paddleSmallShader = new Shader("src/shaders/paddleSmallVertex.shader", "src/shaders/paddleFragment.shader");
		paddleDizzyShader = new Shader("src/shaders/paddleDizzyVertex.shader", "src/shaders/paddleFragment.shader");
		ballShader1 = new Shader("src/shaders/ballVertex1.shader", "src/shaders/ballFragment.shader");
		ballShader2 = new Shader("src/shaders/ballVertex2.shader", "src/shaders/ballFragment.shader");
		ballShader3 = new Shader("src/shaders/ballVertex3.shader", "src/shaders/ballFragment.shader");
		ballShader4 = new Shader("src/shaders/ballVertex4.shader", "src/shaders/ballFragment.shader");
		ballShader5 = new Shader("src/shaders/ballVertex5.shader", "src/shaders/ballFragment.shader");
		powerUpBallShader = new Shader("src/shaders/powerUpBallVertex.shader", "src/shaders/ballFragment.shader");
		
		whitePaddle = new Shader("src/shaders/whiteVertex.shader", "src/shaders/paddleFragment.shader");
		whiteBall = new Shader("src/shaders/whiteVertex.shader", "src/shaders/ballFragment.shader");
		
	} // end loadAll
	
} // end class
