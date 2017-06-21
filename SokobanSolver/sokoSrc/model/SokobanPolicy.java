package model;

import gameObjects.MainCharacter;

public interface SokobanPolicy {
	public boolean moveMainCharacter(MainCharacter ch,String direction);
}
