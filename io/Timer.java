package io;
/**
 * <h1>Timer</h1>
 * 
 * <p>
 * Handles the time clock of the application
 * used in animations and entity movement
 * 
 * @author August B. Sandoval
 * @author Kevin Bornemeier
 * @author Josiah Salas
 * @version 1.3
 * @since 2017-11-29
 */
public class Timer {
	/**
	 * getTime - static method gets the systems time clock
	 * 
	 * @return - double - seconds
	 */
	public static double getTime() {
		//divide by 1 billion to convert from nanoseconds t0 seconds.
		return (double)System.nanoTime() / 1000000000L;
	}

}
