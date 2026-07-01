package catalog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ecommerce.catalog.Attribute;
import ecommerce.catalog.Product;

class AttributesTest extends ProductTest{

	//setUp de ProductTest
	
	@Test // test getValue de todas las clases de atributos
	void testValuesExtraAttributes() {
		assertEquals(200.0, mililitros.getValue());
		assertEquals(true, ventaLibre.getValue());
		assertEquals("Quemaduras", indicacion.getValue());
	}

	@Test // getName de todas clases de atributos
	void testNamesExtraAttributes() {
		assertEquals("Ml", mililitros.getName());
		assertEquals("Venta libre", ventaLibre.getName());
		assertEquals("Indicacion", indicacion.getName());
	}
	
	@Test
	void testValidateAttributesExtraName() {
		Exception e = assertThrows(IllegalArgumentException.class, () ->
        new Attribute<Double>("",0.0));
		
		assertEquals("Error: El nombre del atributo es invalido", e.getMessage());
	}
	
	@Test
	void testValidateAttributesExtraValue() {
		Exception e = assertThrows(IllegalArgumentException.class, () ->
        new Attribute<Double>("Test",null));
		
		assertEquals("Error: El valor del atributo es invalido", e.getMessage());
	}
	// filtro inicial con atributos estaticos: producto
	// filtro con atributos dinamicos: productos**
}
