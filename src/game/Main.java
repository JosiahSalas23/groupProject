
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
import world.Tile;
import world.TileRenderer;
import world.World;

public class Main {
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
		
		Window window = new Window();
		window.setSize(640, 480);
		window.setFullscreen(false);
		window.createWindow("Game");
		
		GL.createCapabilities();
		
		//blend player texture to fit better with background.
		//glEnable(GL_BLEND);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		
		Camera camera = new Camera(window.getWidth(), window.getHeight());
		glEnable(GL_TEXTURE_2D);
		
		TileRenderer tiles = new TileRenderer();
		
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
		Shader shader = new Shader("shader");
		
		World world = new World("test_level");
		
		
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
			
			while(unprocessed >= frame_cap) {
				unprocessed-=frame_cap;
				can_render = true;
				
				if(window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
				//	glfwSetWindowShouldClose(window.getWindow(), GL_TRUE);
				}
				
				world.update((float)frame_cap, window, camera);
				
				world.correctCamera(camera, window);
				
				window.update();
				
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
				
				world.render(tiles, shader, camera, window);
				
				window.swapBuffers();
				frames++;
			}
		}
		
		Entity.deleteAsset();
		
		glfwTerminate();
	}


}