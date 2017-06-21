package algorithm;

import java.util.ArrayList;
import java.util.List;

public class ComplexPredicate<T> extends Predicate<T> {
	protected List<Predicate<T>> components;

	public ComplexPredicate(String name, List<Predicate<T>> components) {
		super(name);
		this.components = components;
	}

	public List<Predicate<T>> getComponents() {
		return components;
	}

	public void setComponents(List<Predicate<T>> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Predicate<T> predicate : components) {
			sb.append("\n" + predicate.toString() + "\n");
		}
		return "** COMPLEX PREDICATE, Name: " + this.name + ", preds:" + sb.toString();
	}

	public void update(AndPredicate<T> effects,Plannable<T> plannable) {
		effects.getComponents().forEach((Predicate<T> p)->components.removeIf((Predicate<T> pr)->plannable.contradicts(p,pr)));
		components.addAll(effects.getComponents());
		
	}
	
	public void add(Predicate<T> p)
	{
		if(this.components==null)
		{
			components=new ArrayList<Predicate<T>>();
		}
		this.components.add(p);
		
	}
}
