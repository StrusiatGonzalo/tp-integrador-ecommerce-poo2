package catalog.lifecycle.notification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Canceled;
import ecommerce.catalog.lifecycle.Confirmed;
import ecommerce.catalog.lifecycle.Delivered;
import ecommerce.catalog.lifecycle.Draft;
import ecommerce.catalog.lifecycle.InPreparation;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.Sent;
import ecommerce.catalog.lifecycle.notifications.EmailNotifier;
import ecommerce.catalog.lifecycle.notifications.InvoiceGenerator;
import ecommerce.catalog.lifecycle.notifications.LoyaltyProgram;
import ecommerce.catalog.lifecycle.notifications.MailSender;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentMethod;
import ecommerce.catalog.lifecycle.shippingmethods.LocalPickUp;

@ExtendWith(MockitoExtension.class)
class NotificationTest {

    @Mock MailSender mailSender;
    @Mock PaymentMethod paymentMethod;

    EmailNotifier emailNotifier;
    LoyaltyProgram loyaltyProgram;
    InvoiceGenerator invoiceGenerator;
    Order order;

    @BeforeEach
    void setUp() {
        emailNotifier = new EmailNotifier(mailSender);
        loyaltyProgram = new LoyaltyProgram(mailSender);
        invoiceGenerator = new InvoiceGenerator();

        order = new Order("Av. Siempreviva 742", new LocalPickUp(), paymentMethod, "cliente@mail.com");
    }

    // EMAIL NOTIFIER 
    @Test // envia un correo cuando pasa de borrador a confirmado
    void emailEnviaMailCuandoPasaAConfirmado() {
        emailNotifier.onStateChanged(order, new Draft(), new Confirmed());
        verify(mailSender).enviarMail(eq("cliente@mail.com"), any(), any(), any()); // esta verificando que la api se ejecuto 
    }

    @Test // envia un correo cuando pasa de confirmado a enviado
    void emailEnviaMailCuandoPasaAEnviado() {
        emailNotifier.onStateChanged(order, new Confirmed(), new Sent());
        verify(mailSender).enviarMail(eq("cliente@mail.com"), any(), any(), any());
    }

    @Test // envia un correo cuando pasa de enviado a entregado
    void emailEnviaMailCuandoPasaAEntregado() {
        emailNotifier.onStateChanged(order, new Sent(), new Delivered());
        verify(mailSender).enviarMail(eq("cliente@mail.com"), any(), any(), any());
    }

    @Test // envia un correo cuando pasa de borrador a cancelado
    void emailNoEnviaMailCuandoPasaACancelado() {
        emailNotifier.onStateChanged(order, new Draft(), new Canceled());
        verifyNoInteractions(mailSender);
    }

    @Test // envia un correo cuando pasa de confirmado a en preparacion
    void emailNoEnviaMailCuandoPasaAEnPreparacion() {
        emailNotifier.onStateChanged(order, new Confirmed(), new InPreparation());
        verifyNoInteractions(mailSender);
    }

    @Test // envia un correo cuando pasa de borrador a confirmado
    void emailElMensajeContieneElNuevoEstado() {
        emailNotifier.onStateChanged(order, new Draft(), new Confirmed());
        ArgumentCaptor<String> mensaje = ArgumentCaptor.forClass(String.class);
        verify(mailSender).enviarMail(any(), any(), mensaje.capture(), any());
        assertTrue(mensaje.getValue().contains("CONFIRMADO"));
    }

    // LOYALTY PROGRAM (programa de fidelizacion)
    @Test // el programa envia un cupon cuando el estado pasa a cancelado
    void loyaltyEnviaCuponCuandoPasaACancelado() {
        loyaltyProgram.onStateChanged(order, new Confirmed(), new Canceled());
        verify(mailSender).enviarMail(eq("cliente@mail.com"), eq("CUPON DE DESCUENTO"), any(), any());
    }

    @Test // transiciona a todos los estados y no envia ningun mail
    void loyaltyNoEnviaCuponParaNingunaOtraTransicion() {
        loyaltyProgram.onStateChanged(order, new Draft(), new Confirmed());
        loyaltyProgram.onStateChanged(order, new Confirmed(), new InPreparation());
        loyaltyProgram.onStateChanged(order, new Sent(), new Delivered());
        verifyNoInteractions(mailSender);
    }

    // INVOICE GENERATOR 
    @Test // crea una factura cuando el pedido pasa a entregado
    void invoiceGeneratorCreaFacturaCuandoElPedidoEsEntregado() {
        invoiceGenerator.onStateChanged(order, new Sent(), new Delivered());
        assertEquals(1, invoiceGenerator.getInvoices().size());
    }

    @Test // no genera una factura cuando el estado es cancelado
    void invoiceGeneratorNoGeneraFacturaCuandoEsCancelado() {
        invoiceGenerator.onStateChanged(order, new Sent(), new Canceled());
        assertEquals(0, invoiceGenerator.getInvoices().size());
    }

    @Test // no genera factura cuando es confirmado
    void invoiceGeneratorNoGeneraFacturaCuandoEsConfirmado() {
        invoiceGenerator.onStateChanged(order, new Draft(), new Confirmed());
        assertEquals(0, invoiceGenerator.getInvoices().size());
    }

    @Test // se chequea los datos de la factura
    void invoiceGeneratorIntegradoComoObserverGeneraUnaFacturaAlEntregar() {
        Product venda = new Product("V01", "Venda", "Suavet", "Apositos", "desc", 1.0, 500.0, 10);
        order.add(venda, 2);
        order.subscribe(invoiceGenerator);

        order.confirm(); 
        order.start();  
        order.send();    
        order.deliver(); 

        assertEquals(1, invoiceGenerator.getInvoices().size());
        assertEquals(1000.0, invoiceGenerator.getInvoices().get(0).getAmount()); 
        assertEquals("Av. Siempreviva 742", invoiceGenerator.getInvoices().get(0).getAddress());
    }
}
