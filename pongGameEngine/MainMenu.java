// This class is the main menu interface for the game Pong. This class will be used when the game state is (menu). 
// This class draws simple text asking the user to input a command to
// either play singleplayer or multiplayer. This class has two boolean variables(singePlayer, multiPlayer) that the Driver class will
// use to switch the state of the game to (play) and to determine to run the Level class or LevelMulti class.
// Date written/modified: November 2017
// Author: Josiah Salas

package pongGameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_N;

import game.Main;
import io.Input;
import pongText.Text;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;

public class MainMenu {

	public boolean singlePlayer = false;
	public boolean multiPlayer = false;
	
	public void update() {
		Input KeyboardInput = new Input(Main.window.getWindow());
		
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
