package catalog.lifecycle.payment;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.paymentmethods.BankTransferAPI;
import ecommerce.catalog.lifecycle.paymentmethods.BankTransferPayment;
import ecommerce.catalog.lifecycle.paymentmethods.CreditCardAPI;
import ecommerce.catalog.lifecycle.paymentmethods.CreditCardPayment;
import ecommerce.catalog.lifecycle.paymentmethods.VirtualWalletAPI;
import ecommerce.catalog.lifecycle.paymentmethods.VirtualWalletPayment;
import ecommerce.catalog.lifecycle.shippingmethods.LocalPickUp;

@ExtendWith(MockitoExtension.class)
class PaymentTest {

    @Mock CreditCardAPI creditCardApi;
    @Mock BankTransferAPI bankTransferApi;
    @Mock VirtualWalletAPI virtualWalletApi;

    Product producto;
    CreditCardPayment pagoTarjeta;
    BankTransferPayment pagoTransferencia;
    VirtualWalletPayment pagoBilletera;

    Order orderTarjeta;
    Order orderTransferencia;
    Order orderBilletera;

    @BeforeEach
    void setUp() {
        producto = new Product("P01", "Producto", "Marca", "Cat", "desc", 1.0, 1000.0, 20);

        pagoTarjeta = new CreditCardPayment(creditCardApi, "4111111111111111", "123", "12/27");
        pagoTransferencia = new BankTransferPayment(bankTransferApi, "miAlias", "CBU123");
        pagoBilletera = new VirtualWalletPayment(virtualWalletApi, "vendedor-123", "comprador-456");

        
        orderTarjeta = new Order("Av. Test 1", new LocalPickUp(), pagoTarjeta, "test@mail.com");
        orderTransferencia = new Order("Av. Test 2", new LocalPickUp(), pagoTransferencia, "test@mail.com");
        orderBilletera = new Order("Av. Test 3", new LocalPickUp(), pagoBilletera, "test@mail.com");

        orderTarjeta.add(producto, 2); //
        orderTransferencia.add(producto, 3); //
        orderBilletera.add(producto, 1); // 
    }

    // TARJETA DE CRÉDITO 
    @Test // aca se hace la validacion de los datos con la api
    void creditCardValidateDelegaEnLaApiConLosDatosDeLaTarjeta() {
        pagoTarjeta.validateData(orderTarjeta);
        verify(creditCardApi).validateCard("4111111111111111", "123", "12/27");
    }

    @Test // ejecuta una transaccion usando el numero de autorizacion mock 
    void creditCardExecuteTransactionUsaElNumeroDePreAutorizacion() {
        when(creditCardApi.preAuthorize(2000.0)).thenReturn("AUTH-001");
        pagoTarjeta.setAsideFunds(orderTarjeta);
        pagoTarjeta.executeTransaction(orderTarjeta);
        verify(creditCardApi).charge(2000.0, "AUTH-001");
    }

    @Test // valida el fallo al cobrar
    void creditCardSiValidacionFallaNoSeLlegaACobrar() {
        doThrow(new RuntimeException("Tarjeta rechazada"))
            .when(creditCardApi).validateCard(any(), any(), any());
        assertThrows(RuntimeException.class, () -> pagoTarjeta.process(orderTarjeta));
    }

    @Test // genera un recibo y verifica el monto del recibo
    void creditCardProcessGeneraUnRecibo() {
        when(creditCardApi.preAuthorize(anyDouble())).thenReturn("AUTH-003");
        pagoTarjeta.process(orderTarjeta);
        assertNotNull(pagoTarjeta.getReceipt());
        assertEquals(2000.0, pagoTarjeta.getReceipt().getAmount());
    }

    // TRANSFERENCIA BANCARIA
    @Test // verifica que la api valide los datos 
    void bankTransferValidateDelegaEnLaApiConCbuYAlias() {
        pagoTransferencia.validateData(orderTransferencia);
        verify(bankTransferApi).validateCBU("CBU123", "miAlias");
    }

    @Test // verifica la api no tiene interacciones al reservar fondos
    void bankTransferSetAsideFundsNoInteractuaConLaApi() {
        pagoTransferencia.setAsideFunds(orderTransferencia);
        verifyNoInteractions(bankTransferApi); 
    }

    @Test // verifica que la api transfiera correctamente
    void bankTransferExecuteTransactionTransfiereElTotal() {
        when(bankTransferApi.transfer(3000.0, "CBU123")).thenReturn("OP-001");
        pagoTransferencia.executeTransaction(orderTransferencia);
        verify(bankTransferApi).transfer(3000.0, "CBU123");
    }

    // BILLETERA VIRTUAL
    @Test // la api verifica el saldo del comprador
    void virtualWalletValidateVerificaSaldoDelComprador() {
        pagoBilletera.validateData(orderBilletera);
        verify(virtualWalletApi).validateBalance("comprador-456", 1000.0);
    }

    @Test // verifica que la api bloquee el saldo
    void virtualWalletSetAsideFundsBloqueaElSaldo() {
        pagoBilletera.setAsideFunds(orderBilletera);
        verify(virtualWalletApi).blockFunds("comprador-456", 1000.0);
    }

    @Test // la api verifica que se acredite la transaccion al vendedor 
    void virtualWalletExecuteTransactionAcreditaAlVendedor() {
        when(virtualWalletApi.realTimeAccreditation("vendedor-123", 1000.0)).thenReturn("ACRED-001");
        pagoBilletera.executeTransaction(orderBilletera);
        verify(virtualWalletApi).realTimeAccreditation("vendedor-123", 1000.0);
    }

    @Test // la api verifica que se esten haciendo en orden las cosas
    void virtualWalletProcessRespetaElOrdenValidarBloquearAcreditar() {
        when(virtualWalletApi.realTimeAccreditation(any(), anyDouble())).thenReturn("ACRED-002");
        pagoBilletera.process(orderBilletera);
        InOrder orden = inOrder(virtualWalletApi);
        orden.verify(virtualWalletApi).validateBalance("comprador-456", 1000.0);
        orden.verify(virtualWalletApi).blockFunds("comprador-456", 1000.0);
        orden.verify(virtualWalletApi).realTimeAccreditation("vendedor-123", 1000.0);
    }
} 
