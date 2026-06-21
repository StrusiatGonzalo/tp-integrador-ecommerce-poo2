package ecommerce;

public class Draft extends State { // BORRADOR

	@Override
	public void addItem(Order order, OrderItem item) {
		order.getItems().add(item); 
	}
	
	@Override
	public void deleteItem(Order order, OrderItem item) {
		if (!order.getItems().contains(item)) {
			throw new IllegalArgumentException("Error: El item no esta en la orden");
		}
		order.getItems().remove(item);
	}
	
	@Override
	public void confirm(Order order) {
		order.getItems().forEach(i -> i.getItem().decreaseStock(i.getQuantity()));
		order.setState(new Confirm());
	}
	
	@Override
	public void cancel(Order order) { // Cancelar
		order.setState(new Cancel());
	}
	
	@Override
	public String getName() {
		return "BORRADOR";
	}
}
