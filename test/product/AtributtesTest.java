package product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AtributtesTest extends ProductTest{

	//setUp de ProductTest
	
	@Test // test getValue de todas las clases de atributos
	void testValuesExtraAtributtes() {
		assertEquals(200.0, mililitros.getValue());
		assertEquals(true, ventaLibre.getValue());
		assertEquals("Quemaduras", indicacion.getValue());
	}

	@Test // getName de todas clases de atributos
	void testNamesExtraAtributtes() {
		assertEquals("Ml", mililitros.getName());
		assertEquals("Venta libre", ventaLibre.getName());
		assertEquals("Indicacion", indicacion.getName());
	}
	
	@Test // hasValue de todos los atributos dinámicos
	void testExtraAtributtesHasValues() {
		assertTrue(mililitros.hasValue());
		assertTrue(ventaLibre.hasValue());
		assertTrue(indicacion.hasValue());
	}
	
	@Test // string vacío en StringAtributte - validación
	void testStringAtributteEmpty() { // es false porque el valor es un string vacío
		// Atributte<String> laboratorio = new StringAtributte("Laboratorio", "");
		assertFalse(laboratorio.hasValue());
	}
	
	@Test
	void testShowValue() { //muestra los valores como string
		assertEquals("true", ventaLibre.showValue());
		assertEquals("", laboratorio.showValue()); //no tiene un valor
		assertEquals("Quemaduras", indicacion.showValue());
		assertEquals("200.0", mililitros.showValue());
	}
	
	@Test //test el parseo valor de los atributos dinamicos y el metodo compareTo
	void testParseValue() {
		assertTrue(mililitros.compareTo("200.0"));
		assertTrue(ventaLibre.compareTo("true"));
		assertTrue(indicacion.compareTo("Quemaduras"));
		assertFalse(laboratorio.compareTo("Bago"));
		assertFalse(mililitros.compareTo("201.0"));
		assertFalse(ventaLibre.compareTo("asd"));
		
	}
	
	//filtro inicial con atributos estaticos: producto
	// filtro con atributos dinamicos: productos**
}
