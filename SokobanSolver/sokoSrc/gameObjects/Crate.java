package gameObjects;

@SuppressWarnings("serial")
public class Crate extends PushableObject {

	public Crate() {
		super();
	}
	public Crate(Position position) {
		super(position);
		Crate.texture='@';
		this.solid=false;
		priority=2;
	}
	
}
