
package src.game;


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

import src.collision.AABB;
import src.entity.Entity;
import src.io.Timer;
import src.io.Window;
import src.render.Camera;
import src.render.Shader;
import src.world.Tile;
import src.world.TileRenderer;
import src.world.World;

import src.pongGameEngine.LevelEasy;
import src.pongGameEngine.LevelMedium;
import src.pongGameEngine.LevelHard;
import src.pongGameEngine.LevelMulti;
import src.prototypeGUI.PrototypeMenuGUI;

// keeps track of what state the program is in
enum State {
    MENU, PONGEASY, PONGMEDIUM, PONGHARD, PONGMULTI, MAZE
};

public class Main {
	

	boolean quit = false;
	boolean inPlay = false;
	
	TileRenderer tiles;
	public static Camera camera;
	public static Shader shader;
	World world;
	public static Window window;
	
	LevelEasy pongEasy;
	LevelMedium pongMedium;
	LevelHard pongHard;
	LevelMulti pongMulti;
	PrototypeMenuGUI menuGUI;
	double frame_cap;
	
	
	
	private static State gameState = State.MENU;
	
	public Main() {
		//Window.setCallbacks();
	
		
		AABB box1 = new AABB(new Vector2f(0,0), new Vector2f(1,1));
		AABB box2 = new AABB(new Vector2f(1,0), new Vector2f(1,1));
		
	//	if(box1.isIntersecting(box2)) {
		//	System.out.println("The boxes are intersecting");
	//	}		
		
		if(!glfwInit()) {
			System.err.println("GLFW Failed to initialize!");
			System.exit(1);
		}
		
		
		window = new Window();
		window.setSize(640, 640);
		window.setFullscreen(false);
		window.createWindow("Game");
		
		GL.createCapabilities();
		
		//blend player texture to fit better with background.
		//glEnable(GL_BLEND);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		pongEasy = new LevelEasy();
		pongMedium = new LevelMedium();
		pongHard = new LevelHard();
		pongMulti = new LevelMulti();
		menuGUI = new PrototypeMenuGUI();
		
		
		camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		tiles = new TileRenderer();
		
		Entity.initAsset();
		
//		float[] vertices = new float[] {
//				-1f, 1f, 0, //TOP LEFT     0
//				1f, 1f, 0,  //TOP RIGHT    1
//				1f, -1f, 0, //BOTTOM RIGHT 2
//				-1f, -1f, 0,//BOTTOM LEFT  3
//		};
//		
//		float[] texture = new float[] {
//				0,0,
//				1,0,
//				1,1,
//				0,1,
//		};
//		
//		int[] indices = new int[] {
//				0,1,2,
//				2,3,0
//		};
//		
//		Model model = new Model(vertices, texture, indices);
		shader = new Shader("shader");
		
		world = new World("test_level",camera);
		/*
		world.setTile(Tile.test2, 6, 0);
		world.setTile(Tile.test2, 7, 0);
		world.setTile(Tile.test2, 8, 0);
		world.setTile(Tile.test2, 9, 0);
		
		
		world.setTile(Tile.test2, 6, 5);
		world.setTile(Tile.test2, 5, 5);
		world.setTile(Tile.test2, 5, 2);
		world.setTile(Tile.test2, 5, 3);
		world.setTile(Tile.test2, 7, 4);
		
		world.setTile(Tile.test2, 4, 2);
		world.setTile(Tile.test2, 4, 3);
		world.setTile(Tile.test2, 4, 4);
		world.setTile(Tile.test2, 4, 5);
		world.setTile(Tile.test2, 10, 0);
		
		world.setTile(Tile.test2, 11, 0);
		world.setTile(Tile.test2, 12, 0);
		world.setTile(Tile.test2, 12, 1);
		world.setTile(Tile.test2, 13, 1);
		world.setTile(Tile.test2, 13, 2);
		
		world.setTile(Tile.test2, 14, 2);
		world.setTile(Tile.test2, 15, 2);
		world.setTile(Tile.test2, 16, 2);
		world.setTile(Tile.test2, 17, 2);
		world.setTile(Tile.test2, 18, 2);
		
		world.setTile(Tile.test2, 18, 3);
		world.setTile(Tile.test2, 18, 4);
		world.setTile(Tile.test2, 18, 5);
		world.setTile(Tile.test2, 18, 6);
		world.setTile(Tile.test2, 18, 7);
		world.setTile(Tile.test2, 18, 8);
		world.setTile(Tile.test2, 18, 9);
		world.setTile(Tile.test2, 18, 10);
		world.setTile(Tile.test2, 18, 11);
		world.setTile(Tile.test2, 18, 12);
		
		world.setTile(Tile.test2, 18, 13);
		world.setTile(Tile.test2, 18, 14);
		world.setTile(Tile.test2, 18, 15);
		world.setTile(Tile.test2, 18, 16);
		world.setTile(Tile.test2, 18, 17);
		world.setTile(Tile.test2, 18, 18);
		world.setTile(Tile.test2, 18, 19);
		world.setTile(Tile.test2, 18, 20);
		world.setTile(Tile.test2, 18, 21);
		world.setTile(Tile.test2, 18, 22);
		
		world.setTile(Tile.test2, 17, 20);
		world.setTile(Tile.test2, 16, 20);
		world.setTile(Tile.test2, 15, 20);
		world.setTile(Tile.test2, 14, 20);
		world.setTile(Tile.test2, 13, 20);
		world.setTile(Tile.test2, 12, 20);
		world.setTile(Tile.test2, 11, 20);
		world.setTile(Tile.test2, 10, 20);
		
		world.setTile(Tile.test2,  9, 20);
		world.setTile(Tile.test2, 9, 19);
		world.setTile(Tile.test2, 9, 18);
		world.setTile(Tile.test2, 9, 17);
		world.setTile(Tile.test2, 9, 16);
		world.setTile(Tile.test2, 9, 15);
		world.setTile(Tile.test2, 11, 20);
		world.setTile(Tile.test2, 10, 20);
		*/
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
				
//				shader.bind();
//				shader.setUniform("sampler", 0);
//				shader.setUniform("projection", camera.getProjection().mul(target));
				//model.render();
				//tex.bind(0);
				
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
		box1 = null;
		box2 = null;
		
		glfwTerminate();
		
	} // end of game constructor
	
