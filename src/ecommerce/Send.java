package ecommerce;

public class Send extends State{
	
	@Override
	public String getName() {
		return "ENVIADO";
	}
	
	@Override
	public void cancel(Order order) { // Cancelar
		order.registerCreditNote(new CreditNote(order.totalCost(), "reenbolso por el costo total de los productos"));
		order.setState(new Cancel());
	}
	
	@Override
	public void deliver(Order order) { // Entregar
		order.setState(new Delivered());
	}
}
