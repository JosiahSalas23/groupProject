// This class draws simple text on the screen that tells the user what game they want to play or if they want to quit out of the program
// for the prototype menu main GUI.
// Date written/modified: November 2017
// Author: Josiah Salas

package prototypeGUI;

import static game.Main.window;

import io.Window;

import static game.Main.camera;
import static game.Main.shader;
//import static org.lwjgl.glfw.GLFW.*;

import pongGraphicEngine.ShaderManager;
import pongText.Text;
import render.Camera;
import render.Shader;
import world.TileRenderer;
import world.World;

public class PrototypeMenuGUI {

	
	public MenuButton pongButton;
	public MenuButton mazeButton;
	public MenuButton quitButton;
	private Window win;
	private Camera cam;
	private Shader shade;
	private World BG;
	private TileRenderer tiles;
	
	ShaderManager shaderManager;
	
	public PrototypeMenuGUI() {
		win = window;
		cam = new Camera(win.getWidth(),win.getHeight());
		shade = shader;
		BG =  new World("Test", cam, 450 );
		tiles = new TileRenderer(250,22);
		
		pongButton = new MenuButton();
		mazeButton = new MenuButton();
		quitButton = new MenuButton();
		
		
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		
		pongButton.position.x = -0.4f;
		pongButton.position.y = 0.0f;
		
		mazeButton.position.x = -0.4f;
		mazeButton.position.y = -0.3f;
		
		quitButton.position.x = -0.4f;
		quitButton.position.y = -0.6f;
		
	} // end PrototypeMenuGUI
	
	
	public void draw() {
		
		BG.render(this.tiles, shader, this.cam, this.win);
		BG.correctCamera(this.cam, this.win);
		
		// we will be using paddle shaders from pong to draw out buttons
		ShaderManager.paddleShader.start();
		ShaderManager.paddleShader.setUniform3f("pos", pongButton.position);
		pongButton.draw();
		ShaderManager.paddleShader.stop();
		
		ShaderManager.paddleShader.start();
		ShaderManager.paddleShader.setUniform3f("pos", mazeButton.position);
		mazeButton.draw();
		ShaderManager.paddleShader.stop();
		
		ShaderManager.paddleShader.start();
		ShaderManager.paddleShader.setUniform3f("pos", quitButton.position);
		quitButton.draw();
		ShaderManager.paddleShader.stop();
		
		// draw text on the buttons
		Text.drawString("arcade", -1.5f, 4.5f, .6f, 9f);
		Text.drawString("p o n g", -1f, 1.6f, .38f, 9f);
		Text.drawString("m a z e", -1f, -1.8f, .4f, 9f);
		Text.drawString("q  u  i  t", -1f, -6.5f, .3f, 9f);
		
	} // end draw

} // end MainMenu