package ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Product implements CatalogItem {
	private String sku;
	private String name;
	private String brand;
	private String category;
	private String description;
	private double weight;
	private double price;
	private double discountRate;
	private int stock;
	private List<Attribute<?>> extraAttributes; 
	
	public Product(String sku, String name, String brand, String category, String description, double weight, double price, int stock) {
		this.sku = sku;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.description = description;
		this.weight = weight;
		this.price = price;
		this.discountRate = 0.0;
		this.stock = stock;
		this.extraAttributes = new ArrayList<>();
		
		validate();
	}
	
	public Product(String sku, String name, String brand, String category, String description, double weight, double price, int stock, List<Attribute<?>> attributes) {
		this.sku = sku;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.description = description;
		this.weight = weight;
		this.price = price;
		this.discountRate = 0.0;
		this.stock = stock;
		this.extraAttributes = attributes;
		
		validate();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public double getBasePrice() {
		return price * (1- discountRate);
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	public List<Attribute<?>> getExtraAttributes(){
		return extraAttributes;
	}
	
	public String getSku() {
		return sku;
	}

	public String getBrand() {
		return brand;
	}
	
	public String getCategory() {
		return category;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	
	public void setDiscountRate(double discountRate) {
		if(discountRate >= 1.0 || discountRate < 0) {
			throw new IllegalArgumentException("Error: El descuento no puede superar el 100%");
		}
		this.discountRate = discountRate;
	}
	
	public void addExtraAttribute(Attribute<?> attribute) {
		extraAttributes.add(attribute);
	}
	
	public void validate() { 
		if (sku == null || sku.isBlank()) {
			throw new IllegalArgumentException("Error: El sku es invalido");
		}
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Error: El nombre es invalido");			
		}
		if (brand == null || brand.isBlank()) {
			throw new IllegalArgumentException("Error: La marca es invalida");
		}
		if (category == null || category.isBlank()) {
			throw new IllegalArgumentException("Error: La categoria es invalida");
		}
		if (price < 0.0) {
			throw new IllegalArgumentException("Error: El precio es invalido");
		}
		if (weight < 0.0) {
			throw new IllegalArgumentException("Error: El peso es invalido");
		}
		if (stock < 0) {
			throw new IllegalArgumentException("Error: El stock inicial es invalido");
		}
	}
}















