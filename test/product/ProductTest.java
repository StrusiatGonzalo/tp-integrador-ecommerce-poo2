package product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ecommerce.catalog.Attribute;
import ecommerce.catalog.Bundle;
import ecommerce.catalog.Product;

class ProductTest {
	
	Product ibuprofeno;
	Product ponstil;
	Product gaza;
	Product curitas;
	Product alcohol;
	Product mertiolateC;
	Product mertiolateS;
	Product aguaOxigenada;
	Product cintaHipoalergenica;
	Product productoInvalido;
	Product sertal;
	
	Attribute<Double> mililitros;
	Attribute<String> indicacion;
	Attribute<Boolean> ventaLibre;
	Attribute<String> laboratorio; 
	
	Bundle botiquinPA;
	Bundle botiquin;
	
	List<Attribute<?>> attributes;
	
	@BeforeEach
	void setUp() {
		
		// Productos
		ibuprofeno = new Product("P01", "Ibuprofeno 600", "Ibupirac", "Pastillas", "Ibuprofeno 600mg blister x10", 200.0, 2000.0, 10);
		ponstil = new Product("P02", "Postil 500", "Posti", "Pastillas", "Ponstil 500mg blister x20", 100.0, 10000.0, 10);
		gaza = new Product("A01", "Gaza", "Castrol", "Apositos", "Gaza caja 10u", 300.0, 5000.0, 5);
		curitas = new Product("A02", "Curitas", "IVC", "Apositos", "Curitas 20u", 80.0, 1500.0, 10);
		alcohol = new Product("B01", "Alcohol", "ACME", "Botellas", "Alcohol 70% botella", 500.0, 2000.0, 20);
		mertiolateC = new Product("C01", "Mertiolate crema", "ACME", "Crema", "Crema Mertiolate", 200.0, 5000.0, 8);
		mertiolateS = new Product("B03", "Mertiolate spray", "ACME", "Spray", "Mertiolate en spray", 400.0, 3000.0, 20);
		cintaHipoalergenica = new Product("A03", "Cinta hipoalergenica", "Cintach", "Primeros auxilios", "Cinta hipoalergénica 25m", 25.0, 6350.0, 10);
		
		// Atributos
		mililitros = new Attribute<Double>("Ml", 200.0);
		indicacion = new Attribute<String>("Indicacion", "Quemaduras");
		ventaLibre = new Attribute<Boolean>("Venta libre", true);
		
		// Bundles
		botiquinPA = new Bundle("Botiquin PA","Botiquin primeros auxilios", 0.1);
		botiquin = new Bundle("Botiquin Completo","Botiquin Completo", 0.15);

		// Listas de Atributos
		attributes = new ArrayList<>();
		attributes.add(ventaLibre);
		attributes.add(indicacion);
		
		// Producto con lista de atributos dinamicos
		sertal = new Product("P03", "Sertal Perlas", "Sertalito", "Pastillas", "Sertal 50mg blister x10", 30.0, 2000.0, 15, attributes);
	}
	
	@Test // test inicial - getters
	void testGetters() {
		assertEquals("Posti", ponstil.getBrand());
		assertEquals("Gaza", gaza.getName());
		assertEquals("A02", curitas.getSku());
		assertEquals("Crema", mertiolateC.getCategory());
		assertEquals("Ibuprofeno 600mg blister x10", ibuprofeno.getDescription());
	}
	
	@Test // test simple de descuentos
	void testProductDiscount() { // testear aplicación de descuento sobre el precio de un producto 
		ibuprofeno.setDiscountRate(0.2);
		assertEquals(0.2, ibuprofeno.getDiscountRate());
		assertNotEquals(2000, ibuprofeno.getBasePrice()); // el precio base ya tiene el descuento
		assertEquals(1600.00, ibuprofeno.getBasePrice());
	}
	
	@Test // test - caso de descuentos
	void testDiscountError() { //
		Exception e = assertThrows(IllegalArgumentException.class, () -> alcohol.setDiscountRate(1.2));
		assertEquals("Error: El descuento no puede superar el 100%", e.getMessage());
	}
	
	@Test // test agregar atributos dinámicos
	void testAddExtraAttributes() {
		mertiolateC.addExtraAttribute(mililitros);
		mertiolateC.addExtraAttribute(ventaLibre);
		
		List<Attribute<?>> atributos = mertiolateC.getExtraAttributes();
		
		assertTrue(atributos.contains(mililitros));
		assertFalse(atributos.contains(indicacion));
		assertTrue(atributos.contains(ventaLibre));	
	}
	
	
	@Test // test errores de atributos. Lanza el sku porque es el primero
	void testValidateAttributesSKU() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("", "Invalid", "Not Brand", "", "",10.0, 0.0, 0)
	    );

	    assertEquals("Error: El sku es invalido", e.getMessage());
	}
	
	@Test 
	void testValidateAttributesName() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("1", "", "Not Brand", "", "",10.0, 0.0, 0)
	    );

	    assertEquals("Error: El nombre es invalido", e.getMessage());
	}
	
	@Test 
	void testValidateAttributesBrand() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("1", "Invalid", null, "", "",10.0, 0.0, 0)
	    );

	    assertEquals("Error: La marca es invalida", e.getMessage());
	}
	
	@Test
	void testValidateAttributesCategory() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("1", "Invalid", "Not Brand", "", "",10.0, 0.0, 0)
	    );

	    assertEquals("Error: La categoria es invalida", e.getMessage());
	}
	
	@Test
	void testValidateAttributesPrice() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("1", "Invalid", "Not Brand", "null category", "",10.0, -10.0, 0)
	    );

	    assertEquals("Error: El precio es invalido", e.getMessage());
	}
	
	@Test
	void testValidateAttributesWeight() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("1", "Invalid", "Not Brand", "null category", "",-10.0, 10.0, 0)
	    );

	    assertEquals("Error: El peso es invalido", e.getMessage());
	}
	
	@Test
	void testValidateAttributesStock() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("1", "Invalid", "Not Brand", "null category", "",10.0, 10.0, -1)
	    );

	    assertEquals("Error: El stock inicial es invalido", e.getMessage());
	}
}
