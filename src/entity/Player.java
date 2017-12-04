package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import io.Window;
import render.Animation;
import render.Camera;
import world.World;

//this class makes use of IO, animations, and camera to create a functional player for the Maze game.
public class Player extends Entity {
	public static final int ANIM_SIZE = 2;
	public static final int ANIM_IDLE = 0;
	public static final int ANIM_WALK_LEFT = 1;
	public static final int ANIM_WALK_RIGHT = 1;
	public static final int ANIM_WALK_UP = 1;
	public static final int ANIM_WALK_DOWN = 1;
	
	
	//parameters for new Animation: (num frames to cycle, speed of cycle, filepath).
	//different filepaths are used depending on direction of movement to make player movement more fluid.
	//player animation should stay still when remaining idle.
	Animation walkLeft = new Animation(9, 12, "player/walkingleft");
	Animation walkRight = new Animation(9, 12, "player/walkingright");
	Animation walkUp = new Animation(9, 12, "player/walkingup");
	Animation walkDown = new Animation(9, 12, "player/walkingdown");
	
	Animation idle = new Animation(4, 2, "player/idle");
	
	//initialize player with idle animation.
	public Player(Transform transform) {
		super(ANIM_SIZE, transform);
		setAnimation(ANIM_IDLE, idle);
	}
	
	//update player's position in the world according to key presses.
	//use different animations depending on the direction the character is moving.
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();
		

		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) {
			movement.add(-10*delta, 0);
			
		}
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) 
			movement.add(10*delta, 0);
		
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) 
			movement.add(0, 10*delta);
	
		
		if(window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) 
			movement.add(0, -10*delta);
		
	
		move(movement);
		
		
		
		//logic to choose correct movement frame depending on key press.
		//player will remain idle when no movement keys are pressed.
		
		if(movement.x > 0 && movement.y == 0) {
			setAnimation(ANIM_WALK_RIGHT, walkRight);
			useAnimation(ANIM_WALK_RIGHT);
		}	
		
		else if(movement.x < 0 && movement.y == 0) {
			setAnimation(ANIM_WALK_LEFT, walkLeft);
			useAnimation(ANIM_WALK_LEFT);
		}
		
		else if(movement.x == 0 && movement.y > 0) {
			setAnimation(ANIM_WALK_UP, walkUp);
			useAnimation(ANIM_WALK_UP);
		}
		
		else if(movement.x == 0 && movement.y < 0) {
			setAnimation(ANIM_WALK_DOWN, walkDown);
			useAnimation(ANIM_WALK_DOWN);
		}
		
		else if(movement.x < 0 && movement.y < 0) {
			setAnimation(ANIM_WALK_LEFT, walkLeft);
			useAnimation(ANIM_WALK_LEFT);
		}
		
		else if(movement.x < 0 && movement.y > 0) {
			setAnimation(ANIM_WALK_LEFT, walkLeft);
			useAnimation(ANIM_WALK_LEFT);
		}
		
		else if(movement.x > 0 && movement.y < 0) {
			setAnimation(ANIM_WALK_RIGHT, walkRight);
			useAnimation(ANIM_WALK_RIGHT);
		}
		
		else if(movement.x > 0 && movement.y > 0) {
			setAnimation(ANIM_WALK_RIGHT, walkRight);
			useAnimation(ANIM_WALK_RIGHT);
		}
		
		else {
			useAnimation(ANIM_IDLE);
		}
		
		//adjust camera as position changes.
		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.05f);
	}
}