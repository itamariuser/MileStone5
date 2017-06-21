package gameObjects;

@SuppressWarnings("serial")
public abstract class GameCharacter extends MovableObject {
	
	public GameCharacter() {
		super();
		this.currentLocation=new Position2D(0, 0);
		this.priority=10;
	}
	
	public GameCharacter(Position position) {
		super(position);
	}
}
