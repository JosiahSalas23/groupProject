// This class purpose is to initialize all of the objects for pong as well as update and draw them, 
// when the state of the game is (play) and the user selects the single player option/hard.
// It handles single player logic.
// This class will help keep the code clean in our driver class because we don't have to call each individual function.
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=qvFu0WhNPWE&index=6&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Another video here
// https://www.youtube.com/watch?v=Y3dbT-H_p20&index=8&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// another video here
// https://www.youtube.com/watch?v=4CWXcWTsxog&index=10&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas

package src.pongGameEngine;

//import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
//import static org.lwjgl.opengl.GL11.*;

import src.io.Window;

import src.pongGraphicEngine.ShaderManager;
//import src.pongText.Text;

public class LevelHard extends LevelDif{
	/*
	public Paddle player1;
	public PaddleLong player1Long;
	public Paddle player2;
	public PaddleLong player2Long;
	
	Ball ball;
	PowerUpBall powerBall;
	
	ShaderManager shaderManager;
	
	public boolean pause = false;
	
	public int hitCount = 0;
	public int nonPowerHitCount = 0;
	public int PowerHitCount = 0;
	
	// powerups variables
	
	// variable if a powerBall droped
	public boolean powerUpBallDroped = false;
	
	// long paddles
	public boolean player1LongPaddle = false;
	public boolean player2LongPaddle = false;
	*/
	
