package ecommerce.catalog.lifecycle.shippingmethods.payment;

import ecommerce.catalog.lifecycle.Order;

// Pago con billetera virtual
public class VirtualWalletPayment extends PaymentMethod {
	private String accountId;
	private String sellerAccountId;
	private VirtualWalletAPI apiConnection;
	
	// Constructor
	public VirtualWalletPayment(VirtualWalletAPI api, String sellerAccountId, String accountId) {
		this.accountId = accountId;
		this.sellerAccountId =  sellerAccountId;
		this.apiConnection = api;
	}

	@Override
	public void validateData(Order order) {
		apiConnection.validateBalance(accountId, order.getTotalToPay());		
	}

	@Override
	public void setAsideFunds(Order order) {
		apiConnection.blockFunds(accountId, order.getTotalToPay());
	}

	@Override
	public void executeTransaction(Order order) {
		setOperationNumber(accreditAndGetOperationNumber(order));
	}
	
	// solicitar a la api la acreditación, esto devuelve el numero de transacción
	private String accreditAndGetOperationNumber(Order order) {
		return apiConnection.realTimeAccreditation(sellerAccountId, order.getTotalToPay());
	}
}
