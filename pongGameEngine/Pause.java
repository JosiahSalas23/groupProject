// This class is the pause screen for Pong. This class will be used when when th user presses 'p' when playing pong This class draws simple text
// telling the user that the game is paused. It also tells the user the command to resume the game (unpause) or to quit out of the game
// and go back into the pong main menu.
// Date written/modified: November 2017
// Author: Josiah Salas

package pongGameEngine;

import pongText.Text;

public class Pause {
	
	public void draw() {
		
		Text.drawString("paused", -1.5f, 3f, .55f, 10f);
		Text.drawString("press r to unpause", -5.0f, 0f, .55f, 10f);
		Text.drawString("q to menu", -2.5f, -2.5f, .55f, 10f);
		
	} // end draw
	
} // end pause
