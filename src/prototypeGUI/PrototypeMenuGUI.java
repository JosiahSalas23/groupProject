// This class draws simple text on the screen that tells the user what game they want to play or if they want to quit out of the program
// for the prototype menu main GUI.
// Date written/modified: November 2017
// Author: Josiah Salas

package prototypeGUI;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;

import pongGraphicEngine.ShaderManager;
import pongText.Text;

public class PrototypeMenuGUI {

	
	public MenuButton pongButton;
	public MenuButton mazeButton;
	public MenuButton quitButton;
	
	ShaderManager shaderManager;
	
	public PrototypeMenuGUI() {
		
		pongButton = new MenuButton();
		mazeButton = new MenuButton();
		quitButton = new MenuButton();
		
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		
		pongButton.position.x = -0.4f;
		pongButton.position.y = 0.0f;
		
		mazeButton.position.x = -0.4f;
		mazeButton.position.y = -0.3f;
		
		quitButton.position.x = -0.4f;
		quitButton.position.y = -0.6f;
		
	} // end PrototypeMenuGUI
	
	
	public void draw() {
		
		Text.drawString("arcade", -1.5f, 4.5f, .6f, 9f);
		
		// we will be using paddle shaders from pong to draw out buttons
		shaderManager.paddleShader.start();
		shaderManager.paddleShader.setUniform3f("pos", pongButton.position);
		pongButton.draw();
		shaderManager.paddleShader.stop();
		
		shaderManager.paddleShader.start();
		shaderManager.paddleShader.setUniform3f("pos", mazeButton.position);
		mazeButton.draw();
		shaderManager.paddleShader.stop();
		
		shaderManager.paddleShader.start();
		shaderManager.paddleShader.setUniform3f("pos", quitButton.position);
		quitButton.draw();
		shaderManager.paddleShader.stop();
		
		// draw text on the buttons
		Text.drawString("p o n g", -1f, 1.6f, .38f, 9f);
		Text.drawString("m a z e", -1f, -1.8f, .4f, 9f);
		Text.drawString("q  u  i  t", -1f, -6.5f, .3f, 9f);
		
	} // end draw

} // end MainMenu