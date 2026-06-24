package ecommerce.catalog.lifecycle.shippingmethods.payment;

public interface BankTransferAPI { // Tranferencia bancaria
	
	void validateCBU(String cbu, String alias);
	void transfer(double amount, String cbu);
	
}
