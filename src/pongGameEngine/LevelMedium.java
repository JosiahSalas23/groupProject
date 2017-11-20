// This class purpose is to initialize all of the objects for pong as well as update and draw them, 
// when the state of the game is (play) and the user selects the single player option/medium.
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

import src.pongGraphicEngine.ShaderManager;
import src.io.Window;
//import src.pongText.Text;

public class LevelMedium extends LevelDif {
	
	// Create both players paddles and a ball
	public LevelMedium() {
		super();
		
		super.shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		
		super.ball = new Ball();
		super.powerBall = new PowerUpBall();
		
	} // end Level Constructor
	
	public void update(Window window) {
		
		// if a powerball hasnt already spawned and there has been five ball hits since the last power up spawn a power up
		if (nonPowerHitCount == 5 && !powerUpBallDroped) {
			
			float powerBallPosition;
			powerBallPosition = (float)(Math.random() * 0.8 -0.2);
			super.powerBall.position.y = powerBallPosition;
			powerUpBallDroped = true;
			
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
				checkCollision(super.player1);
			
			if (super.ball.movement.x > 0)
				checkCollision(super.player2);
			
		} // end else
		
		
		checkGoal();
		
		super.player1.update("player1", window);
		super.player1Long.update("player1", window);
		
		super.ball.update();
		
		if (powerUpBallDroped)
			checkPowerBallCollision();
		
		// AI logic
		if (super.ball.movement.y > 0 && super.ball.position.y > super.player2.position.y + 0.125f && super.ball.movement.x > 0 && super.ball.position.x > -0.55f) {
			
			super.player2.moveAIUpMedium();
			
		} // end if
		
		if (super.ball.movement.y < 0 && super.ball.position.y < super.player2.position.y + 0.125f && super.ball.movement.x > 0 && super.ball.position.x > -0.55f) {
			
			super.player2.moveAIDownMedium();
			
		} // end if
		
		if (super.ball.movement.y > 0 && super.ball.position.y > super.player2Long.position.y + 0.25f && super.ball.movement.x > 0 && super.ball.position.x > -0.55f) {
			
			super.player2Long.moveAIUpMedium();
			
		} // end if
		
		if (super.ball.movement.y < 0 && super.ball.position.y < super.player2Long.position.y + 0.25f && super.ball.movement.x > 0 && super.ball.position.x > -0.55f) {
			
			super.player2Long.moveAIDownMedium();
			
		} // end if	
		
	} // end update
	
} // end class