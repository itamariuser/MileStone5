package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import gameObjects.BlankSpace;
import gameObjects.Crate;
import gameObjects.GameObject;
import gameObjects.GameObjectFactory;
import gameObjects.GoalPoint;
import gameObjects.MainCharacter;
import gameObjects.Position2D;
import gameObjects.Wall;
import gameObjects.Winnable;

public class TextLevel2DLoader implements LevelLoader {

private HashMap<Character,String> dictionary;
	
	public TextLevel2DLoader(){
		
		dictionary = new HashMap<Character,String>();
		dictionary.put('#', new Wall().getClass().toString());
		dictionary.put('A', new MainCharacter().getClass().toString());
		dictionary.put(' ', new BlankSpace().getClass().toString());
		dictionary.put('@', new Crate().getClass().toString());
		dictionary.put('o', new GoalPoint().getClass().toString());
	}
	
	@Override
	public Level2D loadLevelFromStream(InputStream in) throws Exception {
		Level2D levelToReturn = new Level2D();
		ArrayList<GameObject> newGameObjArray= new ArrayList<GameObject>();
		
		for (int k = 0; k < newGameObjArray.size(); k++) {
			newGameObjArray.set(k, null);
		}
		GameObjectFactory factory = new GameObjectFactory();
		
		BufferedReader inp = new BufferedReader(new InputStreamReader(in));
		
		String line = inp.readLine();
		levelToReturn.setName(line);
		line=inp.readLine();
		levelToReturn.setDifficulty(Integer.parseInt(line));
		line=inp.readLine();
		levelToReturn.setLevelNum(Integer.parseInt(line));
		line=inp.readLine();
		int j = 0;
		ArrayList<GameObject> objRef = new ArrayList<GameObject>();
		
		while(line != null){
			
			for(int i = 0; i < line.length(); i++)
			{
				ArrayList<GameObject> arr = new ArrayList<GameObject>();
				Position2D pos = new Position2D(i,j);
				GameObject obj = factory.createObject(dictionary.get(line.charAt(i)));
				if(obj instanceof Winnable)
				{
					levelToReturn.setNumberOfGoals(levelToReturn.getNumberOfGoals()+1);
				}
				obj.setCurrentLocation(pos);
				arr.add(obj);
				levelToReturn.putInLayout(pos, arr);
				objRef.add(obj);
				levelToReturn.setObjReferences(objRef);
				
			}
			line = inp.readLine();
			j++;
		}
		
		inp.close();
		return levelToReturn;
	}
}