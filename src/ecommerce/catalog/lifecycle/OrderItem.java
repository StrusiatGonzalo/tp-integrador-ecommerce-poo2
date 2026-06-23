package ecommerce.catalog.lifecycle;

import ecommerce.catalog.CatalogItem;

// item del pedido
public class OrderItem {
	private CatalogItem item; // producto o bundle
	private int quantity; // cantidad en el pedido
	
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
}
