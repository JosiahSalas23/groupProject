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

import static src.game.Main.camera;
import static src.game.Main.shader;
import static src.game.Main.window;

import src.pongGraphicEngine.ShaderManager;
import src.pongText.Text;
import src.world.TileRenderer;
import src.world.World;

public abstract class LevelDif {
	
	protected World PongBG = new World("PongMap", camera, 160);
	protected TileRenderer tiles = new TileRenderer(127,24);
	
	public Paddle player1;
	public PaddleLong player1Long;
	public Paddle player2;
	public PaddleLong player2Long;
	
	public boolean pause = false;
	
	public int hitCount = 0;
	public int nonPowerHitCount = 0;
	public int PowerHitCount = 0;
	
	// powerups variables
	protected Ball ball;
	protected PowerUpBall powerBall;
	protected ShaderManager shaderManager;
	
	// variable if a powerBall droped
	public boolean powerUpBallDroped = false;
	
	// long paddles
	public boolean player1LongPaddle = false;
	public boolean player2LongPaddle = false;
	
	
	// Create both players paddles and a ball
	public LevelDif() {
		
		player1 = new Paddle();
		player2 = new Paddle();
		player1Long = new PaddleLong();
		player2Long = new PaddleLong();
		
		player1.position.x = -0.8f;
		player1Long.position.x = -0.8f;
		player2.position.x = 0.8f;
		player2Long.position.x = 0.8f;
		
		
	} // end Level Constructor
	
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
	
	// update both players paddles and the ball
	public abstract void update(Window window); // end update
	
	// draws objects
	public void draw() {
		
		// draws the score for each player
		
		PongBG.render(this.tiles, shader, camera, window);
		PongBG.correctCamera(camera, window);
		
		Text.drawString(String.valueOf(player1.getScore()), -12f, 12f, 0.3f, 0.3f);
		Text.drawString(String.valueOf(player2.getScore()), 12.5f, 12f, 0.3f, 0.3f);
		
		// if player 1 has a long paddle draw it, player 2 will have a normal paddle
		if (player1LongPaddle) {
			
			ShaderManager.paddleShader.start();
			ShaderManager.paddleShader.setUniform3f("pos", player1Long.position);
			player1Long.draw();
			ShaderManager.paddleShader.stop();
			
			ShaderManager.paddleShader.start();
			ShaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			ShaderManager.paddleShader.stop();
			
		}
		
		// if player 2 has a long paddle draw it, player 1 will have a normal paddle
		if (player2LongPaddle) {
			
			ShaderManager.paddleShader.start();
			ShaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			ShaderManager.paddleShader.stop();
			
			ShaderManager.paddleShader.start();
			ShaderManager.paddleShader.setUniform3f("pos", player2Long.position);
			player2Long.draw();
			ShaderManager.paddleShader.stop();
			
		}
		
		// if player 1 and 2 dont have modified paddles draw the normal ones
		if(!player1LongPaddle && !player2LongPaddle) {
			
			ShaderManager.paddleShader.start();
			ShaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			ShaderManager.paddleShader.stop();
			
			ShaderManager.paddleShader.start();
			ShaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			ShaderManager.paddleShader.stop();
			
		}
		
		
		// changes main ball's color as it gets faster
		if (hitCount > 30) {
			
			ShaderManager.ballShader5.start();
			ShaderManager.ballShader5.setUniform3f("pos", ball.position);
			ball.draw();
			ShaderManager.ballShader5.stop();
			
		} // end if
		
		if (hitCount >= 20 && hitCount <= 30) {
			
			ShaderManager.ballShader4.start();
			ShaderManager.ballShader4.setUniform3f("pos", ball.position);
			ball.draw();
			ShaderManager.ballShader4.stop();
			
		} // end if
		
		if (hitCount >= 10 && hitCount < 20) {
			
			ShaderManager.ballShader3.start();
			ShaderManager.ballShader3.setUniform3f("pos", ball.position);
			ball.draw();
			ShaderManager.ballShader3.stop();
			
		} // end if
		
		if (hitCount >= 5 && hitCount < 10) {
			
			ShaderManager.ballShader2.start();
			ShaderManager.ballShader2.setUniform3f("pos", ball.position);
			ball.draw();
			ShaderManager.ballShader2.stop();
			
		} // end if
		
		if (hitCount >= 0 && hitCount < 5) {
			
			ShaderManager.ballShader1.start();
			ShaderManager.ballShader1.setUniform3f("pos", ball.position);
			ball.draw();
			ShaderManager.ballShader1.stop();
			
		} // if
		
		// if a powerball spawned draw it
		if (powerUpBallDroped) {
			
			ShaderManager.powerUpBallShader.start();
			ShaderManager.powerUpBallShader.setUniform3f("pos", powerBall.position);
			powerBall.draw();
			ShaderManager.powerUpBallShader.stop();
			
		} // end if
		
	} // end draw
	
} // end class
