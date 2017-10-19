package main;
import java.util.Scanner;

import game.Main;

public class GamesLauncher {
	
	private enum state {
		
		selection, pong, maze, quit
	};

	public static void main(String[] args) {
		
		System.out.println("Pong");
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
				System.out.println("What would you like to play?");
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
