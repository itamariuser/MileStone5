package algorithm;

public class SimplePredicate<T> extends Predicate<T> {

	public SimplePredicate(String name,T data) {
		super(name,data);
	}

	public SimplePredicate(String name) {
		super(name);
	}
	public SimplePredicate(SimplePredicate<T> other) {
		super("DEFAULT");
		this.name=other.name;
		this.data=other.data;
	}
}
