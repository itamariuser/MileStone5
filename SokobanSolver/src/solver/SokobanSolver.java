package solver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import algorithm.Action;
import algorithm.AndPredicate;
import algorithm.Plannable;
import algorithm.Predicate;
import algorithm.SimplePredicate;
import data.GoalNotFoundException;
import data.Searchable;
import data.Searcher;
import data.Solution;
import data.State;
import gameObjects.Crate;
import gameObjects.GameObject;
import gameObjects.GameObjectFactory;
import gameObjects.GoalPoint;
import gameObjects.MainCharacter;
import gameObjects.Position2D;
import gameObjects.Wall;
import model.Level2D;

public class SokobanSolver implements Plannable<Position2D> {//Generates a new searchable, everytime goalState= where we want to go for "Move player" action
	Searcher<Position2D> searcher;
	AndPredicate<Position2D> kb;
	AndPredicate<Position2D> goal;
	int[] bounds;
	public SokobanSolver(Level2D level, Searcher<Position2D> s) {
		kb=new AndPredicate<Position2D>();
		//generate knowledgebase
		HashMap<Position2D, ArrayList<GameObject>> layout=level.getPositionObjectLayout();
		layout.keySet().forEach((p) -> level.getGameObjectArrayOf(p).forEach((gameObj)->kb.add(generatePredicate(gameObj,level))));
		
		//generate goal
		AndPredicate<Position2D> pToReturn = new AndPredicate<Position2D>();
		level.getObjReferences().forEach((g)-> { 
			if(g instanceof GoalPoint) 
				level.getPositionsOfObject(g).forEach((p)->pToReturn.add(new SimplePredicate<Position2D>("Crate_At",p)));});
		goal = pToReturn;
		//WE DIDNT CHECK IF OUT OF BOUNDS
		this.searcher=s;
	}
	
	public Level2D toLevel(AndPredicate<Position2D> knowledgeB)
	{
		Level2D levelToReturn = new Level2D();
		GameObjectFactory factory = new GameObjectFactory();
		GameObject obj;
		ArrayList<GameObject> objRef = new ArrayList<GameObject>();
		ArrayList<GameObject> arr = new ArrayList<GameObject>();
		for (Predicate<Position2D> p : knowledgeB.getComponents()) {
			Position2D pos = p.getData();
			if(p.getName().startsWith("Crate_At"))
			{
				obj = factory.createObject(Crate.class.getName().toString());
				obj.setCurrentLocation(pos);
				arr.add(obj);
				levelToReturn.putInLayout(pos, arr);
				objRef.add(obj);
				levelToReturn.setObjReferences(objRef);
			}
			else if(p.getName().startsWith("MainCharacter_At"))
			{
				obj = factory.createObject(MainCharacter.class.getName().toString());
				obj.setCurrentLocation(pos);
				arr.add(obj);
				levelToReturn.putInLayout(pos, arr);
				objRef.add(obj);
				levelToReturn.setObjReferences(objRef);
			}
			else if(p.getName().startsWith("Wall_At"))
			{
				obj = factory.createObject(Wall.class.getName().toString());
				obj.setCurrentLocation(pos);
				arr.add(obj);
				levelToReturn.putInLayout(pos, arr);
				objRef.add(obj);
				levelToReturn.setObjReferences(objRef);
			}
			else if(p.getName().startsWith("Crate_At"))
			{
				obj = factory.createObject(Crate.class.getName().toString());
				obj.setCurrentLocation(pos);
				arr.add(obj);
				levelToReturn.putInLayout(pos, arr);
				objRef.add(obj);
				levelToReturn.setObjReferences(objRef);
			}
			arr.clear();
		}
		return levelToReturn;
	}
	
	
	
