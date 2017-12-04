package gamesLauncher;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;

import selectionScreenInput.KeyboardInput;
import pongText.Text;

public class PrototypeMenuGUI {

	public static boolean pong = false;
	public static boolean maze = false;
	public static boolean quit = false;
	
	public void update() {
		
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_P)) {
			
			   System.out.println("pong");
			   pong = true;
				
			} // end if
		
		KeyboardInput.keys[GLFW_KEY_P] = false;
		
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_M)) {
			
			   System.out.println("maze");
			   maze = true;
				
			} // end if
		KeyboardInput.keys[GLFW_KEY_M] = false;
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_Q)) {
			
			   System.out.println("quit");
			   quit = true;
				
			} // end if
		
	} // end update
	
	public void draw() {
		
		Text.drawString("press p for pong", -6f, 3f, .48f, .5f);
		Text.drawString("press m for maze", -6f, 0f, .48f, .5f);
		Text.drawString("press q to quit", -6f, -3f, .48f, .5f);
		//Text.drawString("press q to quit", -3f, -2.5f, .48f, .5f);
		
		
	} // end draw

} // end MainMenu