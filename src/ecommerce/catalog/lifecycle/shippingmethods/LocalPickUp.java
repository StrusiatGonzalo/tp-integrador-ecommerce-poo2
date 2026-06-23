package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// Metodo de envío a sucursal
public class LocalPickUp implements ShippingType{ // retirar en sucursal
	
	@Override
	public double cost(Order order) { // método polimorfico, no usa los parámetros
		return 0.0; // siempre de costo 0
	}
	
	@Override
	public String waitingDays(Order order) {
		 // preguntar si todos los items del pedido están con stock disponible
		return (order.isEverythingInStock()) ? "hasta 3 días hábiles" : "a partir de 3 días hábiles";
	}
}
