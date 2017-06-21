package boot;

import java.io.IOException;

import controller.CLI;

public class Run {

	public static void main(String[] args) throws IOException {
		CLI l=new CLI();
		l.receiveInput();
	}

}
