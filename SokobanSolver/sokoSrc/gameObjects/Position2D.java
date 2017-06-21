package gameObjects;

import java.awt.Point;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Position2D extends Position {
	
	protected Integer y;
	
	public Position2D() {
		this.x=0;
		this.y=0;
	}
	
	public Position2D(int x, int y) {
		this.x=x;
		this.y=y;
		
	}
	
	public Position2D(Position2D p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	
	public double getDistanceFromPosition2D(Position2D p2)
	{
		return Math.sqrt(Math.pow(this.x-p2.getX(), 2)+Math.pow(this.y-p2.getY(), 2));
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public ArrayList<Integer> getPositions()
	{
		ArrayList<Integer> arr = super.getPositions();
		arr.add(this.y);
		return arr;
	}
	@Override
	public String toString() {
		return "("+x+", "+y+")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Position2D)
		{
			Position2D pos=(Position2D) obj;
			return (this.x==pos.x && this.y==pos.y);//klkl
		}
		return false;
	}
	
	
}
