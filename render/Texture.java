package render;
import   java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL13.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

/**
 * <h1>Texture</h1>
 * The Texture class reads and stores an image file at a given location
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Texture {
	private int id;
	private int width;
	private int height;
	/**
	 * Texture constructor
	 * Loads and stores an image to be binded to a 2d object
	 * 
	 * @param filename - string file path
	 */
	public Texture(String filename) {
		BufferedImage bi;
		try{
			bi  =  ImageIO.read(new File("./textures/"+filename));
			width = bi.getWidth();
			height = bi.getHeight();
			
			//int array width and height are parameters for size of image
			int[] pixels_raw = new int[width * height * 4 ];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					int pixel = pixels_raw[i*width + j];
					pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
					pixels.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
					pixels.put((byte) (pixel  & 0xFF)); //BLUE
					pixels.put((byte) ((pixel >> 24) & 0xFF));//ALPHA
				}
			}
			//openGL wants flipped buffer.
			pixels.flip();
			
			id = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D,  0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		} catch(IOException e) {
			System.out.println("Cannot Open file: " + filename);
			//e.printStackTrace();
		}
		
	}
	
	//override existing object finalize
	/**
	 * Finalize - destroys the Texture object
	 */
	protected void finalize()  throws Throwable {
		glDeleteTextures(id);
		super.finalize();
	}
	
	/**
	 * bind - binds the texture to a OpenGL 2D object
	 * 
	 * @param sampler - integer OpenGL sampler ID
	 */
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
		//bind our texture to the first sampler
		glActiveTexture(GL_TEXTURE0 + sampler);
		glBindTexture(GL_TEXTURE_2D, id);
		}
	}

}
