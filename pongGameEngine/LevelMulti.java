// This class purpose is to initialize all of the objects for pong as well as update and draw them, 
// when the state of the game is (pong) and the user selects the multiplayer option.
// It handles multiplayer logic.
// This class will help keep the code clean in our pongMenu class because we don't have to call each individual function.
// This class was adapted from a youtube video from Elliot Forbes
// Video Here
// https://www.youtube.com/watch?v=qvFu0WhNPWE&index=6&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Another video here
// https://www.youtube.com/watch?v=Y3dbT-H_p20&index=8&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// another video here
// https://www.youtube.com/watch?v=4CWXcWTsxog&index=10&list=PLzUGFf4GhXBJZ2FurlDXVGr0iqScUfi9e
// Date written/modified: November 2017
// Author: Josiah Salas

package pongGameEngine;

import org.lwjgl.glfw.GLFW;
import io.Window;
import pongGraphicEngine.ShaderManager;
import pongText.Text;

public class LevelMulti {

	public Paddle player1;
	public PaddleLong player1Long;
	public PaddleShort player1Short;
	
	public Paddle player2;
	public PaddleLong player2Long;
	public PaddleShort player2Short;
	
	public Ball ball;
	public PowerUpBall powerBall;
	
	public Bomb bomb;
	
	ShaderManager shaderManager;
	
	public boolean pause = false;
	public boolean quit = false;
	public Pause pauseScreen;
	
	public int hitCount = 0;
	public int nonPowerHitCount = 0;
	public int PowerHitCount = 0;
	
	// powerups variables
	
	// variable if a powerBall droped
	public boolean powerUpBallDroped = false;
	
	// variable if the power is active
	public boolean powerUpActive = false;
	
	// long paddles
	public boolean player1LongPaddle = false;
	public boolean player2LongPaddle = false;
	
	// small paddles
	public boolean player1ShortPaddle = false;
	public boolean player2ShortPaddle = false;
	
	// slown paddles
	public boolean player1SlowPaddle = false;
	public boolean player2SlowPaddle = false;
	
	// dizzy paddles
	public boolean player1DizzyPaddle = false;
	public boolean player2DizzyPaddle = false;
	
	// invis paddles
	public boolean player1InvisPaddle = false;
	public boolean player2InvisPaddle = false;
	
	// bombs
	public boolean player1Bomb = false;
	public boolean player2Bomb = false;
	
	// if the bomb has been released flag
	public boolean bombReleased = false;
	
	// if the paddle has been destroyed flag
	public boolean player1DestroyedPaddle = false;
	public boolean player2DestroyedPaddle = false;
	
	
	// Create both players paddles and a ball
	public LevelMulti() {
		
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		
		player1 = new Paddle();
		player1Long = new PaddleLong();
		player1Short = new PaddleShort();
		
		player2 = new Paddle();
		player2Long = new PaddleLong();
		player2Short = new PaddleShort();
		
		ball = new Ball();
		powerBall = new PowerUpBall();
		
		pauseScreen = new Pause();
		
		player1.position.x = -0.8f;
		player1Long.position.x = -0.8f;
		player1Short.position.x = -0.8f;	
		
		player2.position.x = 0.8f;
		player2Long.position.x = 0.8f;
		player2Short.position.x = 0.8f;
		
	} // end Level Constructor
	
