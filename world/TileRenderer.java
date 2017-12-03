/**
 * <h1>TileRender</h1>
 * TileRenderer is the implements the rendering of a sequence of tile objects
 * 
 * <p>
 * The TileRenderer class is responceable for rendering tiled images.
 * Storing a Hashmap of both Texture objects and Animation objects.
 * Supports both static and animated tiles
 * 
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
package world;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import render.Texture;
import render.Model;
import render.Shader;
import render.Animation;
import render.Camera;

public class TileRenderer {
	private HashMap <String, Animation> tile_Animated;
	private HashMap <String, Texture> tile_textures;
	private Model model;
	private boolean animated;
	private float[] vertices = new float[] {
			-1f, 1f, 0, //TOP LEFT     0
			1f, 1f, 0,  //TOP RIGHT    1
			1f, -1f, 0, //BOTTOM RIGHT 2
			-1f, -1f, 0,//BOTTOM LEFT  3
	};
	
	private float[] texture = new float[] {
			0,0,
			1,0,
			1,1,
			0,1,
	};
	
	private int[] indices = new int[] {
			0,1,2,
			2,3,0
	};
	/**
	 * TileRenderer - default constructor
	 */
	public TileRenderer() {
		animated = false;
		tile_textures = new HashMap<String, Texture>();
		
		 model = new Model(vertices, texture, indices);
		 
		 for(int i = 0; i < Tile.tiles.length; i++ ) {
			 //if statement returns the string of the texture.
			 if(Tile.tiles[i] != null) {
			    if(!tile_textures.containsKey (Tile.tiles[i].getTexture())) {
			        String tex = Tile.tiles[i].getTexture();
				    //tile_textures.put(tex, new Animation(20,20, tex )  );
			        tile_textures.put(tex, new Texture (tex + ".jpg"));
			    }
			 }   
				 
		 }
	}
	/**
	 * TileRenderer - constructor taking two integer values
	 * 
	 * @param frames - the number of frames in the animation
	 * @param fps - the speed the animation is to be played on screen
	 */
	public TileRenderer(int frames, int fps) {
		animated = true;
		tile_Animated = new HashMap<String, Animation>();
	
		 model = new Model(vertices, texture, indices);
		 
		 for(int i = 0; i < Tile.tiles.length; i++ ) {
			 //if statement returns the string of the texture.
			 if(Tile.tiles[i] != null) {
			    if(!tile_Animated.containsKey (Tile.tiles[i].getTexture())) {
			        String tex = Tile.tiles[i].getTexture();
			        System.out.println("tex = " + tex);
			        tile_Animated.put(tex, new Animation(frames,fps, tex )  );
			    }
			 }   
				 
		 }
	}
	/**
	 * 
	 * @param tile - the tile object being passed
	 * @param x - the tile x position in the world
	 * @param y - the tile y position in the world
	 * @param shader - the opengl shader id
	 * @param world - the world matrix used
	 * @param cam - the Camera object being passed
	 */
	public void renderTile(Tile  tile, int x, int y, Shader shader, Matrix4f world, Camera cam) {
		shader.bind();
		if(animated == true) {
			if(tile_Animated.containsKey(tile.getTexture())) {
				//tests to make sure texture is there then bind the texture
				tile_Animated.get(tile.getTexture()).bind(0);
			}
		}
		else {
			if(tile_textures.containsKey(tile.getTexture())) {
				//tests to make sure texture is there then bind the texture
				tile_textures.get(tile.getTexture()).bind(0);
			}
		}
		//position multiplied by 2 for scaling.
		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x*2,  y*2,  0));
		//target is tile position * world *camera projection.
		Matrix4f target = new Matrix4f();
		
		//put tile in correct spot.
		cam.getProjection().mul(world,target);
		target.mul(tile_pos);
		
		
		shader.setUniform("sampler", 0);		
		shader.setUniform("projection", target);
		
		//render the model
		model.render();

   }

}
