package ecommerce.catalog.lifecycle.notifications;

import java.util.ArrayList;
import java.util.List;

import ecommerce.catalog.lifecycle.Delivered;
import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;
import ecommerce.catalog.lifecycle.paymentmethods.PaymentReceipt;

public class InvoiceGenerator implements OrderObserver{
	private List<PaymentReceipt> receipts;

	public InvoiceGenerator() {
		this.receipts = new ArrayList<>();
	}
	
	@Override
	public void onStateChanged(Order order, State prev, State next) {
		if (!applies(next)) {
			return ;
		}
		receipts.add(order.getPaymentReceipt());
	}
	
	public boolean applies(State next) {
		return next instanceof Delivered;
	}

}
