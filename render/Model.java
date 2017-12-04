package render;
import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL15.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;


/**
 * <h1>Model</h1>
 * contains the a OpenGL object
 * 
 * <p>
 * takes a array of vertices, texture coordinates and texture indices
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Model {
	private int draw_count;
	private int v_id;
	private int t_id;
	
	private int i_id;
	
	/**
	 * Model constructor
	 * 
	 * @param vertices - Array of floating point values representing 3D coordinates
	 * @param tex_coords - Array of floating point values representing texture coordinates on the model
	 * @param indices - Array of integer values representing texture index on the 3D object
	 */
	public Model(float[] vertices, float[] tex_coords , int[] indices) {
		draw_count = indices.length ;
			
		
		v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,  v_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices) , GL_STATIC_DRAW);
		
		t_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,  t_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_STATIC_DRAW);
		
		i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER,  0);
		
	}
	
	//delete every bit of info 
	/**
	 * finalize - destroy the object
	 * Overloaded from Object
	 */
	protected void finalize() {
		glDeleteBuffers(v_id);
		glDeleteBuffers(t_id);
		glDeleteBuffers(i_id);
		
	}
	
	/**
	 * render - renders the model to the screen in 3D space
	 */
	public void render() {
		//enable and disable texture attribute
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0 );
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
	}
	/**
	 * createBuffer
	 * 
	 * @param data - Floating point array
	 * @return - Floating point buffer array
	 */
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	}

}
