package product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ecommerce.catalog.Bundle;

class BundleTest extends AttributesTest{

	
	// Testeamos un bundle solo con productos
	@Test
	void agregaProductosAlBundle() {
		botiquinPA.addItem(curitas);
		botiquinPA.addItem(gaza);
		botiquinPA.addItem(mertiolateS);

		assertEquals("Botiquin PA", botiquinPA.getName());
		assertEquals("Botiquin primeros auxilios", botiquinPA.getDescription());
		assertEquals(8550, botiquinPA.getBasePrice());
	}
	
	// Testeamos un bundle con productos y otro budle dentro
	@Test
	void agregaProductosYBundlesAlBundle() {
		botiquinPA.addItem(curitas);
		botiquinPA.addItem(gaza);
		botiquinPA.addItem(mertiolateS);

		botiquin.addItem(botiquinPA);
		botiquin.addItem(alcohol);
		botiquin.addItem(cintaHipoalergenica);

		assertEquals("Botiquin Completo", botiquin.getName());
		assertEquals("Botiquin Completo", botiquin.getDescription());
		assertEquals(14365.00, botiquin.getBasePrice());
	}

	// Testeamos que si la lista de prodcutos dentro de un budle esta vacia, el precio sea 0. (CASO BORDE)
	@Test
	void testListaVacia() {
		assertEquals(0, botiquin.getBasePrice());
	}
	
	// testeamos el stock recursivo con bundle anidado
	@Test
	void hasStockRecursivoConBundleAnidado() {
		botiquinPA.addItem(curitas);
		botiquinPA.addItem(gaza);

		botiquin.addItem(botiquinPA);
		botiquin.addItem(alcohol);
		botiquin.addItem(cintaHipoalergenica);

		assertTrue(botiquin.hasStock(5));
		assertFalse(botiquin.hasStock(6));
	}

	// testeamos el calculo del peso recursivo
	@Test
	void getWeightSumaPesosDeItemsYBundlesAnidados() {
		botiquinPA.addItem(curitas);
		botiquinPA.addItem(gaza);

		botiquin.addItem(botiquinPA);
		botiquin.addItem(alcohol);

		assertEquals(880.0, botiquin.getWeight());
	}

	// testeamos el validate del constructor del bundle
	@Test
	void bundleConNombreVacioLanzaExcepcion() {
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			new Bundle("", "desc", 0.1));
		assertEquals("Error: El nombre del paquete es invalido", e.getMessage());
	}

	@Test
	void bundleConDescuentoNegativoLanzaExcepcion() {
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			new Bundle("Nombre", "desc", -0.1));
		assertEquals("Error: El descuento del paquete es invalido", e.getMessage());
	}
}


