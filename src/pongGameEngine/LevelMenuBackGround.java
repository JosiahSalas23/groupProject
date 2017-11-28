// This class updates and draws a background for the pong menu. It has pong playing in the background.
// Date written/modified: Novemeber 2017
// Author: Josiah Salas
package pongGameEngine;

import org.lwjgl.glfw.GLFW;
import pongGraphicEngine.ShaderManager;

public class LevelMenuBackGround {

	public Paddle player1;
	public Paddle player2;
	
	public Ball ball;
	
	public ShaderManager shaderManager;
	
	public LevelMenuBackGround() {
		
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		
		player1 = new Paddle();
		player2 = new Paddle();
		
		ball = new Ball();
		
		player1.position.x = -0.8f;
		player2.position.x = 0.8f;
		
	} // end LevelMenuBackGround
	
	public void checkCollision(Paddle paddle) {
		
		// checks to see if there is no space between the ball and the paddle using AABB (Allined-Axis-Bounding-Box) collision algorithm
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			ballCollisionLogic();
			
		} // end if
		
	} // end checkCollision
	
	// this function will be called once a paddle makes contact with a ball. It moves the ball
	public void ballCollisionLogic() {
		
		// moves the ball in the opposite direction
		ball.movement.x *= -1.05f;
		
		if (ball.movement.y == 0.0f)
			ball.movement.y = 0.01f;
			
		int upOrDown;
		upOrDown = (int)(Math.random() * 10 + 0);
		
		// decides if it should move the ball up or down randomly if a paddle hits the ball
		if (upOrDown >= 5) {
			
			ball.movement.y *= -1.00f; 
			
		} // end if
		
		ball.movement.y *= 1.03f;
		
	} // end ballCollisionLogic
	
	// checks to see if a player had scored
	public void checkGoal() {
		
		// if the ball touches player two's side then player one scores. give the score and reset playing field.
		if (ball.position.x + ball.WIDTH >= 1.0f) {
			
			player1.score();
			resetPlayingField();
			
		} // end if
		
		// if the ball touches player one's side then player two scores. give the score and reset playing field.
		if (ball.position.x <= -1.0f) {
			
			player2.score();
			resetPlayingField();
			ball.movement.x *= -1.0f;
			
		} // end if
		
	} // end goal
	
	// resets the playing field after a goal
	public void resetPlayingField() {
		
		player1.position.y = 0.0f;
		player2.position.y = 0.0f;
		ball = new Ball();
		
	} // end resetPlayingField
	
	public void update() {
		
		ball.update();
		
		// AI logic for player 1
		if (ball.movement.y > 0 && ball.position.y > player1.position.y + 0.125f && ball.movement.x < 0) {
			
			player1.moveAIUpHard();
			
		} // end if
		
		if (ball.movement.y < 0 && ball.position.y < player1.position.y + 0.125f && ball.movement.x < 0) {
			
			player1.moveAIDownHard();
			
		} // end if
		
		// AI logic for player 2
		if (ball.movement.y > 0 && ball.position.y > player2.position.y + 0.125f && ball.movement.x > 0) {
			
			player2.moveAIUpHard();
			
		} // end if
		
		if (ball.movement.y < 0 && ball.position.y < player2.position.y + 0.125f && ball.movement.x > 0) {
			
			player2.moveAIDownHard();
			
		} // end if
		
		if (ball.movement.x < 0)
			checkCollision(player1);
		
		if (ball.movement.x > 0)
			checkCollision(player2);
		
		
		
		checkGoal();
		
	} // end update
	
	public void draw() {
		
		shaderManager.whitePaddle.start();
		shaderManager.whitePaddle.setUniform3f("pos", player1.position);
		player1.draw();
		shaderManager.whitePaddle.stop();
		
		shaderManager.whitePaddle.start();
		shaderManager.whitePaddle.setUniform3f("pos", player2.position);
		player2.draw();
		shaderManager.whitePaddle.stop();
		
		shaderManager.whiteBall.start();
		shaderManager.whiteBall.setUniform3f("pos", ball.position);
		ball.draw();
		shaderManager.whiteBall.stop();
		
	} // end draw
	
} // end class
