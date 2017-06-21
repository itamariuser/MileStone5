package controller;

public class ExitCommand implements Command {

	@Override
	public void execute() throws Exception {
		System.out.println("Exiting now...");
	}

}
