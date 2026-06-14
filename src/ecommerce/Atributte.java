package ecommerce;

public abstract class Atributte<T> {
	private String description;
	private T value;
	
	public String getDescription() {
		return description;
	}
	
	public T getValue() {
		return value;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public abstract boolean hasValue();
	
	public abstract String showValue();
	
	protected abstract T parsearValue(String value);
		
}

