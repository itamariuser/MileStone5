package model;

import java.util.ArrayList;

import gameObjects.GameObject;
import gameObjects.Position2D;
import gameObjects.Textures;

public class TextDisplayer implements LevelDisplayer {

	String checkMax(ArrayList<GameObject> z)
	{
		int maxPriority=-1;
		String c=" ";
		for (GameObject gameObject : z) {
			if(gameObject.getPriority()>=maxPriority)
			{
				maxPriority=gameObject.getPriority();
				c=gameObject.getClass().toString();
			}
		}
		return c;
	}
	
	public boolean displayLevel(Level2D level) 
	{
		int tempX = 0;
		int tempY = 0;
		
		for (Position2D pos : level.getPositionObjectLayout().keySet()) {
			if(pos.getY() > tempY)
				tempY = pos.getY();
			if(pos.getX() > tempX)
				tempX = pos.getX();	
		}

		
		for (int j = 0; j < tempY+1 ; j++) {
			for (int i = 0; i < tempX+1; i++) {
				Position2D temp = new Position2D(i ,j);
				for (Position2D pos : level.getPositionObjectLayout().keySet()) {
					if(pos.getX() == temp.getX() && pos.getY() == temp.getY())
					{
						System.out.print(new Textures().getDictionary().get(checkMax(level.getPositionObjectLayout().get(pos))));
					}
				}
			}
			System.out.println();
		}
		
		return true;
	}
}