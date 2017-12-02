// This class creates a paddle object and updates(handles player input or moves the paddle for the AI)
// and shows the score for that paddle. It also checks to see if the paddle is in bounds for the game pong.
// This class is an extension/child of the GameObject class
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=qvFu0WhNPWE&index=6&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Another video here
// https://www.youtube.com/watch?v=Y3dbT-H_p20&index=8&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas
package pongGameEngine;

import pongGraphicEngine.VertexArrayObject;
import io.Window;
import pongUtils.Vector3f;

import org.lwjgl.glfw.GLFW;

public class PaddleLong extends GameObject {

	private VertexArrayObject vao;
	
	public Vector3f position;
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.50f;
	public int score = 0;
	
	// paddles vertices and bytes
	float[] vertices = {
			
		0.0f, 0.0f, 0.0f,
		0.0f, HEIGHT, 0.0f,
		WIDTH, HEIGHT, 0.0f,
		WIDTH, 0.0f, 0.0f
			
	};
	
	public byte[] indices = new byte[] {
			
		0, 1, 2,
		2, 3, 0
			
	};
	
	// our Paddle Constructor
	public PaddleLong() {
		
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
		
	} // end Paddle
	
	// Controls the paddles for both player 1 and 2
	public void update(String player, Window window) {
		
		if (player.equals("player1")) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
				position.y += 0.040f;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
				position.y -= 0.040f;
			
		} // end if
		
		if (player.equals("player2")) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_UP))
				position.y += 0.040f;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN))
				position.y -= 0.040f;
					
		} // end if
		
		// Keeps the paddle from moving above the screen
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
		// keeps the paddle from moving below the screen
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end update
	
	// if the paddle is dizzy, this changes the paddles controls
	public void updateDizzy(String player, Window window) {
		
		if (player.equals("player1")) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
				position.y += 0.050f;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
				position.y -= 0.050f;
			
		} // end if
		
		if (player.equals("player2")) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN))
				position.y += 0.050f;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_UP))
				position.y -= 0.050f;
					
		} // end if
		
		// Keeps the paddle from moving above the screen
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
		// keeps the paddle from moving below the screen
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end updateDizzy
	
	// Controls the paddles for both player 1 and 2 when they are slown down
	public void updateSlow(String player, Window window) {
		
		if (player.equals("player1")) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
				position.y += 0.020f;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
				position.y -= 0.020f;
			
		} // end if
		
		if (player.equals("player2")) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_UP))
				position.y += 0.020f;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN) )
				position.y -= 0.020f;
					
		} // end if
		
		// Keeps the paddle from moving above the screen
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
		// keeps the paddle from moving below the screen
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end update
	
	public void moveAIUpHard() {
		
		position.y += 0.035f;
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
	} // end if
	
	public void moveAIDownHard() {
		
		position.y -= 0.036f;
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end if
	
	public void moveAIUpMedium() {
		
		position.y += 0.029f;
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
	} // end if
	
	public void moveAIDownMedium() {
		
		position.y -= 0.03f;
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end if
	
	public void moveAIUpEasy() {
		
		position.y += 0.027f;
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
	} // end if
	
	public void moveAIDownEasy() {
		
		position.y -= 0.028f;
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end if
	
	public void moveAIUpSlow() {
		
		position.y += 0.020f;
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
	} // end if
	
	public void moveAIDownSlow() {
		
		position.y -= 0.020f;
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end if
	
} // end class