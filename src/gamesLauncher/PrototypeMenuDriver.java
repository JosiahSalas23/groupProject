// This class creates a window and has the main in it.
package gamesLauncher;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;


import pongGameEngine.Level;
import selectionScreenInput.KeyboardInput;

public class PrototypeMenuDriver {

	public boolean isRunning = false;
	public final int WIDTH = 1000;
	public final int HEIGHT = 1000;
	public long window;
	public static boolean pongSelection = false;
	public static boolean mazeSelection = false;
	public static boolean quitSelection = false;
	
	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorCallback;
	
	// create paddles and the ball
	PrototypeMenuGUI menu;
	
	public void init() {
		
		this.isRunning = true;
		
		if (glfwInit() != true) {
			
			System.err.println("GLFW init failed");
			
		} // end if
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		
		window = glfwCreateWindow(WIDTH, HEIGHT, "Launcher", NULL, NULL);
		
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
		
		menu = new PrototypeMenuGUI();
		
	} // end init
	
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		menu.draw();
		
		glfwSwapBuffers(window);
		
	} // end render
	
	public void update() {
		
		menu.update();
		glfwPollEvents();

	} // end update
	
	public void run() {
		
		init();
		quitSelection = false;
		pongSelection = false;
		mazeSelection = false;
		PrototypeMenuGUI.pong = false;
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
				
				quitSelection = true;
				isRunning = false;
				
			} // end if
			
			// game ends once the score limit is reached
			if (PrototypeMenuGUI.pong) {
				
				pongSelection = true;
				isRunning = false;
				
			} // end if
			
			if (PrototypeMenuGUI.maze) {
				
				mazeSelection = true;
				PrototypeMenuGUI.maze = false;
				isRunning = false;
				
			} // end if
			
			if (PrototypeMenuGUI.quit) {
				
				quitSelection = true;
				PrototypeMenuGUI.quit = false;
				isRunning = false;
				
			} // end if
			
		} // end while
		
		glfwDestroyWindow(window);
		
	} // end run
	
} // end class
