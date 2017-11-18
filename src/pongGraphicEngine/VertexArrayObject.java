// This class helps us create a game object for pong
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=rXh7HBoJa44&t=819s&index=4&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas

package src.pongGraphicEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static pongUtils.Utilities.*;

public class VertexArrayObject {
	
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	private int vaoID;

	public VertexArrayObject(float[] vertices, byte[] indices) {
		
		createArrayObject(vertices, indices);
		
	} // end VertexArrayObject constructor
	
	public void createArrayObject(float[] vertices, byte[] indices) {
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		createVerticesBuffer(vertices);
		createIndicesBuffer(indices);
		
		glBindVertexArray(0);
		
	} // end createArrayObject
	
	private void createVerticesBuffer(float[] vertices) {
		
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0); 
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	} // end createVerticesBuffer
	
	private void createIndicesBuffer(byte[] indices) {
		
		int ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createByteBuffer(indices), GL_STATIC_DRAW);
		
	}
	
	public int getVaoID() {
		
		return this.vaoID;
		
	} // end getVaoID
	
} // end class
