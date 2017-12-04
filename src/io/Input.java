package io;
import static org.lwjgl.glfw.GLFW.*;

//class to handle all input. Mainly needed for player movement, but can also implement other functions such as
//closing window, fullscreen, changing settiings, etc. For now, only handles functional requirements (player movements)
public class Input {
	private long window;
	
	private boolean keys[];
	
	public Input(long window) {
		this.window = window;
		this.keys = new boolean[GLFW_KEY_LAST];
		for(int i = 0; i < GLFW_KEY_LAST; i++)
			keys[i] = false;
	}
	
	//logic to handle key presses for input.
	
	public boolean isKeyDown(int key) {
		return glfwGetKey(window, key) == 1;
	}
	
	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);
	}
	
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) && keys[key]);
	}
	
	public boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}
	
	public void update() {
		for(int i = 32; i < GLFW_KEY_LAST; i++)
			keys[i] = isKeyDown(i);
	}
}