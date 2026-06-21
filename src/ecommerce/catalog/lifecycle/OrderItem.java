package ecommerce.catalog.lifecycle;

import ecommerce.catalog.CatalogItem;

// item del pedido
public class OrderItem {
	private CatalogItem item; // producto o bundle
	private int quantity; // cantidad en el pedido
	
	//GETTERS
	public int getQuantity() {
		return quantity;
	}
	
	public CatalogItem getItem() {
		return item;
	}
}
