package gameObjects;

@SuppressWarnings("serial")
public class MainCharacter extends PlayableCharacter {
	
	public MainCharacter() {
		super();
	}
	
	public MainCharacter(Position position) {
		super(position);
		MainCharacter.texture='A';
		this.solid=true;
	}
	

}
