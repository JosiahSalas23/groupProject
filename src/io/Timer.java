package io;

//timer used to update the world environment.
public class Timer {
	public static double getTime() {
		//divide by 1 billion to convert from nanoseconds t0 seconds.
		return (double)System.nanoTime() / 1000000000L;
	}

}
