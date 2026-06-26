package ecommerce.catalog.search;
import ecommerce.catalog.CatalogItem;

public interface SearchCriteria { //
	boolean isSatisfiedBy(CatalogItem item); // si el catalogItem item satiface la condicion
}
