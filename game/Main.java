
package game;


import static game.Main.camera;
import static game.Main.shader;
import static game.Main.window;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;


import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

//import com.sun.corba.se.impl.ior.ByteBuffer;

import collision.AABB;
import entity.Entity;
import io.Timer;
import io.Window;
import pongGameEngine.Pong;
import render.Camera;
import render.Shader;
import world.Tile;
import world.TileRenderer;
import world.World;
import prototypeGUI.PrototypeMenuGUI;

// keeps track of what state the program is in
enum State {
    MENU, PONG, MAZE
};

public class Main {
	

	boolean quit = false;
	boolean inPlay = false;
	
	TileRenderer tiles;
	public static Camera camera;
	public static Shader shader;
	World world;
	public static Window window;
	
	World PongBG;
	TileRenderer pongTiles;
	
	double frame_cap;
	
	PrototypeMenuGUI menuGUI;
	
	Pong pong;
	
	private static State gameState = State.MENU;
	
	public Main() {	
		
		if(!glfwInit()) {
			System.err.println("GLFW Failed to initialize!");
			System.exit(1);
		}
		
		
		window = new Window();
		window.setSize(640, 640);
		window.setFullscreen(false);
		window.createWindow("Game");
		
		GL.createCapabilities();
		
		menuGUI = new PrototypeMenuGUI();
		
		pong = new Pong();
		
		camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		tiles = new TileRenderer();
		
		Entity.initAsset();
		
		shader = new Shader("shader");
		
		world = new World("test_level", camera);
		
		PongBG = new World("PongMap", camera, 160);
		pongTiles = new TileRenderer(127,24);
		
		frame_cap = 1.0/60.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		while(!window.shouldClose()) {
			
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed+=passed;
			frame_time +=passed;
			
			time = time_2;
			
			while(unprocessed >= frame_cap) {
				unprocessed-=frame_cap;
				can_render = true;
				
				update();
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if(can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				render();
				
				window.swapBuffers();
				frames++;
			}
		}
		
		Entity.deleteAsset();
		
		world = null;
		camera = null;
		window = null;
		tiles = null;
		
		glfwTerminate();
		
	} // end of game constructor
	
	public void update() {
		
		
		if(quit) {
			
			glfwSetWindowShouldClose(window.getWindow(), true);
		
		} // end if
		
		// to exit out of games press q to go back to main menu
		if (inPlay) {
			
			if(window.getInput().isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
					
				gameState = State.MENU;
				
			} // end if
					
		} // end inPlay
			
		switch(gameState) {
		
			// if the menu is on this checks for user input to check what game to run/ or to close
			case MENU:
				
				// checks to see if mouse is inside the area of the pongButton
				if ((menuGUI.pongButton.position.x + menuGUI.pongButton.WIDTH) * (window.getWidth()/2) > window.getDX()
						&& window.getDX() > menuGUI.pongButton.position.x * (window.getWidth()/2)
						&& menuGUI.pongButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (menuGUI.pongButton.position.y + menuGUI.pongButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the pong button set the quitSelection to true and exit out of the loop
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						gameState = State.PONG;
						pong = new Pong();
						glPushAttrib( GL_CURRENT_BIT );
						inPlay = true;
						
					} // end if
					
				} // end if
				
				// checks to see if mouse is inside the area of the mazeButton
				if ((menuGUI.mazeButton.position.x + menuGUI.mazeButton.WIDTH) * (window.getWidth()/2) > window.getDX() 
						&& window.getDX() > menuGUI.mazeButton.position.x * (window.getWidth()/2) 
						&& menuGUI.mazeButton.position.y * (window.getHeight()/2) < (window.getDY())
						&& (menuGUI.mazeButton.position.y + menuGUI.mazeButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the maze button set the mazeSelection to true and exit out of the loop
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						gameState = State.MAZE;
						inPlay = true;
						
					} // end if
					
				} // end if
				
				// checks to see if mouse is inside the area of the quitButton
				if ((menuGUI.quitButton.position.x + menuGUI.quitButton.WIDTH) * (window.getWidth()/2) > window.getDX() 
						&& window.getDX() > menuGUI.quitButton.position.x * (window.getWidth()/2) 
						&& menuGUI.quitButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (menuGUI.quitButton.position.y + menuGUI.quitButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the quit button set the quitSelection to true and exit out of the loop 
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						quit = true;
						
					} // end if
									
				} // end if
				
				window.update();
				break;
				
			case PONG:
				pong.update(window);
				window.update();
				break;
				
			// if maze is playing update it
			case MAZE:
				world.update((float)frame_cap, window, camera);
				world.correctCamera(camera, window);
				window.update();
				break;
			
		} // end switch
		
	} // end update
	
	public void render() {
		
		
		switch(gameState) {
		
			case MENU:
				menuGUI.draw();
				break;
			
			case PONG:
				PongBG.render(this.pongTiles, shader, camera, window);
				PongBG.correctCamera(camera, window);
				pong.draw();
				break;
			
			case MAZE:
				world.render(tiles, shader, camera, window);
				break;
	
		} // end switch	
		
	} // end render
	
	public static void main(String [] args) {
		
		new Main();
		
		
	} // end main


} // end class