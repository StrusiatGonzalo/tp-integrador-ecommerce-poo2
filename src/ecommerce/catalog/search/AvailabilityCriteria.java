package ecommerce.catalog.search;

import ecommerce.catalog.CatalogItem;

public class AvailabilityCriteria implements SearchCriteria {
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return item.hasStock(1);
	} 
}
