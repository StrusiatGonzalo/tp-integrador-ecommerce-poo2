package ecommerce.catalog.lifecycle;

import ecommerce.catalog.CatalogItem;

// BORRADOR
public class Draft extends State { 
	
	// Constructor por defecto
	
	// este método asigna el nombre del estado, sirve para devolver el error personalizado en caso de fallar (clase State)
	@Override
	public String getName() {
		return "BORRADOR";
	}
	
	// Operaciones válidas para el estado BORRADOR
	// método para agregar un item al pedido
	@Override
	public void addItem(Order order, CatalogItem item, int quantity) {
		order.addNewItem(new OrderItem(item, quantity)); 
	}
	
	// método para borrar un item del pedido, si el item no existe en el pedido se lanza un error 
	@Override
	public void deleteItem(Order order, OrderItem item) {
		if (!order.getItems().contains(item)) {
			throw new IllegalArgumentException("Error: El item no esta en la orden");
		}
		order.getItems().remove(item);
	}
	
	// método para confirmar un pedido
	// decrementa el stock de cada producto y cambia el estado a CONFIRMADO
	@Override
	public void confirm(Order order) {
	    if (!order.isEverythingInStock()) { // verifica si hay stock de todo lo que tiene la orden
	        throw new IllegalArgumentException("Error: no hay stock suficiente para confirmar el pedido");
	    }
	    
	    order.getPaymentMethod().process(order);
	    
	    order.getItems().forEach(i -> i.getItem().decreaseStock(i.getQuantity()));
	    order.setPaymentReceipt(order.getPaymentMethod().getReceipt());
	    order.setState(new Confirmed());
	    
	    //aca notificamos a todos los subscriptos del cambio de estado a confirmado
	    order.notifySuccessfulProgress();
	}
	
	// método para cancelar el pedido, setea el estado a CANCELADO
	@Override
	public void cancel(Order order) {
		order.setState(new Canceled());
		order.notifyCanceled();
	}
}
