package utils;

public class Vector3f {

	public float x, y, z;
	
	public Vector3f() {
		
		
	} // end default constructor
	
	public Vector3f(float x, float y, float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		
	} // end Vector3f

	public void translate(Vector3f vec) {
		
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
		
	} // end translate
	
} // end class
