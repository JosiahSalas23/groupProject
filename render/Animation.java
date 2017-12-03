/**
 * <h1>Animation</h1>
 * The Animation is the implementation of image sequences that are displayed to the player
 * 
 * <p>
 * The Animation class takes a directory path that stores images, 
 * Each Animation has a array of Textures.
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
package render;

import io.Timer;

public class Animation {

	//contains texture values for animation
	private Texture[] frames;
	
	//pointer value for above array.
	private int pointer;
	
	private double elapsedTime;
	private double currentTime;
	private double lastTime;
	private double fps;
	
	/**
	 * Is the constructor for Animation
	 * 
	 * @param amount used to tell the amount of frames in the given animation 
	 * @param fps is the Frames Per Second for the animation to be played
	 * @param filename is the file path for which all the animation frames are stored
	 */
	public Animation(int amount, int fps, String filename) {
		this.pointer = 0;
		this.elapsedTime = 0;
		this.currentTime = 0;
		this.lastTime = Timer.getTime();
		this.fps = 1.0 / (double) fps;
		
		this.frames = new Texture[amount];
		for (int i = 0; i < amount; i++) {
			this.frames[i] = new Texture(filename + "/" + i + ".png");
		}
	}
	
	/**
	 * bind Is the method that binds the opengl polygon to its texture
	 */
	public void bind() { 
		bind(0);
	}

	/**
	 * bind is the overloaded method that binds the opengl polygon to its texture
	 * it takes a id of the opengl shader being binded
	 * @param sampler is the id of the opengl sampler being used on the opengl polygon
	 */
	public void bind(int sampler) {
		this.currentTime = Timer.getTime();
		this.elapsedTime += currentTime - lastTime;
		
		if(elapsedTime >= fps) {
			elapsedTime = 0;
			pointer++;
		}
		
		if (pointer >= frames.length) pointer = 0;
		
		this.lastTime = currentTime;
		
		frames[pointer].bind(sampler);
	}
	
}
