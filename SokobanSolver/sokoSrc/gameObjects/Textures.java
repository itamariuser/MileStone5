package gameObjects;

import java.util.HashMap;

public class Textures {
	private HashMap<String,Character> dictionary;
	
	
public Textures() {
	this.dictionary=new HashMap<String,Character>();
	dictionary.put(new Wall().getClass().toString(),'#');
	dictionary.put(new MainCharacter().getClass().toString(),'A');
	dictionary.put(new BlankSpace().getClass().toString(),' ');
	dictionary.put(new Crate().getClass().toString(),'@');
	dictionary.put(new GoalPoint().getClass().toString(),'o');
}


public HashMap<String, Character> getDictionary() {
	return this.dictionary;
}


public void setDictionary(HashMap<String, Character> dictionary) {
	this.dictionary = dictionary;
}
}
