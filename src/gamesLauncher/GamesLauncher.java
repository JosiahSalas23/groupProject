// This class determines what game to run or to run the main GUI. This class should see the static boolean flag variables from the main
// GUI class to determine what game/window to launch. Each game/window should be destroyed if the user quits out of it so that for there
// would be only one window at a time when the program is running
// Date written/modified: November 2017
// Author: Josiah Salas

package gamesLauncher;
import java.util.Scanner;

import pongDriver.Driver;
import game.Main;

public class GamesLauncher {
	
	// variables to determine which state the program is in (In MainGUI or playing one of the games)
	private enum state {
		
		selection, pong, maze, quit
	};

	public static void main(String[] args) {
		
		System.out.println("Game Launcher");
		// Initalizes Pong
		Driver driver = new Driver();
		// Initalizes the prtotype mainGUI
		PrototypeMenuDriver menu = new PrototypeMenuDriver();
		//Scanner scan = new Scanner (System.in);
		//String answer;
		
		// boolean that determines if the program should keep running or not
		boolean playAgain = true;
		
		// sets the state of the program to be running the main GUI
		state Game = state.selection;
		
		while (playAgain) {
			
			// Switch that determines what game/window to launch depending on which state the program is in
			switch(Game) {
			
			case pong:
				driver.run();
				Game = state.selection;
				break;
			
			case quit:
				playAgain = false;
				break;
				
			case selection:
				menu.run();
				
				// If the pongSelection flag is set true from the main GUI then switch the state of the program to the pong state
				// The flag is then set to false so the user can switch between games from the main GUI
				if (PrototypeMenuDriver.pongSelection) {
					
					PrototypeMenuDriver.pongSelection = false;
					Game = state.pong;
					
				} // end if
				
				// If the mazeSelection flag is set true from the main GUI then switch the state of the program to the maze state
				// The flag is then set to false so the user can switch between games from the main GUI
				if (PrototypeMenuDriver.mazeSelection) {	
					
					PrototypeMenuDriver.mazeSelection = false;
					Game = state.maze;
					
				} // end if
				
				// If the quitSelection flag is set true from the main GUI then switch the state of the program to the quit state
				// which then would exit out of the main menu and quit out of the program
				if (PrototypeMenuDriver.quitSelection) {
					
					PrototypeMenuDriver.quitSelection = false;
					Game = state.quit;
					
				} // end if
				break;
				
			case maze:
				new Main();
				Game = state.selection;
				break;
				
			default:
				Game = state.selection;
				break;
			
			} // end switch
			
		} // end while
		

	} // end main

}
