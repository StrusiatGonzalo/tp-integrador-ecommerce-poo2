package catalog.lifecycle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.OrderItem;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentMethod;
import ecommerce.catalog.lifecycle.shippingmethods.ExpressShipping;
import ecommerce.catalog.lifecycle.shippingmethods.ExpressShippingAPI;

@ExtendWith(MockitoExtension.class)
class StateTest {
	
	@Mock ExpressShippingAPI shippingApi;
	@Mock PaymentMethod paymentMethod;
	
	ExpressShipping envioExpress;

	Product venda;
	Order order;

	@BeforeEach
	void setUp() {
		
		envioExpress = new ExpressShipping(shippingApi);
		
		// producto generico para armar el pedido en cada test
		venda = new Product("V01", "Venda elastica", "Suavet", "Apositos", "Venda elastica 10cm", 60.0, 1100.0, 10);
		order = new Order("Avenida Siempreviva 742", envioExpress, paymentMethod, "correo@mail.com");
		order.add(venda, 2);
	}

	// BORRADOR

	@Test // se agrega item en estado de borrador
	void draftPermiteAgregarItem() {
		//order.add(venda, 2);
		assertEquals(1, order.getItems().size());
	}

	@Test // se confirma el pedido, pasa de borrador a confirmado
	void draftConfirmaYPasaAConfirmado() {
		//order.add(venda, 2);
		order.confirm();
		assertEquals("CONFIRMADO", order.getState().getName());
	}
	
	@Test // no se confirma el pedido porque no tiene suficiente stock
	void draftConfirmaYLanzaErrorPorNoTenerStock() {
		order.add(venda, 15);
		Exception e = assertThrows(IllegalArgumentException.class, () -> order.confirm());
		assertEquals("Error: no hay stock suficiente para confirmar el pedido", e.getMessage());
		
	}

	@Test // se cancela un borrador, pasa de borrador a cancelado
	void draftCancelaYPasaACancelado() {
		order.cancel();
		assertEquals("CANCELADO", order.getState().getName());
	}

	@Test // desde borrador no se puede empezar a preparar un pedido
	void draftNoPermiteStart() {
		assertThrows(IllegalStateException.class, () -> order.start());
	}

	@Test // desde borrador no se puede enviar un pedido
	void draftNoPermiteSend() {
		assertThrows(IllegalStateException.class, () -> order.send());
	}

	@Test // desde borrador no se puede entregar un pedido
	void draftNoPermiteDeliver() {
		assertThrows(IllegalStateException.class, () -> order.deliver());
	}

	@Test // borrar un item que no esta en el pedido lanza error
	void draftDeleteItemInexistenteLanzaError() {
		OrderItem itemSuelto = new OrderItem(venda, 1); // nunca se agrego al pedido
		Exception e = assertThrows(IllegalArgumentException.class, () -> order.delete(itemSuelto));
		assertEquals("Error: El item no esta en la orden", e.getMessage());
	}
	
	@Test // borrar un item que esta
	void draftDeleteItemExistente() {
		OrderItem itemSuelto = new OrderItem(venda, 1);
		order.addNewItem(itemSuelto); // agregue un item
		order.delete(itemSuelto);
		assertEquals(1, order.getItems().size()); // verifico si se quito el item
	}

	// CONFIRMADO

	@Test // confirmado puede empezar a prepararse, pasa a estado en_preparacion
	void confirmadoPermiteStart() {
		//order.add(venda, 2);
		order.confirm();
		assertTrue(order.getState().isSuccessfulProgress()); // cuando el estado se pone en confirmado se verifica
		order.start();
		assertEquals("EN_PREPARACION", order.getState().getName());
	}

	@Test // confirmado pasa a cancelado y repone el stock de lo decrementado
	void confirmadoCancelaYReponeStock() {
		//order.add(venda, 3);
		order.confirm();
		order.cancel();
		assertEquals(10, venda.getStock());
		assertEquals("CANCELADO", order.getState().getName());
	}

	@Test // confirmado no puede volver a agregar items
	void confirmadoNoPermiteAddItem() {
		//order.add(venda, 1);
		order.confirm();
		assertThrows(IllegalStateException.class, () -> order.add(venda, 1));
	}

	@Test // confirmado no puede volver al estado confirmarse
	void confirmadoNoPermiteConfirmarDeNuevo() {
		//order.add(venda, 1);
		order.confirm();
		assertThrows(IllegalStateException.class, () -> order.confirm());
	}

	// EN_PREPARACION

	@Test // en preparacion puede enviarse, pasa a estado enviado
	void enPreparacionPermiteSend() {
		//order.add(venda, 2);
		order.confirm();
		order.start();
		order.send();
		assertTrue(order.getState().isSuccessfulProgress());
		assertEquals("ENVIADO", order.getState().getName());
		
	}

	@Test // en preparacion al cancelar el pedido repone el stock y genera dos notas de credito
	void enPreparacionCancelaReponeStockYGeneraDosNotasDeCredito() {
		//order.add(venda, 4);
		order.confirm();
		order.start();
		order.cancel();

		assertEquals(10, venda.getStock());
		assertEquals(2, order.getCreditNote().size());
	}

	@Test // en preparacion no puede pasar a estado confirmado
	void enPreparacionNoPermiteConfirmar() {
		//order.add(venda, 1);
		order.confirm();
		order.start();
		assertThrows(IllegalStateException.class, () -> order.confirm());
	}

	// ENVIADO

	@Test // enviado puede entregarse, pasa a estado entregado
	void enviadoPermiteDeliver() {
		//order.add(venda, 2);
		order.confirm();
		order.start();
		order.send();
		order.deliver();
		assertTrue(order.getState().isFinal()); // verifica que es un estado terminal
		assertTrue(order.getState().isSuccessfulProgress());
		assertEquals("ENTREGADO", order.getState().getName());
	}

	@Test // enviado al cancelar el pedido solo genera una nota de credito y no repone stock
	void enviadoCancelaSoloReembolsaProductosSinReponerStock() {
		//order.add(venda, 3);
		order.confirm();
		order.start();
		order.send();
		order.cancel();

		assertEquals(8, venda.getStock());
		assertEquals(1, order.getCreditNote().size());
	}

	@Test // enviado no puede empezar a prepararse un pedido nuevamente
	void enviadoNoPermiteStart() {
		//order.add(venda, 1);
		order.confirm();
		order.start();
		order.send();
		assertThrows(IllegalStateException.class, () -> order.start());
	}

	// ENTREGADO

	@Test // entregado no permite cancelar el pedido
	void entregadoNoPermiteCancelar() {
		//order.add(venda, 1);
		order.confirm();
		order.start();
		order.send();
		order.deliver();
		assertThrows(IllegalStateException.class, () -> order.cancel());
	}

	@Test // entregado no permite ningun otro cambio de estado
	void entregadoNoPermiteDeliverDeNuevo() {
		//order.add(venda, 1);
		order.confirm();
		order.start();
		order.send();
		order.deliver();
		assertThrows(IllegalStateException.class, () -> order.deliver());
	}

	// CANCELADO

	@Test // cancelado no permite ninguna transicion
	void canceladoNoPermiteConfirmar() {
		//order.add(venda, 1);
		order.cancel();
		assertTrue(order.getState().isCancelled()); // verifica que es un estado cancelado
		assertThrows(IllegalStateException.class, () -> order.confirm());
	}
}
