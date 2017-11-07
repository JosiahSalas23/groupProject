/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;

import org.joml.Vector3f;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

/**
 *
 * @author August's PC
 */
public class GUI extends Entity{
	
	public static ArrayList<GUI> GUIs = new ArrayList<GUI>();
	
	public GUI(String file,Transform transform, int ID) {
		super(new Animation(1,1,file), transform, ID);
	}
	
	public void update(Window window, Camera camera, World world) {
		Vector3f pos = transform.pos;
		
		int w = -world.getWidth() * world.getScale() * 2;
		int h = world.getHeight() * world.getScale() * 2;
		
        if(pos.x > -(window.getWidth()/2) + world.getScale() )
            pos.x = -(window.getWidth()/2) + world.getScale();
        if(pos.x < w + (window.getWidth()/2) + world.getScale())
            pos.x = w +(window.getWidth()/2) + world.getScale();
        
        if(pos.y < (window.getHeight()/2) - world.getScale())
            pos.y = (window.getHeight()/2) - world.getScale();
        if(pos.y > h - (window.getHeight()/2) - world.getScale())
            pos.y = h - (window.getHeight()/2) - world.getScale();
		
        
	}
	
	public static void updateAll(Window window, Camera camera, World world) {
		
		for(int i = 0; i < GUIs.size(); i++) {
			GUIs.get(i).update(window, camera, world);
		}
		
	}
}
