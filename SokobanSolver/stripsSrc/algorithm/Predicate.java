package algorithm;

public abstract class Predicate<T> {
	
	T data;
	protected String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Predicate(String name) {
		this.name = name;
	}

	public Predicate(String name, T data) {
		this.name = name;
		this.data = data;
	}

	@Override
	public int hashCode() {
		return data.hashCode();
	}

	@Override
	public String toString() {
		return "Type: "+this.getClass().getSimpleName()+"\nName: "+this.name+"\nData: "+data.toString();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Predicate)
		{
			@SuppressWarnings("unchecked")
			Predicate<T> pred=(Predicate<T>) obj;
			return (this.data.equals(pred.data) && this.name.equals(pred.name));
		}
		return false;
	}
}
