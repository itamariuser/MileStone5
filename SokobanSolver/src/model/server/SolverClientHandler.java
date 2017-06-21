package model.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import algorithm.Plan;
import algorithm.Strips;
import data.BestFirstSearcher;
import gameObjects.Position2D;
import model.Level2D;
import solver.SokobanSolver;

public class SolverClientHandler implements ClientHandler {

	@Override
	public Boolean handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception {
		ObjectInputStream input = new ObjectInputStream(inFromClient);
		ObjectOutputStream output = new ObjectOutputStream(outToClient);
		Level2D levelToSolve = (Level2D) input.readObject(); //reading the level from the sokoban client
		SokobanSolver plannable = new SokobanSolver(levelToSolve, new BestFirstSearcher<>());
		Strips<Position2D> strips = new Strips<Position2D>();
		levelToSolve = (Level2D) input.readObject();
		Plan<Position2D> levelPlan = strips.plan(plannable);
		output.writeObject(strips.plan(plannable)); //planning the solution and writing to the client
		try {
			
		} finally {
			input.close();
			output.close();
		}
		
		if (levelPlan.getActions().isEmpty()) {
			return false;
		}
		return true;
		
	}

	
}
