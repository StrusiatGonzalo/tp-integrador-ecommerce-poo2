package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

public class BankTransferPayment extends PaymentMethod{ // Pago por transferencia bancaria
	private String operationNumber;
	private BankTransferAPI apiConection;
	private String alias;
	private String cbu;
	
	public BankTransferPayment(String operationNumber, BankTransferAPI apiConection) {
		this.apiConection = apiConection;
	}
	
	@Override
	public void validateData(Order order) {
		apiConection.validateCBU(cbu, alias);
	}
	
	@Override
	public void setAsideFunds(Order order) {
		
	}
	
	@Override
	public void executeTransaction(Order order) {
		apiConection.transfer(order.totalCost(), cbu);
	}
	
	@Override
	public void notifyResult(Order order) {
		
	}
	
	public PaymentReceipt getReceipt(Order order) {
		return new PaymentReceipt(operationNumber, order.totalCost());
	}
}
