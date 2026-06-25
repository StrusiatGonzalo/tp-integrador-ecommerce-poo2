package ecommerce.catalog.lifecycle.shippingmethods.payment;

// Tarjeta de credito
public interface CreditCardAPI { 
	void validateCard(String cardNumber, String cvv, String expirationDate);
	String preAuthorize(double amount); // devuelve el numero de operación
	void charge(double amount, String operationNumber); // cargar el pago a la tarjeta
}
