package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// Método de envío express
public class ExpressShipping implements ShippingType{
	
	@Override
	public double cost(Order order) { // método polimorfico, no usa address
		return EnvioExpressMock.calcularCosto(order.totalCost()); // de librería, retorna el costo
	}
	
	@Override
	public String waitingDays(Order order) {
		return "1 día hábil";
	}	
}
