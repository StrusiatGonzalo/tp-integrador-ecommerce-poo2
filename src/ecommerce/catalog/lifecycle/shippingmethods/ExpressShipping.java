package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// Método de envío express
public class ExpressShipping implements ShippingType{
	private final ExpressShippingAPI apiConnection;
	
	public ExpressShipping(ExpressShippingAPI apiConnection) {
		this.apiConnection = apiConnection;
	}
	
	@Override
	public double cost(Order order) { // método polimorfico, no usa address
		return apiConnection.calculateCost(order.totalCost()); // de librería, retorna el costo
	}
	
	@Override
	public String waitingDays(Order order) {
		return "1 día hábil";
	}	
}
