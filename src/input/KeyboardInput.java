// This class checks to see if a specific key on the keyboard is pressed
package input;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		// TODO Auto-generated method stub
		keys[key] = action != GLFW_RELEASE;
		
	}
	
	// checks to see if the current key is down, if so it returns true
	public static boolean isKeyDown(int keycode) {
		
		return keys[keycode];
		
	} // end isKeyDown

} // end class
