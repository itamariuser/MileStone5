package gameObjects;

public class BlankSpace extends GameObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4314981787597371532L;

	public BlankSpace() {
		
	}

	public BlankSpace(Position position) {
		super((Position2D)position);
		BlankSpace.texture=' ';
		this.solid=false;
		priority=0;
	}
}
