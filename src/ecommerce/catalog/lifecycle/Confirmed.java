package ecommerce.catalog.lifecycle;

// CONFIRMADO
public class Confirmed extends State{
	
	// Constructor por defecto
	
	// este método asigna el nombre del estado, sirve para devolver el error personalizado en caso de fallar (clase State)
	@Override
	public String getName() {
		return "CONFIRMADO";
	}
	
	// Operaciones válidas para el estado CONFIRMADO
	// método para empezar a preparar el pedido
	@Override
	public void start(Order order) {
		order.setState(new InPreparation()); // setea el estado a EN_PREPARACION
	}
	
	// método para cancelar el pedido
	// fue cancelado en este estado entonces, se incrementa el stock de los items del pedido
	@Override
	public void cancel(Order order) {
		order.getItems().forEach(i -> i.getItem().increaseStock(i.getQuantity()));
		order.setState(new Canceled()); // se setea el estado a CANCELADO

	}
}
