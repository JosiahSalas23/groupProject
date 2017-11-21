package src.world;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import src.render.Texture;
import src.render.Model;
import src.render.Shader;
import src.render.Animation;
import src.render.Camera;

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
