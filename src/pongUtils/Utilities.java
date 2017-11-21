// This class handles utilities for pong
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=rXh7HBoJa44&t=819s&index=4&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Another video
// https://www.youtube.com/watch?v=_rjSel-6K38&index=7&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas

package src.pongUtils;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Utilities {

	// Load in the shader code, assign it to an object and compile and return the Id of the shader
	
	public static int loadShader(String filePath, int type) {
		
		StringBuilder result = new StringBuilder();
		
		// Find the file path of the shader and create it as a one byte string.
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				
				result.append(buffer);
				result.append("\n");
				
			} // end while
			
		} catch (IOException e) {
			
			System.err.println(e);
		
		} // end catch
		
		// creates a shader object and assign it to the source code. Then compile it
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, result.toString());
		glCompileShader(shaderID);
		
		if ((glGetShaderi(shaderID, GL_COMPILE_STATUS)) == GL_FALSE) {
			
			System.err.println(glGetShaderInfoLog(shaderID, 500));
			System.err.println("Shader was unable to compile");
			
		} // end if
		
		return shaderID;
		
	} // end loadShader
	
	public static FloatBuffer createFloatBuffer(float[] data) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	} // end FloataBuffer
	
	public static ByteBuffer createByteBuffer(byte[] data) {
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
		
	} // end createByteBuffer

}
