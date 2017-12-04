// This class creates a ball object for pong.
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=qvFu0WhNPWE&index=6&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// another video here
// https://www.youtube.com/watch?v=4CWXcWTsxog&index=10&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
package pongGameEngine;

import pongGraphicEngine.VertexArrayObject;
import pongUtils.Vector3f;

public class Ball extends GameObject {
	
	private VertexArrayObject vao;
	
	public Vector3f position;
	public Vector3f movement;
	
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.05f;
	
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

	// Creates the ball
	public Ball() {
		
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
		this.movement = new Vector3f(0.015f, 0.0f, 0.0f);
		
	} // end ball
	
	// Checks to see if the ball remains in the window, if not then it puts the ball back in the window in moves it in the oposite direction
	public boolean checkBounds () {
		
		// below
		if (position.y <= -1.0f) {
			
			position.y = -0.99f;
			movement.y *= -1.0f;
			return true;
			
		} // end if
		
		// above
		if (position.y + HEIGHT >= 1.0f) {
			
			position.y = 0.99f - HEIGHT;
			movement.y *= -1.0f;
			return true;
			
		} // end if
		
		// right
		if (position.x + WIDTH >= 1.0f) {
			
			position.x = 0.99f - WIDTH;
			movement.x *= -1.0f;
			return true;
			
		} // end if
		
		// left
		if (position.x <= -1.0f) {
			
			position.x = -0.99f;
			movement.x *= -1.0f;
			return true;
			
		} // end if
		
		return false;
		
	} // end checkBounds
	
	// updates the position of the ball
	public void update() {
		
		if (!checkBounds()) {
			
			this.position.translate(movement);
			
		} // end if
		
	} // end update
	
}
