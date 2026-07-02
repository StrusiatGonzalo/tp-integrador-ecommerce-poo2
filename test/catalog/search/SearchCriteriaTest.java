package catalog.search;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ecommerce.catalog.Attribute;
import ecommerce.catalog.Bundle;
import ecommerce.catalog.CatalogItem;
import ecommerce.catalog.Product;
import ecommerce.catalog.search.AndCriteria;
import ecommerce.catalog.search.AvailabilityCriteria;
import ecommerce.catalog.search.Catalog;
import ecommerce.catalog.search.CategoryCriteria;
import ecommerce.catalog.search.MaxPriceCriteria;
import ecommerce.catalog.search.NameContainsCriteria;
import ecommerce.catalog.search.NotCriteria;
import ecommerce.catalog.search.OrCriteria;
import ecommerce.catalog.search.SearchCriteria;

class SearchCriteriaTest {

    Catalog catalog;
    Product auriculares, cable, pack;
    Bundle comboElectronica;

    @BeforeEach
    void setUp() {
        catalog = new Catalog();

        auriculares = new Product("E01", "Auriculares Bluetooth", "Marca", "Electronica", "desc", 0.2, 8000.0, 5);
        cable = new Product("E02", "Cable USB-C", "Marca", "Electronica", "desc", 0.05, 1500.0, 0); 
        pack  = new Product("E03", "Pack Oferta Verano", "Marca", "Indumentaria", "desc", 0.5, 12000.0, 10);
        comboElectronica = new Bundle("Combo Electronica", "desc", 0.1, "Electronica");
        comboElectronica.addItem(auriculares);

        catalog.addItem(auriculares);
        catalog.addItem(cable);
        catalog.addItem(pack);
        catalog.addItem(comboElectronica);
    }

    // NOMBRE CONTIENE 
    @Test // busca por el criterio "cable" y verifica que lo contenga
    void busquedaYVerificacionPorNombre() {
        List<CatalogItem> res = catalog.search(new NameContainsCriteria("Cable"));
        assertTrue(res.contains(cable));
        assertFalse(res.contains(auriculares));
    }

    @Test // verifica que ignore las mayusculas
    void busquedaPoNombreIgnoraMayusculas() {
        List<CatalogItem> res = catalog.search(new NameContainsCriteria("BLUETOOTH"));
        assertTrue(res.contains(auriculares));
    }

    
    @Test // busca por precio y lo verifica
    void busquedaPorPrecioMaximoMenorOIgual() {
        List<CatalogItem> res = catalog.search(new MaxPriceCriteria(8000.0));
        assertTrue(res.contains(auriculares)); 
        assertTrue(res.contains(cable));       
        assertFalse(res.contains(pack));       
    }

    // CATEGORÍA 
    @Test // busca por categoria 
    void busquedaPorCategoria() {
        List<CatalogItem> res = catalog.search(new CategoryCriteria("electronica"));
        assertTrue(res.contains(auriculares));
        assertTrue(res.contains(comboElectronica)); 
        assertFalse(res.contains(pack));
    }

    @Test // busqueda por categoria y no encuentra
    void bundleSinCategoria() {
        Bundle bundleSinCategoria = new Bundle("Genérico", "desc", 0.05, "generico");
        catalog.addItem(bundleSinCategoria);
        List<CatalogItem> res = catalog.search(new CategoryCriteria("Electronica"));
        assertFalse(res.contains(bundleSinCategoria)); 
    }

    // DISPONIBILIDAD 
    @Test // busqueda por stock 
    void busquedaPorStock() {
        List<CatalogItem> res = catalog.search(new AvailabilityCriteria());
        assertTrue(res.contains(auriculares));
        assertFalse(res.contains(cable)); 
    }

    // AND 
    @Test // verifica que funcione la busqueda compuesta con AND
    void andRequiereTodosLosCriterios() {
        SearchCriteria criterio = new AndCriteria(List.of(
            new CategoryCriteria("Electronica"),
            new MaxPriceCriteria(10000.0),
            new AvailabilityCriteria()
        ));
        List<CatalogItem> res = catalog.search(criterio);
        assertTrue(res.contains(auriculares));
        assertFalse(res.contains(cable)); 
    }

    @Test // verifica que AND no funcina si no cumple con todos los criterios
    void andFallaSiUnoCualquieraNoCumple() {
        SearchCriteria criterio = new AndCriteria(List.of(
            new CategoryCriteria("Electronica"),
            new MaxPriceCriteria(1000.0) 
        ));
        assertFalse(catalog.search(criterio).contains(auriculares));
    }

    // OR 
    @Test // verifica que OR funcione con al menos un criterio
    void busquedaConAlMenosUnCriterio() {
        SearchCriteria criterio = new OrCriteria(List.of(
            new NameContainsCriteria("cable"),
            new NameContainsCriteria("adaptador")
        ));
        List<CatalogItem> res = catalog.search(criterio);
        assertTrue(res.contains(cable));
        assertFalse(res.contains(auriculares));
    }

    // NOT 
    @Test // verifica que funcione el criterio NOT
    void notNiegaElCriterio() {
        List<CatalogItem> res = catalog.search(new NotCriteria(new CategoryCriteria("Indumentaria")));
        assertTrue(res.contains(auriculares));
        assertTrue(res.contains(cable));
        assertFalse(res.contains(pack)); 
    }

    // ANIDADO 
    @Test // verifica que funcionen todos los criterios recursivamente
    void criteriosAnidadosFuncionanRecursivamente() {
        SearchCriteria criterio = new OrCriteria(List.of(
            new AndCriteria(List.of(
                new CategoryCriteria("Electronica"),
                new AvailabilityCriteria()
            )),
            new NameContainsCriteria("oferta")
        ));
        List<CatalogItem> res = catalog.search(criterio);
        assertTrue(res.contains(auriculares));  
        assertTrue(res.contains(pack));          
        assertFalse(res.contains(cable));        
    }
    
    @Test // verifica que funcionen todos los criterios recursivamente
    void errorEnAndPorCriterioNull() {
    	Exception e = assertThrows(IllegalArgumentException.class, () ->
        	new AndCriteria(List.of())
        );
		
		assertEquals("Error: AND necesita al menos un criterio", e.getMessage());
    }
    
    @Test // verifica que funcionen todos los criterios recursivamente
    void errorEnOrPorCriterioNull() {
    	Exception e = assertThrows(IllegalArgumentException.class, () ->
        	new OrCriteria(List.of())
        );
		
		assertEquals("Error: OR necesita al menos un criterio", e.getMessage());
    }
    
    @Test // verifica que funcionen todos los criterios recursivamente
    void errorEnNotCriterioNull() {
    	Exception e = assertThrows(IllegalArgumentException.class, () ->
        	new NotCriteria(null)
        );
		
		assertEquals("Error: NOT necesita un criterio", e.getMessage());
    }


}
