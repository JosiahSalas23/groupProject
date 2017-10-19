package myGame;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWVidMode; 
import org.lwjgl.opengl.*;

public class Main {
	
	public static void main(String[] args) {
		if (!glfwInit()) {
			throw new IllegalStateException("failed to initizalize GLFW");
		}
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		long window = glfwCreateWindow(640, 480, "My Program", 0, 0);
		
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (videoMode.width() - 640) / 2, (videoMode.height() - 480) / 2);
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		GL.createCapabilities();
		float v1_x = -.05f;
		float v2_x = .05f;
		float v3_x = .05f;
		float v4_x = -.05f;
		float v1_y = .05f;
		float v2_y = .05f;
		float v3_y = -.05f;
		float v4_y = -.05f;
		while(!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBegin(GL_QUADS);
			glColor4f(1,1,1,0);
			
			glVertex2f(v1_x, v1_y);
			glColor4f(0,0,1,0);

			glVertex2f(v2_x, v2_y);
			glColor4f(1,1,1,0);

			glVertex2f(v3_x, v3_y);
			glColor4f(0,0,1,0);

			glVertex2f(v4_x, v4_y);
			
			glEnd();
			
			glfwSwapBuffers(window);

			
			if(glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
				v1_y += .005f;
				v2_y += .005f;
				v3_y += .005f;
				v4_y += .005f;
			}
			if(glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
				v1_x += .005f;
				v2_x += .005f;
				v3_x += .005f;
				v4_x += .005f;
			}
			if(glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
				v1_x -= .005f;
				v2_x -= .005f;
				v3_x -= .005f;
				v4_x -= .005f;
			}
			if(glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
				v1_y -= .005f;
				v2_y -= .005f;
				v3_y -= .005f;
				v4_y -= .005f;
			}
		}
		
		glfwTerminate();
		
	}
	
}
