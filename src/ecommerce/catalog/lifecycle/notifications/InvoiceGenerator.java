package ecommerce.catalog.lifecycle.notifications;

import java.util.ArrayList;
import java.util.List;

import ecommerce.catalog.lifecycle.Delivered;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentReceipt;

public class InvoiceGenerator implements OrderObserver{ // Un generador de facturas
	private List<Invoice> invoices; // lista de facturas.

	public InvoiceGenerator() {
		this.invoices = new ArrayList<>();
	}
	
	@Override
	public void onStateChanged(Order order, State prev, State next) {
		if (!applies(next)) {
			return ;
		}
		invoices.add(new Invoice(order.getTotalToPay(), order.getAddress()));
	}
	
	private boolean applies(State next) {
		return next instanceof Delivered;
	}
	
	public List<Invoice> getInvoices(){
		return invoices;
	}
}
