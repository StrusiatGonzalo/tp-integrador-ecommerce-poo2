package ecommerce;

public class Attribute<T> {
	protected String name;
	protected T value;
	
	public Attribute(String nameAttribute, T value){
		this.name = nameAttribute;
		this.value = value;
		
		validateAttribute();
	}
	
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
		
	private void validateAttribute() {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Error: El nombre del atributo es invalido");
		}
		if (value == null || value.toString().isBlank()) {
			throw new IllegalArgumentException("Error: El valor del atributo es invalido");
		}
	}
}

