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
		return items.stream()
				    .mapToDouble(i -> i.getBasePrice())
				    .sum() * (1 - discountRate);
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
}
