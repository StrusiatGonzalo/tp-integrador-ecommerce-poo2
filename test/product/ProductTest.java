package product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ecommerce.Atributte;
import ecommerce.DoubleAtributte;
import ecommerce.StringAtributte;
import ecommerce.BooleanAtributte;
import ecommerce.Bundle;
import ecommerce.Product;

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
	
	Atributte<Double> mililitros;
	Atributte<String> indicacion;
	Atributte<Boolean> ventaLibre;
	Atributte<String> laboratorio; 
	
	Bundle botiquinPA;
	Bundle botiquin;
	
	@BeforeEach
	void setUp() {
		// Productos
		ibuprofeno = new Product("P01", "Ibuprofeno 600", "Ibupirac", "Pastillas", "Ibuprofeno 600mg blister x10", 2000.0);
		ponstil = new Product("P02", "Postil 500", "Posti", "Pastillas", "Ponstil 500mg blister x20", 10000.0);
		gaza = new Product("A01", "Gaza", "Castrol", "Apositos", "Gaza caja 10u", 5000.0);
		curitas = new Product("A02", "Curitas", "IVC", "Apositos", "Curitas 20u", 1500.0);
		alcohol = new Product("B01", "Alcohol", "ACME", "Botellas", "Alcohol 70% botella", 2000.0);
		mertiolateC = new Product("C01", "Mertiolate crema", "ACME", "Crema", "Crema Mertiolate", 5000.0);
		mertiolateS = new Product("B03", "Mertiolate spray", "ACME", "Spray", "Mertiolate en spray", 3000.0);
		cintaHipoalergenica = new Product("A03", "Cinta hipoalergenica", "Cintach", "Primeros auxilios", "Cinta hipoalergénica 25m", 6350.0);
		
		// Atributos
		mililitros = new DoubleAtributte("Ml", 200.0);
		indicacion = new StringAtributte("Indicacion", "Quemaduras");
		ventaLibre = new BooleanAtributte("Venta libre", true);
		laboratorio = new StringAtributte("Laboratorio", "");
		
		// Bundles
		botiquinPA = new Bundle("Botiquin PA","Botiquin primeros auxilios", 0.1);
		botiquin = new Bundle("Botiquin Completo","Botiquin Completo", 0.15);
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
	void testAddExtraAtributtes() {
		mertiolateC.addExtraAtributte(mililitros);
		mertiolateC.addExtraAtributte(ventaLibre);
		
		List<Atributte<?>> atributos = mertiolateC.getExtraAtributtes();
		
		assertTrue(atributos.contains(mililitros));
		assertFalse(atributos.contains(indicacion));
		assertTrue(atributos.contains(ventaLibre));	
	}
	
	
	@Test // test errores de atributos. Lanza el sku porque es el primero
	void testValidateAtributtes() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Product("", "Invalid", "Not Brand", "", "", 0.0)
	    );

	    assertEquals("Error: El sku es invalido", e.getMessage());
	}
	
}
