package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import gameObjects.Position2D;

public class BestFirstSearcher<T> extends CommonSearcher<T> {
	
	private HashSet<State<T>> closed;
	HashMap<State<T>,Integer> distances;
	HashMap<State<T>,State<T>> cameFromStates=new HashMap<>();

	public BestFirstSearcher() {
		 closed = new HashSet<State<T>>();
		 distances=new HashMap<>();
		 openList=new PriorityQueue<>(new Comparator<State<T>>() {
			@Override
			public int compare(State<T> arg0, State<T> arg1) {
				if(getEqualData(distances, arg0)==null)
				{
					System.out.println();
				}
				if(distances.get(arg1)==null)
				{
					System.out.println();
				}
				return distances.get(arg0)-distances.get(arg1);
			}
		});
	}
	
	private boolean containsData(Collection<State<T>> list,State<T> s)
	{
		for (State<T> state : list) {
			if(s.getLayout().equals(state.getLayout()))
				return true;
		}
		return false;
	}
	private Integer getEqualData(HashMap<State<T>,Integer> map,State<T> state)
	{
		for (State<T> s1 : map.keySet()) {
			if(s1.getLayout().equals(state.getLayout())){
				return map.get(state);
			}
		}
		
		return null;
	}
	
	private State<T> getEqualDataS(HashMap<State<T>,State<T>> map,State<T> state)
	{
		if(state.getLayout().equals(new Position2D(1,3)))
		{
			System.out.println();
		}
		for (State<T> s1 : map.keySet()) {
			if(state.getLayout().equals(s1.getLayout())){
				return map.get(state);
			}
		}
		
		return null;
	}

	@Override
	public Solution<T> search(Searchable<T> s) throws GoalNotFoundException {
		State<T> initialState=s.getInitialState();
		State<T> goalState=s.getGoalState();
		
		openList.add(initialState);
		distances.put(initialState, 0);
		while (!openList.isEmpty())
		{
			State<T> state = openList.poll();
			closed.add(state);
			if (state.equals(goalState)) 
			{
				return backtrace(goalState, initialState);
			}
			else 
			{
				PriorityQueue<State<T>> nextStates = s.getAllPossibleStates(state);
				for (State<T> tempState : nextStates)
				{
					if(!containsData(closed, tempState))
					{
						if (!containsData(openList, tempState)) 
						{
							cameFromStates.put(tempState, state);
							tempState.setCameFromState(state);
							distances.put(tempState, Integer.MAX_VALUE);
							openList.add(tempState);
						}
						else if(getEqualData(distances, tempState)> getEqualData(distances, state) + tempState.getCostFromParent())//if the new path to tempState is better than the previous path to tempState
						{
							if(!openList.contains(tempState))
							{
								openList.add(tempState);
							}
							else
							{
								openList.remove(tempState);
								distances.put(tempState, distances.get(state)+tempState.getCostFromParent());
								openList.add(tempState);
							}
						}
					}
				}
			}
		}
		throw new GoalNotFoundException();

	}

	@Override
	public Solution<T> backtrace(State<T> goalState,State<T> initialState) {
		ArrayList<State<T>> pathToVictory = new ArrayList<State<T>>();
		int cost=0;
		State<T> tempState = goalState;
		do
		{
			pathToVictory.add(tempState);
			cost++;
			tempState.setCameFromState(s(cameFromStates, tempState));
			tempState=s(cameFromStates, tempState);
			if(tempState.equals(initialState))
			{
				pathToVictory.add(initialState);
			}
		}while(s(cameFromStates, tempState)!=null);
		return new Solution<>(pathToVictory,cost);
	}
	
	private State<T> s(HashMap<State<T>,State<T>> fck, State<T> ths)
	{
		for (State<T> state : fck.keySet()) {
			if(ths.equals(state))
			{
				return cameFromStates.get(state);
			}
		}
		return null;
	}
}
