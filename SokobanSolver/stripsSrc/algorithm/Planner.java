package algorithm;

public interface Planner<T> {
	public Plan<T> plan(Plannable<T> plannable);
	
}
