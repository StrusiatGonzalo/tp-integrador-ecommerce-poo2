package product;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
	
}
