package gameObjects;

@SuppressWarnings("serial")
public abstract class Winnable extends GameObject {
	
	public Winnable() {
		super();
	}
	public Winnable(Position position) {
		super(position);
		GoalPoint.texture='o';
		this.solid=false;
		priority=1;
	}
	
	public abstract boolean checkWin();
}
