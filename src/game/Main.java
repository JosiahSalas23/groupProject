
package game;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;


import collision.AABB;
import entity.Entity;
import io.Timer;
import io.Window;
import render.Camera;
import render.Shader;
import world.TileRenderer;
import world.World;

//main Class to initialize everything for game.

public class Main {
	public Main() {
		
		//initialize AABB objects to set up collision.
		AABB box1 = new AABB(new Vector2f(0,0), new Vector2f(1,1));
		AABB box2 = new AABB(new Vector2f(1,0), new Vector2f(1,1));
		
		//print system error in case glfw fails to initialize.
		if(!glfwInit()) {
			System.err.println("GLFW Failed to initialize!");
			System.exit(1);
		}
		
		//initialize game window and set its dimensions.
		Window window = new Window();
		window.setSize(640, 480);
		window.setFullscreen(false);
		window.createWindow("Maze");
		GL.createCapabilities();
		
		
		//initialize camera and render tiles.
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		TileRenderer tiles = new TileRenderer();
		
		//initialize player entity and shader
		Entity.initAsset();
		Shader shader = new Shader("shader");
		
		//initialize world to read from file path "test_level", set up camera.
		World world = new World("test_level", camera);
		
		//commented code that may be used to hard code tiles if needed.
		//world.setTile(Tile.test2, 6, 0);
		//world.setTile(Tile.test2, 6, 0);
		//world.setTile(Tile.test2, 7, 0);
		//world.setTile(Tile.test2, 7, 1);
		//world.setTile(Tile.test2, 7, 2);
		
		//adjust frame cap and fram time.
		double frame_cap = 1.0/60.0;
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
			
			//loop to update frames and correct camera position based on update.
			while(unprocessed >= frame_cap) {
				unprocessed-=frame_cap;
				can_render = true;
	
				world.update((float)frame_cap, window, camera);
				
				world.correctCamera(camera, window);
				
				window.update();
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			//render tiles
			if(can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				world.render(tiles, shader, camera, window);
				window.swapBuffers();
				frames++;
			}
		}
		
		Entity.deleteAsset();
		
		glfwTerminate();
	}


}