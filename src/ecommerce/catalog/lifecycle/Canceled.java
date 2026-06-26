package ecommerce.catalog.lifecycle;

public class Canceled extends State {
	// Constructor por defecto
	
	// este método asigna el nombre del estado, sirve para devolver el error personalizado en caso de fallar (clase State)
	@Override
	public String getName() {
		return "CANCELADO";
	}
	
	@Override
	public boolean isCancelled() { // Es cancelable
		return true;
	}
}
