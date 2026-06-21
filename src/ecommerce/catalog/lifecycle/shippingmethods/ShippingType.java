package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// TIPO DE ENVÍO
public interface ShippingType {
	public float cost(float criteria, String address);
	public String waitingDays(Order order);
}
