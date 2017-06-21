package gameObjects;

@SuppressWarnings("serial")
public abstract class MovableObject extends GameObject implements Movable {
	
	
	public MovableObject() {
	}
	
	public MovableObject(Position position) {
		super(position);
	}

	public Position getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Position currentLocation) {
		this.currentLocation = currentLocation;
	}

	

}
