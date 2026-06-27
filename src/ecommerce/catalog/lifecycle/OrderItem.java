package ecommerce.catalog.lifecycle;

import ecommerce.catalog.CatalogItem;

// item del pedido
public class OrderItem {
	private CatalogItem item; // producto o bundle
	private int quantity; // cantidad en el pedido
	private double priceAtConfirmation; // precio al momento de la confirmacion de la entrega
	
	//CONSTRUCTOR
	public OrderItem(CatalogItem item, int quantity) {
		this.item = item;
		this.quantity = quantity;
		
		validate();
	}
	
	//GETTERS
	public int getQuantity() {
		return quantity;
	}
	
	public CatalogItem getItem() {
		return item;
	}
	
	//HELPERS
	private void validate() {
		if (getQuantity() <= 0) {
			throw new IllegalArgumentException("Error: La cantidad es inválida");
		}
	}
	
	public void captureSalesSnapshot() { // acá saca una "foto" al momento de "ENTREGADO" que es un estado terminal del pedido
	    this.priceAtConfirmation = item.getBasePrice(); // este es el precio al momento de la confirmacion de la entrega
	}

	public double getPriceAtConfirmation() {
	    return priceAtConfirmation;
	}
}
