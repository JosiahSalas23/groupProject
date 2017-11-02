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

import pongGameEngine.Level;
import pongGameEngine.LevelMulti;
import pongGameEngine.MainMenu;
import pongGameEngine.Pause;
import pongInput.KeyboardInput;
import pongInput.MouseInput;
import pongText.Text;

enum State {
	
	menu, play, pause
	
}; // end State

public class Driver {

	public boolean isRunning = false;
	public final int WIDTH = 1000;
	public final int HEIGHT = 1000;
	public long window;
	public boolean single = false;
	public boolean multi = false;
	public boolean quit = false;
	
	private GLFWKeyCallback keyCallback;
	private GLFWCursorPosCallback cursorCallback;
	
	public static State gameState = State.menu;
	
	// create paddles and the ball
	Level singlePlayerPong;
	LevelMulti multiPlayerPong;
	Pause pauseScreen;
	MainMenu mainMenu;
	
	
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
		
		singlePlayerPong = new Level();
		multiPlayerPong = new LevelMulti();
		pauseScreen = new Pause();
		mainMenu = new MainMenu();
		
	} // end init
	
	public void render() {
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		switch(gameState) {
		
		   case menu:
			   mainMenu.draw();
			   glfwSwapBuffers(window);
			   break;
			   
		   case play:
			   if (single)
				   singlePlayerPong.draw();
			   
			   if (multi)
				   multiPlayerPong.draw();
			   
			   glfwSwapBuffers(window);
			   break;
			   
		   case pause:
			   if (single)
				   singlePlayerPong.draw();
			   
			   if (multi)
				   multiPlayerPong.draw();
			   pauseScreen.draw();
			   glfwSwapBuffers(window);
			   break;
			   
			   default:
				   // call main menu
				   glfwSwapBuffers(window);
				   break;
		
		
		} // end switch
		
		
	} // end render

	
	public void update() {
		
		glfwPollEvents();
		
		
		switch(gameState) {
		
		   case menu:
			   mainMenu.update();
			   if (mainMenu.singlePlayer) {
				   
				   gameState = State.play;
				   singlePlayerPong = new Level();
				   single = true;
				   multi = false;
				   mainMenu.singlePlayer = false;
				   
			   } // end if
			   
			   if (mainMenu.multiPlayer) {
				   
				   gameState = State.play;
				   multiPlayerPong = new LevelMulti();
				   single = false;
				   multi = true;
				   mainMenu.multiPlayer = false;
				   
			   } // end if
			   
			   break;
			   
		   case play:
			   
			   if (single)
				   singlePlayerPong.update();
			   
			   if (multi)
				   multiPlayerPong.update();
			   
				if (singlePlayerPong.pause == true) {
					
					gameState = State.pause;
					singlePlayerPong.pause = false;
					
				} // end if
				
				if (multiPlayerPong.pause == true) {
					
					gameState = State.pause;
					multiPlayerPong.pause = false;
					
				} // end if
			   break;
			   
		   case pause:
			   // dont update game but still draw objects
			   pauseScreen.update();
			   if (pauseScreen.unPause == true) {
				   
				   gameState = State.play;
				   pauseScreen.unPause = false;
				   
			   } // end if
			   
			   if (pauseScreen.toMenu == true) {
				   
				   gameState = State.menu;
				   pauseScreen.toMenu = false;
				   
			   } // end if
			   break;
			   
		   default:
			   break;
		
		} // end switch
		

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
				
				single = false;
				multi = false;
				gameState = State.menu;
				isRunning = false;
				
			} // end if
			
			// game ends once the score limit is reached
			if ((singlePlayerPong.player1.getScore() == 7) || (singlePlayerPong.player2.getScore() == 7)) {
				
				single = false;
				multi = false;
				gameState = State.menu;
				isRunning = false;
				
			} // end if
			
			if ((multiPlayerPong.player1.getScore() == 7) || (multiPlayerPong.player2.getScore() == 7)) {
				
				single = false;
				multi = false;
				gameState = State.menu;
				isRunning = false;
				
			} // end if
			
		} // end while
		
		glfwDestroyWindow(window);

	} // end run
	
} // end class Driver