	// Checks to see if the paddle made contact with the ball
	public void checkCollision(Paddle paddle) {
		
		// checks to see if there is no space between the ball and the paddle using AABB (Allined-Axis-Bounding-Box) collison technique
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			nonPowerHitCount++;
			
			if ((player1SlowPaddle || player1DizzyPaddle || player1InvisPaddle)  && ball.movement.x < 0)
				PowerHitCount++;
			
			if ((player2SlowPaddle || player2DizzyPaddle  || player2InvisPaddle) && ball.movement.x > 0)
				PowerHitCount++;
			
			if (PowerHitCount == 3)
				takeAdvAway();
			
			ballCollisionLogic();
			
		} // end if
		
	} // end checkCollision
	
	// checks to see if the bomb made contact with the paddle
	public void checkBombCollision(Paddle paddle) {
		
		if (paddle.position.x < bomb.position.x + bomb.WIDTH &&
				paddle.position.x + paddle.WIDTH > bomb.position.x &&
				paddle.position.y < bomb.position.y + bomb.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > bomb.position.y) {
			
			if (bomb.movement.x > 0)
				player2DestroyedPaddle = true;
			
			if (bomb.movement.x < 0)
				player1DestroyedPaddle = true;
			
			powerUpActive = true;	
			
		} // end if
		
	} // end checkBombCollision
	
	// Checks to see if the long paddle made contact with the ball
	public void checkCollisionLong(PaddleLong paddle) {
		
		// checks to see if there is no space between the ball and the paddle using AABB (Allined-Axis-Bounding-Box) collison technique
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			PowerHitCount++;
			// after two hits with the long paddle it will go back to a normal paddle
			if(PowerHitCount == 3)
				takeAdvAway();
			
			ballCollisionLogic();
			
		} // end if
		
	} // end checkCollisionLong
	
	// Checks to see if the short paddle made contact with the ball
	public void checkCollisionShort(PaddleShort paddle) {
		
		// checks to see if there is no space between the ball and the paddle using AABB (Allined-Axis-Bounding-Box) collison technique
		if (paddle.position.x < ball.position.x + ball.WIDTH &&
				paddle.position.x + paddle.WIDTH > ball.position.x &&
				paddle.position.y < ball.position.y + ball.HEIGHT &&
				paddle.position.y + paddle.HEIGHT > ball.position.y) {
			
			PowerHitCount++;
			// after two hits with the long paddle it will go back to a normal paddle
			if(PowerHitCount == 3)
				takeAdvAway();
			
			ballCollisionLogic();
			
		} // end if
		
	} // end checkCollisionShort
	
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
		
		player1ShortPaddle = false;
		player2ShortPaddle = false;
		
		player1SlowPaddle = false;
		player2SlowPaddle = false;
		
		player1DizzyPaddle = false;		
		player2DizzyPaddle = false;
		
		player1InvisPaddle = false;
		player2InvisPaddle = false;
		
		player1Bomb = false;
		player2Bomb = false;
		
		bombReleased = false;
		
		powerUpActive = false;
		
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
		
		int selectPowerUp;
		selectPowerUp = (int)(Math.random() * 6 + 1);

		if(selectPowerUp == 1)
			player1LongPaddle = true;
		
		if(selectPowerUp == 2)
			player2ShortPaddle = true;
		
		if(selectPowerUp == 3)
			player2SlowPaddle = true;
		
		if(selectPowerUp == 4)
			player2DizzyPaddle = true;
		
		if(selectPowerUp == 5) {
			
			player1Bomb = true;
			bombReleased = false;
			bomb = new Bomb();
			bomb.position.x = -0.68f;
			
		} // end if
		
		if(selectPowerUp == 6)
			player2InvisPaddle = true;
		
		powerUpActive = true;
		
	} //giveAdvantageToPlayer1
	
	// gives advantage to player 2. It will select a random one
	public void giveAdvantageToPlayer2() {
		
		int selectPowerUp;
		selectPowerUp = (int)(Math.random() * 6 + 1);
		
		if(selectPowerUp == 1)
			player2LongPaddle = true;
		
		if(selectPowerUp == 2)
			player1ShortPaddle = true;
		
		if(selectPowerUp == 3)
			player1SlowPaddle = true;
		
		if(selectPowerUp == 4)
			player1DizzyPaddle = true;
		
		if(selectPowerUp == 5) {
			
			player2Bomb = true;
			bombReleased = false;
			bomb = new Bomb();
			bomb.position.x = 0.68f;
			
		} // end if
		
		if(selectPowerUp == 6)
			player1InvisPaddle = true;
		
		powerUpActive = true;
		
	} //giveAdvantageToPlayer2
	
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
		player1Long.position.y = 0.0f;
		player1Short.position.y = 0.0f;
		
		player2.position.y = 0.0f;
		player2Long.position.y = 0.0f;
		player2Short.position.y = 0.0f;

		hitCount = 0;
		powerUpBallDroped = false;
		
		takeAdvAway();
		
		player1DestroyedPaddle = false;
		player2DestroyedPaddle = false;

		ball = new Ball();
		
	} // end resetPlayingField
	
	// update both players paddles and the ball
	public void update(Window window) {
				
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_P)) 
			   pause = true;
		
		if (pause) {
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_R))	
				pause = false;
			
			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_Q)) {	
				   
				quit = true;
				pause = false;
				   
			} // end if
			
		} // end if
		
		if(!pause) {
			
			// if a powerball hasnt already spawned and there has been five ball hits since the last power up spawn a power up
			if (nonPowerHitCount == 5 && !powerUpBallDroped && !powerUpActive) {
				
				float powerBallPosition;
				powerBallPosition = (float)(Math.random() * 0.8 -0.2);
				powerBall.position.y = powerBallPosition;
				//powerBall.position.x = 0.3f;
				powerUpBallDroped = true;
				
			} // end if
						
			// if player 1 has a long paddle check its collision, player 2 will have a normal paddle
			if (player1LongPaddle && !player1DestroyedPaddle) {
				
				if (ball.movement.x < 0)
					checkCollisionLong(player1Long);
				
				if (ball.movement.x > 0)
					checkCollision(player2);
				
			} // end if
			
			// if player 2 has a long paddle check its collision, player 1 will have a normal paddle
			if (player2LongPaddle && !player2DestroyedPaddle) {
				
				if (ball.movement.x < 0)
					checkCollision(player1);
				
				if (ball.movement.x > 0)
					checkCollisionLong(player2Long);
				
			} // end if
			
			// if player 1 has a short paddle check its collision, player 2 will have a normal paddle
			if (player1ShortPaddle && !player1DestroyedPaddle) {
				
				if (ball.movement.x < 0)
					checkCollisionShort(player1Short);
				
				if (ball.movement.x > 0)
					checkCollision(player2);
				
			} // end if
			
			// if player 2 has a long paddle check its collision, player 1 will have a normal paddle
			if (player2ShortPaddle && !player2DestroyedPaddle) {
				
				if (ball.movement.x < 0)
					checkCollision(player1);
				
				if (ball.movement.x > 0)
					checkCollisionShort(player2Short);
				
			} // end if
			
			// if player 1 and player 2 dont have a modified paddle powerup check normal paddle collision
			if(!player1LongPaddle && !player2LongPaddle && !player1ShortPaddle && !player2ShortPaddle) {
				
				if (ball.movement.x < 0 && !player1DestroyedPaddle)
					checkCollision(player1);
				
				if (ball.movement.x > 0 && !player2DestroyedPaddle)
					checkCollision(player2);
				
			} // end else
			
			// if player 1 has the slow modifier, move them slowly
			if (player1SlowPaddle) {
				
				player1.updateSlow("player1",window);
				player1Long.updateSlow("player1", window);
				player1Short.updateSlow("player1", window);
				
			} // end if
			
			// if player 1 has the slow modifier, move them slowly
			if (player1DizzyPaddle) {
				
				player1.updateDizzy("player1",window);
				player1Long.updateDizzy("player1", window);
				player1Short.updateDizzy("player1", window);
				
			} // end if
			
			// move player 1 normally if they are not slowed down or dizzy
			if (!player1SlowPaddle && !player1DizzyPaddle) {
				
				player1.update("player1",window);
				player1Long.update("player1", window);
				player1Short.update("player1", window);
				
			} // end if
			
			// if player 2 has the slow modifier, move them slowly
			if (player2SlowPaddle) {
				
				player2.updateSlow("player2",window);
				player2Long.updateSlow("player2", window);
				player2Short.updateSlow("player2", window);
				
			} // end if
			
			// if player 2 has the dizzy modifier, change their controls
			if (player2DizzyPaddle) {
				
				player2.updateDizzy("player2",window);
				player2Long.updateDizzy("player2", window);
				player2Short.updateDizzy("player2", window);
				
			} // end if
			
			// move player 2 normally if they are not slowed down
			if (!player2SlowPaddle && !player2DizzyPaddle) {
				
				player2.update("player2",window);
				player2Long.update("player2", window);
				player2Short.update("player2", window);
				
			} // end if
			
			if(player1Bomb && !bombReleased) {
				
				bomb.position.y = player1.position.y + (player1.HEIGHT/2);
				
				if (window.getInput().isKeyDown(GLFW.GLFW_KEY_SPACE)) {
					
					bomb.movement.x = 0.05f;
					bombReleased = true;
					player1Bomb = false;
					
				} // end if
				
			} // end if
			
			if(player2Bomb && !bombReleased) {
				
				bomb.position.y = player2.position.y + (player2.HEIGHT/2);
				
				if (window.getInput().isKeyDown(GLFW.GLFW_KEY_ENTER)) {
					
					bomb.movement.x = -0.05f;
					bombReleased = true;
					player2Bomb = false;
					
				} // end if
				
			} // end if
			
			ball.update();
			
			// moves the ball and checks for collision
			if(bombReleased) {
				
				bomb.update();
				checkBombCollision(player1);
				checkBombCollision(player2);
				
				// if the bomb is out of bounds and didnt hit a paddle reset flags so another powerup can spawn
				if (!bomb.inBounds && !player1DestroyedPaddle && !player2DestroyedPaddle) {
					
					powerUpActive = false;
					bombReleased = false;
					nonPowerHitCount = 0;
			
				} // end if
				
			} // end if
			
			// checks collision for a powerup drop
			if (powerUpBallDroped)
				checkPowerBallCollision();
			
			checkGoal();
			
		} // end if !pause
		
	} // end update
	
	// draws objects
	public void draw() {
		// if player 1 has a long paddle draw it, player 2 will have a normal paddle
		if (player1LongPaddle) {
			
			shaderManager.paddleBigShader.start();
			shaderManager.paddleBigShader.setUniform3f("pos", player1Long.position);
			player1Long.draw();
			shaderManager.paddleBigShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
		// if player 2 has a long paddle draw it, player 1 will have a normal paddle
		if (player2LongPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleBigShader.start();
			shaderManager.paddleBigShader.setUniform3f("pos", player2Long.position);
			player2Long.draw();
			shaderManager.paddleBigShader.stop();
			
		} // end if
		
		// if player 1 has a short paddle draw it, player 2 will have a normal paddle
		if (player1ShortPaddle) {
			
			shaderManager.paddleSmallShader.start();
			shaderManager.paddleSmallShader.setUniform3f("pos", player1Short.position);
			player1Short.draw();
			shaderManager.paddleSmallShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
		// if player 2 has a short paddle draw it, player 1 will have a normal paddle
		if (player2ShortPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleSmallShader.start();
			shaderManager.paddleSmallShader.setUniform3f("pos", player2Short.position);
			player2Short.draw();
			shaderManager.paddleSmallShader.stop();
			
		} // end if
		
		// if the paddles are slow
		if (player1SlowPaddle) {
			
			shaderManager.paddleSlowShader.start();
			shaderManager.paddleSlowShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleSlowShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
		if (player2SlowPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleSlowShader.start();
			shaderManager.paddleSlowShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleSlowShader.stop();
			
		} // end if
		
		// if the paddles are dizzy
		if (player1DizzyPaddle) {
			
			shaderManager.paddleDizzyShader.start();
			shaderManager.paddleDizzyShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleDizzyShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
		if (player2DizzyPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleDizzyShader.start();
			shaderManager.paddleDizzyShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleDizzyShader.stop();
			
		} // end if
		
		// if the paddles are destroyed or invis
		if (player1DestroyedPaddle || player1InvisPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
		if (player2DestroyedPaddle || player2InvisPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
		// if player 1 and 2 dont have modified paddles draw the normal ones
		if(!player1LongPaddle && !player2LongPaddle && !player1ShortPaddle && !player2ShortPaddle && !player1SlowPaddle 
				&& !player2SlowPaddle && !player1DizzyPaddle && !player2DizzyPaddle && !player1InvisPaddle 
				&& !player2InvisPaddle && !player1DestroyedPaddle && !player2DestroyedPaddle) {
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player1.position);
			player1.draw();
			shaderManager.paddleShader.stop();
			
			shaderManager.paddleShader.start();
			shaderManager.paddleShader.setUniform3f("pos", player2.position);
			player2.draw();
			shaderManager.paddleShader.stop();
			
		} // end if
		
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
		
		// if a bomb was awarded, draw it
		if(bombReleased || player1Bomb || player2Bomb) {
			
			shaderManager.ballShader5.start();
			shaderManager.ballShader5.setUniform3f("pos", bomb.position);
			bomb.draw();
			shaderManager.ballShader5.stop();
			
		} // end if
		
		if(pause)
			pauseScreen.draw();
		
		// draws the score for each player
		Text.drawString(String.valueOf(player1.getScore()), -12f, 12f, 0.3f, 10f);
		Text.drawString(String.valueOf(player2.getScore()), 12.5f, 12f, 0.3f, 10f);
		
	} // end draw
	
} // end class
