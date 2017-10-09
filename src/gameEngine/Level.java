// This class purpose is to initialize the ball and each players paddle, as well as update and draw them.
// This class will help keep the code clean in our driver class because we don't have to call each individual function.
package gameEngine;

import graphicEngine.ShaderManager;

public class Level {

	public Paddle player1;
	public Paddle player2;
	
	Ball ball;
	
	ShaderManager shaderManager;
	
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
		
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			//System.out.println("Collision detected");
			
			// moves the ball in the opposite direction
			ball.movement.x *= -1.0f;
			
		} // end if
		
	} // end checkCollision;
	
	// update both players paddles and the ball
	public void update() {
		
		checkCollision(player1);
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
			ball = new Ball();
			
		} // end if
		
		checkCollision(player2);
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
			ball = new Ball();
			ball.movement.x *= -1.0f;
			
		} // end if
		
		player1.update("player1");
		player2.update("player2");
		
		ball.update();
		
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
		
		shaderManager.ballShader.start();
		shaderManager.ballShader.setUniform3f("pos", ball.position);
		ball.draw();
		shaderManager.ballShader.stop();
		
	} // end draw
	
} // end class
