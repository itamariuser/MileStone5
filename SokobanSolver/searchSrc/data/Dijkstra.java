package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Dijkstra<T> extends CommonSearcher<T> {
	int numOfEvaluatedNodes;
	PriorityQueue<State<T>> closed;
	HashMap<State<T>,Integer> distances;
	
	public Dijkstra() {
		numOfEvaluatedNodes=0;
		closed= new PriorityQueue<State<T>>();
		distances = new HashMap<State<T>,Integer>();
	}
	@Override
	public Solution<T> search(Searchable<T> s)
	{
		State<T> initialState=s.getInitialState();
		distances.put(initialState, 0);
		openList.add(initialState);
		while(!openList.isEmpty())
		{
			PriorityQueue<State<T>> neighbors=new PriorityQueue<State<T>>(new Comparator<State<T>>() {

				@Override
				public int compare(State<T> o1, State<T> o2) {
					return distances.get(o1)-distances.get(o2);
				}
			});
			neighbors=(PriorityQueue<State<T>>)s.getAllPossibleStates(openList.poll());
			putAllInf(s,distances);
			//discoverAndPutInfinity(neighbors);
			State<T> minState=neighbors.remove();
			openList.remove(minState);
			for (State<T> neighborState : neighbors) {
				if(distances.containsKey(minState))
				{
					int newDistance=distances.get(minState)+ neighborState.getCostFromParent();
					if(distances.get(neighborState)>newDistance)
					{
						neighborState.setCameFromState(minState);
						distances.put(neighborState, newDistance);
						
					}
				}
				else
				{
					int alt=neighborState.getCostFromParent();
					neighborState.setCameFromState(minState);
					distances.put(neighborState, alt);
				}
			}
			closed.add(minState);
		}
		return backtrace(s.getGoalState(), s.getInitialState());
	}
	
	private void putAllInf(Searchable<T> s,HashMap<State<T>,Integer> distances)
	{
		HashSet<State<T>> visited=new HashSet<>();
		State<T> z=s.getInitialState();
		visited.add(z);
		for (State<T> state : visited) {
			distances.put(state, Integer.MAX_VALUE);
			numOfEvaluatedNodes++;
		}
	}
	private State<T> chooseCheapestState(Collection<State<T>> s,Searchable<T> searchable)
	{
		State<T> stateMin=(State<T>) s.toArray()[0];
		for (State<T> state : s) 
		{
			if(distances.containsKey(stateMin))
			{
				if(distances.get(state)<=distances.get(stateMin))
				{
					stateMin=state;
				}
			}
		}
		return stateMin;
	}
	public int getNumberOfNodesEvaluated()
	{
		return numOfEvaluatedNodes;
	}
//	private void discoverAndPutInfinity(Collection<State<T>> neighbors)
//	{
//		for (State<T> state : neighbors) {
//			if(!openList.contains(state) && !closed.contains(state))
//			{
//				distances.put(state, Integer.MAX_VALUE);
//				openList.add(state);
//			}
//		}
//	}
	
	//SAGUR
	@Override
	public Solution<T> backtrace(State<T> goalState, State<T> initialState) {
		ArrayList<State<T>> pathToVictory = new ArrayList<State<T>>();
		State<T> tempState = goalState;
		while( tempState!=initialState) {
			pathToVictory.add(tempState);
			tempState = tempState.getCameFromState();
		}
		return new Solution<>(pathToVictory);
	}
}







































