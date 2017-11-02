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
				System.out.println("What game would you like to play? Options: 'Pong', 'maze', 'quit'");
				answer = scan.nextLine();
				if (answer.equalsIgnoreCase("pong"))
					Game = state.pong;
				if (answer.equalsIgnoreCase("maze"))
					Game = state.maze;
				if (answer.equalsIgnoreCase("quit"))
					Game = state.quit;
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
