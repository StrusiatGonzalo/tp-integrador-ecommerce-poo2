package ecommerce.catalog.lifecycle.shippingmethods.payment;

// Billetera virtual
public interface VirtualWalletAPI { 
	
	void validateBalance(String accountId, double amount); // valida si el comprador tiene los fondos disponibles
	void blockFunds(String accountId, double amount); // bloquea los fondos del comprador
	String realTimeAccreditation(String sellerAccountId, double amount); // recibe el id de la cuenta del vendedor y el total del pago y notifica

}