package ecommerce.catalog.lifecycle.shippingmethods;

public interface StandarShippingAPI {
	double estimateCost(double weight, String address); // Es estimarEnvio(float peso, Direccion direccionEnvio )
}
