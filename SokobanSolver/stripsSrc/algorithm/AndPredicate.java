package algorithm;

import java.util.ArrayList;
import java.util.List;

import gameObjects.Position2D;

public class AndPredicate<T> extends ComplexPredicate<T> {
	public AndPredicate(String name, List<Predicate<T>> components) {
		super(name, components);
	}
	@SafeVarargs
	public AndPredicate(Predicate<T>... preds) {
		super("Default AndPredicate",new ArrayList<Predicate<T>>());
		for (Predicate<T> predicate : preds) {
			this.add(predicate);
		}
	}
	
	public AndPredicate(AndPredicate<T> pred)
	{
		super(pred.name, pred.components);
	}
	
	public AndPredicate(List<Predicate<T>> preds) {
		super("Default AndPredicate",new ArrayList<Predicate<T>>());
		for (Predicate<T> predicate : preds) {
			this.add(predicate);
		}
	}
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n"+predicate.toString()+"\n");
		}
		return "** 'And' PREDICATE, Name: "+this.name+", preds:"+sb.toString();
	}
	
	public void update(AndPredicate<T> effects,Plannable<T> plannable) {
		//effects.getComponents().forEach((Predicate<T> p)->components.removeIf((Predicate<T> pr)->plannable.contradicts(p,pr)));
		ArrayList<Predicate<T>> toRemove= new ArrayList<>();
		for (Predicate<T> predicate : effects.getComponents()) {
			for (Predicate<T> pr : components) {
				if(plannable.contradicts(predicate, pr)) toRemove.add(pr);
			}
		}
		components.removeAll(toRemove);
		components.addAll(effects.getComponents());
		System.out.println();
	}
}
