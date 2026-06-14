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
	
	public Product(String sku,String name,String brand,String category,String description,double price) {
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
		return 0; //hacer logica! descuento atributo dinamico?
	}
	
	@Override
	public String getDescription() {
		return description;
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
	
	
	public double getPrice() {
		return price;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}	
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
		
	public void setExtraAtributte(Atributte<?> atributte) {
		extraAtributtes.add(atributte);
	}
}
