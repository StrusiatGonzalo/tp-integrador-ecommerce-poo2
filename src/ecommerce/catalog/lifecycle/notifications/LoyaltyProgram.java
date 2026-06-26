package ecommerce.catalog.lifecycle.notifications;

import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;

public class LoyaltyProgram implements OrderObserver{ // Filelizacion
	private final MailSender mailSender; // interface de libreria que envia los mails.
	
	public LoyaltyProgram(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void onStateChanged(Order order, State prev, State next) {
		if (next.isCancelled()) {
			mailSender.enviarMail(order.getEmail(), "CUPON DE DESCUENTO", "Te dejamos un cupón de 5% en tu proxima compra", null);
		}
	}
}
