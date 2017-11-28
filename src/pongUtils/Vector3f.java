// This class positions our pong game objects and moves the ball
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=2aQ02MDbaaQ&index=5&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// another video here
// https://www.youtube.com/watch?v=4CWXcWTsxog&index=10&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: October 2017
// Author: Josiah Salas

package pongUtils;

public class Vector3f {

	public float x, y, z;
	
	public Vector3f() {
		
		
	} // end default constructor
	
	public Vector3f(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		
	} // end Vector3f

	public void translate(Vector3f vec) {
		
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		
	} // end translate
	
} // end class
