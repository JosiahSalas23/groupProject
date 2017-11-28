// This class creates a PowerUpball object for pong.
// It also translates the ball movement. This class is an extension/child of the GameObject class
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=qvFu0WhNPWE&index=6&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// another video here
// https://www.youtube.com/watch?v=4CWXcWTsxog&index=10&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas
package pongGameEngine;

import pongGraphicEngine.VertexArrayObject;
import pongUtils.Vector3f;

public class Bomb extends GameObject {
	
	private VertexArrayObject vao;
	
	public Vector3f position;
	public Vector3f movement;
	
	public float WIDTH = 0.03f;
	public float HEIGHT = 0.03f;
	
	public boolean inBounds;
	
	float[] vertices = {
			
		0.0f, HEIGHT, 0.0f,
		0.0f, 0.0f, 0.0f,
		WIDTH, 0.0f, 0.0f,
		WIDTH, HEIGHT, 0.0f
			
	};
	
	public byte[] indices = new byte[] {
			
		0, 1, 2,
		2, 3, 0
			
	};

	// Creates the power up ball
	public Bomb() {
		
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
		this.movement = new Vector3f(0.0f, 0.0f, 0.0f);
		
	} // end ball
	
	// checks to see if the bomb is still in play
	public void checkBounds() {
		
		// right
		if (position.x + WIDTH >= 1.0f)
			inBounds = false;
		
		// left
		if (position.x <= -1.0f)
			inBounds = false;
		
		if (position.x + WIDTH < 1.0f && position.x > -1.0f)
			inBounds = true;
		
	} // end checkBounds
	
	public void update() {
		
		this.position.translate(movement);
		checkBounds();
		
	} // end update
	
} // end class
