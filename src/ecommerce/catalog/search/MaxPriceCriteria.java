package ecommerce.catalog.search;

import ecommerce.catalog.CatalogItem;

public class MaxPriceCriteria implements SearchCriteria {
	private final double maxPrice;
	
	public MaxPriceCriteria(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return item.getBasePrice() <= maxPrice;
	}
}
