//gives all positioning and scaling
package entity;

import org.joml.Matrix4f;
import org.joml.Vector3f;
/**
 * <h1>Transform</h1>
 * stores the 3D position of and object
 * 
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Transform {
	public Vector3f pos;
	public Vector3f scale;
	
	/**
	 * Transform - default constructor
	 */
	public Transform() {
		pos = new Vector3f();
		scale = new Vector3f(1,1,1);
	}
	/**
	 * getProjection - gets the projection matrix applied to the object
	 * 
	 * @param target - Matrix4f - the binded objects matrix
	 * @return - Matrix4f - the applied object matrix
	 */
	public Matrix4f getProjection(Matrix4f target) {
		target.translate(pos);
		target.scale(scale);
		return target;
	}

}
