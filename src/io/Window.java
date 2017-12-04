package io;
import static org.lwjgl.glfw.GLFW.*;


import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

//class to initialize and set up the game window to desired specifications.
public class Window {
	private long window;
	
	private int width, height;
	private boolean fullscreen;
	
	private Input input;
	
	public static void setCallbacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	public Window() {
		setSize(640, 480);
		setFullscreen(false);
	}

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
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public void update() {
		//update the window
		//update before pollEvents 
		input.update();
		glfwPollEvents();
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public boolean isFullscreen() { return fullscreen; }
	public long getWindow() { return window; }
	public Input getInput() { return input; }
}