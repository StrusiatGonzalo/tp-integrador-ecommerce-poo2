package ecommerce.catalog.search;

import ecommerce.catalog.CatalogItem;

public class NotCriteria implements SearchCriteria {
	private final SearchCriteria criteria;
	
	public NotCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
		
		validate();
	}
	
	public void validate() {
		if (criteria == null) {
			throw new IllegalArgumentException("Error: NOT necesita un criterio");
		}
	}
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return !criteria.isSatisfiedBy(item);
	}
	
}
