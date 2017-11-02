/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import io.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.joml.*;
import render.Camera;
import render.Shader;
import Collision.AABB;
/**
 *
 * @author August's PC
 */
public class World {
    private final int veiw = 16;
    private byte[] tiles;
    private AABB[] bounding_boxes;
    private int width;
    private int height;
    private int scale;
    private Matrix4f world;
    
    public World(String world) {
    	try {
			BufferedImage tile_sheet = ImageIO.read(new File("./levels/" + world + "_tiles.png") );
			BufferedImage entity_sheet = ImageIO.read(new File("./levels/" + world + "_entity.png") );
    	
			width = tile_sheet.getWidth();
			height = tile_sheet.getHeight();
			scale = 16;
			
			this.world = new Matrix4f().setTranslation(new Vector3f(0));
			this.world.scale(scale);
			
			int[] colorTileSheet = tile_sheet.getRGB(0,0, tile_sheet.getWidth(), tile_sheet.getHeight(),null, 0, tile_sheet.getWidth() );
    	
			tiles = new byte[width * height];
			bounding_boxes = new AABB[width * height];
			
			for( int y = 0; y < height; y++) {
				for(int x =0; x < width; x++) {
					int red = (colorTileSheet[x+y * width] << 16) & 0XFF;
					Tile t;
					try {
						t = Tile.tiles[red];
					}catch(ArrayIndexOutOfBoundsException e) {
						t = null;
					}
				}
			}
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public World(){
        width = 16;
        height = 16;
        scale = 64;
        tiles = new byte[width * height];
        bounding_boxes = new AABB[width * height];
        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);
    }
    
    public void render(TileRenderer render, Shader shader, Camera cam, Window window){
         int posX = ((int)cam.getPosition().x + (window.getWidth()/2)) / (scale * 2);
         int posY = ((int)cam.getPosition().y - (window.getHeight()/2)) / (scale * 2);
         
         for(int i = 0; i < veiw; i++){
             for(int j = 0; j < veiw; j++){
                 Tile t = getTile(i-posX, j+posY);
                 if(t != null)
                     render.renderTile(t, i-posX, -j-posY, shader, world, cam);
             }
         }
    }
    
    public void correctCamera(Camera camera, Window window){
        Vector3f pos = camera.getPosition();
        
        int w = -width * scale * 2;
        int h = height * scale * 2;
        
        if(pos.x > -(window.getWidth()/2) + scale)
            pos.x = -(window.getWidth()/2) + scale;
        if(pos.x < w + (window.getWidth()/2) + scale)
            pos.x = w +(window.getWidth()/2) + scale;
        
        if(pos.y < (window.getHeight()/2) - scale)
            pos.y = (window.getHeight()/2) - scale;
        if(pos.y > h - (window.getHeight()/2) - scale)
            pos.y = h - (window.getHeight()/2) - scale;
        }
    
    public void setTile(Tile tile, int x, int y){
        tiles[x  + y * width] = tile.getID();
        if(tile.isSolid())
        	bounding_boxes[x+y*width] = new AABB(new Vector2f(x*2, -y*2), new Vector2f(1,1) );
        else {
        	bounding_boxes[x+y*width] = null;
        }
    }
    public Tile getTile(int x, int y){
        try{
            return Tile.tiles[tiles[x + y * width]];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public AABB getTileBoundingBox(int x, int y){
        try{
            return bounding_boxes[x + y * width];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public int getScale(){return scale;}
}
