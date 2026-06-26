package ecommerce.catalog;

import java.util.Map;

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
	void accumulateProductDemand(int multiplier, Map<String, Integer> demandBySku);
	boolean hasEnoughStockFor(Map<String, Integer> demandBySku);
	String getCategory();
}
