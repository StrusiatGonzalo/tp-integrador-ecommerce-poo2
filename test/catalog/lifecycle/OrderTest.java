package catalog.lifecycle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Bundle;
import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentMethod;
import ecommerce.catalog.lifecycle.shippingmethods.ExpressShipping;
import ecommerce.catalog.lifecycle.shippingmethods.ExpressShippingAPI;


@ExtendWith(MockitoExtension.class)
class OrderTest {

	@Mock ExpressShippingAPI shippingApi;
	@Mock PaymentMethod paymentMethod;
	
	Product paracetamol;
	Product jeringa;
	Product algodon;
	Product agujasDescartables;
	Product alcoholEnGel;
	Product termometro;
	ExpressShipping envioExpres;

	Bundle kitInyectable;

	Order order;

	@BeforeEach
	void setUp() {
		// productos 
		paracetamol = new Product("P05", "Paracetamol 500", "Genérico", "Pastillas", "Paracetamol 500mg blister x20", 50.0, 1200.0, 15);
		jeringa = new Product("J01", "Jeringa 5ml", "BD", "Inyectables", "Jeringa descartable 5ml", 10.0, 350.0, 30);
		algodon = new Product("A05", "Algodon", "Suavet", "Apositos", "Algodon 100g", 100.0, 900.0, 8);
		agujasDescartables = new Product("J02", "Agujas descartables", "BD", "Inyectables", "Agujas 21G x10u", 5.0, 600.0, 25);
		alcoholEnGel = new Product("B05", "Alcohol en gel", "ACME", "Botellas", "Alcohol en gel 250ml", 250.0, 1800.0, 12);
		termometro = new Product("T01", "Termometro digital", "Microlife", "Equipos", "Termometro digital de uso clinico", 50.0, 4500.0, 6);
		envioExpres = new ExpressShipping(shippingApi);

		// bundle (paquete)
		kitInyectable = new Bundle("Kit Inyectable", "Kit para administracion de inyectables", 0.1, "Inyectables");

		// pedido
		order = new Order("Avenida Siempreviva 742", envioExpres, paymentMethod,"mail");
	}

	@Test // test de totalCost, multiplicando por cantidad
	void totalCostMultiplicaPorCantidad() {
		order.add(jeringa, 4);
		assertEquals(1400.0, order.totalCost());
	}

	@Test // test de totalWeight, multiplicando por cantidad
	void totalWeightMultiplicaPorCantidad() {
		order.add(jeringa, 4);
		assertEquals(40.0, order.totalWeight());
	}

	@Test // hay stock suficiente para las cosas en el pedido
	void isEverythingInStockTrueCuandoHayStock() {
		order.add(paracetamol, 10);
		assertTrue(order.isEverythingInStock());
	}

	@Test // no hay stock suficiente para las cosas del pedido
	void isEverythingInStockFalseCuandoNoHayStock() {
		order.add(termometro, 7); 
		assertFalse(order.isEverythingInStock());
	}

	@Test // el mismo producto suelto + dentro de un bundle
	void demandaCombinadaDetectaFaltaDeStockEntreSueltoYBundle() {
		kitInyectable.addItem(jeringa);

		order.add(kitInyectable, 1);
		order.add(jeringa, 30);

		assertFalse(order.isEverythingInStock());
	}

	@Test // el total coincide justo con el stock disponible
	void demandaCombinadaPasaCuandoElTotalCoincideConElStock() {
		kitInyectable.addItem(agujasDescartables);

		order.add(kitInyectable, 5);
		order.add(agujasDescartables, 20);

		assertTrue(order.isEverythingInStock());
	}
	
	@Test // se confirma el pedido decrementa stock cuando hay suficiente
	void confirmDecrementaStockCuandoHayStockSuficiente() {
		order.add(algodon, 3);
		order.confirm();
		assertEquals(5, algodon.getStock());
	}
	
	@Test // se confirma el pedido pero no decrementa nada y lanza excepcion si falta stock
	void confirmLanzaExcepcionYNoDecrementaNadaSiFaltaStock() {
		order.add(alcoholEnGel, 13);

		Exception e = assertThrows(IllegalArgumentException.class, () -> order.confirm());
		assertEquals("Error: no hay stock suficiente para confirmar el pedido", e.getMessage());
		assertEquals(12, alcoholEnGel.getStock());
	}

	@Test // se confirma con stock conjunto insuficiente: tampoco decrementa nada
	void confirmConDemandaCombinadaInsuficienteNoDecrementaNada() {
		kitInyectable.addItem(jeringa);

		order.add(kitInyectable, 1);
		order.add(jeringa, 30);

		assertThrows(IllegalArgumentException.class, () -> order.confirm());
		assertEquals(30, jeringa.getStock());
	}

	@Test // se confirma con bundle y producto distinto
	void confirmConBundleYProductoDistintoSinSolapamiento() {
		kitInyectable.addItem(jeringa);
		kitInyectable.addItem(agujasDescartables);

		order.add(kitInyectable, 4);
		order.add(termometro, 2);

		order.confirm();

		assertEquals(26, jeringa.getStock());
		assertEquals(21, agujasDescartables.getStock());
		assertEquals(4, termometro.getStock());
	}	
	
	@Test
	void orderConDireccionNulaLanzaExcepcion() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Order(null, new ExpressShipping(shippingApi), paymentMethod, "test@mail.com")
	    );
	    assertEquals("Error: La dirección de envío es inválida", e.getMessage());
	}

	@Test
	void orderConDireccionVaciaLanzaExcepcion() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Order("   ", new ExpressShipping(shippingApi), paymentMethod, "test@mail.com")
	    );
	    assertEquals("Error: La dirección de envío es inválida", e.getMessage());
	}

	@Test
	void orderConShippingTypeNuloLanzaExcepcion() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Order("Av. Test 1", null, paymentMethod, "test@mail.com")
	    );
	    assertEquals("Error: El tipo de envío no es válido", e.getMessage());
	}

	@Test
	void orderConPaymentMethodNuloLanzaExcepcion() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Order("Av. Test 1", new ExpressShipping(shippingApi), null, "test@mail.com")
	    );
	    assertEquals("Error: El metodo de pago es válido", e.getMessage());
	}

	@Test
	void orderConEmailNuloLanzaExcepcion() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Order("Av. Test 1", new ExpressShipping(shippingApi), paymentMethod, null)
	    );
	    assertEquals("Error: La dirección de email es inválida", e.getMessage());
	}

	@Test
	void orderConEmailVacioLanzaExcepcion() {
	    Exception e = assertThrows(IllegalArgumentException.class, () ->
	        new Order("Av. Test 1", new ExpressShipping(shippingApi), paymentMethod, "  ")
	    );
	    assertEquals("Error: La dirección de email es inválida", e.getMessage());
	}

}
