// This class updates and draws a basic menu for pong
// It also updates and draws different pong levels depending on the state of this class
// Date written/modified: November 2017
// Author: Josiah Salas

package pongGameEngine;

import org.lwjgl.glfw.GLFW;

import io.Window;
import pongGraphicEngine.ShaderManager;
import pongText.Text;

enum Selection {
	menu, easy,medium,hard,multiplayer, test
}

public class Pong {

	ShaderManager shader;
	
	public static Selection mode;
	
	public Button easyButton;
	public Button mediumButton;
	public Button hardButton;
	public Button multiplayerButton;
	
	LevelMenuBackGround menuBackGroundGame;
	LevelEasy pongEasy;
	LevelMedium pongMedium;
	LevelHard pongHard;
	LevelMulti pongMulti;
	
	LevelTest pongTest;
	
	public Pong() {
		
		shader.loadAll();
		
		mode = Selection.menu;
		
		easyButton = new Button();
		mediumButton = new Button();
		hardButton = new Button();
		multiplayerButton = new Button();
		
		easyButton.position.x = -0.4f;
		easyButton.position.y = 0.3f;
		
		mediumButton.position.x = -0.4f;
		mediumButton.position.y = 0.0f;
		
		hardButton.position.x = -0.4f;
		hardButton.position.y = -0.3f;
		
		multiplayerButton.position.x = -0.4f;
		multiplayerButton.position.y = -0.6f;
		
		menuBackGroundGame = new LevelMenuBackGround();
		
	} // end PongMenu
	
	public void update(Window window) {
		
		switch (mode) {
		
			case menu:
				// checks to see if mouse is inside the area of the easyButton
				if ((easyButton.position.x + easyButton.WIDTH) * (window.getWidth()/2) > window.getDX()
						&& window.getDX() > easyButton.position.x * (window.getWidth()/2)
						&& easyButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (easyButton.position.y + easyButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the easy button
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						mode = Selection.easy;
						pongEasy = new LevelEasy();
						
					} // end if
					
				} // end if
				
				// checks to see if mouse is inside the area of the mediumButton
				if ((mediumButton.position.x + mediumButton.WIDTH) * (window.getWidth()/2) > window.getDX()
						&& window.getDX() > mediumButton.position.x * (window.getWidth()/2)
						&& mediumButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (mediumButton.position.y + mediumButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the medium button
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						mode = Selection.medium;
						pongMedium = new LevelMedium();
						
					} // end if
					
				} // end if
				
				// checks to see if mouse is inside the area of the hardButton
				if ((hardButton.position.x + hardButton.WIDTH) * (window.getWidth()/2) > window.getDX()
						&& window.getDX() > hardButton.position.x * (window.getWidth()/2)
						&& hardButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (hardButton.position.y + hardButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the hard button
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						mode = Selection.hard;
						pongHard = new LevelHard();
						
					} // end if
					
				} // end if
				
				// checks to see if mouse is inside the area of the mulitplayerButton
				if ((multiplayerButton.position.x + multiplayerButton.WIDTH) * (window.getWidth()/2) > window.getDX()
						&& window.getDX() > multiplayerButton.position.x * (window.getWidth()/2)
						&& multiplayerButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (multiplayerButton.position.y + multiplayerButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the multiplayer button
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						mode = Selection.multiplayer;
						pongMulti = new LevelMulti();
						
					} // end if
					
				} // end if
				
				// launches the test/demo verison of pong
				if (window.getInput().isKeyDown(GLFW.GLFW_KEY_T)) {
					
					mode = Selection.test;
					pongTest = new LevelTest();
					
				} // end if
				
				menuBackGroundGame.update();
				break;
				
			case easy:
				pongEasy.update(window);
				
				if (pongEasy.quit) {
					
					mode = Selection.menu;
					pongEasy.quit = false;
					menuBackGroundGame = new LevelMenuBackGround();
					
				} // end if
				break;
				
			case medium:
				pongMedium.update(window);
				
				if (pongMedium.quit) {
					
					mode = Selection.menu;
					pongMedium.quit = false;
					menuBackGroundGame = new LevelMenuBackGround();
					
				} // end if
				
				break;
				
			case hard:
				pongHard.update(window);
				
				if (pongHard.quit) {
					
					mode = Selection.menu;
					pongHard.quit = false;
					menuBackGroundGame = new LevelMenuBackGround();
					
				} // end if
				
				break;
				
			case multiplayer:
				pongMulti.update(window);
				
				if (pongMulti.quit) {
					
					mode = Selection.menu;
					pongMulti.quit = false;
					menuBackGroundGame = new LevelMenuBackGround();
					
				} // end if
				
				break;
				
			case test:
				pongTest.update(window);
				
				if (pongTest.quit) {
					
					mode = Selection.menu;
					pongTest.quit = false;
					menuBackGroundGame = new LevelMenuBackGround();
					
				} // end if
				
				break;
		
		} // end switch
		
	} // end update
	
	public void draw() {
		
		switch (mode) {
		
			case menu:
				
				menuBackGroundGame.draw();
				
				// we will be using paddle shaders from pong to draw out buttons
				shader.paddleShader.start();
				shader.paddleShader.setUniform3f("pos", easyButton.position);
				easyButton.draw();
				shader.paddleShader.stop();
				
				shader.paddleShader.start();
				shader.paddleShader.setUniform3f("pos", mediumButton.position);
				mediumButton.draw();
				shader.paddleShader.stop();
				
				shader.paddleShader.start();
				shader.paddleShader.setUniform3f("pos", hardButton.position);
				hardButton.draw();
				shader.paddleShader.stop();
				
				shader.paddleShader.start();
				shader.paddleShader.setUniform3f("pos", multiplayerButton.position);
				multiplayerButton.draw();
				shader.paddleShader.stop();
				
				Text.drawString("Pong", -0.9f, 5.5f, .6f, 9f);
				
				Text.drawString("e", -0.95f, 3.25f, .55f, 9f);
				Text.drawString("a", -0.48f, 4.2f, .38f, 9f);
				Text.drawString("s", 0.45f, 3.25f, .55f, 9f);
				Text.drawString("y", 1.3f, 4.31f, .43f, 9f);
				
				Text.drawString("m e d i u  m", -1.4f, 1.13f, .38f, 7f);
				Text.drawString("h  a  r  d", -1.3f, -2.3f, .35f, 9f);
				Text.drawString("m  u  l  t  i  p  l  a  y  e  r", -3.5f, -6.5f, .3f, 7f);
				break;
			
			case easy:
				pongEasy.draw();
				break;
				
			case medium:
				pongMedium.draw();
				break;
				
			case hard:
				pongHard.draw();
				break;
				
			case multiplayer:
				pongMulti.draw();
				break;
				
			case test:
				pongTest.draw();
				break;
	
		} // end switch
		
	} // end draw
	
} // end class
