package render;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * <h1>Camera</h1>
 * The Camera class is the implementation of the 3d camera in openGl
 * 
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Camera {
	private Vector3f position;
	private Matrix4f projection;
	/**
	 * Camera's constructor
	 *  
	 * @param width - Integer window width
	 * @param height - Integer widow height
	 */
	public Camera(int width, int height) {
		position = new Vector3f(0,0,0);
		projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height/2, height/2);
		
	}
	/**
	 * setPosition - set the camera at the given position
	 * 
	 * @param position - a Vector3f( x, y, z )
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	/**
	 * addPosiiton - moves the camera by the given vector
	 * 
	 * @param position - Vector3f(x,y,z)
	 */
	public void addPosition(Vector3f position) {
		this.position.add(position);
	}
	/**
	 * getPosition - returns the current position of the camera
	 * 
	 * @return - Vector3f(x,y,z)
	 */
	public Vector3f getPosition() {
		return position;
	}
	/**
	 * getProjetion - returns the cameras projection matrix
	 * 
	 * @return - Martix4f
	 */
	public Matrix4f getProjection() {
		return projection.translate(position, new Matrix4f());
	}

}
