package Collision;

import org.joml.Vector2f;

public class AABB {
	private Vector2f Center, Half_extent;
	
	public AABB(Vector2f center, Vector2f half_extent) {
		Center = center;
		Half_extent = half_extent;
		
	}
	public Collision getCollision(AABB box2) {
		Vector2f distance = box2.Center.sub(Center, new Vector2f());
		distance.x = (float)Math.abs(distance.x);
		distance.y = (float)Math.abs(distance.y);
		
		distance.sub(Half_extent.add(box2.Half_extent),new Vector2f()); 
		return new Collision(distance,distance.x < 0 && distance.y < 0);
	}
	
	public void correctPosition(AABB box2, Collision data) {
		Vector2f correction = box2.Center.sub(Center,new Vector2f());
		if(data.Distance.x > data.Distance.x) {
			if(correction.x > 0) {
			Center.add(data.Distance.x, 0);
			}else {
				Center.add(-data.Distance.x, 0);
			}
		}else {
			if(correction.y > 0) {
				Center.add(0, data.Distance.y);
			}else {
				Center.add(0, -data.Distance.y);
			}
		}
	}
	
	public Vector2f getCenter() {return Center;}
	public Vector2f getHalfExtenr() {return Half_extent;}
}
