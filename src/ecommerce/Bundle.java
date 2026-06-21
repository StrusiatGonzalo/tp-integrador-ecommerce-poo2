package ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Bundle implements CatalogItem{
	private String name;
	private String description;
	private double discountRate;
	private List<CatalogItem> items;
	
	public Bundle(String name, String description, double discountRate) {
		this.name = name;
		this.description = description;
		this.discountRate = discountRate;
		this.items = new ArrayList<>();
	}
	
	@Override
	public double getBasePrice() {
		double res = items.stream()
	    				.mapToDouble(i -> i.getBasePrice())
	    				.sum();
		return res * (1 - discountRate);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	public void addItem(CatalogItem item) {
		items.add(item);
	}

	@Override
	public void decreaseStock(int quantity) {
		items.forEach(i -> i.decreaseStock(quantity));	
	}

	@Override
	public void increaseStock(int quantity) {
		items.forEach(i -> i.increaseStock(quantity));
	}	
}
