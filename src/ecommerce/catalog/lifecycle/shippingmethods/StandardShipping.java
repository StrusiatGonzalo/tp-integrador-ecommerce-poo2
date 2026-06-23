package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// Método de envío estándar
public class StandardShipping implements ShippingType {
	
	@Override
	public double cost(Order order) {
		return CorreoArgentinoMock.estimarEnvio(order.totalWeight(), order.getAddress());// de librería, retorna el precio
	}

	@Override
	public String waitingDays(Order order) {
		return "5 a 7 días hábiles";
	}

}
