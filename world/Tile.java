/**
 * <h1>Tile</h1>
 * Tile creates a tile to be used by entities or world.
 * 
 * <p>
 * The Tile class takes a image file path and stores them in a static array.
 * These tiles support collision with entities.
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
package world;

public class Tile  {
	public static Tile tiles[] = new Tile[255];
	//not = number_of_tiles
	public static byte not = 0;
	
	public static final Tile test_tile = new Tile( "test" );
	public static final Tile test2 = new Tile("checker").setSolid();
	public static final Tile MainBG = new Tile("frame");
	public static final Tile PongBG = new Tile("PongBG");
	
	private byte id;
	private boolean solid;
	private String texture;
	
	/**
	 * The constructor for Tile
	 * 
	 * @param texture - the file path to the image being loaded
	 */
	public Tile( String texture) {
		this.id = not;
		not++;
		this.texture = texture;
		this.solid = false;
		if(tiles[id] != null)
			throw new IllegalStateException("Tiles at ["+id+"] is already being used." );
		tiles[id] = this;
		System.out.println( tiles.toString() );
	}

	/**
	 * setSolid sets the current object solid to entities
	 * 
	 * @return the current object
	 */
	public Tile setSolid() {
		this.solid = true;
		return this;
	}
	/**
	 * isSolid determines if the object is solid
	 * 
	 * @return boolean value
	 */
	public boolean isSolid() {
		return solid;
	}
	/**
	 * setId returns the id of the current object
	 * 
	 * @return byte 
	 */
	public byte getId() {
		return id;
	}
	/**
	 * setId set the idea of the current object to id
	 * 
	 * @param id - the byte ID value
	 */
	public void setId(byte id) {
		this.id = id;
	}

	/**
	 * getTexture returns the texture file path
	 * 
	 * @return - file path string
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * setTexture - sets the image path of the current object
	 * 
	 * @param texture - file path string
	 */
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
}

