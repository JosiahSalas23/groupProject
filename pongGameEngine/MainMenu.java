package pongGameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;

import pongInput.KeyboardInput;
import pongText.Text;

public class MainMenu {

	public boolean singlePlayer = false;
	public boolean multiPlayer = false;
	
	public void update() {
		
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_N)) {
			
			   System.out.println("singlePlayer");
			   singlePlayer = true;
				
			} // end if
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_M)) {
			
			   System.out.println("multiPlayer");
			   multiPlayer = true;
				
			} // end if
		
	} // end update
	
	public void draw() {
		
		Text.drawString("press n for singleplayer", -6f, 3f, .48f, .5f);
		Text.drawString("press m for multiplayer", -6f, 0f, .48f, .5f);
		//Text.drawString("press q to quit", -3f, -2.5f, .48f, .5f);
		
		
	} // end draw

} // end MainMenu
