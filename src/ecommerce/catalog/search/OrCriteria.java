package ecommerce.catalog.search;

import java.util.List;

import ecommerce.catalog.CatalogItem;

public class OrCriteria implements SearchCriteria {
	private List<SearchCriteria> criteria;
	
	public OrCriteria(List<SearchCriteria> criteria) {
		this.criteria = criteria;
		
		validate();
	}
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return criteria.stream().anyMatch(c -> c.isSatisfiedBy(item)); // pregunta recursivamente si el item responde a alguno de los criterios de la lista
	}
	
	public void validate() {
		if (criteria == null || criteria.isEmpty()) {
			throw new IllegalArgumentException("Error: OR necesita al menos un criterio");
		}
	}
}
