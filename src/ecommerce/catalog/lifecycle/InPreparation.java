package ecommerce.catalog.lifecycle;

// EN_PREPARACION
public class InPreparation extends State {
	// Constructor por defecto
	
	// este método asigna el nombre del estado, sirve para devolver el error personalizado en caso de fallar (clase State)
	@Override
	public String getName() {
		return "EN_PREPARACION";
	}
	
	// método para cancelar el envío
	// se repone el stock y se reembolsa el costo de los productos y el envío con notas de crédito
	@Override
	public void cancel(Order order) {
		order.getItems().forEach(i -> i.getItem().increaseStock(i.getQuantity()));
		order.registerCreditNote(new CreditNote(order.totalCost(), "costo total de los productos"));
		order.registerCreditNote(new CreditNote(order.getShippingCost(), "costo del envio"));
		order.setState(new Canceled()); // cambia el estado a CANCELADO
		
		order.notifyCanceled();
	}
	
	// método para enviar el pedido - setea el estado a ENVIADO
	@Override
	public void send(Order order) {
	    order.setState(new Sent());
	    
	    //aca notificamos a todos los subscriptos del cambio de estado a enviado
	    order.notifySuccessfulProgress();
	}
}
