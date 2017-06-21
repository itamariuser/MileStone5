package algorithm;

import java.util.ArrayList;

public class Action<T> extends Predicate<T> {
	

	protected AndPredicate<T> preconditions;
	
	protected AndPredicate<T> effects;
	
	protected ArrayList<String> sub;

	
	public AndPredicate<T> getPreconditions() {
		return preconditions;
	}


	public void setPreconditions(AndPredicate<T> preconditions) {
		this.preconditions = preconditions;
	}


	public AndPredicate<T> getEffects() {
		return effects;
	}


	public void setEffects(AndPredicate<T> effects) {
		this.effects = effects;
	}


	public Action(String name) {
		super(name);
	}
	
	public Action(String name,ArrayList<String> sub) {
		super(name);
		this.sub=sub;
	}
	
	


	public ArrayList<String> getSub() {
		return sub;
	}


	public void setSub(ArrayList<String> sub) {
		this.sub = sub;
	}


	@Override
	public String toString() {
		return "Type: "+this.getClass().getSimpleName()+"\nName: "+this.name+"\n Preconditions: "+this.preconditions.toString()+"\n Effects: "+this.effects.toString();
	}
	public Action(String name,AndPredicate<T> preconditions,AndPredicate<T> effects) {
		super("DEFAULT");
		this.preconditions=preconditions;
		this.effects=effects;
		this.name=name;
	}
}
