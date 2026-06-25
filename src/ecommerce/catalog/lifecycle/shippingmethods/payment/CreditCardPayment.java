package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

// Pago con tarjeta de crédito
public class CreditCardPayment extends PaymentMethod{
	private String cardNumber;
	private String cvv; 
	private String expirationCode;
	private CreditCardAPI apiConnection;
	
	public CreditCardPayment(CreditCardAPI api, String cardNumber, String cvv, String expirationCode) {
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expirationCode = expirationCode;
		this.apiConnection = api;
	}
	
	@Override
	public void validateData(Order order) {
		apiConnection.validateCard(cardNumber, cvv, expirationCode);
	}
	
	@Override
	public void setAsideFunds(Order order) {
		setOperationNumber(preAuthorizeAndGetOperationNumber(order));
	}
	
	private String preAuthorizeAndGetOperationNumber(Order order) {
		return apiConnection.preAuthorize(order.getTotalToPay());
	}
	
	@Override
	public void executeTransaction(Order order) {
		apiConnection.charge(order.getTotalToPay(), getOperationNumber());
	}
}
