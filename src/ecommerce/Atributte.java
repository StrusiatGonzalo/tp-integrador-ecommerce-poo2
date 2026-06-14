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
	
	public boolean compareTo(String value) {
		return getValue().equals(parseValue(value));
	}
	
	public abstract boolean hasValue();
	
	public abstract String showValue();
	
	protected abstract T parseValue(String value);
		
}

