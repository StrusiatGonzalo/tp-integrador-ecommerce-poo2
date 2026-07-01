package catalog.lifecycle.shipping;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentMethod;
import ecommerce.catalog.lifecycle.shippingmethods.ExpressShipping;
import ecommerce.catalog.lifecycle.shippingmethods.ExpressShippingAPI;
import ecommerce.catalog.lifecycle.shippingmethods.LocalPickUp;
import ecommerce.catalog.lifecycle.shippingmethods.StandardShipping;
import ecommerce.catalog.lifecycle.shippingmethods.StandardShippingAPI;

@ExtendWith(MockitoExtension.class)
class ShippingTest {

    @Mock ExpressShippingAPI expressApi;
    @Mock StandardShippingAPI standardApi;
    @Mock PaymentMethod paymentMethod;
    
    ExpressShipping envioExpress;
    StandardShipping envioStandard;
    LocalPickUp localPickup;

    Product producto;
    Order orderExpress;
    Order orderStandard;
    Order orderPickup;

    @BeforeEach
    void setUp() {
        producto = new Product("P01", "Producto", "Marca", "Cat", "desc", 2.5, 1000.0, 10);
        
        envioExpress = new ExpressShipping(expressApi);
        envioStandard = new StandardShipping(standardApi);
        localPickup = new LocalPickUp();

        orderExpress = new Order("Av. Test 1", envioExpress, paymentMethod, "test@mail.com");
        orderStandard = new Order("Av. Test 2", envioStandard, paymentMethod, "test@mail.com");
        orderPickup = new Order("Av. Test 3", localPickup, paymentMethod, "test@mail.com");
    }

    // EXPRESS SHIPPING
    @Test
    void expressShippingConApi() {
        orderExpress.add(producto, 2);
        when(expressApi.calculateCost(2000.0)).thenReturn(500.0);
        assertEquals(500.0, orderExpress.getShippingCost());
        assertEquals("1 día hábil", envioExpress.waitingDays(orderExpress));
    }

    // STANDARD SHIPPING
    @Test
    void standardShippingDelegaCostoConElPesoYLaDireccion() {
        orderStandard.add(producto, 4);
        when(standardApi.estimateCost(10.0, "Av. Test 2")).thenReturn(300.0);
        assertEquals(300.0, orderStandard.getShippingCost());
        assertEquals("5 a 7 días hábiles", envioStandard.waitingDays(orderStandard));
    }

    // LOCAL PICKUP
    @Test
    void localPickUpCostEsSiempreCero() {
        orderPickup.add(producto, 5);
        assertEquals(0.0, orderPickup.getShippingCost());
    }

    @Test
    void localPickUpWaitingDaysCuandoHayStockSuficiente() {
        orderPickup.add(producto, 5); // hay 10, alcanza
        assertEquals("hasta 3 días hábiles", localPickup.waitingDays(orderPickup));
    }

    @Test
    void localPickUpWaitingDaysCuandoNoHayStockSuficiente() {
        orderPickup.add(producto, 15); // hay 10, no alcanza
        assertEquals("a partir de 3 días hábiles", localPickup.waitingDays(orderPickup));
    }
}
