package ecommerce.catalog.lifecycle;

public class Delivered extends State{ //Entregado
	
	// Constructor por defecto
	
	// este método asigna el nombre del estado, sirve para devolver el error personalizado en caso de fallar (clase State)
	@Override
	public String getName() {
		return "ENTREGADO";
	}
}
