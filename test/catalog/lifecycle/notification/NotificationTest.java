package catalog.lifecycle.notification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Order;
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

    Product venda;
    Order order;

    @BeforeEach
    void setUp() {
        emailNotifier    = new EmailNotifier(mailSender);
        loyaltyProgram   = new LoyaltyProgram(mailSender);
        invoiceGenerator = new InvoiceGenerator();

        venda = new Product("V01", "Venda", "Suavet", "Apositos", "desc", 1.0, 500.0, 10);
        order = new Order("Av. Siempreviva 742", new LocalPickUp(), paymentMethod, "cliente@mail.com");
        order.add(venda, 2);
    }

    // ── EMAIL NOTIFIER ──────────────────────────────────────────────

    @Test
    void emailEnviaMailCuandoPasaAConfirmado() {
        order.subscribe(emailNotifier);
        order.confirm(); // Draft → Confirmed → notifySuccessfulProgress
        verify(mailSender).enviarMail(eq("cliente@mail.com"), any(), any(), any());
    }

    @Test
    void emailEnviaMailCuandoPasaAEnviado() {
        order.subscribe(emailNotifier);
        order.confirm();
        order.start();
        order.send(); // InPreparation → Sent → notifySuccessfulProgress
        verify(mailSender, times(2)) // confirm + send
            .enviarMail(eq("cliente@mail.com"), any(), any(), any());
    }

    @Test
    void emailEnviaMailCuandoPasaAEntregado() {
        order.subscribe(emailNotifier);
        order.confirm();
        order.start();
        order.send();
        order.deliver(); // Sent → Delivered → notifySuccessfulProgress + notifyFinal
        verify(mailSender, times(3)) // confirm + send + deliver
            .enviarMail(eq("cliente@mail.com"), any(), any(), any());
    }

    @Test
    void emailNoEnviaMailCuandoPasaACancelado() {
        order.subscribe(emailNotifier);
        order.cancel(); // Draft → Canceled → solo notifyCanceled, no notifySuccessfulProgress
        verifyNoInteractions(mailSender);
    }

    @Test
    void emailNoEnviaMailCuandoPasaAEnPreparacion() {
        order.subscribe(emailNotifier);
        order.confirm(); // notifySuccessfulProgress → 1 mail
        order.start();   // Draft → InPreparation → sin notificación de email

        // solo 1 mail (el del confirm), start no dispara notifySuccessfulProgress
        verify(mailSender, times(1))
            .enviarMail(eq("cliente@mail.com"), any(), any(), any());
    }

    // ── LOYALTY PROGRAM ─────────────────────────────────────────────

    @Test
    void loyaltyEnviaCuponCuandoSeCancelaDesdeConfirmado() {
        order.subscribe(loyaltyProgram);
        order.confirm();
        order.cancel(); // Confirmed → Canceled → notifyCanceled
        verify(mailSender).enviarMail(eq("cliente@mail.com"), eq("CUPON DE DESCUENTO"), any(), any());
    }

    @Test
    void loyaltyEnviaCuponCuandoSeCancelaDesdeEnPreparacion() {
        order.subscribe(loyaltyProgram);
        order.confirm();
        order.start();
        order.cancel(); // InPreparation → Canceled → notifyCanceled
        verify(mailSender).enviarMail(eq("cliente@mail.com"), eq("CUPON DE DESCUENTO"), any(), any());
    }

    @Test
    void loyaltyEnviaCuponCuandoSeCancelaDesdeEnviado() {
        order.subscribe(loyaltyProgram);
        order.confirm();
        order.start();
        order.send();
        order.cancel(); // Sent → Canceled → notifyCanceled
        verify(mailSender).enviarMail(eq("cliente@mail.com"), eq("CUPON DE DESCUENTO"), any(), any());
    }

    @Test
    void loyaltyNoEnviaCuponSiElPedidoSeEntrega() {
        order.subscribe(loyaltyProgram);
        order.confirm();
        order.start();
        order.send();
        order.deliver(); // flujo exitoso — nunca notifyCanceled
        verifyNoInteractions(mailSender);
    }

    // ── INVOICE GENERATOR ────────────────────────────────────────────

    @Test
    void invoiceGeneratorCreaFacturaSoloCuandoSeEntrega() {
        order.subscribe(invoiceGenerator);
        order.confirm();
        order.start();
        order.send();
        order.deliver(); // Sent → Delivered → notifyFinal
        assertEquals(1, invoiceGenerator.getInvoices().size());
    }

    @Test
    void invoiceGeneratorNoGeneraFacturaSiSeCancela() {
        order.subscribe(invoiceGenerator);
        order.confirm();
        order.start();
        order.cancel(); // notifyCanceled, no notifyFinal
        assertEquals(0, invoiceGenerator.getInvoices().size());
    }

    @Test
    void invoiceGeneratorVerificaDatosDeFactura() {
        order.subscribe(invoiceGenerator);
        order.confirm();
        order.start();
        order.send();
        order.deliver();

        assertEquals(1, invoiceGenerator.getInvoices().size());
        assertEquals(1000.0, invoiceGenerator.getInvoices().get(0).getAmount()); // 2 * 500, envío = 0
        assertEquals("Av. Siempreviva 742", invoiceGenerator.getInvoices().get(0).getAddress());
    }

    @Test
    void unsubscribeDejaDeRecibirNotificaciones() {
        order.subscribe(emailNotifier);
        order.confirm();                  // 1 mail
        order.unsubscribe(emailNotifier);
        order.start();
        order.send();                     // sin emailNotifier ya → no debería enviar más

        verify(mailSender, times(1))
            .enviarMail(any(), any(), any(), any());
    }
}