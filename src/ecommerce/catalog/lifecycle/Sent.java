package ecommerce.catalog.lifecycle;

public class Sent extends State{
	// Constructor por defecto

	// este método asigna el nombre del estado, sirve para devolver el error personalizado en caso de fallar (clase State)
	@Override
	public String getName() {
		return "ENVIADO";
	}
	
	// Operaciones válidas para el estado ENVIADO
	// método para cancelar el pedido
	// solo reembolsa el costo de los items del pedido
	@Override
	public void cancel(Order order) {
		order.registerCreditNote(new CreditNote(order.totalCost(), "reenbolso por el costo total de los productos"));
		order.setState(new Canceled()); // setea el estado a CANCELADO
		
		order.notifyCanceled();
	}
	
	// método para entregar el pedido
	@Override
	public void deliver(Order order) {
		order.setState(new Delivered()); // setea el estado a ENTREGADO
		order.getItems().forEach(i -> i.captureSalesSnapshot()); // guarda la venta
		
	    //aca notificamos a todos los subscriptos del cambio de estado a entregado
	    order.notifySuccessfulProgress();
	    order.notifyFinal();
	}
}
