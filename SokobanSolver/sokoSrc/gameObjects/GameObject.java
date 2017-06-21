package gameObjects;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class GameObject implements Serializable{
	protected Position currentLocation;
	protected boolean solid;
	static protected char texture;
	int priority;
	
	public GameObject() {
		
	}
	
	public GameObject(Position currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public Position getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Position currentLocation) {
		this.currentLocation = currentLocation;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public char getTexture() {
		return texture;
	}

	public void setTexture(char texture) {
		GameObject.texture = texture;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	

}
