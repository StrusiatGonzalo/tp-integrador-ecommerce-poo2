package ecommerce.catalog.search;

import ecommerce.catalog.CatalogItem;

public class NameContainsCriteria implements SearchCriteria {
	private final String text;
	
	public NameContainsCriteria(String text) {
		this.text = text;
	}
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return item.getName().toLowerCase().contains(text.toLowerCase());
	}
}
