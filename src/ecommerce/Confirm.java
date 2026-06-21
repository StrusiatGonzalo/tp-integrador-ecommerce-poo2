package ecommerce;

public class Confirm extends State{
	
	@Override
	public void start(Order order) { // Comenzar
		order.setState(new InPreparation());
		
	}
	
	@Override
	public void cancel(Order order) {
		order.getItems().forEach(i -> i.getItem().increaseStock(i.getQuantity()));
		order.setState(new Cancel());
	}
	
	@Override
	public String getName() {
		return "CONFIRMADO";
	}
}
