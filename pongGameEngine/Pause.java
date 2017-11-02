package pongGameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;

import pongInput.KeyboardInput;
import pongText.Text;

public class Pause {

	public boolean unPause = false;
	public boolean toMenu = false;
	
	public void update() {
		
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_R)) {
			
			   System.out.println("Game unpause");
			   unPause = true;
				
			} // end if
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_Q)) {
			
			   System.out.println("to Menu");
			   toMenu = true;
				
			} // end if
		
	} // end update
	
	public void draw() {
		
		Text.drawString("paused", -1.5f, 3f, .5f, .5f);
		Text.drawString("press r to unpause", -4.5f, 0f, .48f, .5f);
		Text.drawString("q to menu", -3f, -2.5f, .48f, .5f);
		
		
	} // end draw
	
} // end pause