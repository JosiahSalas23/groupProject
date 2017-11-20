package src.world;

public class Tile  {
	public static Tile tiles[] = new Tile[255];
	//not = number_of_tiles
	public static byte not = 0;
	
	public static final Tile test_tile = new Tile( "test" );
	public static final Tile test2 = new Tile("checker").setSolid();
	public static final Tile MainBG = new Tile("frame");
	public static final Tile PongBG = new Tile("");
	
	private byte id;
	private boolean solid;
	private String texture;
	
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

	public Tile setSolid() {
		this.solid = true;
		return this;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}
	
}

