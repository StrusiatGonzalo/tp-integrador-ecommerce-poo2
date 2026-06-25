package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

public class PaymentReceipt { // Recibo de pago 
	private String operationNumber;
	private double amount;
	
	public PaymentReceipt(Order order, String operationNumber) {
		this.operationNumber = operationNumber;
		this.amount = order.totalCost();
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getOperationNumber() {
		return operationNumber;
	}
}
