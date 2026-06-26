package ecommerce.catalog.lifecycle.notifications;

import ecommerce.catalog.lifecycle.Order;
import ecommerce.catalog.lifecycle.State;

public class EmailNotifier implements OrderObserver{ // Un notificar via mail
	private final MailSender mailSender; // interface de libreria que envia los mails.
	
	public EmailNotifier(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	@Override
	public void onStateChanged(Order order, State prev, State next) {
		if (next.isSuccessfulProgress()) {
			mailSender.enviarMail(order.getEmail(), "Tu pedido cambio de estado", "Ahora esta: " + next.getName(), null);
		}		
	}
}
