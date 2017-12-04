package render;
import org.joml.Matrix4f;
import org.joml.Vector3f;

//class to set up the game camera, which adjusts based on player's position. This allows the screen to 
//stay centered on the player.
public class Camera {
	private Vector3f position;
	private Matrix4f projection;
	
	public Camera(int width, int height) {
		position = new Vector3f(0,0,0);
		projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height/2, height/2);
		
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void addPosition(Vector3f position) {
		this.position.add(position);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Matrix4f getProjection() {
		return projection.translate(position, new Matrix4f());
	}

}
