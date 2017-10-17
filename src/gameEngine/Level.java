// This class purpose is to initialize the ball and each players paddle, as well as update and draw them.
// This class will help keep the code clean in our driver class because we don't have to call each individual function.
package gameEngine;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.opengl.GL11.*;

import graphicEngine.ShaderManager;
import input.KeyboardInput;

public class Level {

	public Paddle player1;
	public Paddle player2;
	
	Ball ball;
	
	ShaderManager shaderManager;
	
	public boolean pause = false;
	
	public int hitCount = 0;
	
	
	// Create both players paddles and a ball
	public Level() {
		
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		
		player1 = new Paddle();
		player2 = new Paddle();
		
		ball = new Ball();
		
		player1.position.x = -0.8f;
		player2.position.x = 0.8f;
		
	} // end Level Constructor
	
	// Checks to see if the paddle made contact with the ball
	public void checkCollision(Paddle paddle) {
		
		int upOrDown;
		
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			//System.out.println("Collision detected");
			
			hitCount++;
			
			// moves the ball in the opposite direction
			ball.movement.x *= -1.05f;
			
			if (ball.movement.y == 0.0f)
				ball.movement.y = 0.01f;
				
			upOrDown = (int)(Math.random() * 10 + 0);
			
			// decides if it should move the ball up or down randomly if a paddle hits the ball
			if (upOrDown >= 5) {
				
				ball.movement.y *= -1.00f; 
				
			}
			
			ball.movement.y *= 1.07f;
			
		} // end if
		
	} // end checkCollision;
	
	// checks to see if a player had scored
	public void checkGoal() {
		
		// if the ball touches player two's side then player one scores
		if (ball.position.x + ball.WIDTH >= 1.0f) {
			
			player1.score();
			System.out.println("Player 1 scored!");
			System.out.println("Score: " + player1.getScore() + " : " + player2.getScore());
			
			if((player1.getScore()) == 7) {
				
				System.out.println("Player 1 Wins");
				System.out.println("Final Score: " + player1.getScore() + " : " + player2.getScore());
				
			} // end if
			
			player1.position.y = 0.0f;
			player2.position.y = 0.0f;
			hitCount = 0;
			ball = new Ball();
			
		} // end if
		
		// if the ball touches player one's side then player two scores
		if (ball.position.x <= -1.0f) {
			
			player2.score();
			System.out.println("Player 2 scored!");
			System.out.println("Score: " + player1.getScore() + " : " + player2.getScore());
			
			if((player2.getScore()) == 7) {
				
				System.out.println("Player 2 Wins");
				System.out.println("Final Score: " + player1.getScore() + " : " + player2.getScore());
				
			} // end if
			player1.position.y = 0.0f;
			player2.position.y = 0.0f;
			hitCount = 0;
			ball = new Ball();
			ball.movement.x *= -1.0f;
			
		} // end if
		
	} // end goal
	
	// update both players paddles and the ball
	public void update() {
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_P)) {
			
			   System.out.println("Game pause");
			   pause = true;
				
			} // end if
		
		if (KeyboardInput.isKeyDown(GLFW_KEY_O)) {
			
			   System.out.println("Game unpause");
			   pause = false;
				
			} // end if
		
		// if the game is not paused, update the game logic
		if (pause == false) {
			
			checkCollision(player1);
			checkCollision(player2);
			checkGoal();
			
			player1.update("player1");
			ball.update();
			//player2.update("player2");
			if (ball.movement.y > 0 && ball.position.y > player2.position.y && ball.movement.x > 0) {
				
				player2.moveAIUp();
				
			} // end if
			
			if (ball.movement.y < 0 && ball.position.y < player2.position.y && ball.movement.x > 0) {
				
				player2.moveAIDown();
				
			} // end if			
			
		} // end if
		
	} // end update
	
	// draw each players paddles and the ball
	public void draw() {
		
		shaderManager.paddleShader.start();
		shaderManager.paddleShader.setUniform3f("pos", player1.position);
		player1.draw();
		shaderManager.paddleShader.stop();
		
		shaderManager.paddleShader.start();
		shaderManager.paddleShader.setUniform3f("pos", player2.position);
		player2.draw();
		shaderManager.paddleShader.stop();
		
		if (hitCount > 30) {
			
			shaderManager.ballShader5.start();
			shaderManager.ballShader5.setUniform3f("pos", ball.position);
			ball.draw();
			shaderManager.ballShader5.stop();
			
		} // end if
		
		if (hitCount >= 20 && hitCount <= 30) {
			
			shaderManager.ballShader4.start();
			shaderManager.ballShader4.setUniform3f("pos", ball.position);
			ball.draw();
			shaderManager.ballShader4.stop();
			
		} // end if
		
		if (hitCount >= 10 && hitCount < 20) {
			
			shaderManager.ballShader3.start();
			shaderManager.ballShader3.setUniform3f("pos", ball.position);
			ball.draw();
			shaderManager.ballShader3.stop();
			
		} // end if
		
		if (hitCount >= 5 && hitCount < 10) {
			
			shaderManager.ballShader2.start();
			shaderManager.ballShader2.setUniform3f("pos", ball.position);
			ball.draw();
			shaderManager.ballShader2.stop();
			
		} // end if
		
		if (hitCount >= 0 && hitCount < 5) {
			
			shaderManager.ballShader1.start();
			shaderManager.ballShader1.setUniform3f("pos", ball.position);
			ball.draw();
			shaderManager.ballShader1.stop();
			
		} // end else
		
		if (pause == true) {
			
			glBegin(GL_QUADS);
			glColor3f(1.0f, 1.0f, 0.0f);
			glVertex3f(-0.7f,-0.7f, .0f);
			glVertex3f(-0.7f, 0.7f, .0f);
			glVertex3f(0.7f, 0.7f, .0f);
			glVertex3f(0.7f, -0.7f, .0f);
			glEnd();
			 
		} // end if
		
	} // end draw
	
} // end class
