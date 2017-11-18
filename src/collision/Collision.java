package src.collision;

import org.joml.Vector2f;

//this class helps with collision resolution
public class Collision {
	public Vector2f distance;
	public boolean isIntersecting;
	
	public Collision(Vector2f distance, boolean intersects) {
		this.distance = distance;
		this.isIntersecting = intersects;
	}

}
