package ecommerce.catalog.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.catalog.CatalogItem;

public class Catalog {
	private final List<CatalogItem> items;
	
	public Catalog() {
		this.items = new ArrayList<>();
	}
	
	public void addItem(CatalogItem item) {
		items.add(item);
	}
	
	public List<CatalogItem> search(SearchCriteria criteria){
		return items.stream()
				    .filter(i -> criteria.isSatisfiedBy(i))
				    .collect(Collectors.toList()); // filtro por todos los items que cumplan con el criterio
	}																							   
}
