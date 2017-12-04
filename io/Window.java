package io;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

/**
 * <h1>Window</h1>
 * 
 * <p>
 * Handles the application window
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Window {
	private static long window;
	
	private int width, height;
	private boolean fullscreen;
	
	private Input input;
	
	public static double x;
	public static double y;
	
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	/**
	 * Window - default constructor
	 */
	public Window() {
		setSize(640, 640);
		setFullscreen(false);
	}
	
	/**
	 * createWindow - creates a new system window
	 * 
	 * @param title - string - window title
	 */
	public void createWindow(String title) {
		window = glfwCreateWindow(
				width, 
				height, 
				title, 
				//test to see if full screen is true: pass primaryMonitor, else, pass 0
				fullscreen ? glfwGetPrimaryMonitor() : 0, 
				0);
		
		if(window == 0)
			throw new IllegalStateException("Failed to create window!");
		
		if(!fullscreen) {
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, 
					(vid.width()-width)/2, 
					(vid.height()-height)/2);
		}
		
		glfwShowWindow(window);
		
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
	}
	/**
	 * deturmines if the current window should close
	 * 
	 * @return - boolean
	 */
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	/**
	 * swapBuffers - swaps the window displaybuffers
	 */
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	/**
	 * setSize - sets the size of the window to given values
	 * 
	 * @param width - integer
	 * @param height - integer
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	/**
	 * setFullscreen - sets the window into fullscreen mode
	 * 
	 * @param fullscreen - boolean
	 */
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	/**
	 * cursorPosition - keeps track of the cursors position in the window
	 * 
	 */
	public static void cursorPosition() {
		
		DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, xBuffer, yBuffer);
		x = xBuffer.get(0) - (640/2);
		y = yBuffer.get(0) - (640/2);
		y = y * -1;

	}
	/**
	 * update - updates the window
	 */
	public void update() {
		//update the window
		//update before pollEvents
		cursorPosition();
		input.update();
		glfwPollEvents();
		
	}
	
	
	//getter methods
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean isFullscreen() { return fullscreen; }
	public long getWindow() { return window; }
	public Input getInput() { return input; }
	public double getDX() { return x; }
	public double getDY() { return y; }
}