package ecommerce;

public abstract class Atributte<T> {
	private String name;
	private T value;
	
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public abstract boolean hasValue();
	
	public abstract String showValue();
	
	protected abstract T parseValue(String value);
		
}

