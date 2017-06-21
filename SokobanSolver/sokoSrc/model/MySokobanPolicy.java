package model;

import java.util.ArrayList;

import gameObjects.Crate;
import gameObjects.GameObject;
import gameObjects.GoalPoint;
import gameObjects.MainCharacter;
import gameObjects.Position;
import gameObjects.Position2D;
import gameObjects.Wall;

public class MySokobanPolicy implements SokobanPolicy { //maybe static
	Level2D level;
	boolean winCondition;
	
	public MySokobanPolicy() {
		this.winCondition=false;
		this.level=new Level2D();
	}
	
	public MySokobanPolicy(Level2D level) {
		this.level = level;
	}
	
	
	private boolean collisionBetween(GameObject moved,GameObject stat,String direction)
	{
		String cl=stat.getClass().toString();
		if(moved.getClass()==new Crate().getClass())
		{
			if(cl.equals(new Wall().getClass().toString()))
				return true;
			if(cl.equals(new Crate().getClass().toString()))
				return true;
			for (GameObject obj : this.level.getGameObjectArrayOf(moved.getCurrentLocation())) {
				if(obj instanceof GoalPoint)
				{
					this.level.setNumberOfGoals(this.level.getNumberOfGoals()+1);
					System.out.println("Goals to go: "+this.level.getNumberOfGoals());
					if(this.level.getNumberOfGoals()==0)
					{
						this.winCondition=true;
					}
				}
			}
			for(GameObject obj: level.getGameObjectArrayOf(stat.getCurrentLocation())){
				if(obj instanceof GoalPoint)
				{
					this.level.setNumberOfGoals(this.level.getNumberOfGoals()-1);
					System.out.println("Goals to go: "+this.level.getNumberOfGoals());
				}
			}
			
		}
		if(moved.getClass()==new MainCharacter().getClass())
		{
			if(cl.equals(new Crate().getClass().toString()))
			{
				Position crateDest = this.getNextPos(stat, direction);
				ArrayList<GameObject> arrAtDest=this.level.getGameObjectArrayOf(crateDest);
				for (GameObject gameObject : arrAtDest) {
					if(collisionBetween(stat, gameObject,direction))
						return true;
				}
			}
			
			if(cl.equals(new Wall().getClass().toString()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private Position2D getNextPos(GameObject obj,String direction)
	{
		int cX=obj.getCurrentLocation().getPositions().get(0);
		int cY=obj.getCurrentLocation().getPositions().get(1);
		Position2D nextPos= new Position2D(0,0);
		
		switch (direction.toLowerCase().trim()) {
		case "right":
			nextPos = new Position2D(cX+1, cY);
			break;
		case "left":
			nextPos = new Position2D(cX-1, cY);
			break;
		case "up":
			nextPos = new Position2D(cX, cY-1);
			break;
		case "down":
			nextPos = new Position2D(cX, cY+1);
			break;
		default:
			return null;
		}
		return nextPos;
	}
	
	public boolean moveMainCharacter(MainCharacter ch,String direction)
	{
		Position2D nextPos= getNextPos(ch, direction);
		ArrayList<GameObject> nextObjArray = this.level.getGameObjectArrayOf(nextPos);
		GameObject nextObj=null;
		boolean canMove=true;
		for (GameObject gameObject : nextObjArray) {
			nextObj=gameObject;
			if(this.collisionBetween(ch,nextObj, direction))
			{
				canMove=false;
			}
		}
		
		if(canMove)
		{
			if (nextObj instanceof Crate) {
				level.moveObject(nextObj, getNextPos(nextObj, direction));
			}
			//insert more pushable options
			level=level.moveObject(ch, nextPos);
			return true;
		}
		return false;

	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level2D level) {
		this.level = level;
	}

	public boolean isWinCondition() {
		return winCondition;
	}

	public void setWinCondition(boolean winCondition) {
		this.winCondition = winCondition;
	}
	
	
}
