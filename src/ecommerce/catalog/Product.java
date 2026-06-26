package ecommerce.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private List<Attribute<?>> extraAttributes; // atributos dinámicos que no son de la clase
	
	// CONSTRUCTOR (sin lista de atributos dinámicos)
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
		
		validate(); // validar que lo atributos sean válidos (esten asignados)
	}
	
	// CONSTRUCTOR (con lista de atributos dinámicos instanciada)
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
		
		validate(); // validar que lo atributos sean válidos (esten asignados)
	}
	
	// GETTERS 
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
	
	@Override
	public String getCategory() {
		return category;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	
	public int getStock() {
		return stock;
	}
	
	@Override
	public double getWeight() {
		return weight;
	}
	
	// SETTERS
	public void setDiscountRate(double discountRate) {
		if(discountRate >= 1.0 || discountRate < 0) {
			throw new IllegalArgumentException("Error: El descuento no puede superar el 100%");
		}
		this.discountRate = discountRate;
	}
	
	
	// HELPERS
	// método que agrega un atributo dinámico a la clase
	public void addExtraAttribute(Attribute<?> attribute) {
		extraAttributes.add(attribute);
	}
	
	// valida que todos los atributos estén correctamente asignados
	private void validate() { 
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
	
	// método que decrementa el stock
	// verifica si hay stock disponible para decrementar si no, tira un error
	@Override
	public void decreaseStock(int quantity) {
		if (!hasStock(quantity)) { 
			throw new IllegalArgumentException("Error: El stock no es suficiente para: " + sku);
		}
		stock -= quantity;
	}
	
	// método que incrementa el stock del producto
	@Override
	public void increaseStock(int quantity) {
		stock += quantity;
	}
	
	// metodo que dada una cantidad, indica si hay stock disponible
	@Override
	public boolean hasStock(int quantity) {
		return quantity <= stock;
	}
	
	@Override
	public void accumulateProductDemand(int quantity, Map<String, Integer> demandBySku) {
	    if (demandBySku.containsKey(getSku())) { // si el map que me pasan por parametro me tiene a mi como clave entonces . .
	        int actualValue = demandBySku.get(getSku()); //  me traigo el valor osea la cantidad
	        demandBySku.put(getSku(), actualValue + quantity); // aca sumo los valores 
	    } else {
	        demandBySku.put(getSku(), quantity); // sino simplemente no suma y queda el valor existente 
	    }
	}

	@Override
	public boolean hasEnoughStockFor(Map<String, Integer> demandBySku) {
	    return hasStock(demandBySku.get(this.sku));
	}
}















