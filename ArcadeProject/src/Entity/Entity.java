package Entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import Collision.AABB;
import Collision.Collision;
import io.Window;
import render.Animation;
import render.Camera;
import render.Model;
import render.Shader;
import world.World;

public class Entity {

    private static ArrayList<Model> models = new ArrayList<Model>();
    private AABB bounding_box;
    private Animation texture;
    private Transform transform;
    private int Model_ID;
    
    public Entity(Animation animation, Transform transform, int ID) {
    	texture = animation;
        this.transform = transform;
        Model_ID = ID;
        bounding_box = new AABB(new Vector2f( transform.pos.x, transform.pos.y ), new Vector2f(transform.scale.x ,transform.scale.y) );
    }

    public static void addModel(float[] verts, float[] texture, int[] indices){
    	models.add( new Model(verts, texture, indices) );
    }
    public static void addModel(Model model) {
    	models.add(model);
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
    
    public Transform getTransform() {return transform;}
    
    public void setTransform(Transform trans) {
    	transform = trans;
    }
    
    public void render(Shader shader, Camera camera, World world){
        Matrix4f target = camera.getProjection();
        target.mul(world.getWorldMatrix());
    	shader.bind();
        shader.setUniform("samper", 0);
        shader.setUniform("projection",transform.getProjection( target ) );
        texture.bind();
        models.get(Model_ID).render();
    }
    /*
    public static void IniAsset() {
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
        
        models.add(new Model(vertices,texture,indices));
    }

    public static void deleteAsset() {
    	model = null;
    }
    */
}


