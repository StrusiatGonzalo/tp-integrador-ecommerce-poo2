package ecommerce;

public interface CatalogItem {
		
	double getBasePrice();
	String getName();
	String getDescription();
	void decreaseStock(int quantity); //decrementarStock
	void increaseStock(int quantity); //incrementarStock
}
