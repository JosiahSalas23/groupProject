// This class helps creates a menu object. This class is the parent of all menu objects. It draws the menu object
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=rXh7HBoJa44&t=819s&index=4&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas
package prototypeGUI;

import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class MenuObject {

	public int vaoID;
	public int count;
	public float size = 1.0f;
	
	// MenuObject constructor
	public MenuObject () {
		
		
	} // end GameObject
	
	// draws the menu object
	public void draw() {
		
		glBindVertexArray(this.vaoID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
	} // end draw
	
} // end class
