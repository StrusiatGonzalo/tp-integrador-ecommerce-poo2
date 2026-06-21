package ecommerce.catalog.lifecycle.shippingmethods;

import ecommerce.catalog.lifecycle.Order;

// Método de envío estándar
public class StandardShipping implements ShippingType {
	
	@Override
	public float cost(float totalWeight, String address) {
		return CorreoArgentinoMock.estimarEnvio(totalWeight, address); // de librería, retorna el precio
	}

	@Override
	public String waitingDays(Order order) {
		return "5 a 7 días hábiles";
	}

}
