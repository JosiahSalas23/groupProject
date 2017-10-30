/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import io.Window;

import org.joml.Vector2f;
import org.joml.Vector3f;

import Collision.AABB;
import Collision.Collision;

import static org.lwjgl.glfw.GLFW.*;
import render.Camera;
import render.Model;
import render.Shader;
import render.Texture;
import world.World;

/**
 *
 * @author August's PC
 */
public class Player {
 
    private Model model;
    private Texture tex;
    private AABB bounding_box;
    //private Animation animation
    private Transform transform;
    
    public Player(float[] verts, float[] texture, int[] indices, Texture textures) {
    	model = new Model(verts, texture, indices);
    	tex = textures;
        transform = new Transform();
        transform.scale = new Vector3f(64,64,1);
    }
    
    public Player(){
        float[] vertices = new float[]{
            -1f, 1f,0,
             1f, 1f,0,
             1f,-1f,0,
            -1f,-1f,0
            };
        
        float[] texture = new float[]{
            0,0,
            1,0,
            1,1,
            0,1
        };
        
        int[] indices = new int[]{
            0,1,2,
            2,3,0
        };
        
        model = new Model(vertices,texture,indices);
        tex = new Texture("doge_1.jpg");
        //text = new Animation(1,15, "doge");
        transform = new Transform();
        transform.scale = new Vector3f(64,64,1);
        bounding_box = new AABB(new Vector2f(transform.pos.x,transform.pos.y), new Vector2f(1,1)  );
    }
    public void setTexture(Texture nTex){
        tex = nTex;
    }
    public void setModel(float[] verts, float[] texture, int[] indices){
        model = new Model(verts, texture, indices);
    }
    
    public void update(float delta, Window window, Camera camera, World world){
                if(window.getInput().isKeyDown(GLFW_KEY_A)){
                    transform.pos.add(new Vector3f(-10 * delta,0,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_D)){
                    transform.pos.add(new Vector3f(10 * delta,0,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_W)){
                    transform.pos.add(new Vector3f(0,10 * delta,0));
                }
                if(window.getInput().isKeyDown(GLFW_KEY_S)){
                    transform.pos.add(new Vector3f(0,-10 * delta,0));
                }
                
                bounding_box.getCenter().set(transform.pos.x, transform.pos.y);
                
                AABB[] boxes = new AABB[25];
                
                for(int i = 0; i < 5; i++) {
                	for(int j =0; j < 5; j++) {
                		boxes[i + j * 5] = world.getTileBoundingBox( 
                				(int)( ( ( ( transform.pos.x)/2 ) + 0.5f ) - (5/2)) + i ,
                				(int)( ( ( ( -transform.pos.y)/2 ) + 0.5f ) - (5/2)) + j );
                	}
                }
                
                AABB box = null;
                for(int i =0; i < boxes.length; i++) {
                	if(boxes[i] != null) {
                		if(box == null) { box = boxes[i];}
                		
                		Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                		Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
                		
                		if(length1.lengthSquared() > length2.lengthSquared()) {
                			box = boxes[i];
                		}
                	}
                }
                if(box != null) {
                	Collision data = bounding_box.getCollision(box);
	                if(data.isIntersecting) {
	                	bounding_box.correctPosition(box, data);
	                	transform.pos.set(bounding_box.getCenter(),0);
	                }
	                
	                for(int i =0; i < boxes.length; i++) {
	                	if(boxes[i] == null) {
	                		if(box == null)
	                			box = boxes[i];
	                		
	                		Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
	                		Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
	                		
	                		if(length1.lengthSquared() > length2.lengthSquared()) {
	                			box = boxes[i];
	                		}
	                	}
	                }
	               	data = bounding_box.getCollision(box);
	                if(data.isIntersecting) {
	                	bounding_box.correctPosition(box, data);
	                	transform.pos.set(bounding_box.getCenter(),0);
	                }
                }
                camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()) , 0.1f );
                //camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f() ));
    }
    
    public void render(Shader shader, Camera camera){
        shader.bind();
        shader.setUniform("samper", 0);
        shader.setUniform("projection",transform.getProjection( camera.getProjection() ) );
        tex.bind(0);
        model.render();
    }
}
