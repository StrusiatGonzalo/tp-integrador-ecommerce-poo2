package ecommerce.catalog.lifecycle;

import java.util.ArrayList;
import java.util.List;

import ecommerce.catalog.lifecycle.shippingmethods.ShippingType;

// Pedido
public class Order { 
	private State state;
	private List<OrderItem> items; // items del pedido
	private List<CreditNote> creditNotes; // puede tener notas por el reembolso del costo total de los productos y/o reembolso por el envío
	private ShippingType shippingType; // tipo de envío
	private String address; // direccion de envío
	
	// CONSTRUCTOR
	public Order(String address, ShippingType shippingType) {
		this.state = new Draft();
		this.items = new ArrayList<>();
		this.creditNotes = new ArrayList<>();
		this.address = address;
		this.shippingType = shippingType;
		
		validate();
	}
	
	// GETTERS
	public List<OrderItem> getItems(){
		return items;
	}
	
	public List<CreditNote> getCreditNote() {
		return creditNotes;
	}
	
	// método que describe el costo total del envío según el método de envío
	public double getShippingCost() {
		return shippingType.cost(totalWeight(), address);
	}
	
	// SETTERS
	public void setState(State newState) {
		state = newState;
	}
	
	// HELPERS
	// método que agrega un item al pedido
	public void add(OrderItem item) {
		state.addItem(this, item);
	}
	
	// método que quita un item del pedido
	public void delete(OrderItem item) {
		state.deleteItem(this, item);
	}
	
	// método que confirma el pedido
	public void confirm() {
		state.confirm(this);
	}
	
	// método que registra una nota de crédito para el pedido
	public void registerCreditNote(CreditNote note) {
		creditNotes.add(note);
	}
	
	// método que describe el costo total del pedido
	public double totalCost() {
		return items.stream()
				    .mapToDouble(i -> i.getItem().getBasePrice())
				    .sum();			    
	}
	
	// método que describe el peso total del pedido
	public float totalWeight() {
		return items.stream()
				.map(i -> i.getItem().getWeight())
				.reduce(0f, Float::sum);
	}
	
	// método que indica si hay stock disponible de cada item del pedido
	public boolean isEverythingInStock() { // de todos los productos que estan en la orden hay stock?
		return items.stream()
					.allMatch(i -> i.getItem().hasStock(i.getQuantity()));
	}
	
	// método privado que valida que los atributos esten instanciados correctamente (no null, no "")
	private void validate() {
		if (address == null || address.isBlank()) {
			throw new IllegalArgumentException("Error: La dirección de envío es inválida");
		}
		if (shippingType == null) {
			throw new IllegalArgumentException("Error: El tipo de envío no es válido");
		}
	}
}



















