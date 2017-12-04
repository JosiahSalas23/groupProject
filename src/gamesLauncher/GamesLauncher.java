package gamesLauncher;
import java.util.Scanner;

import pongDriver.Driver;
import game.Main;

public class GamesLauncher {
	
	private enum state {
		
		selection, pong, maze, quit
	};

	public static void main(String[] args) {
		
		System.out.println("Game Launcher");
		Driver driver = new Driver();
		PrototypeMenuDriver menu = new PrototypeMenuDriver();
		Scanner scan = new Scanner (System.in);
		String answer;
		boolean playAgain = true;
		
		state Game = state.selection;
		
		while (playAgain) {
			
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
				if (PrototypeMenuDriver.pongSelection) {
					
					PrototypeMenuDriver.pongSelection = false;
					Game = state.pong;
					
				} // end if
				if (PrototypeMenuDriver.mazeSelection) {	
					
					PrototypeMenuDriver.mazeSelection = false;
					Game = state.maze;
					
				} // end if
				
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
