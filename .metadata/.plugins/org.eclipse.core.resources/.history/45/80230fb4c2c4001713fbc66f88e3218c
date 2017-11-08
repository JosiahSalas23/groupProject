/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

/**
 *
 * @author August's PC
 */
public class Tile {
    public static Tile tiles[] = new Tile[255];
    public static byte NoT = 0;
    
    public static final Tile text_tile = new Tile("/anim/doge_0");
    public static final Tile text_tile2 = new Tile("nebula-space");
    private boolean Solid;
    private byte ID;
    private String Texture;
    
    public Tile(String texture){
    	Solid = false;
        ID = NoT;
        NoT++;
        Texture = texture;
        if(tiles[ID] != null )
            throw new IllegalStateException("Tiles at [" + ID + "] is already being used!");
        tiles[ID] = this;
    }
    
    public byte getID(){
        return ID;
    }
    public String getTexture(){
        return Texture;
    }
    
    public Tile setSolid() {
    	Solid = true;
    	return this;
    }
    public boolean isSolid() { return Solid; }
}
