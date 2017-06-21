package data;

import java.io.PrintStream;

@SuppressWarnings("serial")
public class GoalNotFoundException extends Exception {
	@Override
	public void printStackTrace(PrintStream s) {
		s.println("GoalNotFoundException: The goal state was not found.");
	}
}
