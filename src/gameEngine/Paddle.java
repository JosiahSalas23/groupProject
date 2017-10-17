// This class creates a paddle object and updates and shows the score for that paddle
package gameEngine;

import graphicEngine.VertexArrayObject;
import input.KeyboardInput;
import utils.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Paddle extends GameObject {

	private VertexArrayObject vao;
	
	public Vector3f position;
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.25f;
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
	public Paddle() {
		
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
		
	} // end Paddle
	
	// Controls the paddles for both player 1 and 2
	public void update(String player) {
		
		if (player.equals("player1")) {
			
			if (KeyboardInput.isKeyDown(GLFW_KEY_W))
				position.y += 0.040f;
			
			if (KeyboardInput.isKeyDown(GLFW_KEY_S))
				position.y -= 0.040f;
			
		} // end if
		
		if (player.equals("player2")) {
			
			if (KeyboardInput.isKeyDown(GLFW_KEY_UP))
				position.y += 0.040f;
			
			if (KeyboardInput.isKeyDown(GLFW_KEY_DOWN))
				position.y -= 0.040f;
					
		} // end if
		
		// Keeps the paddle from moving above the screen
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
		// keeps the paddle from moving below the screen
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end update
	
	public void moveAIUp() {
		
		position.y += 0.0344f;
		if (position.y + HEIGHT >= 1.0f)
			position.y = 0.99f - HEIGHT;
		
	} // end if
	
	public void moveAIDown() {
		
		position.y -= 0.0344f;
		if (position.y <= -1.0f)
			position.y = -0.99f;
		
	} // end if
	
	// updates the score for the player that scored
	public void score() {
		
		score++;
		
	} // end score
	
	// shows us the score of that player
	public int getScore() {
		
		return score;
		
	} // end score
	
} // end class