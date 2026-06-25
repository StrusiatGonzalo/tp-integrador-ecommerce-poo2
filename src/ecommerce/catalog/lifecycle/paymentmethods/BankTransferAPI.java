package ecommerce.catalog.lifecycle.paymentmethods;

public interface BankTransferAPI { // Tranferencia bancaria
	
	void validateCBU(String cbu, String alias);
	String transfer(double amount, String cbu); // devolver el numero de operacion 
	
}
