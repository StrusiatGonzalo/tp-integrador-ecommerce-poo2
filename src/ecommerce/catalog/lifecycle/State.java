package ecommerce.catalog.lifecycle;

import ecommerce.catalog.CatalogItem;

// Ciclo de vida del pedido
public abstract class State {
	
	public abstract String getName(); // lo reescribe cada state
	
	// Cada estado sobreescribe las operaciones válidas, por defecto todas lanzan un errror personalizado
	public void addItem(Order order, CatalogItem item, int quantity) { // Agregar
		throw new IllegalStateException("Error: No se pueden agregar items en el estado: " + getName());
	}
	
	public void deleteItem(Order order, OrderItem item) { // Quitar
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
	
	public boolean isSuccessfulProgress() { // Es un progreso exitoso
		return false;
	}
	
	public boolean isFinal() { // Es final
		return false;
	}
	
	public boolean isCancelled() { // Es cancelable
		return false;
	}
}