	// Create both players paddles and a ball
	public LevelHard() {
		super();
		
		super.shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		
//		player1 = new Paddle();
//		player2 = new Paddle();
//		player1Long = new PaddleLong();
//		player2Long = new PaddleLong();
		
		super.ball = new Ball();
		super.powerBall = new PowerUpBall();
//		
//		player1.position.x = -0.8f;
//		player1Long.position.x = -0.8f;
//		player2.position.x = 0.8f;
//		player2Long.position.x = 0.8f;
//		
		
	} // end Level Constructor
	/*
	// Checks to see if the paddle made contact with the ball
	public void checkCollision(Paddle paddle) {
		
		// checks to see if there is no space between the ball and the paddle using AABB (Allined-Axis-Bounding-Box) collison technique
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			nonPowerHitCount++;
			
			ballCollisionLogic();
			
		} // end if
		
	} // end checkCollision
	
	// Checks to see if the long paddle made contact with the ball
	public void checkCollisionLong(PaddleLong paddle) {
		
		// checks to see if there is no space between the ball and the paddle using AABB (Allined-Axis-Bounding-Box) collison technique
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			PowerHitCount++;
			// after two hits with the long paddle it will go back to a normal paddle
			if(PowerHitCount == 2)
				takeAdvAway();
			
			ballCollisionLogic();
			
		} // end if
		
	} // end checkCollisionLong
	
	// this function will be called once a paddle makes contact with a ball. It moves the ball
	public void ballCollisionLogic() {
		
		hitCount++;
		
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
	
	// takes modifiers away
	public void takeAdvAway() {
		
		player1LongPaddle = false;
		player2LongPaddle = false;
		
		nonPowerHitCount = 0;
		PowerHitCount = 0;
		
	} // end takeAdvAway
	
	public void checkPowerBallCollision() {
		
		if (powerBall.position.x < ball.position.x + ball.WIDTH &&
				powerBall.position.x + powerBall.WIDTH > ball.position.x &&
				powerBall.position.y < ball.position.y + ball.HEIGHT &&
				powerBall.position.y + powerBall.HEIGHT > ball.position.y) {
			
			System.out.println("ball collision detected");
			
			powerUpBallDroped = false;
			
			if (ball.movement.x > 0)
				giveAdvantageToPlayer1();
			
			else
				giveAdvantageToPlayer2();
			
			
		} // end if
		
	} // end checkPowerBallCollision
	
	
	// gives advantage to player 1. It will select a random one
	public void giveAdvantageToPlayer1() {
		
		player1LongPaddle = true;
		
	} //giveAdvantageToPlayer1
	
	// gives advantage to player 2. It will select a random one
	public void giveAdvantageToPlayer2() {
		
		player2LongPaddle = true;
		
	} //giveAdvantageToPlayer2
	
	// checks to see if a player had scored
	public void checkGoal() {
		
		// if the ball touches player two's side then player one scores. give the score and reset playing field.
		if (ball.position.x + ball.WIDTH >= 1.0f) {
			
			player1.score();
			System.out.println("Player 1 scored!");
			System.out.println("Score: " + player1.getScore() + " : " + player2.getScore());
			
			if((player1.getScore()) == 7) {
				
				System.out.println("Player 1 Wins");
				System.out.println("Final Score: " + player1.getScore() + " : " + player2.getScore());
				
			} // end if
			
			resetPlayingField();
			
		} // end if
		
		// if the ball touches player one's side then player two scores. give the score and reset playing field.
		if (ball.position.x <= -1.0f) {
			
			player2.score();
			System.out.println("Player 2 scored!");
			System.out.println("Score: " + player1.getScore() + " : " + player2.getScore());
			
			if((player2.getScore()) == 7) {
				
				System.out.println("Player 2 Wins");
				System.out.println("Final Score: " + player1.getScore() + " : " + player2.getScore());
				
			} // end if
			resetPlayingField();
			ball.movement.x *= -1.0f;
			
		} // end if
		
	} // end goal
	
	// resets the playing field after a goal
	public void resetPlayingField() {
		
		player1.position.y = 0.0f;
		player2.position.y = 0.0f;
		hitCount = 0;
		powerUpBallDroped = false;
		player1LongPaddle = false;
		player2LongPaddle = false;
		nonPowerHitCount = 0;
		PowerHitCount = 0;
		ball = new Ball();
		
	} // end resetPlayingField
	*/
	// update both players paddles and the ball
	public void update(Window window) {
				
		/*if (KeyboardInput.isKeyDown(GLFW_KEY_P)) {
			
			   System.out.println("Game pause");
			   pause = true;
				
			} // end if*/
		
		// if a powerball hasnt already spawned and there has been five ball hits since the last power up spawn a power up
		if (super.nonPowerHitCount == 5 && !super.powerUpBallDroped) {
			
			float powerBallPosition;
			powerBallPosition = (float)(Math.random() * 0.8 -0.2);
			super.powerBall.position.y = powerBallPosition;
			super.powerUpBallDroped = true;
			
		} // end if
			
		
		// if player 1 has a long paddle check its collision, player 2 will have a normal paddle
		if (super.player1LongPaddle) {
			
			if (super.ball.movement.x < 0)
				checkCollisionLong(player1Long);
			
			if (super.ball.movement.x > 0)
				checkCollision(player2);
			
		} // end if
		
		// if player 2 has a long paddle check its collision, player 1 will have a normal paddle
		if (super.player2LongPaddle) {
			
			if (super.ball.movement.x < 0)
				checkCollision(player1);
			
			if (super.ball.movement.x > 0)
				checkCollisionLong(player2Long);
			
		} // end if
		
		// if player 1 and player 2 dont have a modified paddle powerup check normal paddle collision
		if(!super.player1LongPaddle && !super.player2LongPaddle) {
			
			if (super.ball.movement.x < 0)
				checkCollision(player1);
			
			if (super.ball.movement.x > 0)
				checkCollision(player2);
			
		} // end else
		
		
		checkGoal();
		
		super.player1.update("player1",window);
		super.player1Long.update("player1", window);
		
		super.ball.update();
		
		if (powerUpBallDroped)
			checkPowerBallCollision();
		
		// AI logic
		if (super.ball.movement.y > 0 && super.ball.position.y > super.player2.position.y + 0.125f && super.ball.movement.x > 0 && super.ball.position.x > -0.6f) {
			
			super.player2.moveAIUpHard();
			
		} // end if
		
		if (super.ball.movement.y < 0 && super.ball.position.y < super.player2.position.y + 0.125f && super.ball.movement.x > 0 && super.ball.position.x > -0.6f) {
			
			super.player2.moveAIDownHard();
			
		} // end if
		
		if (super.ball.movement.y > 0 && super.ball.position.y > super.player2Long.position.y + 0.25f && super.ball.movement.x > 0 && super.ball.position.x > -0.6f) {
			
			player2Long.moveAIUpHard();
			
		} // end if
		
		if (super.ball.movement.y < 0 && super.ball.position.y < super.player2Long.position.y + 0.25f && super.ball.movement.x > 0 && super.ball.position.x > -0.6f) {
			
			super.player2Long.moveAIDownHard();
			
		} // end if	
		
	} // end update
	/*
	// draws objects
	public void draw() {
		
		// draws the score for each player
		Text.drawString(String.valueOf(player1.getScore()), -12f, 12f, 0.3f, 0.3f);
		Text.drawString(String.valueOf(player2.getScore()), 12.5f, 12f, 0.3f, 0.3f);
		
		// if player 1 has a long paddle draw it, player 2 will have a normal paddle
		if (player1LongPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1Long.position);
			player1Long.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		}
		
		// if player 2 has a long paddle draw it, player 1 will have a normal paddle
		if (player2LongPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2Long.position);
			player2Long.draw();
			shaderManager.paddleShader.stop();
			
		}
		
		// if player 1 and 2 dont have modified paddles draw the normal ones
		if(!player1LongPaddle && !player2LongPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		}
		
		
		// changes main ball's color as it gets faster
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
			
		} // if
		
		// if a powerball spawned draw it
		if (powerUpBallDroped) {
			
			shaderManager.powerUpBallShader.start();
			shaderManager.powerUpBallShader.setUniform3f("pos", powerBall.position);
			powerBall.draw();
			shaderManager.powerUpBallShader.stop();
			
		} // end if
		
	} // end draw
	*/
	
} // end class
