package ecommerce.catalog.lifecycle.shippingmethods.payment;

public class PaymentReceipt {
	private String operationNumber;
	private double amount;
	
	public PaymentReceipt(String operationNumber, double amount) {
		this.operationNumber = operationNumber;
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getOperationNumber() {
		return operationNumber;
	}
}
