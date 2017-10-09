// This class creates a game object
package gameEngine;

import graphicEngine.VertexArrayObject;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GameObject {

	public int vaoID;
	public int count;
	public float size = 1.0f;
	
	// GameObject constructor
	public GameObject () {
		
		
	} // end GameObject
	
	// draws the game object
	public void draw() {
		
		glBindVertexArray(this.vaoID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
	} // end draw
	
} // end class
