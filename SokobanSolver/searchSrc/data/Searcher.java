package data;

public interface Searcher<T> {
	public Solution<T> search(Searchable<T> s) throws GoalNotFoundException;
	public int getNumberOfNodesEvaluated();
	Solution<T> backtrace(State<T> goalState, State<T> initialState);
}
