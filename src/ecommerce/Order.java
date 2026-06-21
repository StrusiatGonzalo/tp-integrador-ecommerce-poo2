package ecommerce;

import java.util.List;

public class Order {
	private State state;
	private List<OrderItem> items;
	private double shippingCost; 
	private List<CreditNote> creditNotes;
	
	public void add(OrderItem item) {
		state.addItem(this, item);
	}
	
	public void delete(OrderItem item) {
		state.deleteItem(this, item);
	}
	
	public void setState(State newState) {
		state = newState;
	}
	
	public List<OrderItem> getItems(){
		return items;
	}
	
	public void confirm() {
		state.confirm(this);
	}
	
	public void registerCreditNote(CreditNote note) {
		creditNotes.add(note);
	}
	
	public List<CreditNote> getCreditNote() {
		return creditNotes;
	}
	
	public double totalCost() {
		return items.stream()
				    .mapToDouble(i -> i.getItem().getBasePrice())
				    .sum();			    
	}
	
	public double getShippingCost() {
		return shippingCost;
	}
}
