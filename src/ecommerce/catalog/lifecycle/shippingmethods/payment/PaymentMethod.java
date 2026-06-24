package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

public abstract class PaymentMethod {
	
	public void process(Order order) {
		validateData(order); // validar datos
		setAsideFunds(order); // reservar fondos
		executeTransaction(order); // ejecutar transaccion
		notifyResult(order); // notificar resultado
	}
	
	public abstract void validateData(Order order);
	
	public abstract void setAsideFunds(Order order);
	
	public abstract void executeTransaction(Order order);
	
	public abstract void notifyResult(Order order);
}


