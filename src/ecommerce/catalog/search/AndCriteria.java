package ecommerce.catalog.search;

import java.util.List;

import ecommerce.catalog.CatalogItem;

public class AndCriteria implements SearchCriteria{
	private List<SearchCriteria> criteria;
	
	public AndCriteria(List<SearchCriteria> criteria) {
		this.criteria = criteria;
		
		validate();
	}
	
	@Override
	public boolean isSatisfiedBy(CatalogItem item) {
		return criteria.stream().allMatch(c -> c.isSatisfiedBy(item)); // pregunta recursivamente si el item responde a todos los criterios de la lista
	}
	
	public void validate() {
		if (criteria == null || criteria.isEmpty()) {
			throw new IllegalArgumentException("Error: AND necesita al menos un criterio");
		}
	}
}
