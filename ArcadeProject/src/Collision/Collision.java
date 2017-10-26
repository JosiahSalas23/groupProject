package Collision;

import org.joml.Vector2f;

public class Collision {
	public Vector2f Distance;
	
	public boolean isIntersecting;
	
	public Collision(Vector2f distance, boolean intersect) {
		Distance = distance;
		isIntersecting = intersect;
	}

}
