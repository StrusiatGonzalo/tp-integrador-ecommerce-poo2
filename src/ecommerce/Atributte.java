package ecommerce;

public abstract class Atributte<T> {
	protected String name;
	protected T value;
	
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
	
	public abstract  boolean compareTo(String value);
	
	public abstract boolean hasValue();
	
	public abstract String showValue();
	
	protected abstract T parseValue(String value);
		
}

