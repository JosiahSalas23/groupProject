package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseInput extends GLFWCursorPosCallback {

	@Override
	public void invoke(long winodw, double xpos, double ypos) {
		// TODO Auto-generated method stub
		
		System.out.println("X: " + xpos + "\nY: " + ypos);
		
	} // end invoke

} // end class
