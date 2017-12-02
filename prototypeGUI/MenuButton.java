// This class creates a menu button for our prototype gui
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=qvFu0WhNPWE&index=6&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// another video here
// https://www.youtube.com/watch?v=4CWXcWTsxog&index=10&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas
package prototypeGUI;

import pongGraphicEngine.VertexArrayObject;
import pongUtils.Vector3f;

public class MenuButton extends MenuObject {
	
	private VertexArrayObject vao;
	
	public Vector3f position;
	public Vector3f movement;
	
	public float WIDTH = 0.8f;
	public float HEIGHT = 0.18f;
	
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

	// Creates the button
	public MenuButton() {
		
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
		this.movement = new Vector3f(0.015f, 0.0f, 0.0f);
		
	} // end menuButton
	
} // end class
