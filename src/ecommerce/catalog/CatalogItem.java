package ecommerce.catalog;

//interfaz implementada por Product y Bundle
public interface CatalogItem { 
		
	double getBasePrice();
	String getName();
	String getDescription();
	void decreaseStock(int quantity); //decrementarStock
	void increaseStock(int quantity); //incrementarStock
	boolean hasStock(int quantity); // tiene o no stock disponible
	int getStock();
	double getWeight();
	
}
