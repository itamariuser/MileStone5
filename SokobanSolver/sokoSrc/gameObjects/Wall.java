package gameObjects;

@SuppressWarnings("serial")
public class Wall extends GameObject{
	
	public Wall() {
		this.solid = true;
		texture = '#';
		priority=2;
	}

	public Wall(Position position) {
		super(position);
		Wall.texture='#';
		this.solid=true;
	}

}
