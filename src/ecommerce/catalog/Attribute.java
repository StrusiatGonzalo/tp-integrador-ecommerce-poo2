package ecommerce.catalog;

// atributos dinámicos
public class Attribute<T> { 
	protected String name;
	protected T value;
	
	// CONSTRUCTOR
	public Attribute(String nameAttribute, T value){
		this.name = nameAttribute;
		this.value = value;
		
		validate();
	}
	
	// GETTERS
	public String getName() {
		return name;
	}
	
	public T getValue() {
		return value;
	}
	
	// HELPERS
	// método que valida que los atributos estén asignados correctamente
	private void validate() {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Error: El nombre del atributo es invalido");
		}
		// toString porque el valor es de tipo T, puede ser cualquier tipo y si es un string, hay que controlar que no sea blank porque no sería válido
		if (value == null || value.toString().isBlank()) {  
			throw new IllegalArgumentException("Error: El valor del atributo es invalido");
		}
	}
}

