package ecommerce.catalog.reports;

import ecommerce.catalog.CatalogItem;

public class SalesEntry { // entrada de venta 
	private final CatalogItem item;
    private final int unitsSold; // unidades vendidas
    private final double averagePricePaid; // promedio del precio de las unidades vendidas

    public SalesEntry(CatalogItem item, int unitsSold, double averagePricePaid) {
        this.item = item;
        this.unitsSold = unitsSold;
        this.averagePricePaid = averagePricePaid;
    }

    public CatalogItem getItem() { 
    	return item; 
    }
    
    public int getUnitsSold() { 
    	return unitsSold; 
    }
    
    public double getAveragePricePaid() { 
    	return averagePricePaid;
    }
}