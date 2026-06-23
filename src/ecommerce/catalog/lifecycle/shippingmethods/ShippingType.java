package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// TIPO DE ENVÍO
public interface ShippingType {
	public double cost(Order order);
	public String waitingDays(Order order);
}
