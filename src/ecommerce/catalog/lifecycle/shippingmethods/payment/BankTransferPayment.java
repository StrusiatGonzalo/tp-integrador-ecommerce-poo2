package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

// Pago por transferencia bancaria
public class BankTransferPayment extends PaymentMethod{ 
	private BankTransferAPI apiConnection;
	private String alias;
	private String cbu;
	
	// Constructor
	public BankTransferPayment(BankTransferAPI api, String alias, String cbu) {
		this.cbu = cbu;
		this.alias = alias;
		this.apiConnection = api;
	}
	
	@Override
	public void validateData(Order order) {
		apiConnection.validateCBU(cbu, alias);
	}
	
	@Override
	public void setAsideFunds(Order order) {
		// No aplica porque la transferencia es directa
	}
	
	@Override
	public void executeTransaction(Order order) {
		setOperationNumber(tranferAndGetOperationNumber(order));
	}
	
	private String tranferAndGetOperationNumber(Order order) {
		return apiConnection.transfer(order.getTotalToPay(), cbu);
	}
	
}
