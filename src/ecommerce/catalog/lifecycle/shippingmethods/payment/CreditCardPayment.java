package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

public class CreditCardPayment extends PaymentMethod{
	private String cardNumber;
	private String cvv; 
	private String expirationCode;
	private CreditCardAPI apiConnection;
	
	@Override
	public void validateData(Order order) {
		apiConnection.validateCard(cardNumber, cvv, expirationCode);
	}
	
	@Override
	public void setAsideFunds(Order order) {
		setOperationNumber(preAuthorizeAndGetOperationNumber(order));
	}
	
	private String preAuthorizeAndGetOperationNumber(Order order) {
		return apiConnection.preAuthorize(order.totalCost());
	}
	
	@Override
	public void executeTransaction(Order order) {
		apiConnection.charge(order.totalCost(), getOperationNumber());
	} 
}
