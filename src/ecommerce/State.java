package ecommerce;

public abstract class State {
	
	private String name;
	
	public State(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addItem(Order order, CatalogItem item) { // Agregar
		throw new IllegalStateException("Error: No se pueden agregar items en el estado: " + getName());
	}
	
	public void deleteItem(Order order, CatalogItem item) { // Quitar
		throw new IllegalStateException("Error: No se pueden quitar items en el estado: " + getName()); 
	}
	
	public void confirm(Order order) { // Confirmar
		throw new IllegalStateException("Error: No se puede confirmar el pedido en el estado: " + getName());
	}
	
	public void cancel(Order order) { // Cancelar
		throw new IllegalStateException("Error: No se puede cancelar el pedido en el estado: " + getName());
	}
	
	public void start(Order order) { // Comenzar
		throw new IllegalStateException("Error: No se puede comenzar el pedido en el estado: " + getName());
	}
	
	public void deliver(Order order) { // Entregar
		throw new IllegalStateException("Error: No se puede entregar el pedido en el estado: " + getName());
	}
	
	public void send(Order order) { // Enviar
		throw new IllegalStateException("Error: No se puede enviar el pedido en el estado: " + getName());
	}
	
}
