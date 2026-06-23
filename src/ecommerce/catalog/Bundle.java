package ecommerce.catalog;

import java.util.ArrayList;
import java.util.List;

public class Bundle implements CatalogItem{ // paquete
	private String name;
	private String description;
	private double discountRate;
	private List<CatalogItem> items; // items del paquete (productos o paquetes) - composite
	
	// CONSTRUCTOR
	public Bundle(String name, String description, double discountRate) {
		this.name = name;
		this.description = description;
		this.discountRate = discountRate;
		this.items = new ArrayList<>();
		
		validate();
	}
	
	// GETTERS
	@Override
	public String getName() {
		return this.name;
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
	}
	
}
