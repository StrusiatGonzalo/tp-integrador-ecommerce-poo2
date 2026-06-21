package ecommerce;

import java.util.List;

public class Order {
	private State state;
	private List<CatalogItem> items;
	
	public void add(CatalogItem item) {
		state.addItem(this, item);
	}
	
	public void delete(CatalogItem item) {
		state.deleteItem(this, item);
	}
	
	public void setState(State state) {
		
	}
	
	public List<CatalogItem> getItems(){
		return items;
	}
	
	public State getState() {
		return null;
	}
	
	public void request() {
		
	}
}
