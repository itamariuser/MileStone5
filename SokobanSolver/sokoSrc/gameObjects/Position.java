package gameObjects;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Position implements Serializable {
	protected Integer x;
	
	
	public double getDistanceFromPosition(Position p2)
	{
		return Math.abs((this.x-p2.getX())*1.0);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		
	}
	
	
	public Position() {
		// TODO Auto-generated constructor stub
	}
	public Position(int x) {
		this.x=x;
	}
	
	public ArrayList<Integer> getPositions()
	{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(this.x);
		return arr;
	}
	
	public Position2D toPosition2D()
	{
		Position2D posRet=new Position2D(this.getPositions().get(0),this.getPositions().get(1));
		return posRet;
	}
	

}
