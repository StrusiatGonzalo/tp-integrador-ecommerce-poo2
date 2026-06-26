package ecommerce.catalog.search;

import ecommerce.catalog.CatalogItem;

public class CategoryCriteria implements SearchCriteria {
	private String category;
	
	public CategoryCriteria(String category) {
		this.category = category;
	}
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return category.equalsIgnoreCase(item.getCategory());
	}

}
