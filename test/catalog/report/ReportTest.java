package catalog.report;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ecommerce.catalog.Product;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentMethod;
import ecommerce.catalog.lifecycle.shippingmethods.LocalPickUp;
import ecommerce.catalog.reports.CsvExporter;
import ecommerce.catalog.reports.HtmlExporter;
import ecommerce.catalog.reports.PlainTextExporter;
import ecommerce.catalog.reports.ProductSalesReport;
import ecommerce.catalog.reports.ProductSalesReportGenerator;
import ecommerce.catalog.reports.ReportVisitor;
import ecommerce.catalog.reports.SalesEntry;

@ExtendWith(MockitoExtension.class)
class ReportTest {

    @Mock PaymentMethod paymentMethod;

    Product auriculares;
    Product cable;
    ProductSalesReportGenerator generator;

    @BeforeEach
    void setUp() {
        auriculares = new Product("E01", "Auriculares", "Marca", "Electronica", "desc", 0.3, 5000.0, 50);
        cable = new Product("E02", "Cable USB",   "Marca", "Electronica", "desc", 0.1, 1000.0, 50);
        generator = new ProductSalesReportGenerator();
    }

    // HELPER: arma y entrega un pedido completo
    private Order pedidoEntregado(Product producto, int cantidad) {
        Order order = new Order("Av. Test 1", new LocalPickUp(), paymentMethod, "test@mail.com");
        order.add(producto, cantidad);
        order.confirm(); // process() es final
        order.start();
        order.send();
        order.deliver(); // captura el snapshot del precio en este momento
        return order;
    }

    // GENERADOR
    @Test // genera un reporte de una orden
    void generatorCreaUnaEntradaPorProductoEntregado() {
        Order order = pedidoEntregado(auriculares, 3);
        ProductSalesReport report = generator.generate(List.of(order)); // una lista que contiene el pedido
        assertEquals(1, report.getEntries().size());
        
        SalesEntry entry = report.getEntries().get(0);
        assertEquals(auriculares, entry.getItem());
        assertEquals(3, entry.getUnitsSold());
        assertEquals(5000.0, entry.getAveragePricePaid());
    }

    @Test
    void generatorAcumulaUnidadesDeVariosPedidosDelMismoProducto() {
        Order pedido1 = pedidoEntregado(auriculares, 3);
        Order pedido2 = pedidoEntregado(auriculares, 2);
        ProductSalesReport report = generator.generate(List.of(pedido1, pedido2));
        assertEquals(1, report.getEntries().size());
        assertEquals(5, report.getEntries().get(0).getUnitsSold()); // 3 + 2
    }

    @Test
    void generatorOrdenarPorUnidadesVendidasDescendente() {
        Order pedido1 = pedidoEntregado(auriculares, 2);
        Order pedido2 = pedidoEntregado(cable, 5); // cable tiene mas u 
        ProductSalesReport report = generator.generate(List.of(pedido1, pedido2));
        assertEquals(cable, report.getEntries().get(0).getItem()); // el más vendido primero
        assertEquals(auriculares, report.getEntries().get(1).getItem());
    }

    @Test
    void generatorCalculaPromedioCorrectamenteSiElPrecioCambia() {
        // pedido 1 - 2 auriculares sin descuento se vendio a 5000
        Order pedido1 = pedidoEntregado(auriculares, 2);

        // el precio baja despues de la primera venta
        auriculares.setDiscountRate(0.2); // ahora auriculares salen 4000

        // pedido 2 - 2 auriculares con descuento se vendieron a 4000
        Order pedido2 = pedidoEntregado(auriculares, 2);

        ProductSalesReport report = generator.generate(List.of(pedido1, pedido2));
        SalesEntry entry = report.getEntries().get(0);
        assertEquals(4, entry.getUnitsSold());
        // refleja los precios reales en cada venta
        assertEquals(4500.0, entry.getAveragePricePaid(), 0.01);
    }

    // VISITOR
    @Test
    void acceptDelegaEnElVisitorCorrectamente() {
        Order order = pedidoEntregado(auriculares, 1);
        ProductSalesReport report = generator.generate(List.of(order));

        ReportVisitor mockVisitor = mock(ReportVisitor.class);
        when(mockVisitor.visit(report)).thenReturn("resultado");

        assertEquals("resultado", report.accept(mockVisitor));
        verify(mockVisitor).visit(report);
    }

    @Test
    void plainTextExporter() {
        Order order = pedidoEntregado(auriculares, 3);
        String output = generator.generate(List.of(order)).accept(new PlainTextExporter());
        assertTrue(output.contains("Auriculares"));
        assertTrue(output.contains("3"));
    }

    @Test
    void csvExporter() {
        Order order = pedidoEntregado(cable, 2);
        String output = generator.generate(List.of(order)).accept(new CsvExporter());
        assertTrue(output.startsWith("nombre,unidades_vendidas,precio_promedio"));
        assertTrue(output.contains("Cable USB"));
        assertTrue(output.contains("2"));
    }

    @Test
    void htmlExporter() {
        Order order = pedidoEntregado(auriculares, 1);
        String output = generator.generate(List.of(order)).accept(new HtmlExporter());
        assertTrue(output.contains("<table>"));
        assertTrue(output.contains("<tr>"));
        assertTrue(output.contains("Auriculares"));
        assertTrue(output.contains("</table>"));
    }
}