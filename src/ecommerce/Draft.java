package ecommerce;

public class Draft extends State { // BORRADOR

	public Draft() {
		super("BORRADOR");
	}
	
	@Override
	public void addItem(Order order, CatalogItem item) {
		order.getItems().add(item); 
	}
	
	@Override
	public void deleteItem(Order order, CatalogItem item) {
		if (!order.getItems().contains(item)) {
			throw new IllegalArgumentException("Error: El item no esta en la orden");
		}
		order.getItems().remove(item);
	}
	
	@Override
	public void confirm(Order order) {
		
	}
}