	/**
	 * return true only if p1 contradicts p2
	 *  
	 */
	@Override
	public boolean contradicts(Predicate<Position2D> p1, Predicate<Position2D> p2) {//p1 => ~p2
		String name1 = p1.getName();
		
		if(p2==null)
		{
			System.out.println();
		}
		String name2 = p2.getName();
		
		if(p1 instanceof SimplePredicate)
		{
			if(p2 instanceof AndPredicate)
			{
				boolean doesContra=false;
				AndPredicate<Position2D> temp=(AndPredicate<Position2D>)p2;
				for (Predicate<Position2D> predicate : temp.getComponents()) {
					doesContra=contradicts(p1, predicate);
				}
				return doesContra;
			}
			if(p2 instanceof SimplePredicate)
			{
				if(p1.getData().equals(p2.getData()))
				{
					if(name1.startsWith("Wall_At"))
					{
						if(name2.startsWith("Crate_At")) return true;
						else if(name2.startsWith("MainCharacter_At")) return true;
						else if(name2.startsWith("GoalPoint_At")) return true;
					}
					else if(name1.startsWith("MainCharacter_At"))
					{
						if(name2.startsWith("Wall_At")) return true;
						else if(name2.startsWith("Crate_At")) return true;
					}
					else if(name1.startsWith("Crate_At"))
					{
						if(name2.startsWith("Wall_At")) return true;
						else if(name2.startsWith("MainCharacter_At")) return true;
						else if(name2.startsWith("Crate_At") &&  !(name1.equals(name2)))  return true;
					}
					else if(name1.startsWith("GoalPoint_At"))
					{
						if(name2.startsWith("GoalPoint_At") &&  !(name1.equals(name2)))  return true;
						else if(name2.startsWith("Wall_At")) return true;
					}
					else if(name1.startsWith("BlankSpace_At"))
					{
						if(name2.startsWith("Wall_At")) return true; //TODO
					}

				}
				return false;
			}
		}
		if(p1 instanceof AndPredicate )
		{
			if(p2 instanceof SimplePredicate)
			{
				AndPredicate<Position2D> temp=new AndPredicate<Position2D>((AndPredicate<Position2D>)p1);
				
				//max
				int maxY=-1;
				int maxX=-1;
				for (Predicate<Position2D> pred : temp.getComponents()) {
					Position2D currentPos=pred.getData();
					if(maxY<currentPos.getY()) maxY=currentPos.getY();
					if(maxX<currentPos.getX()) maxX=currentPos.getX();
					if(contradicts(pred,p2)) return true;
				}
				if(p2.getData().getX()>maxX || p2.getData().getY()>maxY)
				{
					if(name2.startsWith("Wall_At")) return false;
					return true;
				}
				return false;
				
			}
			else if( p2 instanceof AndPredicate )
			{
				AndPredicate<Position2D> temp1=(AndPredicate<Position2D>)p1;
				boolean doesContra=false;
				for (Predicate<Position2D> p11 : temp1.getComponents()) {
					doesContra=contradicts(p11,p2);
				}
				return doesContra;
			}
		}
		return false;
	}
	@Override
	public Predicate<Position2D> getGoal() {
		return this.goal;
	}
	@Override
	public AndPredicate<Position2D> getKnowledgebase() {
		return this.kb;
	}
	
	private Predicate<Position2D> generatePredicate(GameObject gameObj,Level2D level)//TODO: generate predicate for each game object
	{
		
		return new SimplePredicate<Position2D>(gameObj.getClass().getSimpleName()+"_At",getPosOf(gameObj,level) );
	}
	
	private Position2D getPosOf(GameObject obj,Level2D level)
	{
		for(Position2D pos : level.getPositionObjectLayout().keySet()) {
			if(level.getPositionObjectLayout().get(pos).contains(obj)) return pos;
		}
		return null;
	}
	