	public void update() {
		
		if(quit) {
			
			glfwSetWindowShouldClose(window.getWindow(), true);
		
		} // end if
		
		// to exit out of games press q to go back to main menu
		if (inPlay) {
			
			if(window.getInput().isKeyDown(GLFW.GLFW_KEY_Q)) {
					
				gameState = State.MENU;
				
			} // end if
					
		} // end inPlay
			
		switch(gameState) {
		
			// if the menu is on this checks for user input to check what game to run/ or to close
			case MENU:
				if(window.getInput().isKeyDown(GLFW.GLFW_KEY_M)) {
					
					gameState = State.MAZE;
					inPlay = true;
								
				} // end if
				
				if(window.getInput().isKeyDown(GLFW.GLFW_KEY_P)) {
					
					gameState = State.PONGMULTI;
					pongMulti = new LevelMulti();
					inPlay = true;
				
				} // end if
				
				if(window.getInput().isKeyDown(GLFW.GLFW_KEY_E)) {
					
					gameState = State.PONGEASY;
					pongEasy = new LevelEasy();
					inPlay = true;
					
				
				} // end if
				
				if(window.getInput().isKeyDown(GLFW.GLFW_KEY_T)) {
					
					gameState = State.PONGMEDIUM;
					pongMedium = new LevelMedium();
					inPlay = true;
				
				} // end if
				
				// checks to see if mouse is inside the area of the pongButton
				if ((menuGUI.pongButton.position.x + menuGUI.pongButton.WIDTH) * (window.getWidth()/2) > window.getDX()
						&& window.getDX() > menuGUI.pongButton.position.x * (window.getWidth()/2)
						&& menuGUI.pongButton.position.y * (window.getHeight()/2) < window.getDY()
						&& (menuGUI.pongButton.position.y + menuGUI.pongButton.HEIGHT) * (window.getHeight()/2) > window.getDY()) {
					
					// if the mouse clicks the pong button set the quitSelection to true and exit out of the loop
					if(window.getInput().isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
						
						gameState = State.PONGHARD;
						pongHard = new LevelHard();
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
				
			case PONGEASY:
				pongEasy.update(window);
				window.update();
				break;
				
			case PONGMEDIUM:
				pongMedium.update(window);
				window.update();
				break;
				
			case PONGHARD:
				pongHard.update(window);
				window.update();
				break;
				
			case PONGMULTI:
				pongMulti.update(window);
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
			
		case PONGEASY:
			pongEasy.draw();
			break;
			
		case PONGMEDIUM:
			pongMedium.draw();
			
		case PONGHARD:
			pongHard.draw();
			break;
			
		case PONGMULTI:
			pongMulti.draw();
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