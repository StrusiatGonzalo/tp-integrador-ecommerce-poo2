package ecommerce;

public class InPreparation extends State { // en preparacion
	
	@Override
	public String getName() {
		return "EN_PREPARACION";
	}
	
	@Override
	public void cancel(Order order) {
		order.getItems().forEach(i -> i.getItem().increaseStock(i.getQuantity()));
		order.registerCreditNote(new CreditNote(order.totalCost(), "costo total de los productos"));
		order.registerCreditNote(new CreditNote(order.getShippingCost(), "costo del envio"));
		order.setState(new Cancel());
	}
	
	@Override
	public void send(Order order) { // Enviar
		
	}
}