	@Override
	public Action<Position2D> getSatisfyingAction(Predicate<Position2D> arg0) {
		List<Action<Position2D>> list=getSatisfyingActions(arg0); 
		return list.get(0);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Action<Position2D>> getSatisfyingActions(Predicate<Position2D> top) {
 		int x=top.getData().getX();
		int y=top.getData().getY();
		
		if(top.getName().startsWith("Crate_At"))// change to fit class CratePredicate<Position>
		{
			if(satisfies(kb,top))//
			{
				ArrayList<Action<Position2D>> listu=new ArrayList<>();
				ArrayList<Predicate<Position2D>> z=new ArrayList<Predicate<Position2D>>();
				z.add(top);
				AndPredicate<Position2D> ay=new AndPredicate<>(z);
				AndPredicate<Position2D> s=new AndPredicate<Position2D>();
				listu.add(new Action<Position2D>("Crate_At",s,ay));
			}
			ArrayList<Action<Position2D>> possibleActions= new ArrayList<>();//this list will be returned
			ArrayList<Predicate<Position2D>> toGenerate=new ArrayList<>();//all items in this list will be generated for each action
			ArrayList<Predicate<Position2D>> toRemove=new ArrayList<>();//items in this list will be removed after each generation of action
			Action<Position2D> act=new Action<Position2D>("Move_Crate_To");
			act.setEffects(new AndPredicate<>(top));//set effects to be "Crate at position "(x,y)", "No non solid at position (x,y)" (which means crate is in pos)
			SimplePredicate<Position2D> MainCharacterIsAtPosition=new SimplePredicate<Position2D>("MainCharacter_At",null);//add player in position to push the crate
			
			SimplePredicate<Position2D> wallAtNextPos=new SimplePredicate<Position2D>("Wall_At",new Position2D(x,y));
			SimplePredicate<Position2D> crateAtPosition=new SimplePredicate<>("Crate_At",new Position2D(x,y));
			
			if(contradicts(kb,wallAtNextPos))//check if no wall at nextPos
			{
				if(!satisfies(kb, crateAtPosition))//it THE FCKING GOAL
				{
					SimplePredicate<Position2D> playerNewPos=new SimplePredicate<Position2D>("MainCharacter_At");
					SimplePredicate<Position2D> CrateIsAtPosition=new SimplePredicate<Position2D>("Crate_At",null);//crate is at position to be pushed
					
					
					//push right
					CrateIsAtPosition.setData(new Position2D(x-1,y));
					MainCharacterIsAtPosition.setData(new Position2D(x-2,y));
					if(contradicts(kb, new SimplePredicate<>("Wall_At",MainCharacterIsAtPosition.getData())))
					{
						playerNewPos.setData(new Position2D(x-1,y));
						updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(MainCharacterIsAtPosition));
						act.setEffects(new AndPredicate<>(top,new SimplePredicate<Position2D>(playerNewPos)));
						act.setPreconditions(new AndPredicate<Position2D>(toGenerate));
						possibleActions.add(new Action<Position2D>(act.getName(),act.getPreconditions(),act.getEffects()));
					}
					//up
					CrateIsAtPosition.setData(new Position2D(x,y+1));
					MainCharacterIsAtPosition.setData(new Position2D(x,y+2));
					if(contradicts(kb, new SimplePredicate<>("Wall_At",MainCharacterIsAtPosition.getData())))
					{
						playerNewPos.setData(new Position2D(x,y+1));
						updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(MainCharacterIsAtPosition));
						act.setEffects(new AndPredicate<>(top,new SimplePredicate<Position2D>(playerNewPos)));
						act.setPreconditions(new AndPredicate<Position2D>(toGenerate));
						possibleActions.add(new Action<Position2D>(act.getName(),act.getPreconditions(),act.getEffects()));
					}
					//left
					CrateIsAtPosition.setData(new Position2D(x+1,y));
					MainCharacterIsAtPosition.setData(new Position2D(x+2,y));
					if(contradicts(kb, new SimplePredicate<>("Wall_At",MainCharacterIsAtPosition.getData())))
					{
						playerNewPos.setData(new Position2D(x+1,y));
						updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(MainCharacterIsAtPosition));
						act.setEffects(new AndPredicate<>(top,new SimplePredicate<Position2D>(playerNewPos)));
						act.setPreconditions(new AndPredicate<Position2D>(toGenerate));
						possibleActions.add(new Action<Position2D>(act.getName(),act.getPreconditions(),act.getEffects()));
					}
					
					//down
					CrateIsAtPosition.setData(new Position2D(x,y-1));
					MainCharacterIsAtPosition.setData(new Position2D(x,y-2));
					if(contradicts(kb, new SimplePredicate<>("Wall_At",MainCharacterIsAtPosition.getData())))
					{
						playerNewPos.setData(new Position2D(x,y-1));
						updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(MainCharacterIsAtPosition));
						act.setEffects(new AndPredicate<>(top,new SimplePredicate<Position2D>(playerNewPos)));
						act.setPreconditions(new AndPredicate<Position2D>(toGenerate));
						possibleActions.add(new Action<Position2D>(act.getName(),act.getPreconditions(),act.getEffects()));
					}
				}
				else if(satisfies(kb, crateAtPosition))//there's a crate in the position
				{
					ArrayList<Action<Position2D>> list=new ArrayList<>();
					list.add(getSatisfyingAction(new SimplePredicate<>("Crate_At",new Position2D(x+1,y))));
					list.add(getSatisfyingAction(new SimplePredicate<>("Crate_At",new Position2D(x-1,y))));
					list.add(getSatisfyingAction(new SimplePredicate<>("Crate_At",new Position2D(x,y+1))));
					list.add(getSatisfyingAction(new SimplePredicate<>("Crate_At",new Position2D(x,y-1))));
					return list;
					
				}
			}
			return possibleActions;
		}
		if(top.getName().startsWith("MainCharacter")){
			for (Predicate<Position2D> p : kb.getComponents()) {
				
				if(p.getName().startsWith("MainCharacter_At")){
					Searchable<Position2D> searchable=new Searchable<Position2D>() {
						
						@Override
						public State<Position2D> getInitialState() {
							State z=new State<Position2D>(new Position2D(p.getData()));
							return z;
						}
						
						@Override
						public State<Position2D> getGoalState() {
							State z= new State<Position2D>(new Position2D(top.getData()));
							return z;
						}
						
						@Override
						public PriorityQueue<State<Position2D>> getAllPossibleStates(State<Position2D> state) {
							int x=state.getLayout().getX();
							int y=state.getLayout().getY();
							PriorityQueue<State<Position2D>> pq=new PriorityQueue<>(new Comparator<State<Position2D>>() {

								@Override
								public int compare(State<Position2D> o1, State<Position2D> o2) {
									return (o1.getCostFromParent()-o2.getCostFromParent());
								}
							});
							
							SimplePredicate<Position2D> wallAtNextPos=new SimplePredicate<Position2D>("Wall_At",null);
							
							wallAtNextPos.setData(new Position2D(x+1,y));//right state
							if(contradicts(kb,wallAtNextPos))
							{
								State<Position2D> state1=new State<>(new Position2D(x+1,y));
								pq.add(state1);
							}
							
							wallAtNextPos.setData(new Position2D(x-1,y));//left state
							if(contradicts(kb,wallAtNextPos))
							{
								State<Position2D> state1=new State<>(new Position2D(x-1,y));
								pq.add(state1);
							}
							
							wallAtNextPos.setData(new Position2D(x,y+1));//up state
							if(contradicts(kb,wallAtNextPos))
							{
								State<Position2D> state1=new State<>(new Position2D(x,y+1));
								pq.add(state1);
							}
							
							wallAtNextPos.setData(new Position2D(x,y-1));//down state
							if(contradicts(kb,wallAtNextPos))
							{
								State<Position2D> state1=new State<>(new Position2D(x,y-1));
								pq.add(state1);
							}
							return pq;
						}
					};
					try {
						searcher.search(searchable);
					} catch (GoalNotFoundException e) {
						e.getMessage();
					}
					
					
//					LinkAction<Position2D> linkAction=new LinkAction<>("");
//					
//					for (State<Position2D> s : solution.getPathToVictory()) {
//						AndPredicate<Position2D> preconditions=null;
//						AndPredicate<Position2D> effects=new AndPredicate<>();
//						ArrayList<Predicate<Position2D>> precList=new ArrayList<>();
//						ArrayList<Predicate<Position2D>> effList=new ArrayList<>();	
//						if(s.getCameFromState() != null)
//						{
//							x = s.getCameFromState().getLayout().getX() - s.getLayout().getX();
//							y = s.getCameFromState().getLayout().getY() - s.getLayout().getY();
//							precList.add(new SimplePredicate<Position2D>("MainCharacter_At",s.getCameFromState().getLayout()));
//							effList.add(new SimplePredicate<Position2D>("MainCharacter_At",s.getLayout()));
//							preconditions=new AndPredicate<>(precList);
//							effects=new AndPredicate<>(effList);
//							Action<Position2D> action = new Action<Position2D>("Move_MainCharacter_To",preconditions,effects);
//							linkAction.getActions().add(action);
//							System.out.println();
//						}
//					}
//					LinkedList<Action<Position2D>> list=new LinkedList<>();
//					LinkedList<Predicate<Position2D>> efflist1=new LinkedList<>();
//					efflist1.add(new SimplePredicate<>("MainCharacter_At",top.getData()));
//					linkAction.setEffects(new AndPredicate<>(efflist1));
//					list.add(linkAction);
//					return list;
					
					
					Solution<Position2D> solution=searcher.backtrace(searchable.getGoalState(), searchable.getInitialState());
					//State->State->State
					
					ArrayList<State<Position2D>> list=solution.getPathToVictory();
					Predicate<Position2D> prec=new SimplePredicate<Position2D>("MainCharacter_At",list.get(0).getLayout());
					Predicate<Position2D> eff=new SimplePredicate<Position2D>("MainCharacter_At",list.get(list.size()-1).getLayout());
					ArrayList<String> array=new ArrayList<>();
					for(int i=1;i<list.size();i++)
					{
						Position2D currPos=list.get(i).getLayout();
						int xCurr=currPos.getX();
						int yCurr=currPos.getY();
						Position2D lastPos=list.get(i-1).getLayout();
						int xLast=lastPos.getX();
						int yLast=lastPos.getY();
						
						if(yCurr==yLast)
						{
							if(xCurr==xLast+1 )//2=>1 move left
							{
								array.add("Move left");
							}
							if(xCurr==xLast-1 )//1=>2 move right
							{
								array.add("Move right");
							}
						}
						else if(xCurr==xLast)
						{
							if(yCurr==yLast+1 )//2=>1 move up
							{
								array.add("Move up");
							}
							if(yCurr==yLast-1 )//1=>2 move down
							{
								array.add("Move down");
							}
						}		
					}
					Action<Position2D> ac=new Action<>("Move_MainCharacter_In_Direction",array);
					ac.setEffects(new AndPredicate<>(prec));
					ac.setPreconditions(new AndPredicate<>(eff));
					ArrayList<Action<Position2D>> arr=new ArrayList<>();
					arr.add(ac);
					return arr;
					//ITS TELEPORT
//					if(solution!=null)
//					{
//						ArrayList<Predicate<Position2D>> precList=new ArrayList<>();
//						AndPredicate<Position2D> preconditions=new AndPredicate<>(precList);
//						for (Predicate predicateOfJustice : kb.getComponents()) {
//							if(predicateOfJustice.getName().startsWith("MainCharacter_At"))
//							{
//								preconditions.add(predicateOfJustice);
//							}
//						}
//						AndPredicate<Position2D> effects=new AndPredicate<>(new SimplePredicate<Position2D>("MainCharacter_At",top.getData()));
//						Action<Position2D> action=new Action<>("Move_MainCharacter_To", preconditions, effects);
//						ArrayList<Action<Position2D>> a=new ArrayList<>();
//						a.add(action);
//						return arr;//so it only walks once
//						//thats good
//					}
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	/*
	 * add 
	 */
	private void updateLists(List<Predicate<Position2D>> toGenerate,List<Predicate<Position2D>> toRemove,Predicate<Position2D>...predicates )
	{
		toRemove.forEach((p)->toGenerate.remove(p));
		toRemove.clear();
		
		for (Predicate<Position2D> predicate : predicates) {
			toGenerate.add(predicate);
			toRemove.add(predicate);
		}
	}
	
	
	@Override
	public boolean satisfies(Predicate<Position2D> p1, Predicate<Position2D> p2) {
		//if p1 -> p2
		if(p2==null)
		{
			System.out.println();
		}
		if(contradicts(p1, p2)) return false;
		
		
		if(p1 instanceof AndPredicate)
		{
			if(p2 instanceof SimplePredicate)
			{
				AndPredicate<Position2D> s=((AndPredicate<Position2D>) p1);
				for (Predicate<Position2D> p : s.getComponents()) {
					if(satisfies(p, p2)) return true;
				}
				return false;
			}
			if(p2 instanceof AndPredicate)
			{
				return ((AndPredicate<Position2D>) p1).getComponents().containsAll(((AndPredicate<Position2D>) p2).getComponents());
			}
		}
//		if(p1 instanceof Action || p2 instanceof Action)
//			return false;
		boolean eq=p1.equals(p2);
		return eq;
	}
}
