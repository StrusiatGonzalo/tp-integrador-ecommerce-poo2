package ecommerce.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bundle implements CatalogItem{ // paquete
	private String name;
	private String description;
	private double discountRate;
	private List<CatalogItem> items; // items del paquete (productos o paquetes) - composite
	private String category; 
	
	// CONSTRUCTOR
	public Bundle(String name, String description, double discountRate, String category) {
		this.name = name;
		this.description = description;
		this.discountRate = discountRate;
		this.category = category;
		this.items = new ArrayList<>();
		
		validate();
	}
	
	public Bundle(String name, String description, double discountRate) {
		this.name = name;
		this.description = description;
		this.discountRate = discountRate;
		this.category = null;
		this.items = new ArrayList<>();
		
		validate();
	}
	
	// GETTERS
	@Override
	public String getName() {
		return this.name;
	}
	
	public String getCategory() {
		return category;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public double getWeight() {
		return items.stream()
				.mapToDouble(i -> i.getWeight())
				.sum();
	}
	
	public double getDiscountRate() {
		return discountRate;
	}

	@Override
	public int getStock() {
		return items.stream()
				.mapToInt(i -> i.getStock())
				.sum();
	}
	
	// HELPERS
	@Override
	// metódo que devuelve el precio total del paquete
	// suma de todos los items con descuento aplicado (cada item, si corresponde, tambien tiene su descuento)
	public double getBasePrice() {
		double res = items.stream()
	    				.mapToDouble(i -> i.getBasePrice())
	    				.sum();
		return res * (1 - discountRate);
	}
	
	// método que agrega un item a la lista de items
	public void addItem(CatalogItem item) {
		items.add(item);
	}
	
	// método que decrementa el stock de todos los productos
	@Override
	public void decreaseStock(int quantity) {
		items.forEach(i -> i.decreaseStock(quantity));	
	}
	
	// método que incrementa el stock de todos los productos
	@Override
	public void increaseStock(int quantity) {
		items.forEach(i -> i.increaseStock(quantity));
	}
	
	// método que dada una cantidad de productos indica si hay stock disponible del mismo
	@Override
	public boolean hasStock(int quantity) {
		return  items.stream()
				     .allMatch(i -> i.hasStock(quantity));
	}
	
	private void validate() {
		if (getName().isBlank()) {
			throw new IllegalArgumentException("Error: El nombre del paquete es invalido");
		}	
		if (getDescription().isBlank()) {
			throw new IllegalArgumentException("Error: La descripcion del paquete es invalido");
		}		
		if (getDiscountRate() < 0) {
			throw new IllegalArgumentException("Error: El descuento del paquete es invalido");
		}
		if (getCategory().isBlank()) {
			throw new IllegalArgumentException("Error: El nombre de la categoria es invalido");
		}
	}
	
	@Override
	public void accumulateProductDemand(int quantity, Map<String, Integer> demandBySku) {
	    items.forEach(i -> i.accumulateProductDemand(quantity, demandBySku));
	}

	@Override
	public boolean hasEnoughStockFor(Map<String, Integer> demandBySku) {
	    return items.stream().allMatch(i -> i.hasEnoughStockFor(demandBySku));
	}
}
