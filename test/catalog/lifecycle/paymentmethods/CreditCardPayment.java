package catalog.lifecycle.paymentmethods;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.paymentmethods.CreditCardAPI;
import ecommerce.catalog.lifecycle.paymentmethods.CreditCardPayment;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentMethod;
import ecommerce.catalog.lifecycle.shippingmethods.ShippingType;

@ExtendWith(MockitoExtension.class)
class CreditCardPaymentTest {

    @Mock private CreditCardAPI apiMock;
    @Mock private ShippingType shippingMock;
    @Mock private PaymentMethod dummyPayment; // para construir Order, luego lo reemplazamos

    private Order order;
    private CreditCardPayment payment;

    @BeforeEach
    void setUp() {
        // Construir Order con un PaymentMethod dummy (luego lo reemplazamos)
        order = new Order("Calle Falsa 123", shippingMock, dummyPayment, "cliente@test.com");
        payment = new CreditCardPayment(apiMock, "1234567890123456", "123", "12/25");
        order.setPaymentMethod(payment); // reemplazamos el dummy por el real
    }

    @Test
    void testProcessCallsValidatePreAuthorizeAndCharge() {
        when(apiMock.preAuthorize(anyDouble())).thenReturn("OP-789");

        payment.process(order);

        verify(apiMock).validateCard("1234567890123456", "123", "12/25");
        verify(apiMock).preAuthorize(order.getTotalToPay());
        verify(apiMock).charge(order.getTotalToPay(), "OP-789");
        assertNotNull(payment.getReceipt());
        assertEquals(order.getTotalToPay(), payment.getReceipt().getAmount());
        assertEquals("OP-789", payment.getReceipt().getOperationNumber());
    }

    @Test
    void testValidateDataDelegatesToApi() {
        payment.validateData(order);
        verify(apiMock).validateCard("1234567890123456", "123", "12/25");
    }

    @Test
    void testSetAsideFundsCallsPreAuthorizeAndStoresOperationNumber() {
        when(apiMock.preAuthorize(order.getTotalToPay())).thenReturn("OP-123");

        payment.setAsideFunds(order);

        verify(apiMock).preAuthorize(order.getTotalToPay());
        assertEquals("OP-123", payment.getOperationNumber());
    }

    @Test
    void testExecuteTransactionCallsCharge() {
        // Primero simulamos que ya tenemos un número de operación (setAsideFunds ya lo puso)
        payment.setOperationNumber("OP-456");

        payment.executeTransaction(order);

        verify(apiMock).charge(order.getTotalToPay(), "OP-456");
    }

    @Test
    void testNotifyResultGeneratesReceipt() {
        payment.setOperationNumber("OP-999");
        payment.notifyResult(order);

        assertNotNull(payment.getReceipt());
        assertEquals(order.getTotalToPay(), payment.getReceipt().getAmount());
        assertEquals("OP-999", payment.getReceipt().getOperationNumber());
    }

    // Casos borde: si preAuthorize devuelve null, igual debe funcionar
    @Test
    void testSetAsideFundsWithNullOperationNumber() {
        when(apiMock.preAuthorize(anyDouble())).thenReturn(null);
        payment.setAsideFunds(order);
        assertNull(payment.getOperationNumber());
    }
}