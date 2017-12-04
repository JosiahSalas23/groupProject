package io;
import static org.lwjgl.glfw.GLFW.*;
/**
 * <h1>Input</h1>
 * 
 * <p>
 * Handles all user input from the mouse and keyboard
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Input {
	private long window;
	
	private boolean keys[];
	
	/**
	 * Input - constructor
	 * 
	 * @param window - Long value - OpenGL window ID
	 */
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		for(int i = 32; i < GLFW_KEY_LAST; i++)
			keys[i] = false;
	}
	/**
	 * isKeyDown - determines if the key with id of key is down
	 * checks current state
	 * 
	 * @param key - integer - OpenGL Key ID
	 * @return - boolean
	 */
	public boolean isKeyDown(int key) {
		return glfwGetKey(window, key) == 1;
	}
	
	/**
	 * isKeyPressed - determines if the key with id of key is down
	 * checks last state
	 * 
	 * @param key - integer - OpenGL Key ID
	 * @return - boolean
	 */
	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);
	}
	/**
	 * isKeyReleased - determines if the key as been released
	 * 
	 * @param key - integer - OpenGL Key ID
	 * @return - boolean
	 */
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) && keys[key]);
	}
	
	/**
	 * isMouseButtonDown - determines of a mouse button is down
	 * 
	 * @param button - integer - OpenGL Key ID
	 * @return - boolean
	 */
	public boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	/**
	 * update - updates the key state array
	 */
	public void update() {
		for(int i = 32; i < GLFW_KEY_LAST; i++)
			keys[i] = isKeyDown(i);
	}
}