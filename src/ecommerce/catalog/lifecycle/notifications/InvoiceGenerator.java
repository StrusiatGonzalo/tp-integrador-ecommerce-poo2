package ecommerce.catalog.lifecycle.notifications;

import java.util.ArrayList;
import java.util.List;

import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;


public class InvoiceGenerator implements OrderObserver{ // Un generador de facturas
	private List<Invoice> invoices; // lista de facturas.

	public InvoiceGenerator() {
		this.invoices = new ArrayList<>();
	}
	
	@Override
	public void notifyFinal(Order order) {
		invoices.add(new Invoice(order.getTotalToPay(), order.getAddress()));
	}
	
	public List<Invoice> getInvoices(){
		return invoices;
	}
}
