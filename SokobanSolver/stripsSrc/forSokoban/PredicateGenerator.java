package forSokoban;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import algorithm.Action;
import algorithm.AndPredicate;
import algorithm.Plannable;
import algorithm.Position;
import algorithm.Predicate;
import algorithm.SimplePredicate;

public class PredicateGenerator  {// Convert level
																// to
																// predicates,
																// TODO: make
																// object
																// adapter from
																// sokoban policy
																// to Plannable
	
	static public Plannable<Position> readFile(String fileName) {
		try {
			
			ArrayList<char[]> level = new ArrayList<char[]>();//level.get(x)[y]
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
				level.add(line.toCharArray());
			}
			br.close();
			AndPredicate<Position> kb=getKB(level);
			Plannable<Position> plannable=new Plannable<Position>() {			
				
				@Override
				public String toString() {
					// TODO Auto-generated method stub
					return super.toString();
				}
				
				@Override
				public Action<Position> getSatisfyingAction(Predicate<Position> top) {//data: (x ,y)
						List<Action<Position>> possibleActions=getSatisfyingActions(top);
						//System.out.println(possibleActions.get(0));//it's ok, null pointer because we haven't programmed getSatisfyingActions for "Player1" in position
						int fitCount=0;
						int maxFit=-1;
						Action<Position> mostFitAction=null;
						for (Action<Position> act : possibleActions) {
							fitCount=0;
							for (Predicate<Position> precondition : act.getPreconditions().getComponents()) {
								fitCount = this.satisfies(kb,precondition)? fitCount+1 : fitCount;
							}
							if(fitCount>maxFit)
							{
								maxFit=fitCount;
								mostFitAction=act;
							}
						}
					return mostFitAction;
				}
				
				@Override
				public AndPredicate<Position> getKnowledgebase() {
					return kb;
				}
				
				@Override
				public Predicate<Position> getGoal() {
					AndPredicate<Position> goal=new AndPredicate<>("Goal_Predicates",new ArrayList<Predicate<Position>>() );
					int goalCount=0;
					int index=-1;
					int goalIndex=0;
					for (Predicate<Position> p: kb.getComponents()) {
						index++;
						if(p.getName().startsWith("Goal "))//find goals
						{
							goalCount++;
							goalIndex=index;
							goal.add(new SimplePredicate<Position>("Crate #?",p.getData()));
						}
					}
					if(goalCount<=1)
					{
						SimplePredicate<Position> temp=new SimplePredicate<Position>("Crate #?",kb.getComponents().get(goalIndex).getData());//p.getData()
						return temp;
					}
					return goal;
				}

				@Override
				public List<Action<Position>> getSatisfyingActions(Predicate<Position> top) {//We know for sure that top is not satisfied
					int x=top.getData().getX();
					int y=top.getData().getY();
					if(top.getName().startsWith("Crate"))// change to fit class CratePredicate<Position>
					{
						ArrayList<Action<Position>> possibleActions= new ArrayList<>();//this list will be returned
						ArrayList<Predicate<Position>> toGenerate=new ArrayList<>();//all items in this list will be generated for each action
						ArrayList<Predicate<Position>> toRemove=new ArrayList<>();//items in this list will be removed afer each generation of action
						Action<Position> act=new Action<Position>("Move_Crate_To_Position");
						act.setEffects(new AndPredicate<>(new SimplePredicate<>("Crate #?",new Position(x,y)),new NotPredicate<Position>(new SimplePredicate<Position>("Non Solid", new Position(x,y)))));//set effects to be "Crate at position "(x,y)", "No non solid at position (x,y)" (which means crate is in pos)
						if(this.satisfies(kb,new SimplePredicate<>("Crate #?",new Position(x,y))))//if there's a solid at position, then add targetSpaceIsFree predicate
						{
							toGenerate.add(new NotPredicate<>(new SimplePredicate<Position>("Crate",new Position(x,y))));//add a predicate: no crate in next point
						}
						SimplePredicate<Position> player1IsAtPosition=new SimplePredicate<Position>("Player1",null);//add player in position to push the crate
						SimplePredicate<Position> CrateIsAtPosition=new SimplePredicate<Position>("Crate #?",null);//crate is at position to be pushed
						if(this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x,y)))))
						{
							//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)
							if(this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x-2,y)))) && this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x-1,y)))) )
							{
								CrateIsAtPosition.setData(new Position(x-1,y));//TODO: add position checking
								player1IsAtPosition.setData(new Position(x-2,y));
								updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(player1IsAtPosition));
								act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to right
								possibleActions.add(new Action<Position>(act.getName(),act.getPreconditions(),act.getEffects()));
							}
							
							//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)
							if(this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x,y-2)))) && this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x,y-1)))) )
							{
								CrateIsAtPosition.setData(new Position(x,y-1));
								player1IsAtPosition.setData(new Position(x,y-2));
								
								updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(player1IsAtPosition));
								act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to down
								possibleActions.add(new Action<Position>(act.getName(),act.getPreconditions(),act.getEffects()));
							}
							
							//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)
							if(this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x+2,y)))) && this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x+1,y)))) )
							{
								CrateIsAtPosition.setData(new Position(x+1,y));
								player1IsAtPosition.setData(new Position(x+2,y));
								
								updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(player1IsAtPosition));
								act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to left
								possibleActions.add(new Action<Position>(act.getName(),act.getPreconditions(),act.getEffects()));
							}
							
							//TODO: if no wall at next position, then add (still need to check if there's a crate in next pos)		
							if(this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x,y+2)))) && this.satisfies(kb,new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x,y+1)))) )
							{
								CrateIsAtPosition.setData(new Position(x,y+1));
								player1IsAtPosition.setData(new Position(x,y+2));
								
								updateLists(toGenerate,toRemove,new SimplePredicate<>(CrateIsAtPosition),new SimplePredicate<>(player1IsAtPosition));
								act.setPreconditions(new AndPredicate<Position>(toGenerate));//push crate to up
								possibleActions.add(new Action<Position>(act.getName(),act.getPreconditions(),act.getEffects()));
							}
						}
						StringBuilder actionsToString= new StringBuilder();
						for (Action<Position> action : possibleActions) {
							actionsToString.append(action.toString()+"\n");
						}
						
						System.out.println("GENERATED FOR PREDICATE:\n"+top.toString()+"\nNEW ACTIONS: \n"+actionsToString.toString());
						return possibleActions;
					}
					
					if(top.getName().startsWith("No Crate"))// if location needs to be free
					{
						// generate all the ways to move the crate, AKA crate to the top, bottom, right, left of this position
						ArrayList<Action<Position>> actions=new ArrayList<>();
						SimplePredicate<Position> pred=new SimplePredicate<>("Crate #?");
						pred.setData(new Position(x+1,y));
						actions.addAll(getSatisfyingActions(new SimplePredicate<Position>(pred)));
						pred.setData(new Position(x-1,y));
						actions.addAll(getSatisfyingActions(new SimplePredicate<Position>(pred)));
						pred.setData(new Position(x,y+1));
						actions.addAll(getSatisfyingActions(new SimplePredicate<Position>(pred)));
						pred.setData(new Position(x,y-1));
						actions.addAll(getSatisfyingActions(new SimplePredicate<Position>(pred)));
						return actions;
					}
					
					//TODO: if there's already a player in the position, activate Searcher (from searchLib)
					if(top.getName().startsWith("Player1")){
						
						if(this.satisfies(kb, new NotPredicate<>(new SimplePredicate<Position>("Wall", new Position(x,y)))))
						{
							//Use searcher
						}
					}
					return null;
				}

				@Override
				public boolean contradicts(Predicate<Position> pred1, Predicate<Position> pred2) {//TODO: WORK: decide which contradict which
					if(pred1.getData().equals(pred2.getData()))
					{
						if(pred1.getName().startsWith("Wall"))
						{
								if(pred2.getName().startsWith("Player1")) return true;
								if(pred2.getName().startsWith("Crate")) return true;
								if(pred2.getName().startsWith("Goal")) return true;
								
						}
						
						if(pred1.getName().startsWith("Crate"))
						{
							if(pred2.getName().startsWith("Player1")) return true;
							if(pred2.getName().startsWith("Wall")) return true;
						}
					}
					return false;
				}

				@Override
				public boolean satisfies(Predicate<Position> pred1, Predicate<Position> pred2) {//TODO: WORK: decide which contradict which
					
					
					return false;
				}
			};	
			return plannable;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	@SafeVarargs
	private static void updateLists(List<Predicate<Position>> toGenerate,List<Predicate<Position>> toRemove,Predicate<Position>...predicates )
	{
		toRemove.forEach((Predicate<Position> p)->
		{
			toRemove.remove(p);
			toGenerate.remove(p);
		});
		for (Predicate<Position> predicate : predicates) {
			toGenerate.add(predicate);
			toRemove.add(predicate);
		}
	}
	
	static public AndPredicate<Position> getKB(ArrayList<char[]> level) {//TODO: Remove some of the 
		
		AndPredicate<Position> kb = new AndPredicate<>("Knowledge base", null);
		int crateCount = 0;
		int goalCount = 0;
		for (int i = 0; i < level.size(); i++) {
			for (int j = 0; j < level.get(i).length; j++) {
				switch (level.get(i)[j]) {
				case ('#'):
					kb.add(new SimplePredicate<Position>("Wall", new Position(i,j)));
					break;
				case (' '):
					kb.add(new SimplePredicate<Position>("BlankSpace", new Position(i,j)));
					//kb.add(new SimplePredicate<Position>("Non_Solid", new Position(i,j)));
					//kb.add(new SimplePredicate<Position>("No Crate", new Position(i,j)));
					//kb.add(new NotPredicate<>( new SimplePredicate<Position>("Wall", new Position(i,j))));
					break;
				case ('A'):
					kb.add(new SimplePredicate<Position>("Player1", new Position(i,j)));
					//kb.add(new SimplePredicate<Position>("No Crate", new Position(i,j)));
					//kb.add(new NotPredicate<>( new SimplePredicate<Position>("Wall", new Position(i,j))));
					break;
				case ('@'):
					kb.add(new SimplePredicate<Position>("Crate #" + (crateCount++), new Position(i,j)));
					//kb.add(new NotPredicate<>( new SimplePredicate<Position>("Wall", new Position(i,j))));
					break;
				case ('o'):
					kb.add(new SimplePredicate<Position>("Goal #" + (goalCount++), new Position(i,j)));
					//kb.add(new SimplePredicate<Position>("Non_Solid", new Position(i,j)));
					//kb.add(new SimplePredicate<Position>("No Crate", new Position(i,j)));
					//kb.add(new NotPredicate<>( new SimplePredicate<Position>("Wall", new Position(i,j))));
					break;
				default:
					break;
				}
			}
		}
		return kb;
	}
}
