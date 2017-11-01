// This class creates a window for pong and renders/updates objects in 60FPS.
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=msGMaQbRbQc&index=2&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Another Video Here
// https://www.youtube.com/watch?v=m4Gal_OERns&t=1s&index=9&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
package pongDriver;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.awt.Font;
import java.nio.ByteBuffer;
import java.util.Scanner;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import org.newdawn.slick.TrueTypeFont;

import pongGameEngine.Level;
import pongInput.KeyboardInput;
import pongInput.MouseInput;


public class Driver {

	public boolean isRunning = false;
	public final int WIDTH = 1000;
	public final int HEIGHT = 1000;
	public long window;
	public boolean pause;
	
	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorCallback;
	
	// create paddles and the ball
	Level gamePaddlesAndBall;
	
	
	public void init() {
		
		this.isRunning = true;
		
		if (glfwInit() != true) {
			
			System.err.println("GLFW init failed");
			
		} // end if
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "PONG", NULL, NULL);
		
		if (window == NULL) {
			
			System.err.println("ERROR. Window not created");
			
		} // end if
		
		glfwSetKeyCallback(window, keyCallback = new KeyboardInput());
		//glfwSetCursorPosCallback(window, cursorCallback = new MouseInput());
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		
		glfwSetWindowPos(window, 100, 100);
		
		glfwMakeContextCurrent(window);
		
		glfwShowWindow(window);
		
		GL.createCapabilities();
		//System.out.println("OpenGL: " + glGetString(GL_VERSION));
		
		gamePaddlesAndBall = new Level();
		
	} // end init
	
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gamePaddlesAndBall.draw();
		
		glfwSwapBuffers(window);
		
	} // end render

	
	public void update() {
		
		glfwPollEvents();
		
		gamePaddlesAndBall.update();

	} // end update
	
	public void run() {
		
		init();
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while (isRunning) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1.0) {
				
				update();
				updates++;
				delta--;
				
			} // end if
			
			render();
			
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				
				timer += 1000;
				//System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
				
			} // end if
			
			if (glfwWindowShouldClose(window) == true) {
				
				isRunning = false;
				
			} // end if
			
			// game ends once the score limit is reached
			if ((gamePaddlesAndBall.player1.getScore() == 7) || (gamePaddlesAndBall.player2.getScore() == 7)) {
				
				isRunning = false;
				
			} // end if
			
		} // end while
		
		glfwDestroyWindow(window);
		
	} // end run
	
} // end class Driver
