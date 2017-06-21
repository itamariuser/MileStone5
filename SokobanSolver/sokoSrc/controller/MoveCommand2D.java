package controller;

import java.util.ArrayList;

import gameObjects.MainCharacter;
import model.Level;
import model.Level2D;
import model.MySokobanPolicy;


public class MoveCommand2D implements Command {
	
	String direction;
	Level2D level;
	MainCharacter ch;
	boolean winCondition;
	ArrayList<String> directionsAvailable;
	
	public MoveCommand2D() {
		this.winCondition=false;
	}
	
	public ArrayList<String> getDirectionsAvailable() {
		return directionsAvailable;
	}

	public void setDirectionsAvailable(ArrayList<String> directionsAvailable) {
		this.directionsAvailable = directionsAvailable;
	}

	public MoveCommand2D(Level level, String direction) {
		this.direction = direction;
		this.level = (Level2D)level;
		this.directionsAvailable=new ArrayList<String>();
		this.directionsAvailable.add("up");
		this.directionsAvailable.add("down");
		this.directionsAvailable.add("left");
		this.directionsAvailable.add("right");
	}
	
	@Override
	public void execute() throws Exception {	
		MySokobanPolicy policy=new MySokobanPolicy(this.level);
		boolean doestDirectionExist=false;
		for (String string : this.directionsAvailable) {
			if(string.equals(direction))
			{
				doestDirectionExist=true;
			}
		}
		if(doestDirectionExist==false)
		{
			throw new Exception("InvalidDirection");
		}
		
		if(!policy.moveMainCharacter( level.getMainCharacter(), direction))
		{
			throw new Exception("CantMove");//Character can't move in direction
		}
		if(policy.isWinCondition())
		{
			this.winCondition=true;
		}
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Level2D getLevel() {
		return level;
	}

	public void setLevel(Level2D level) {
		this.level = level;
	}

	public MainCharacter getCh() {
		return ch;
	}

	public void setCh(MainCharacter ch) {
		this.ch = ch;
	}

	public boolean isWinCondition() {
		return winCondition;
	}

	public void setWinCondition(boolean winCondition) {
		this.winCondition = winCondition;
	}

}
