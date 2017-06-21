package gameObjects;

import java.util.HashMap;

public class GameObjectFactory {
		HashMap<String,Creator> creatorMap;
		public interface Creator {
			
			public GameObject create ();	
			}
		
		public GameObjectFactory()
		{
			creatorMap= new HashMap<String,Creator>();
			creatorMap.put(new Crate().getClass().toString(), new CrateCreator());
			creatorMap.put(new Wall().getClass().toString(), new WallCreator());
			creatorMap.put(new GoalPoint().getClass().toString(), new GoalPointCreator());
			creatorMap.put(new MainCharacter().getClass().toString(), new MainCharacterCreator());
			creatorMap.put(new BlankSpace().getClass().toString(), new BlankSpaceCreator());
			
		}
		
		private class CrateCreator implements Creator {
			public GameObject create(){
				return new Crate();
			}
		}
		
		private class WallCreator implements Creator {
			public GameObject create(){
				return new Wall();
			}
		}
		
		private class GoalPointCreator implements Creator {
			public GameObject create(){
				return new GoalPoint();
			}
		}
		private class BlankSpaceCreator implements Creator
		{
			public GameObject create(){
				return new BlankSpace();
			}
		}
		
		private class MainCharacterCreator implements Creator {
			public GameObject create(){
				return new MainCharacter();
			}
			
		}
		
		public GameObject createObject (String type)
		{
			Creator c=creatorMap.get(type);
			if(c!=null)
				return c.create();
			return null;
		}
		
		public void putObjectCreator(String className, Creator creator)
		{
			creatorMap.put(className, creator);
		}

		public HashMap<String, Creator> getCreatorMap() {
			return creatorMap;
		}

		public void setCreatorMap(HashMap<String, Creator> creatorMap) {
			this.creatorMap = creatorMap;
		}
	}
	
	

