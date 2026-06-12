package ecommerce;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Product {
	private String sku;
	private String name;
	private String brand;
	private String category;
	private String description;
	private double price;
	private double discountRate;
	private List<Atributte> extraAtributtes;
	
	public Product(String sku,String name,String brand,String category,String description,double price,double discountRate) {
		this.sku = sku;
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.description = description;
		this.price = price;
		this.discountRate = discountRate;
		this.extraAtributtes = new ArrayList<>();
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Product setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
		return this;
	}
	
	public double getFinalPrice() {
		return 0; //hacer logica! descuento atributo dinamico?
	}
	
	public Object getValueAtributte(Atributte atributte) {
		//rehacer- pensar
		return 0;
	}
	
	public void setExtraAtributte(Atributte atributte) {
		this.extraAtributtes.add(atributte);
	}
	
	public String getSku() {
		return sku;
	}

	public String getName() {
		return name;
	}

	public String getBrand() {
		return brand;
	}
	
	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}	
}
