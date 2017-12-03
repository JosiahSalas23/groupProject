package world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import entity.Entity;
import entity.Player;
import io.Window;
import render.Camera;
import render.Shader;
import entity.Transform;

/**
 * <h1>World</h1> 
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class World {
	private final int view = 24;
	private byte[] tiles;
	private AABB[] bounding_boxes;
	private int width;
	private int height;
	private int scale;
	private List<Entity> entities;
	
	private Matrix4f world;
	
	/**
	 * World - default constructor
	 */
	public World() {
		width = 64;
		height = 64;
		scale = 16;
		
		tiles = new byte[width * height];
		bounding_boxes = new AABB[width * height];
		
		world = new Matrix4f().setTranslation(new Vector3f(0));
		world.scale(scale);
	}
	/**
	 * constructor
	 * 
	 * @param world - file path to image file
	 * @param camera - Camera object
	 */
	public World(String world, Camera camera) {
		this(world ,camera, 16);
	}
	
	/**
	 * 
	 * @param world - file path to image file
	 * @param camera - Camera object
	 * @param s - scale integer value
	 */
	public World(String world,Camera camera, int s) {
		try {
			System.out.println("world file:" + world);
			BufferedImage tile_sheet = ImageIO.read(new File("./levels/" + world + "/tiles.png"));
			BufferedImage entity_sheet = ImageIO.read(new File("./levels/" + world + "/entities.png"));
			
			width = tile_sheet.getWidth();
			height = tile_sheet.getHeight();
			scale = s;
			
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			
			int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);
			int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width, height, null, 0, width);
			
			
			tiles = new byte[width * height];
			bounding_boxes = new AABB[width * height];
			entities = new ArrayList<Entity>();
			
			Transform transform;
			
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					int red = (colorTileSheet[x + y * width] >> 16) & 0xFF;
					int entity_index = (colorEntitySheet[x + y * width] >> 16) & 0xFF;
					int entity_alpha = (colorEntitySheet[x + y * width] >> 24) & 0xFF;
					
					Tile t;
					try {
						t = Tile.tiles[red];
					}catch(ArrayIndexOutOfBoundsException e) {
						t = null;
					}
					
					if (t != null)
						setTile(t, x, y);
					
					if(entity_alpha > 0) {
						transform = new Transform();
						transform.pos.x = x;
						transform.pos.y = -y;
						switch(entity_index) {
						case 1:
							Player player = new Player(transform);
							entities.add(player);
							camera.getPosition().set(transform.pos.mul(-scale, new Vector3f()) );
							break;
						default:
								
							break;
						}
						
					}
				}
			}
			
			//add player.
			//entities.add(new Player(new Transform()));
			
			
		} catch (IOException e) {
			System.out.println("Cannot open file: \"" + world + "\"");
			e.printStackTrace();
		}
	}
	/**
	 * getWorldMatrix - returns the objects world matrix
	 * @return world matrix4f
	 */
	public Matrix4f getWorldMatrix() {
		return world;
	}
	
	/**
	 * render - renders the world
	 * 
	 * @param render - TileRenderer object
	 * @param shader - Shader object
	 * @param cam - Camera object
	 * @param window - Window object
	 */
	public void render(TileRenderer render, Shader shader, Camera cam, Window window) {
		int posX = ((int)cam.getPosition().x + (window.getWidth()/2)) / (scale * 2);
		int posY = ((int)cam.getPosition().y - (window.getHeight()/2)) / (scale * 2);
		
		for(int i = 0; i < view; i++) {
			for(int j = 0; j < view; j++) {
				Tile t = getTile(i-posX, j+posY);
				if(t != null)
					render.renderTile(t, i-posX, -j-posY, shader, world, cam);
			}
		}
		
		//render each entity.
		for(Entity entity : entities) {
			entity.render(shader, cam, this);
		}
		
	}
	
	/**
	 * Update - updates the world
	 * 
	 * @param delta - the frame rate cap
	 * @param window - Window object
	 * @param camera - Camera object
	 */
	public void update(float delta, Window window, Camera camera) {
		//iterate and update all entities of entity list.
		for(Entity entity : entities) {
			entity.update(delta, window, camera, this);
		}
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).collideWithTiles(this);
			for(int j = i+1; j < entities.size(); j++) {
				entities.get(i).collideWithEntity(entities.get(j));
			}
			entities.get(i).collideWithTiles(this);
		}
		

	}
	
	/**
	 * correctCamera - tracks the camera to the player
	 * 
	 * @param camera - Camera object
	 * @param window - Window object
	 */
	public void correctCamera(Camera camera, Window window) {
		Vector3f pos = camera.getPosition();
		
		int w = -width * scale * 2;
		int h = height * scale * 2;
		
		if(pos.x > -(window.getWidth()/2)+scale) 
			pos.x = -(window.getWidth()/2)+scale;
		if(pos.x < w + (window.getWidth()/2) + scale)
			pos.x = w + (window.getWidth()/2) + scale;
		
		if(pos.y < (window.getHeight()/2)-scale)
			pos.y = (window.getHeight()/2)-scale;
		if(pos.y > h-(window.getHeight()/2)-scale)
			pos.y = h-(window.getHeight()/2)-scale;
	}
	
	/**
	 * setTile - sets the tile at a position x and y
	 * 
	 * @param tile - Tile object
	 * @param x - x position integer value
	 * @param y - y position integer value
	 */
	public void setTile(Tile tile, int x, int y) {
		tiles[x + y * width] = tile.getId();
		if(tile.isSolid()) {
			bounding_boxes[x + y * width] = new AABB(new Vector2f(x*2, -y*2), new Vector2f(1,1));
		}else{
			bounding_boxes[x + y * width] = null;
		}
	}
	
	/**
	 * getTile - get the Tile at position x and y
	 * 
	 * @param x - x position integer value
	 * @param y - y position integer value
	 * @return the Tile object at position x and y
	 */
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * width]];
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	/**
	 * getTileBoundingBox - gets the tile bounding box at position x and y
	 * 
	 * @param x - x position integer value
	 * @param y - y position integer value
	 * @return a AABB object
	 */
	public AABB getTileBoundingBox(int x, int y) {
		try {
			return bounding_boxes[x + y * width];
		}catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * getScale - gets the scale of the world
	 * 
	 * @return scale integer
	 */
	public int getScale() { return scale; }
}
