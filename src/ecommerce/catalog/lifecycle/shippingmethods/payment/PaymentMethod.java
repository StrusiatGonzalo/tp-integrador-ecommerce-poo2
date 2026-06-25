package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

// Método de pago 
public abstract class PaymentMethod {
	private PaymentReceipt receipt; // recibo
	private String operationNumber; // numero de operación
	
	public void process(Order order) {
		validateData(order); // validar datos
		setAsideFunds(order); // reservar fondos
		executeTransaction(order); // ejecutar transacción
		notifyResult(order); // notificar resultado
	}
	
	public abstract void validateData(Order order);
	
	public abstract void setAsideFunds(Order order);
	
	public abstract void executeTransaction(Order order);
	
	// Hook: este método puede ser sobreescrito en las subclases según se necesite
	public void notifyResult(Order order) {
		this.receipt = new PaymentReceipt(order, operationNumber);
	}
	
	public PaymentReceipt getReceipt() {
		return receipt;
	}
	
	public String getOperationNumber() {
		return operationNumber;
	}
	
	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}
}


