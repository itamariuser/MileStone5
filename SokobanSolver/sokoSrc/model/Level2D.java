package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gameObjects.BlankSpace;
import gameObjects.GameObject;
import gameObjects.MainCharacter;
import gameObjects.Position;
import gameObjects.Position2D;

@SuppressWarnings("serial")
public class Level2D extends Level implements Serializable {
	protected String Name;
	protected int difficulty;
	protected int levelNum;
	protected int numberOfGoals;
	
	public int getNumberOfGoals() {
		return numberOfGoals;
	}

	
	public void setNumberOfGoals(int numberOfGoals) {
		this.numberOfGoals = numberOfGoals;
	}

	protected int score;
	protected int timePassed = 0;
	protected int numOfSteps;
	protected int numOfTries;
	protected ArrayList<GameObject> objReferences;
	protected HashMap<Position2D, ArrayList<GameObject>> positionObjectLayout;
	
	
	public Level2D() {
		this.positionObjectLayout = new HashMap<Position2D, ArrayList<GameObject>>();
		this.objReferences = new ArrayList<GameObject>();
	}
	
	public ArrayList<Position2D> getPositionsOfObject(GameObject g)
	{
		ArrayList<Position2D> posList=new ArrayList<>();
		for (Position2D pos : positionObjectLayout.keySet()) {
			if(positionObjectLayout.get(pos).contains(g))
			{
				posList.add(pos);
			}
		}
		return posList;
	}
	
	
	public ArrayList<GameObject> getGameObjectArrayOf(Position pos1)
	{
		ArrayList<GameObject> temp=null;
		for (Position2D pos : positionObjectLayout.keySet()) {
			if(pos.equals(pos1))
			{
				temp= positionObjectLayout.get(pos);
			}
		}
		return temp;
	}
	
	private boolean putInLayout(Position pos1,ArrayList<GameObject> arr1)
	{
		for (Position2D pos : positionObjectLayout.keySet()) {
			if(pos.equals(pos1))
			{
				this.positionObjectLayout.put(pos, arr1);
				return true;
			}
		}
		return false;
	}
	
	public Level2D moveObject(GameObject obj, Position2D dest)
	{
		ArrayList<GameObject> temp=this.getGameObjectArrayOf(obj.getCurrentLocation());//remove object and add blankspace
		temp.remove(obj);
		if(this.getGameObjectArrayOf(obj.getCurrentLocation()).isEmpty())
		{
			temp.add(0,new BlankSpace(obj.getCurrentLocation()));//add if no blankspace is underneath
		}
		this.putInLayout(obj.getCurrentLocation(), temp);   //END
		
		obj.setCurrentLocation(dest);  //change object's position
		ArrayList<GameObject> arr=new ArrayList<GameObject>();
		arr = this.getGameObjectArrayOf(dest);
		arr.add(obj);
		this.putInLayout(dest, arr);
		
		return this;
	}

	public MainCharacter getMainCharacter() throws Exception//MOR
	{
		for (GameObject gameObject : objReferences) {
			if(gameObject instanceof MainCharacter)
			{
				return (MainCharacter)gameObject;
			}
		}
		throw new Exception("No main character in level, please add a main character");
	}
	
	
	
	public void putInLayout(Position2D key, ArrayList<GameObject> value)
	{
		ArrayList<GameObject> arr = new ArrayList<GameObject>();
		for (GameObject gameObject : value) {
			arr.add(gameObject);
		}
		this.positionObjectLayout.put(key, value);
	}
	
	
	public void addToObjRef(GameObject lmao)
	{
		this.objReferences.add(lmao);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTimePassed() {
		return timePassed;
	}

	public void setTimePassed(int timePassed) {
		this.timePassed = timePassed;
	}

	public int getNumOfSteps() {
		return numOfSteps;
	}

	public void setNumOfSteps(int numOfSteps) {
		this.numOfSteps = numOfSteps;
	}

	public int getNumOfTries() {
		return numOfTries;
	}

	public void setNumOfTries(int numOfTries) {
		this.numOfTries = numOfTries;
	}

	public ArrayList<GameObject> getObjReferences() {
		return objReferences;
	}

	public void setObjReferences(ArrayList<GameObject> objReferences) {
		this.objReferences = objReferences;
	}

	public HashMap<Position2D, ArrayList<GameObject>> getPositionObjectLayout() {
		return positionObjectLayout;
	}

	public void setPositionObjectLayout(HashMap<Position2D, ArrayList<GameObject>> positionObjectLayout) {
		this.positionObjectLayout = positionObjectLayout;
	}
}
