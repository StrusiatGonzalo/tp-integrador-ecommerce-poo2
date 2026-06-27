package ecommerce.catalog.reports;

import java.util.*;
import java.util.stream.Collectors;

import ecommerce.catalog.CatalogItem;
import ecommerce.catalog.lifecycle.Order;

public class ProductSalesReportGenerator {

    public ProductSalesReport generate(List<Order> orders) {
        Map<CatalogItem, Integer> unitsSold = new HashMap<>(); // unidades del producto vendidas
        Map<CatalogItem, Double> revenue = new HashMap<>(); // plata ganada con estas ventas 

        orders.stream()
              .filter(order -> order.getState().isFinal()) // final es ENTREGADO 
              .flatMap(order -> order.getItems().stream()) // transformar y aplanar - nos devuelve todos los items de los pedidos juntos
              .forEach(oi -> { // por cada OrderItem del pedido
                  unitsSold.merge(oi.getItem(), oi.getQuantity(), Integer::sum); // hace un merge entre los productos y las cantidades
                  revenue.merge(oi.getItem(), oi.getPriceAtConfirmation() * oi.getQuantity(), Double::sum); // merge entre cada item y va sumando los precios
              });

        List<SalesEntry> entries = unitsSold.keySet().stream() // var local, inicialmete lo que hace es guardar un set de claves del map (liste de catalog items) y la transforma en un stream 
        		// este map, por cada item instancia una nueva entrada de venta, 
        		// le setea el item(catalogItem), las unidades vendidas y el promedio de todas estas ventas
        		.map(item -> new SalesEntry(item, unitsSold.get(item), revenue.get(item) / unitsSold.get(item)))
        		// comparator es generico entonces hay que explicitar el tipo, es de tipo SalesEntry
        		// esto ordena en base a las unidades vedidas (lo pide el enunciado) 
            	.sorted(Comparator.<SalesEntry>comparingInt(s -> s.getUnitsSold()).reversed()) 
            	// finalmente lo transforma en lista con toList()
            	.collect(Collectors.toList());

        return new ProductSalesReport(entries); // retorna la venta
    }
}
