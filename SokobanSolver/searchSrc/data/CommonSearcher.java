package data;

import java.util.PriorityQueue;

public abstract class CommonSearcher<T> implements Searcher<T> {
	protected PriorityQueue<State<T>> openList;//Maybe priority queue?
	private int evaluatedNodes;

	public CommonSearcher() {
		evaluatedNodes = 0;
	}

	protected State<T> pollOpenList() {
	evaluatedNodes++;
	return openList.poll();
	}

	@Override
	public int getNumberOfNodesEvaluated() {
	return evaluatedNodes;
	}

}
