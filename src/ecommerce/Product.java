package ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Product implements CatalogItem {
	private String sku;
	private String name;
	private String brand;
	private String category;
	private String description;
	private double price;
	private double discountRate;
	private List<Atributte<?>> extraAtributtes; 
	
	public Product(String sku,String name,String brand,String category,String description, double price) {
		this.sku = sku;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.description = description;
		this.price = price;
		this.discountRate = 0.0;
		this.extraAtributtes = new ArrayList<>();
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
	
	public List<Atributte<?>> getExtraAtributtes(){
		return extraAtributtes;
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
			throw new IllegalArgumentException("Error: el descuento no puede superar el 100%");
		}
		
		this.discountRate = discountRate;
	
	}
		
	public void addExtraAtributte(Atributte<?> atributte) {
		extraAtributtes.add(atributte);
	}
}
